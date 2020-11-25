
package objects;

import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * The type Action card.
 */
public class ActionCard extends Card{

    private ActionCardType type;
    private Image image;
    private FloorCard floor;

    private String fireImagePath;
    private String iceImagePath;
    private String backtrackImagePath;
    private String doubleMoveImagePath;


    /**
     * The enum Action card type.
     */
    enum ActionCardType {
        /**
         * Fire action card type.
         */
        FIRE ,
        /**
         * Ice action card type.
         */
        ICE ,
        /**
         * Backtrack action card type.
         */
        BACKTRACK ,
        /**
         * Double move action card type.
         */
        DOUBLE_MOVE;
}

    /**
     * Instantiates a new Action card.
     *
     * @param type the type
     */
    public ActionCard(String type){
        switch(type) {
            case "FIRE" :
                this.type = ActionCardType.FIRE;
                image = new Image(fireImagePath);
                break;
            case "ICE" :
                this.type = ActionCardType.ICE;
                image = new Image(iceImagePath);
                break;
            case "BACKTRACK" :
                this.type = ActionCardType.BACKTRACK;
                image = new Image(backtrackImagePath);
                break;
            case "DOUBLE_MOVE" :
                this.type = ActionCardType.DOUBLE_MOVE;
                image = new Image(doubleMoveImagePath);
                break;
        }
 }
 public void useCard(Board board, int x, int y){
        switch(type){
            case FIRE:
                useFireCard(board, x , y);
                break;
            case ICE:
                useIceCard(board, x , y);
                break;
            case BACKTRACK:
                useBackTrackCard(board, x , y);
                break;
            case DOUBLE_MOVE:
                useDoubleMove(board, x , y);
                break;
        }
 }


    private void useFireCard(Board board, int x, int y) {
        ArrayList<FloorCard> tiles = getAreaOfEffect(board, x, y);

        for(FloorCard tile : tiles){
                tile.setOnFire();
        }

    }

    private void useIceCard(Board board, int x, int y) {
        ArrayList<FloorCard> tiles = getAreaOfEffect(board, x, y);

        for(FloorCard tile : tiles){
            tile.setOnIce();
        }
    }
    private void useBackTrackCard(Board board, int x, int y) {

    }

    private void useDoubleMove(Board board, int x, int y) {

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
