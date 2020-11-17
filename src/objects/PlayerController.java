package objects;

import java.util.*;

/**
 * The type Player controller.
 */
public class PlayerController {
    private int playerX;
    private int playerY;
    private boolean isGoalReached;
    private ArrayList<Card> cardsHeld;

    /**
     * Instantiates a new Player controller.
     */
    PlayerController() {

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

    /**
     * Is goal reached boolean.
     *
     * @return the boolean
     */
    public boolean isGoalReached() {
        return isGoalReached;
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
     * Move player.
     */
    public void movePlayer() {

    }

    /**
     * Determine legal moves floor card [ ].
     *
     * @return the floor card [ ]
     */
    public FloorCard[] determineLegalMoves() {
        return null;
    }

    /**
     * Draw card card.
     *
     * @return the card
     */
    public Card drawCard() {
        return null;
    }

    /**
     * Use card.
     */
    public void useCard() {

    }
}
