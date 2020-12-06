package objects;

import java.util.ArrayList;

/**
 * This class represents the Action Card, allowing it to be used on a game
 * board and enabling the features associated with each type.
 *
 * @author Stefani Dimitrova
 * @author Kacper Lisikiewicz
 */
public class ActionCard extends Card {

    public static final int CARD_SIZE = 54;
    private final String fireImagePath = "resources/ROAD-Cardfire.png";
    private final String iceImagePath = "resources/ROAD-CardIce.png";
    private final String backtrackImagePath = "resources/ROAD-CardReverse.png";
    private final String doubleMoveImagePath = "resources/ROAD-CardDouble.png";

    private ActionCardType type;
    private boolean canBeUsed = false;

    /**
     * The enum Action card type.
     */
    public enum ActionCardType {
        FIRE,
        ICE,
        BACKTRACK,
        DOUBLE_MOVE
    }

    /**
     * Instantiates a new Action card.
     *
     * @param type the type of the card.
     */
    public ActionCard(String type) {
        switch (type) {
            case "FIRE":
                this.type = ActionCardType.FIRE;
                this.setImage(fireImagePath);
                break;
            case "ICE":
                this.type = ActionCardType.ICE;
                this.setImage(iceImagePath);
                break;
            case "BACKTRACK":
                this.type = ActionCardType.BACKTRACK;
                this.setImage(backtrackImagePath);
                break;
            case "DOUBLE_MOVE":
                this.type = ActionCardType.DOUBLE_MOVE;
                this.setImage(doubleMoveImagePath);
                break;
        }
    }

    /**
     * Use the card.
     *
     * @param board the board.
     * @param x     the x coordinate of the tile.
     * @param y     the y coordinate of the tile.
     */
    public boolean useCard(Board board, int x, int y) {
        if (this.canBeUsed()) {
            switch (type) {
                case FIRE:
                    return useFireCard(board, x, y);
                case ICE:
                    return useIceCard(board, x, y);
                case BACKTRACK:
                    return useBackTrackCard(board, x, y);
                case DOUBLE_MOVE:
                    return useDoubleMove(board, x, y);
                default:
                    return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Use fire card.
     *
     * @param board the board.
     * @param x     the x coordinate of the tile.
     * @param y     the y coordinate of the tile.
     * @return true if the card can be used on that position, false otherwise.
     */
    private boolean useFireCard(Board board, int x, int y) {
        ArrayList<FloorCard> tiles = getAreaOfEffect(board, x, y);

        boolean playerIsInRange = false;

        for (FloorCard tile : tiles) {
            if (board.checkPlayerPosition(tile.getX(), tile.getY())) {
                playerIsInRange = true;
            }
        }

        if (playerIsInRange) {
            return false;
        } else {
            for (FloorCard tile : tiles) {
                board.getTilesOnFire().add(tile);
                tile.setOnFire(fireEffectTimer(board));
            }
        }
        return true;
    }

    /**
     * Use ice card.
     *
     * @param board the board.
     * @param x     the x coordinate of the tile.
     * @param y     the y coordinate of the tile.
     * @return true if the card can be used on that position, false otherwise.
     */
    private boolean useIceCard(Board board, int x, int y) {
        ArrayList<FloorCard> tiles = getAreaOfEffect(board, x, y);

        for (FloorCard tile : tiles) {
            board.getFrozenTiles().add(tile);
            tile.setOnIce(iceEffectTimer(board));
        }
        return true;
    }

    /**
     * Use fire card.
     *
     * @param board the board.
     * @param x     the x coordinate of the tile.
     * @param y     the y coordinate of the tile.
     * @return true if the card can be used.
     */
    private boolean useBackTrackCard(Board board, int x, int y) {
        final int BACKTRACK_LENGTH = 3;
        if (!board.checkPlayerPosition(x, y)) {
            return false;
        }

        PlayerController player = board.getPlayer(x, y);

        if (player.isCurrentPlayer()) {
            return false;
        }

        if (player.getLastThree().size() < BACKTRACK_LENGTH) {
            return false;
        }

        if (player.isBackTracked()) {
            return false;
        } else {
            int[] temp = player.getLastThree().getFirst();
            player.getLastThree().removeFirst();
            if (board.getTile(player.getLastThree().getLast()[0],
                    player.getLastThree().getLast()[1]).isOnFire()) {
                player.getLastThree().addFirst(temp);
                return false;
            } else {
                if (board.getTile(player.getLastThree().getFirst()[0],
                        player.getLastThree().getFirst()[1]).isOnFire() &&
                        !board.checkPlayerPosition(
                                player.getLastThree().getLast()[0],
                                player.getLastThree().getLast()[1])) {
                    player.setBackTracked(true);
                    player.movePlayer(player.getLastThree().getLast()[0],
                            player.getLastThree().getLast()[1]);
                    return true;
                } else {
                    if (board.checkPlayerPosition(
                            player.getLastThree().getFirst()[0],
                            player.getLastThree().getFirst()[1]) &&
                            !board.checkPlayerPosition(
                                    player.getLastThree().getLast()[0],
                                    player.getLastThree().getLast()[1])) {
                        player.setBackTracked(true);
                        player.movePlayer(player.getLastThree().getLast()[0],
                                player.getLastThree().getLast()[1]);
                        return true;
                    } else if (!board.checkPlayerPosition(
                            player.getLastThree().getLast()[0],
                            player.getLastThree().getLast()[1])) {
                        player.setBackTracked(true);
                        player.movePlayer(player.getLastThree().getFirst()[0]
                                , player.getLastThree().getFirst()[1]);
                        return true;
                    } else {
                        player.getLastThree().addFirst(temp);
                        return false;
                    }
                }
            }
        }
    }

    /**
     * Use fire card.
     *
     * @param board the board.
     * @param x     the x coordinate of the tile.
     * @param y     the y coordinate of the tile.
     * @return true if the card can be used.
     */
    private boolean useDoubleMove(Board board, int x, int y) {
        if (!board.checkPlayerPosition(x, y)) {
            return false;
        }

        PlayerController player = board.getPlayer(x, y);

        if (!player.isCurrentPlayer()) {
            return false;
        }

        player.setDoubleMove(true);
        return true;
    }

    /**
     * Gets the area of effect.
     *
     * @param board the board.
     * @param x     the x coordinate of the tile.
     * @param y     the y coordinate of the tile.
     * @return the FloorCards inside the area of effect.
     */
    private ArrayList<FloorCard> getAreaOfEffect(Board board, int x, int y) {
        ArrayList<FloorCard> area = new ArrayList<>();

        if (board.checkBoardBoundary(x - 1, y)) {
            area.add(board.getTile(x - 1, y));
        }
        if (board.checkBoardBoundary(x + 1, y)) {
            area.add(board.getTile(x + 1, y));
        }
        if (board.checkBoardBoundary(x, y + 1)) {
            area.add(board.getTile(x, y + 1));
        }
        if (board.checkBoardBoundary(x, y - 1)) {
            area.add(board.getTile(x, y - 1));
        }
        if (board.checkBoardBoundary(x + 1, y + 1)) {
            area.add(board.getTile(x + 1, y + 1));
        }
        if (board.checkBoardBoundary(x - 1, y - 1)) {
            area.add(board.getTile(x - 1, y - 1));
        }
        if (board.checkBoardBoundary(x - 1, y + 1)) {
            area.add(board.getTile(x - 1, y + 1));
        }
        if (board.checkBoardBoundary(x + 1, y - 1)) {
            area.add(board.getTile(x + 1, y - 1));
        }

        area.add(board.getTile(x, y));

        return area;
    }

    /**
     * Sets the fire effect timer.
     *
     * @param board the board
     * @return int
     */
    private int fireEffectTimer(Board board) {
        return (board.getPlayers().size() * 2);
    }

    /**
     * Sets the ice effect timer.
     *
     * @param board the board
     * @return int
     */
    private int iceEffectTimer(Board board) {
        return board.getPlayers().size();
    }

    /**
     * Checks if a card can be used.
     *
     * @return true if the card can be used.
     */
    public boolean canBeUsed() {
        return this.canBeUsed;
    }

    /**
     * Sets a card to be used to true.
     */
    public void setCanBeUsed() {
        this.canBeUsed = true;
    }

    /**
     * Gets the type of the card.
     *
     * @return the type of the card.
     */
    public ActionCardType getType() {
        return type;
    }
}

