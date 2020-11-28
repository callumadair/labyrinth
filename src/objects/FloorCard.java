package objects;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.image.Image;

/**
 * This class represents the different floor tiles of the game.
 */
public class FloorCard extends Card { //need to continue javadoc


    public static final int TILE_SIZE = 54;

    private int x, y;
    private FloorType type;
    private boolean isFixed;
    private FloorTileState state = FloorTileState.NORMAL;
    private boolean[] possiblePaths; //0 left, 1 up, 2 right, 3 down
    private int rotation = 0;
    private Image image;

    private String straightTileImagePath = "resources/ROAD_straight.png";
    private String cornerTileImagePath = "resources/ROAD_curved.png";
    private String tshapedTileImagePath = "resources/ROAD_Tshaped.png";
    private String goalTileImagePath = "resources/ROAD_goal.png";

    public enum Direction {
        RIGHT, LEFT, UP, DOWN;
    }

    /**
     * The enum Floor type.
     */
    public enum FloorType {
        STRAIGHT, CORNER, T_SHAPED, GOAL;
    }

    /**
     * The enum Floor tile state.
     */
    public enum FloorTileState {
        FIRE, FROZEN, NORMAL;
    }

    /**
     * Instantiates a new Floor card.
     *
     * @param type - the floor tile type
     */
    public FloorCard(String type) {
        switch (type) {
            case "STRAIGHT":
                this.type = FloorType.STRAIGHT;
                image = new Image(straightTileImagePath);
                break;
            case "CORNER":
                this.type = FloorType.CORNER;
                image = new Image(cornerTileImagePath);
                break;
            case "T_SHAPED":
                this.type = FloorType.T_SHAPED;
                image = new Image(tshapedTileImagePath);
                break;
            case "GOAL":
                this.type = FloorType.GOAL;
                image = new Image(goalTileImagePath);
                break;
        }
        possiblePaths = new boolean[4];
        setPaths();
    }

    /**
     * Instantiates a new Floor card.
     *
     * @param type     - the floor tile type
     * @param rotation - the rotation of the tile
     */
    public FloorCard(String type, int x, int y, int rotation) {
        this(type);
        this.x = x;
        this.y = y;
        setRotation(rotation);
        this.isFixed = true;
    }

    /**
     * State of the floor tile.
     *
     * @return the state of the floor tile
     */
    public FloorTileState state() {
        return state;
    }

    public FloorType getType() {
        return type;
    }

    /**
     * Sets on fire.
     */
    public void setOnFire() {
        this.state = FloorTileState.FIRE;
    }

    /**
     * Sets on ice.
     */
    public void setOnIce() {
        this.state = FloorTileState.FROZEN;
    }

    /**
     * Sets normal state.
     */
    public void setStateToNormal() {
        this.state = FloorTileState.NORMAL;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    private void setPaths() {
        switch (type) {
            case STRAIGHT:
                possiblePaths[0] = true;
                possiblePaths[1] = false;
                possiblePaths[2] = true;
                possiblePaths[3] = false;
                break;
            case CORNER:
                possiblePaths[0] = false;
                possiblePaths[1] = true;
                possiblePaths[2] = true;
                possiblePaths[3] = false;
                break;
            case T_SHAPED:
                possiblePaths[0] = true;
                possiblePaths[1] = true;
                possiblePaths[2] = true;
                possiblePaths[3] = false;
                break;
            case GOAL:
                possiblePaths[0] = true;
                possiblePaths[1] = true;
                possiblePaths[2] = true;
                possiblePaths[3] = true;
                break;
        }
    }

    /**
     * Use card.
     */
    @Override
    public boolean useCard(Board board, int x, int y) {
        this.x = x;
        this.y = y;
        board.insertTile(this, x, y);
        return true;
    }

    /**
     * Check goal boolean to see if the goal have been reached.
     *
     * @return the boolean
     */
    public boolean checkGoal() {
        return type == FloorType.GOAL;
    }

    /**
     * Is fixed boolean to indicate if some floor tiles are fixed.
     *
     * @return the boolean
     */
    public boolean isFixed() {
        return isFixed;
    }

    /**
     * Gets rotation.
     *
     * @return the rotation
     */
    public int getRotation() {
        return rotation;
    }


    private void changePaths() {
        boolean temp = possiblePaths[3];
        for (int i = 3; i > 0; i--) {
            possiblePaths[i] = possiblePaths[i - 1];
        }
        possiblePaths[0] = temp;
    }

    private void changePaths(int times) {
        while (times > 0) {
            times--;
            boolean temp = possiblePaths[3];
            for (int i = 3; i > 0; i--) {
                possiblePaths[i] = possiblePaths[i - 1];
            }
            possiblePaths[0] = temp;
        }
    }

    /**
     * Next rotation of each tile.
     */
    public void nextRotation() {
        if (rotation == 0) {
            this.rotation = 90;
            changePaths();
        } else if (rotation == 90) {
            this.rotation = 180;
            changePaths();
        } else if (rotation == 180) {
            this.rotation = 270;
            changePaths();
        } else if (rotation == 270) {
            this.rotation = 0;
            changePaths();
        }
    }

    private void setRotation(int rotation) {
        if (rotation == 90) {
            this.rotation = 90;
            changePaths(1);
        } else if (rotation == 180) {
            this.rotation = 180;
            changePaths(2);
        } else if (rotation == 270) {
            this.rotation = 270;
            changePaths(3);
        }
    }

    /**
     * Get image image.
     *
     * @return the image
     */
    public Image getImage() {
        return image;
    }

    /**
     * Get x coordinate.
     *
     * @return the X coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Get y coordinate.
     *
     * @return the Y coordinate
     */
    public int getY() {
        return y;
    }

    public boolean getOpeningAt(Direction dir) {
        switch (dir) {
            case LEFT:
                return possiblePaths[0];
            case UP:
                return possiblePaths[1];
            case RIGHT:
                return possiblePaths[2];
            case DOWN:
                return possiblePaths[3];
            default:
                return false;
        }
    }

    public boolean checkPath(FloorCard compare, Direction dir) {
        switch (dir) {
            case LEFT:
                if (compare.getOpeningAt(Direction.RIGHT) && this.getOpeningAt(Direction.LEFT)) {
                    return true;
                }
                break;
            case UP:
                if (compare.getOpeningAt(Direction.DOWN) && this.getOpeningAt(Direction.UP)) {
                    return true;
                }
                break;
            case RIGHT:
                if (compare.getOpeningAt(Direction.LEFT) && this.getOpeningAt(Direction.RIGHT)) {
                    return true;
                }
                break;
            case DOWN:
                if (compare.getOpeningAt(Direction.UP) && this.getOpeningAt(Direction.DOWN)) {
                    return true;
                }
                break;
        }
        return false;
    }

}