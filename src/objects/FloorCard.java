package objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;

/**
 * This class represents the different floor tiles of the game.
 */
public class FloorCard extends Card {


    public static final int TILE_SIZE = 54;

    private int x;
    private int y;
    private FloorType type;
    private boolean isFixed;
    private FloorTileState state = FloorTileState.NORMAL;
    private boolean[] possiblePaths; //0 left, 1 up, 2 right, 3 down
    private int rotation = 0;

    private int effectTimer = 0;

    private String straightTileImagePath = "resources/ROAD_straight";
    private String cornerTileImagePath = "resources/ROAD_curved";
    private String tshapedTileImagePath = "resources/ROAD_Tshaped";
    private String goalTileImagePath = "resources/ROAD_goal.png";
    private String onFireImagePath = "resources/ROAD-Fireeffect.png";
    private String onIceImagePath = "resources/ROAD-Iceeffect.png";

    /**
     * The enum Direction
     */
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
                this.setImage(straightTileImagePath, this.getRotation());
                break;
            case "CORNER":
                this.type = FloorType.CORNER;
                this.setImage(cornerTileImagePath, this.getRotation());
                break;
            case "T_SHAPED":
                this.type = FloorType.T_SHAPED;
                this.setImage(tshapedTileImagePath, this.getRotation());
                break;
            case "GOAL":
                this.type = FloorType.GOAL;
                this.setImage(goalTileImagePath);
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

    public boolean isOnFire() {
        if (state == FloorTileState.FIRE) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get type of floor.
     *
     * @return floor type
     */
    public FloorType getType() {
        return type;
    }

    public void setFixed(boolean fixed) {
        this.isFixed = fixed;
    }

    /**
     * Sets on fire.
     */
    public void setOnFire(int effectTimer) {
        setEffectTimer(effectTimer);
        this.state = FloorTileState.FIRE;
    }

    /**
     * Sets on ice.
     */
    public void setOnIce(int effectTimer) {
        setEffectTimer(effectTimer);
        this.state = FloorTileState.FROZEN;
    }

    public FloorTileState getState() {
        return this.state;
    }

    public void decrementEffectTimer() {
        effectTimer--;
        if (effectTimer <= 0) {
            this.setStateToNormal();
            effectTimer = 0;
        }
    }

    public boolean isEffectActive() {
        if (effectTimer > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void setEffectTimer(int effectTimer) {
        if (effectTimer < 0) {
            this.effectTimer = 0;
        } else {
            this.effectTimer = effectTimer;
        }
    }

    public int getEffectTimer() {
        return effectTimer;
    }

    /**
     * Sets normal state.
     */
    public void setStateToNormal() {
        this.state = FloorTileState.NORMAL;
    }

    /**
     * Sets x coordinate.
     *
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets y coordinate.
     *
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Sets possible paths taken by player.
     */
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

    /**
     * Change path for tile.
     */
    private void changePaths() {
        boolean temp = possiblePaths[3];
        for (int i = 3; i > 0; i--) {
            possiblePaths[i] = possiblePaths[i - 1];
        }
        possiblePaths[0] = temp;
    }

    /**
     * Change path for tile.
     *
     * @param times
     */
    private void changePaths(int times) {
        while (times > 0) {
            times--;
            changePaths();
        }
    }

    /**
     * Next rotation of each tile.
     */
    public void nextRotation() {
        if (rotation == 0) {
            this.rotation = 90;
            setImageWithRotation();
            changePaths();
        } else if (rotation == 90) {
            this.rotation = 180;
            setImageWithRotation();
            changePaths();
        } else if (rotation == 180) {
            this.rotation = 270;
            setImageWithRotation();
            changePaths();
        } else if (rotation == 270) {
            this.rotation = 0;
            setImageWithRotation();
            changePaths();
        }
    }

    /**
     * Set rotation of the tile.
     *
     * @param rotation
     */
    public void setRotation(int rotation) {
        if (rotation == 90) {
            this.rotation = 90;
            setImageWithRotation();
            changePaths(1);
        } else if (rotation == 180) {
            this.rotation = 180;
            setImageWithRotation();
            changePaths(2);
        } else if (rotation == 270) {
            this.rotation = 270;
            setImageWithRotation();
            changePaths(3);
        }
    }

    public void setRandomRotation() {
        Random random = new Random();
        int randomRotation = random.nextInt(4);
        switch (randomRotation) {
            case 0:
                setRotation(90);
                break;
            case 1:
                setRotation(180);
                break;
            case 2:
                setRotation(270);
                break;
            default:
                setRotation(0);
                break;
        }
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

    /**
     * Get opening at certain direction.
     *
     * @param dir
     * @return true if possible paths otherwise return false
     */
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

    /**
     * Check to see if there is a path in the direction the player wants to go.
     *
     * @param compare
     * @param dir
     * @return true if path has opening in certain direction, otherwise false
     */
    public boolean checkPath(FloorCard compare, Direction dir) {
        if (compare.isOnFire()) {
            return false;
        }
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

    private void setImageWithRotation() {
        switch (type) {
            case STRAIGHT:
                this.setImage(straightTileImagePath, this.getRotation());
                break;
            case CORNER:
                this.setImage(cornerTileImagePath, this.getRotation());
                break;
            case T_SHAPED:
                this.setImage(tshapedTileImagePath, this.getRotation());
                break;
            case GOAL:
                this.setImage(goalTileImagePath);
                break;
        }
    }

    public void drawTile(GraphicsContext gc, int x, int y) {
        gc.drawImage(this.getImage(), x * TILE_SIZE, y * TILE_SIZE);
        if (state != FloorTileState.NORMAL) {
            gc.drawImage(getTileEffectImage(), x * TILE_SIZE, y * TILE_SIZE);
        }
    }

    private Image getTileEffectImage() {
        switch (state) {
            case FIRE:
                return new Image(onFireImagePath);
            case FROZEN:
                return new Image(onIceImagePath);
        }
        return this.getImage();
    }
}