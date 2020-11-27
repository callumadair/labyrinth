package objects;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * The type Player controller.
 */
public class PlayerController {

    private int x, y;
    private boolean isGoalReached;

    private ArrayList<Card> cardsHeld;
    private LinkedList<FloorCard> lastThree;

    private FloorCard currentPosition;
    private Board board;



    /**
     * Instantiates a new Player controller.
     */
    public PlayerController() {

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

    public LinkedList<FloorCard> getLastThree() {
        return lastThree;
    }

    public void setLastThree(LinkedList<FloorCard> cards) {
        this.lastThree = lastThree;
    }

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
     * @param x
     * @param y
     */

    public void storePosition(int x, int y) {
        //last el = current pos, first el 2 pos back
        if(lastThree.size() == 3){
            lastThree.add(board.getTile(x, y));
            lastThree.removeFirst();
        }else {
            lastThree.add(board.getTile(x, y));
        }
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
