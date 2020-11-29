package objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * The type Player controller.
 */
public class PlayerController {

    public FloorCard currentLocation;
    private String playerImage1 = "resources/ROAD-player1.png";
    private String playerImage2 = "resources/ROAD-player2.png";
    private String playerImage3 = "resources/ROAD-player3.png";
    private String playerImage4 = "resources/ROAD-player4.png";

    private Image image;
    private int x, y;
    private ArrayList<Card> cardsHeld;

    private LinkedList<int[]> lastThree;

    private PlayerProfile profile;
    private int playerIndex;
    private boolean doubleMove = false;
    private boolean isBackTracked = false;


    /**
     * Instantiates a new Player controller.
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

    public boolean checkDoubleMove() {
        return doubleMove;
    }

    public void setDoubleMove(boolean value) {
        this.doubleMove = value;
    }

    public boolean isBackTracked() {
        return isBackTracked;
    }

    public void setBackTracked(boolean backTracked) {
        isBackTracked = backTracked;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public LinkedList<int[]> getLastThree() {
        return lastThree;
    }

    public void setLastThree(LinkedList<int[]> cards) {
        this.lastThree = lastThree;

    }

    /**
     * stores position of the player
     *
     * @param x
     * @param y
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

    public void movePlayer(int x, int y) {
        if (!isBackTracked) {
            storePosition(this.x, this.y);
        }
        this.x = x;
        this.y = y;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public PlayerProfile getProfile() {
        return profile;
    }

    public ArrayList<FloorCard> determineLegalMoves(Board board) {
        ArrayList<FloorCard> legalMoves = new ArrayList<>();
        FloorCard currentTile = board.getTile(x, y);
        FloorCard left = board.getTile(x - 1, y);
        FloorCard top = board.getTile(x, y + 1);
        FloorCard right = board.getTile(x + 1, y);
        FloorCard bottom = board.getTile(x, y - 1);

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
     * getter for cards held
     *
     * @return list of cards held
     */
    public ArrayList<Card> getCardsHeld() {
        return this.cardsHeld;
    }

    /**
     * add cards
     *
     * @param card
     */
    public void addInCardsHeld(Card card) {
        cardsHeld.add(card);
    }

    public void drawPlayer(GraphicsContext gc) {
        gc.drawImage(image, x * FloorCard.TILE_SIZE, y * FloorCard.TILE_SIZE);
    }
}
