package objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import javax.swing.*;
import java.rmi.activation.ActivationID;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * The type Player controller.
 */
public class PlayerController {

    private String playerImage1 = "resources/ROAD-player1.png";
    private String playerImage2 = "resources/ROAD-player2.png";
    private String playerImage3 = "resources/ROAD-player3.png";
    private String playerImage4 = "resources/ROAD-player4.png";

    private Image image;
    private int x, y;
    private ArrayList<ActionCard> cardsHeld;

    private LinkedList<int[]> lastThree;

    private PlayerProfile profile;
    private int playerIndex;
    private boolean doubleMove = false;
    private boolean isBackTracked = false;


    /**
     * Instantiates a new Player controller.
     *
     * @param profile     the profile
     * @param playerIndex the player index
     */
    public PlayerController(PlayerProfile profile, int playerIndex) {
        this.profile = profile;
        this.playerIndex = playerIndex;
        switch (playerIndex) {
            case 0:
                image = new Image(playerImage1);
                break;
            case 1:
                image = new Image(playerImage2);
                break;
            case 2:
                image = new Image(playerImage3);
                break;
            case 3:
                image = new Image(playerImage4);
                break;
        }
        lastThree = new LinkedList<>();
        cardsHeld = new ArrayList<>();
    }

    /**
     * Check double move boolean.
     *
     * @return boolean
     */
    public boolean checkDoubleMove() {
        return doubleMove;
    }

    /**
     * Sets double move.
     *
     * @param value - the value
     */
    public void setDoubleMove(boolean value) {
        this.doubleMove = value;
    }

    /**
     * Is back tracked boolean.
     *
     * @return the boolean
     */
    public boolean isBackTracked() {
        return isBackTracked;
    }

    /**
     * Sets back tracked.
     *
     * @param backTracked - the back tracked
     */
    public void setBackTracked(boolean backTracked) {
        isBackTracked = backTracked;
    }

    /**
     * Gets x coordinate of player.
     *
     * @return the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Sets x coordinate of player.
     *
     * @param x - the x coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets y coordinate of player.
     *
     * @return the y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Sets y coordinate of player.
     *
     * @param y - the y coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Gets last three moves made by the player.
     *
     * @return the last three moves
     */
    public LinkedList<int[]> getLastThree() {
        return lastThree;
    }

    /**
     * Sets last three moves made by the player.
     *
     * @param cards - the cards
     */
    public void setLastThree(LinkedList<int[]> cards) {
        this.lastThree = lastThree;

    }

    /**
     * stores position of the player
     *
     * @param x - the x coordinate
     * @param y - the y coordinate
     */
    public void storePosition(int x, int y) {
        int pos[] = {x, y};

        if (lastThree.size() == 3) {
            lastThree.add(pos);
            lastThree.removeFirst();
        } else {
            lastThree.add(pos);
        }

    }

    /**
     * Move player.
     *
     * @param x - the x coordinate
     * @param y - the y coordinate
     */
    public void movePlayer(int x, int y) {
        if (!isBackTracked) {
            storePosition(this.x, this.y);
        }
        this.x = x;
        this.y = y;
    }

    /**
     * Gets player index.
     *
     * @return the player index
     */
    public int getPlayerIndex() {
        return playerIndex;
    }

    /**
     * Gets profile.
     *
     * @return the profile
     */
    public PlayerProfile getProfile() {
        return profile;
    }

    /**
     * Determine legal moves array list.
     *
     * @param board the board
     * @return the array list
     */
    public ArrayList<FloorCard> determineLegalMoves(Board board) {
        ArrayList<FloorCard> legalMoves = new ArrayList<>();
        FloorCard currentTile = board.getTile(x, y);
        FloorCard left = board.getTile(x - 1, y);
        FloorCard top = board.getTile(x, y - 1);
        FloorCard right = board.getTile(x + 1, y);
        FloorCard bottom = board.getTile(x, y + 1);

        if (left != null) {
            if (currentTile.checkPath(left, FloorCard.Direction.LEFT)) {
                legalMoves.add(left);
            }
        }
        if (top != null) {
            if (currentTile.checkPath(top, FloorCard.Direction.UP)) {
                legalMoves.add(top);
            }
        }
        if (right != null) {
            if (currentTile.checkPath(right, FloorCard.Direction.RIGHT)) {
                legalMoves.add(right);
            }
        }
        if (bottom != null) {
            if (currentTile.checkPath(bottom, FloorCard.Direction.DOWN)) {
                legalMoves.add(bottom);
            }
        }

        return legalMoves;
    }


    /**
     * Getter for cards held.
     *
     * @return list of cards held
     */
    public ArrayList<ActionCard> getCardsHeld() {
        return this.cardsHeld;
    }

    /**
     * Add cards.
     *
     * @param card the card
     */
    public void addCard(ActionCard card) {
        cardsHeld.add(card);
    }

    @Override
    public String toString() {
        return "Player index: " + getPlayerIndex();
    }

    /**
     * Draw player.
     *
     * @param gc the gc
     */
    public void drawPlayer(GraphicsContext gc) {
        gc.drawImage(image, x * FloorCard.TILE_SIZE, y * FloorCard.TILE_SIZE);
    }

    @Override
    public String toString() {
        return "Player index: " + playerIndex;
    }
}
