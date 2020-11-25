package objects;

import javafx.scene.image.Image;

/**
 * This class represents the different floor tiles of the game.
 *
 * @author Maha Malik
 * @version 1.8
 */
public class FloorCard extends Card {


    public static final int TILE_SIZE = 61;

    private int x, y;
    private FloorType type;
    private boolean isFixed = true;
    private FloorTileState state = FloorTileState.NORMAL;
    private int[] openings = new int[4];
    private int rotation;
    private Image image;

    private String straightTileImagePath;
    private String cornerTileImagePath;
    private String tshapedTileImagePath;
    private String goalTileImagePath;

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
    }

    /**
     * Instantiates a new Floor card.
     *
     * @param type     - the floor tile type
     * @param rotation - the rotation of the tile
     * @param isFixed  - the is fixed
     */
    public FloorCard(String type, int rotation, boolean isFixed) {
        this(type);
        this.rotation = rotation;
        this.isFixed = isFixed;
    }

    /**
     * State of the floor tile.
     *
     * @return the state of the floor tile
     */
    public FloorTileState state() {
        return state;
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
     * Sets no state.
     */
    public void setNoState() {
        this.state = FloorTileState.NORMAL;
    }

    /**
     * Use card.
     */
    @Override
    public void useCard(Board board, int x, int y) {
        board.insertTile(this, x, y);
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
     * Sets fixed tiles in the board
     *
     * @param fixedTiles the fixed tiles
     */
    public void setFixed(boolean fixedTiles) {
        isFixed = fixedTiles;
    }


    /**
     * Gets rotation.
     *
     * @param rotation - the rotation of the tile
     * @return the rotation
     */
    public int getRotation() {
        return rotation;
    }

    /**
     * Sets rotation.
     *
     * @param rotation - the rotation of the tile
     */

    public void rotateShape(int rotation) {
        switch (type) {
            case STRAIGHT:
                if (rotation == 0 || rotation == 180) {
                    setOpenings(1, 0, 1, 0);
                } else if (rotation == 90 || rotation == 270) {
                    setOpenings(0, 1, 0, 1);
                }
                break;
            case CORNER:
                if (rotation == 0) {
                    setOpenings(0, 1, 1, 0);
                } else if (rotation == 90) {
                    setOpenings(0, 0, 1, 1);
                } else if (rotation == 180) {
                    setOpenings(1, 0, 0, 1);
                } else if (rotation == 270) {
                    setOpenings(1, 1, 0, 0);
                }
                break;
            case T_SHAPED:
                if (rotation == 0) {
                    setOpenings(1, 0, 1, 1);
                } else if (rotation == 90) {
                    setOpenings(1, 1, 0, 1);
                } else if (rotation == 180) {
                    setOpenings(1, 1, 1, 0);
                } else if (rotation == 270) {
                    setOpenings(0, 1, 1, 1);
                }
                break;
        }
    }

    /**
     * Next rotation of each tile.
     */
    public void nextRotation() {
        if (rotation == 0) {
            rotateShape(90);
        } else if (rotation == 90) {
            rotateShape(180);
        } else if (rotation == 180) {
            rotateShape(270);
        } else if (rotation == 270) {
            rotateShape(0);
        }
    }

    /**
     * set rotation for tiles
     * @param rotation
     */
    private void setRotation(int rotation) {
        this.rotation = rotation;
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

    /**
     * Sets openings from which the player can access the path for the game
     *
     * @param left   - opening from the left
     * @param top    -  opening from the top
     * @param right  - opening from the right
     * @param bottom - opening from the bottom
     */
    public void setOpenings(int left, int top, int right, int bottom) {
        openings[0] = left;
        openings[1] = top;
        openings[2] = right;
        openings[3] = bottom;
    }


    /**
     * gets openings
     * @return openings
     */
    public int[] getOpenings() {
        return openings;
    }

}