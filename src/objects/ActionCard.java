package objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * The type Action card.
 */
public class ActionCard extends Card {

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
        switch (type) {
            case FIRE:
                return useFireCard(board, x, y);
            case ICE:
                return useIceCard(board, x, y);
            case BACKTRACK:
                return useBackTrackCard(board, x, y);
            case DOUBLE_MOVE:
                return useDoubleMove(board, x, y);

        }
        return true;
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
                tile.setOnFire();
            }
        }
        return true;
    }


    private boolean useIceCard(Board board, int x, int y) {
        ArrayList<FloorCard> tiles = getAreaOfEffect(board, x, y);

        for (FloorCard tile : tiles) {
            board.getFrozenTiles().add(tile);
            tile.setOnIce();
        }
        return true;
    }

    private boolean useBackTrackCard(Board board, int x, int y) {
        if (!board.checkPlayerPosition(x, y)) {
            return false;
        }

        PlayerController player = board.getPlayer(x, y);

        if (player.isBackTracked() == true) {
            return false;
        } else {
            board.changePlayerPosition(player,
                    player.getLastThree().getFirst()[0], player.getLastThree().getFirst()[1]);
            player.setBackTracked(true);
        }
        return true;
    }

    private boolean useDoubleMove(Board board, int x, int y) {
        if (!board.checkPlayerPosition(x, y)) {
            return false;
        }
        board.getPlayer(x, y).setDoubleMove(true);
        return true;
    }

    private ArrayList<FloorCard> getAreaOfEffect(Board board, int x, int y) {
        ArrayList<FloorCard> area = new ArrayList<>();

        if (x == 0 && y == 0) { //left upper corner
            area.add(board.getTile(x, y));
            area.add(board.getTile(x + 1, y));
            area.add(board.getTile(x, y - 1));
            area.add(board.getTile(x + 1, y - 1));
        } else if ((x == board.getWidth() - 1) && y == 0) { //right upper corner
            area.add(board.getTile(x, y));
            area.add(board.getTile(x - 1, y));
            area.add(board.getTile(x - 1, y - 1));
            area.add(board.getTile(x + 1, y - 1));
        } else if (x == 0 && (y == board.getHeight() - 1)) { //left bottom corner
            area.add(board.getTile(x, y));
            area.add(board.getTile(x, y + 1));
            area.add(board.getTile(x + 1, y + 1));
            area.add(board.getTile(x + 1, y));
        } else if (x == 0 && (y == board.getWidth() - 1)) { //right bottom corner
            area.add(board.getTile(x, y));
            area.add(board.getTile(x - 1, y + 1));
            area.add(board.getTile(x, y + 1));
            area.add(board.getTile(x - 1, y));
        } else if (y == 0) { //upper middle
            area.add(board.getTile(x, y));
            area.add(board.getTile(x - 1, y));
            area.add(board.getTile(x - 1, y - 1));
            area.add(board.getTile(x, y - 1));
            area.add(board.getTile(x + 1, y - 1));
            area.add(board.getTile(x + 1, y));
        } else if (x == 0 && ((y != 0) || y != board.getHeight() - 1)) { //left middle
            area.add(board.getTile(x, y));
            area.add(board.getTile(x, y + 1));
            area.add(board.getTile(x + 1, y + 1));
            area.add(board.getTile(x + 1, y));
            area.add(board.getTile(x + 1, y - 1));
            area.add(board.getTile(x, y - 1));
        } else if ((x == board.getWidth() - 1) && ((y != 0) || (y != board.getHeight() - 1))) { //right middle
            area.add(board.getTile(x, y));
            area.add(board.getTile(x, y + 1));
            area.add(board.getTile(x - 1, y + 1));
            area.add(board.getTile(x - 1, y));
            area.add(board.getTile(x - 1, y - 1));
            area.add(board.getTile(x, y - 1));
        } else if ((y == board.getHeight() - 1) && ((x != board.getWidth() - 1) || (x != 0))) { //bottom middle
            area.add(board.getTile(x, y));
            area.add(board.getTile(x + 1, y));
            area.add(board.getTile(x + 1, y + 1));
            area.add(board.getTile(x, y + 1));
            area.add(board.getTile(x - 1, y + 1));
            area.add(board.getTile(x - 1, y));
        } else {
            area.add(board.getTile(x, y));
            area.add(board.getTile(x + 1, y));
            area.add(board.getTile(x - 1, y));
            area.add(board.getTile(x, y + 1));
            area.add(board.getTile(x, y - 1));
            area.add(board.getTile(x + 1, y - 1));
            area.add(board.getTile(x + 1, y + 1));
            area.add(board.getTile(x - 1, y - 1));
            area.add(board.getTile(x - 1, y + 1));
        }
        return area;
    }

    public boolean canBeUsed() {
        return this.canBeUsed;
    }

    public void setCanBeUsed() {
        this.canBeUsed = true;
    }
}

