package objects;

import javafx.scene.image.Image;

public class FloorCard extends Card{

    public static final int TILE_SIZE = 61;

    private int x, y;
    private FloorType type;
    private boolean isMoveable;
    private Image image;

    private FloorTileState state = FloorTileState.NORMAL;
    private int[] openings = new int[4];

    public enum FloorType {
        STRAIGHT, CORNER, T_SHAPED, GOAL;
    }

    public enum FloorTileState{
        FIRE, FROZEN, NORMAL;
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

    public boolean isMoveable() {
        return isMoveable;
    }

    public void setMoveable(boolean moveable) {
        isMoveable = moveable;
    }

    public void rotateShape() {
        //change rotation base on 0,90,180,270
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
