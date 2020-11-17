package objects;

import javafx.scene.image.Image;

public class FloorCard extends Card{

    private FloorType type;
    private boolean isMoveable;
    private boolean isOnFire;

    public enum FloorType {
        STRAIGHT, CORNER, T_SHAPED, GOAL;
    }

    public void useCard() {
    }

    public boolean checkGoal() {
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
    }
}
