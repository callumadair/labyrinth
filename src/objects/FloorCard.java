package objects;
/**
 * This class represents the different floor tiles of the game.
 * @author Maha Malik
 * @verson 1.8
 */

import javafx.scene.image.Image;

public class FloorCard extends Card{

    public static final int TILE_SIZE = 61;

    private int x, y;
    private FloorType type;
    private boolean isFixed;
    private FloorTileState state = FloorTileState.NORMAL;
    private int[] openings = new int[4];
    private int rotation;

    public enum FloorType {
        STRAIGHT, CORNER, T_SHAPED, GOAL;
    }

    public enum FloorTileState{
        FIRE, FROZEN, NORMAL;
    }

    public FloorCard (String type, int rotation) {
        switch (type) {
            case "STRAIGHT" :
                this.type = FloorCard.STRAIGHT;
                image straight = new image(StraightPath);
                break;
            case "CORNER" :
                this.type = FloorCard.CORNER;
                image corner = new image(CornerPath);
                break;
            case "T_SHAPED" :
                this.type = FloorCard.T_SHAPED;
                image tshaped = new image(TShapedPath);
                break;
            case "GOAL" :
                this.type = FloorCard.GOAL;
                image goal = new image(GoalPATH);
                break;
        }

        this.rotation = rotation;
    }

    public FloorCard () {
        isFixed = true;
    }

    public FloorTileState state() {
        return state;
    }

    public void setOnFire() {
        this.state = FloorTileState.FIRE;
    }

    public void setOnIce() {
        this.state = FloorTileState.FROZEN;
    }

    public void setNoState() {
        this.state = FloorTileState.NORMAL;
    }

    public void useCard() {
    }

    public boolean checkGoal() {
        return type == FloorType.GOAL;
    }

    public boolean isFixed() {
        return isFixed;
    }

    public void setFixed(boolean fixedTiles) {
        isFixed = fixedTiles;
    }

    public int getRotation (int rotation) {
        return rotation;
    }

    public void setRotation () {
        switch (rotation) {
            case STRAIGHT:
                if (rotation = 0 || 180) {
                    setOpenings(1,0,1,0);
                } else if (rotation = 90 || 270) {
                    setOpenings(0,1,0,1);
                }
                break;
            case CORNER:
                if (rotation = 0) {
                    setOpenings(0,1,1,0);
                } else if (rotation = 90) {
                    setOpenings(0,0,1,1);
                } else if (rotation = 180) {
                    setOpenings(1,0,0,1);
                } else (rotation = 270) {
                    setOpenings(1, 1, 0, 0);
                }
                break;
            case T_SHAPED:
                if (rotation = 0) {
                    setOpenings(1,0,1,1);
                } else if (rotation = 90) {
                    setOpenings(1,1,0,1);
                } else if (rotation = 180) {
                    setOpenings(1,1,1,0);
                } else (rotation = 270) {
                    setOpenings(0, 1, 1, 1);
                }
                break;
        }
    }

    public Image getImage(){
        return image;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void setOpenings(int left, int top, int right, int bottom) {
        openings [0] = left;
        openings [1] = top;
        openings [2] = right;
        openings [3] = bottom;
    }
}
