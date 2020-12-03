package objects;

import java.util.ArrayList;

/**
 * The type Action card.
 */
public class ActionCard extends Card {

    /**
     * The constant CARD_SIZE.
     */
    public static final int CARD_SIZE = 54;

    private ActionCardType type;

    private String fireImagePath = "resources/ROAD-Cardfire.png";
    private String iceImagePath = "resources/ROAD-CardIce.png";
    private String backtrackImagePath = "resources/ROAD-CardReverse.png";
    private String doubleMoveImagePath = "resources/ROAD-CardDouble.png";

    private boolean canBeUsed = false;

    /**
     * The enum Action card type.
     */
    enum ActionCardType {
        FIRE,
        ICE,
        BACKTRACK,
        DOUBLE_MOVE;
    }

    /**
     * Instantiates a new Action card.
     *
     * @param type the type
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

    public boolean useCard(Board board, int x, int y) {
        if(this.canBeUsed()){
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


    private boolean useFireCard(Board board, int x, int y) {
        ArrayList<FloorCard> tiles = getAreaOfEffect(board, x, y);

        boolean playerIsInRange = false;

        for (FloorCard tile : tiles) {
            if (board.checkPlayerPosition(tile.getX(), tile.getY())) {
                playerIsInRange = true;
            }
        }

        if (playerIsInRange == true) {
            return false;
        } else {
            for (FloorCard tile : tiles) {
                board.getTilesOnFire().add(tile);
                tile.setOnFire(fireEffectTimer(board));
            }
        }
        return true;
    }


    private boolean useIceCard(Board board, int x, int y) {
        ArrayList<FloorCard> tiles = getAreaOfEffect(board, x, y);

        for (FloorCard tile : tiles) {
            board.getFrozenTiles().add(tile);
            tile.setOnIce(iceEffectTimer(board));
        }
        return true;
    }

    private boolean useBackTrackCard(Board board, int x, int y) {
        if (!board.checkPlayerPosition(x, y)) {
            return false;
        }

        PlayerController player = board.getPlayer(x, y);

        if(player.isCurrentPlayer()){
            return false;
        }

        if(player.getLastThree().size() < 3){
            return false;
        }

        if (player.isBackTracked() == true) {
            return false;
        } else {
            int[] temp = player.getLastThree().getFirst();
            player.getLastThree().removeFirst();
            if(board.getTile(player.getLastThree().getFirst()[0], player.getLastThree().getFirst()[1]).isOnFire()){
                player.getLastThree().addFirst(temp);
                return false;
            } else {
                if(board.getTile(temp[0], temp[1]).isOnFire()){
                    player.setBackTracked(true);
                    player.movePlayer(player.getLastThree().getFirst()[0], player.getLastThree().getFirst()[1]);
                    return true;
                } else {
                    player.setBackTracked(true);
                    player.movePlayer(temp[0], temp[1]);
                    return true;
                }
            }
        }
    }

    private boolean useDoubleMove(Board board, int x, int y) {
        if (!board.checkPlayerPosition(x, y)) {
            return false;
        }

        PlayerController player = board.getPlayer(x, y);

        if(!player.isCurrentPlayer()){
            return false;
        }

        player.setDoubleMove(true);
        return true;
    }

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

    private int fireEffectTimer(Board board){
        return (board.getPlayers().size() * 2);
    }

    private int iceEffectTimer(Board board){
        return board.getPlayers().size();
    }

    /**
     * Can be used boolean.
     *
     * @return the boolean
     */
    public boolean canBeUsed() {
        return this.canBeUsed;
    }

    /**
     * Sets can be used.
     */
    public void setCanBeUsed() {
        this.canBeUsed = true;
    }
}

