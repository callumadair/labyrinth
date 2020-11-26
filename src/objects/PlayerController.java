package objects;

import java.util.ArrayList;

/**
 * The type Player controller.
 */
public class PlayerController {

    private int x, y;
    private boolean isGoalReached;

    private ArrayList<Card> cardsHeld;

    private FloorCard currentPosition;
    private Board board;



    /**
     * Instantiates a new Player controller.
     */
    public PlayerController() {

    }



    /*
    public boolean isGoalReached() {
        if((board.getTile((playerX), playerY)).equals(FloorCard.FloorType.GOAL)){
            return isGoalReached;
        } else {
            return false;
        }
    }*/

    /**
     * Sets goal reached.
     *
     * @param goalReached the goal reached
     */
    public void setGoalReached(boolean goalReached) {
        isGoalReached = goalReached;
    }

    /**
     * stores position of the player
     *
     * @param playerX
     * @param playerY
     */

    public void storePosition(int playerX, int playerY) {

    }

    /**
     * moves the player across the board
     *
     */
    public void movePlayer(int x, int y) {
        storePosition(this.x, this.y);
        this.x = x;
        this.y = y;

    }


    /**
     * Determine legal moves floor card [].
     *
     * @return the floor card []
     */
    public ArrayList<FloorCard> determineLegalMoves() {
        ArrayList<FloorCard> legalMoves = new ArrayList<>();

        if (currentPosition.getX() > 0 && currentPosition.getY() > 0) {
            if (currentPosition.getOpeningAt(0) == currentPosition.getOpeningAt(2)) {
                legalMoves.add(board.getTile(x, y));
            }
            if (currentPosition.getOpeningAt(1) == currentPosition.getOpeningAt(3)) {
                legalMoves.add(board.getTile(x, y));
            }
            if (currentPosition.getOpeningAt(2) == currentPosition.getOpeningAt(0)) {
                legalMoves.add(board.getTile(x, y));
            }
            if (currentPosition.getOpeningAt(3) == currentPosition.getOpeningAt(1)) {
                legalMoves.add(board.getTile(x, y));
            }
        } else if (currentPosition.getX() == 0 && currentPosition.getY() == 0) {
            if (currentPosition.getOpeningAt(2) == currentPosition.getOpeningAt(0)) {
                legalMoves.add(board.getTile(x, y));
            }
            if (currentPosition.getOpeningAt(3) == currentPosition.getOpeningAt(1)) {
                legalMoves.add(board.getTile(x, y));
            }
        } else if (currentPosition.getX() == board.getWidth() - 1 && currentPosition.getY() == 0) {
            if (currentPosition.getOpeningAt(0) == currentPosition.getOpeningAt(2)) {
                legalMoves.add(board.getTile(x, y));
            }
            if (currentPosition.getOpeningAt(3) == currentPosition.getOpeningAt(1)) {
                legalMoves.add(board.getTile(x, y));
            }
        } else if (currentPosition.getX() == 0 && currentPosition.getY() == 1 - board.getHeight()) {
            if (currentPosition.getOpeningAt(1) == currentPosition.getOpeningAt(3)) {
                legalMoves.add(board.getTile(x, y));
            }
            if (currentPosition.getOpeningAt(2) == currentPosition.getOpeningAt(0)) {
                legalMoves.add(board.getTile(x, y));
            }
        } else if (currentPosition.getX() == board.getWidth() + 1 && currentPosition.getY() == 1 - board.getHeight()) {
            if (currentPosition.getOpeningAt(0) == currentPosition.getOpeningAt(2)) {
                legalMoves.add(board.getTile(x, y));
            }
            if (currentPosition.getOpeningAt(1) == currentPosition.getOpeningAt(3)) {
                legalMoves.add(board.getTile(x, y));
            }
        } else if (currentPosition.getX() == 0) {
            if (currentPosition.getOpeningAt(1) == currentPosition.getOpeningAt(3)) {
                legalMoves.add(board.getTile(x, y));
            }
            if (currentPosition.getOpeningAt(2) == currentPosition.getOpeningAt(0)) {
                legalMoves.add(board.getTile(x, y));
            }
            if (currentPosition.getOpeningAt(3) == currentPosition.getOpeningAt(1)) {
                legalMoves.add(board.getTile(x, y));
            }
        } else if (currentPosition.getX() == board.getHeight()) {
            if (currentPosition.getOpeningAt(0) == currentPosition.getOpeningAt(2)) {
                legalMoves.add(board.getTile(x, y));
            }
            if (currentPosition.getOpeningAt(1) == currentPosition.getOpeningAt(3)) {
                legalMoves.add(board.getTile(x, y));
            }
            if (currentPosition.getOpeningAt(3) == currentPosition.getOpeningAt(1)) {
                legalMoves.add(board.getTile(x, y));
            }
        } else if (currentPosition.getY() == 0) {
            if (currentPosition.getOpeningAt(0) == currentPosition.getOpeningAt(2)) {
                legalMoves.add(board.getTile(x, y));
            }
            if (currentPosition.getOpeningAt(1) == currentPosition.getOpeningAt(3)) {
                legalMoves.add(board.getTile(x, y));
            }
            if (currentPosition.getOpeningAt(2) == currentPosition.getOpeningAt(0)) {
                legalMoves.add(board.getTile(x, y));
            }
        } else if (currentPosition.getY() == board.getWidth()) {
            if (currentPosition.getOpeningAt(0) == currentPosition.getOpeningAt(2)) {
                legalMoves.add(board.getTile(x, y));
            }
            if (currentPosition.getOpeningAt(3) == currentPosition.getOpeningAt(1)) {
                legalMoves.add(board.getTile(x, y));
            }
            if (currentPosition.getOpeningAt(2) == currentPosition.getOpeningAt(0)) {
                legalMoves.add(board.getTile(x, y));
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

}
