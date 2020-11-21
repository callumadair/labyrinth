package objects;

import javafx.scene.image.Image;

public class FloorCard extends Card{

    public static final int TILE_SIZE = 61;

    private int x, y;
    private FloorType type;
    private boolean isMoveable;
    private boolean isOnFire;
    private Image image;

    public FloorCard(Image image){
        this.image = image;
    }

    public enum FloorType {
        STRAIGHT, CORNER, T_SHAPED, GOAL;
    }

    public void useCard() {
    }

    public boolean checkGoal() {
        return false;
    }

    public boolean isMoveable() {
        return isMoveable;
    }

    public void setMoveable(boolean moveable) {
        isMoveable = moveable;
    }

    public boolean isOnFire() {
        return isOnFire;
    }

    public void setOnFire(boolean onFire) {
        isOnFire = onFire;
    }

    public FloorCard rotateShape(FloorCard floorTile) {
        return null;
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
}
