package objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.lang.reflect.Array;
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
    private ArrayList<Card> cardsHeld;

    private LinkedList<int[]> lastThree;

    private PlayerProfile profile;
    private int playerIndex;
    private int playerMoveValue;
    private boolean isBackTracked;
	/**
     * Instantiates a new Player controller.
     */

    public PlayerController(PlayerProfile profile, int playerIndex) {
        this.profile = profile;
        this.playerIndex = playerIndex;
        switch (playerIndex){
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
    }

    public int getPlayerMoveValue() {
        return playerMoveValue;
    }

    public void setPlayerMoveValue(int playerMoveValue) {
        this.playerMoveValue = playerMoveValue;
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
        storePosition(this.x, this.y);
        this.x = x;
        this.y = y;
    }

    public int getPlayerIndex(){
        return playerIndex;
    }

    public PlayerProfile getProfile(){
        return profile;
    }


    public boolean isOnLeft(FloorCard currentFloor, Board board){
        return board.getTile(x, y).getX() == currentFloor.getX() - 1 && board.getTile(x, y).getY() == currentFloor.getY();
    }

    public boolean isOnRight(FloorCard currentFloor, Board board){
        return board.getTile(x, y).getX() == currentFloor.getX() + 1 && board.getTile(x, y).getY() == currentFloor.getY();
    }

    public boolean isOnTop(FloorCard currentFloor, Board board){
        return board.getTile(x, y).getX() == currentFloor.getX() && board.getTile(x, y).getY() == currentFloor.getY() + 1;
    }

    public boolean isOnBottom(FloorCard currentFloor, Board board){
        return board.getTile(x, y).getX() == currentFloor.getX() && board.getTile(x, y).getY() == currentFloor.getY() - 1;
    }

    public FloorCard getOnLeft(FloorCard currentFloor, Board board){
        if (currentFloor.getX() == 0) {
            return null;
        } else {
            return board.getTile(currentFloor.getX() - 1, currentFloor.getY());
        }
    }

    public FloorCard getOnRight(FloorCard currentFloor, Board board){
        if (currentFloor.getX() == board.getWidth() - 1) {
            return null;
        } else {
            return board.getTile(currentFloor.getX() + 1, currentFloor.getY());
        }
    }

    public FloorCard getOnTop(FloorCard currentFloor, Board board){
        if (currentFloor.getY() == 0) {
            return null;
        } else {
            return board.getTile(currentFloor.getX(), currentFloor.getY() + 1);
        }
    }

    public FloorCard getOnBottom(FloorCard currentFloor, Board board){
        if (currentFloor.getY() == board.getHeight() - 1) {
            return null;
        } else {
            return board.getTile(currentFloor.getX(), currentFloor.getY() - 1);
        }
    }


    public ArrayList<FloorCard> determineLegalMoves(Board board) {
        ArrayList<FloorCard> legalMoves = new ArrayList<>();
        FloorCard left = getOnLeft(board.getTile(x, y), board);
        FloorCard top = getOnTop(board.getTile(x, y), board);
        FloorCard right = getOnRight(board.getTile(x, y), board);
        FloorCard bottom = getOnBottom(board.getTile(x, y), board);

        if (left != null){
            if (board.getTile(x,y).getOpeningAt(0) == getOnLeft(board.getTile(x,y), board).getOpeningAt(2)) {
                legalMoves.add(getOnLeft(board.getTile(x,y), board));
            }
        } else if (top != null) {
            if(board.getTile(x,y).getOpeningAt(1) == getOnTop(board.getTile(x,y), board).getOpeningAt(3)) {
                legalMoves.add(getOnTop(board.getTile(x,y), board));
            }
        } else if (right != null) {
            if(board.getTile(x,y).getOpeningAt(2) == getOnRight(board.getTile(x,y), board).getOpeningAt(0)) {
                legalMoves.add(getOnRight(board.getTile(x,y), board));
            }
        } else if (bottom != null) {
            if(board.getTile(x,y).getOpeningAt(3) == getOnBottom(board.getTile(x,y), board).getOpeningAt(3)) {
                legalMoves.add(getOnBottom(board.getTile(x,y), board));
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

    public void drawPlayer(GraphicsContext gc){
        gc.drawImage(image, x * FloorCard.TILE_SIZE, y * FloorCard.TILE_SIZE);
    }
}
