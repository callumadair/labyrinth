package objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * This class represents the Player controller operating on the board during
 * a game.
 *
 * @author Maha Malik
 * @author Kacper Lisikiewicz
 * @author Stefani Dimitrova
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
    private boolean isCurrentPlayer = false;

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
     * Check double move.
     *
     * @return boolean.
     */
    public boolean checkDoubleMove() {
        return doubleMove;
    }

    /**
     * Sets double move.
     *
     * @param value - the value.
     */
    public void setDoubleMove(boolean value) {
        this.doubleMove = value;
    }

    /**
     * Is backtracked true.
     *
     * @return true if the player's been backtracked.
     */
    public boolean isBackTracked() {
        return isBackTracked;
    }

    /**
     * Sets backtracked.
     *
     * @param backTracked - the backtracked boolean.
     */
    public void setBackTracked(boolean backTracked) {
        isBackTracked = backTracked;
    }

    /**
     * Gets x coordinate of player.
     *
     * @return the x coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Sets x coordinate of player.
     *
     * @param x - the x coordinate.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets y coordinate of player.
     *
     * @return the y coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets y coordinate of player.
     *
     * @param y - the y coordinate.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Set current player.
     *
     * @param flag boolean.
     */
    public void setCurrentPlayer(boolean flag) {
        isCurrentPlayer = flag;
    }

    /**
     * Checks if a player is the current player in the game.
     *
     * @return true if the player is the current player.
     */
    public boolean isCurrentPlayer() {
        return isCurrentPlayer;
    }

    /**
     * Adds a card into the player's held cards.
     *
     * @param card - the card to be added.
     */
    public void addInCardsHeld(ActionCard card) {
        cardsHeld.add(card);
    }

    /**
     * Gets last three moves made by the player.
     *
     * @return the last three moves.
     */
    public LinkedList<int[]> getLastThree() {
        return lastThree;
    }

    /**
     * Sets last three moves made by the player.
     *
     * @param cards - the cards.
     */
    public void setLastThree(LinkedList<int[]> cards) {
        this.lastThree = lastThree;
    }

    /**
     * Stores position of the player.
     *
     * @param x - the x coordinate.
     * @param y - the y coordinate.
     */
    public void storePosition(int x, int y) {
        int pos[] = {x, y};

        //Last is the previous position
        if (lastThree.size() == 3) {
            lastThree.addLast(pos);
            lastThree.removeFirst();
        } else {
            lastThree.addLast(pos);
        }

    }

    /**
     * Move player.
     *
     * @param x - the x coordinate.
     * @param y - the y coordinate.
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
     * @return the player index.
     */
    public int getPlayerIndex() {
        return playerIndex;
    }

    /**
     * Gets profile of the player.
     *
     * @return the profile of the player.
     */
    public PlayerProfile getProfile() {
        return profile;
    }

    /**
     * Determine legal moves array list.
     *
     * @param board the board.
     * @return the array list of Floor Cards.
     */
    public ArrayList<FloorCard> determineLegalMoves(Board board) {
        ArrayList<FloorCard> legalMoves = new ArrayList<>();
        FloorCard currentTile = board.getTile(x, y);
        FloorCard left = board.getTile(x - 1, y);
        FloorCard top = board.getTile(x, y - 1);
        FloorCard right = board.getTile(x + 1, y);
        FloorCard bottom = board.getTile(x, y + 1);

        if (left != null) {
            if (currentTile.checkPath(left, FloorCard.Direction.LEFT)
                    && !board.checkPlayerPosition(left.getX(), left.getY())) {
                legalMoves.add(left);
            }
        }
        if (top != null) {
            if (currentTile.checkPath(top, FloorCard.Direction.UP)
                    && !board.checkPlayerPosition(top.getX(), top.getY())) {
                legalMoves.add(top);
            }
        }
        if (right != null) {
            if (currentTile.checkPath(right, FloorCard.Direction.RIGHT)
                    && !board.checkPlayerPosition(right.getX(), right.getY())) {
                legalMoves.add(right);
            }
        }
        if (bottom != null) {
            if (currentTile.checkPath(bottom, FloorCard.Direction.DOWN)
                    && !board.checkPlayerPosition(bottom.getX(),
                    bottom.getY())) {
                legalMoves.add(bottom);
            }
        }

        return legalMoves;
    }


    /**
     * Gets the player's cards held.
     *
     * @return list of cards held.
     */
    public ArrayList<ActionCard> getCardsHeld() {
        return this.cardsHeld;
    }

    /**
     * Draw player.
     *
     * @param gc the gc.
     */
    public void drawPlayer(GraphicsContext gc) {
        gc.drawImage(image, x * FloorCard.TILE_SIZE,
                y * FloorCard.TILE_SIZE);
    }

    /**
     * Gets player's color.
     */
    private String getPlayerColor() {
        switch (playerIndex) {
            case 0:
                return "Green";
            case 1:
                return "Purple";
            case 2:
                return "Red";
            case 3:
                return "Orange";
            default:
                return "No color";
        }
    }

    /**
     * @return the players name and the color of his figure as a string
     */
    @Override
    public String toString() {
        return getPlayerColor() + ": " + getProfile().getPlayerName();
    }

}
