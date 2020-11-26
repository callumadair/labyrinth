package objects;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * The type Player controller.
 */
public class PlayerController {
    private int playerX;
    private int playerY;
    private boolean isGoalReached;

    private ArrayList<Card> cardsHeld;
    private LinkedList<FloorCard> threePositions;

    /**
     * Instantiates a new Player controller.
     */
    public PlayerController() {

    }

    /**
     * Gets player x.
     *
     * @return the player x
     */
    public int getPlayerX() {
        return playerX;
    }

    /**
     * Sets player x.
     *
     * @param playerX the player x
     */
    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    /**
     * Gets player y.
     *
     * @return the player y
     */
    public int getPlayerY() {
        return playerY;
    }

    /**
     * Sets player y.
     *
     * @param playerY the player y
     */
    public void setPlayerY(int playerY) {
        this.playerY = playerY;
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


    public void movePlayer() {

    }


    /**
     * Determine legal moves floor card [ ].
     *
     * @return the floor card [ ]
     */
    public ArrayList<FloorCard> determineLegalMoves() {
        return null;
    }

   

    public ArrayList<Card> getCardsHeld(){
        return this.cardsHeld;
    }

    public void addInCardsHeld(Card card){
        cardsHeld.add(card);
    }

    /**
     * Use card.
     */
    public void useCard(Card card){

    }

    public void addLastPositions(int x, int y){

        threePositions.add(currentPos);
        }


}
