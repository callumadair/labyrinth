package objects;

import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * The type Action card.
 */
public class ActionCard extends Card {

    private ActionCardType type;
    private Image image;
    private FloorCard floor;
    private PlayerController player;

    private String fireImagePath;
    private String iceImagePath;
    private String backtrackImagePath;
    private String doubleMoveImagePath;


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
                image = new Image(fireImagePath);
                break;
            case "ICE":
                this.type = ActionCardType.ICE;
                image = new Image(iceImagePath);
                break;
            case "BACKTRACK":
                this.type = ActionCardType.BACKTRACK;
                image = new Image(backtrackImagePath);
                break;
            case "DOUBLE_MOVE":
                this.type = ActionCardType.DOUBLE_MOVE;
                image = new Image(doubleMoveImagePath);
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

        if (playerIsInRange = true) {
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
                tile.setOnIce();
            }
            return true;
    }


    private boolean useBackTrackCard(Board board, int x, int y) {
        board.changePlayerPosition(player, player.getLastThree().getLast().getX(), player.getLastThree().getLast().getY());
        return false;
    }

    private boolean useDoubleMove(Board board, int x, int y) {
        board.changePlayerPosition(player, x, y);
        board.changePlayerPosition(player, x, y);
        return true;
    }

    private ArrayList<FloorCard> getAreaOfEffect(Board board, int x, int y){
            ArrayList<FloorCard> area = new ArrayList<>();

            if(x == 0 && y == 0){ //left upper corner
                area.add(board.getTile(x , y));
                area.add(board.getTile(x + 1, y));
                area.add(board.getTile(x , y - 1));
                area.add(board.getTile(x + 1 , y - 1));
            }
            else if((x == board.getWidth() - 1) && y == 0){ //right upper corner
            area.add(board.getTile(x , y));
            area.add(board.getTile(x - 1, y));
            area.add(board.getTile(x - 1 , y - 1));
            area.add(board.getTile(x + 1 , y - 1));
            }
            else if( x == 0 && (y == board.getHeight() - 1)){ //left bottom corner
            area.add(board.getTile(x , y));
            area.add(board.getTile(x , y + 1));
            area.add(board.getTile(x + 1 , y + 1));
            area.add(board.getTile(x + 1 , y));
            }
            else if( x == 0 && (y == board.getWidth() - 1)){ //right bottom corner
                area.add(board.getTile(x , y));
                area.add(board.getTile(x - 1, y + 1));
                area.add(board.getTile(x , y + 1));
                area.add(board.getTile(x - 1 , y));
            }
            else if(y == 0){ //upper middle
                area.add(board.getTile(x , y));
                area.add(board.getTile(x - 1, y));
                area.add(board.getTile(x - 1, y - 1));
                area.add(board.getTile(x , y - 1));
                area.add(board.getTile(x + 1, y - 1));
                area.add(board.getTile(x + 1 , y));
            }
            else if(x == 0 && ((y != 0) || y != board.getHeight() - 1) ){ //left middle
                area.add(board.getTile(x , y));
                area.add(board.getTile(x , y + 1));
                area.add(board.getTile(x + 1 , y + 1));
                area.add(board.getTile(x + 1, y));
                area.add(board.getTile(x + 1 , y - 1));
                area.add(board.getTile(x , y - 1));
            }
            else if((x == board.getWidth() - 1) && ((y != 0 ) || (y != board.getHeight() - 1))){ //right middle
                area.add(board.getTile(x , y));
                area.add(board.getTile(x, y + 1));
                area.add(board.getTile(x - 1, y + 1));
                area.add(board.getTile(x - 1, y));
                area.add(board.getTile(x - 1, y - 1));
                area.add(board.getTile(x , y - 1));
            }
            else if((y == board.getHeight() - 1) && ((x != board.getWidth() - 1) || (x != 0))){ //bottom middle
                area.add(board.getTile(x , y));
                area.add(board.getTile(x + 1, y));
                area.add(board.getTile(x + 1, y + 1));
                area.add(board.getTile(x, y + 1));
                area.add(board.getTile(x - 1, y + 1));
                area.add(board.getTile(x - 1, y ));
            }
            else {
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

}

