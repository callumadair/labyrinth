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

    private FloorCard currentPosition;
    private FloorCard moveToPosition;
    private Board board;
    


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


    public boolean isMovePossible(FloorCard currentPosition, FloorCard moveToPosition) {
       return determineLegalMoves().contains(moveToPosition);
    }

    public void storePosition(int playerX, int playerY) {


    }

    public void movePlayer(FloorCard currentPosition, FloorCard moveToPosition) {
        if (isMovePossible(currentPosition, moveToPosition)) {
            playerX = moveToPosition.getX();
            playerY = moveToPosition.getY();
        } else {
        }

        storePosition(playerX, playerY);
        determineLegalMoves();

    }


    /**
     * Determine legal moves floor card [].
     *
     * @return the floor card []
     */
    public ArrayList<FloorCard> determineLegalMoves() {
        ArrayList<FloorCard> legalMoves = null;


        if (!board.getTile(playerX, playerY).state().equals(FloorCard.FloorTileState.FIRE)) {
            if (currentPosition.getX()==0 && currentPosition.getY()==0) {
                if (currentPosition.getOpeningAt(2) == currentPosition.getOpeningAt(0)){
                    legalMoves.add(board.getTile(playerX, playerY));
                }
                if (currentPosition.getOpeningAt(3) == currentPosition.getOpeningAt(1)){
                    legalMoves.add(board.getTile(playerX, playerY));
                }
            }
            else if (currentPosition.getX()==0 && currentPosition.getY()== board.getWidth() + 1) {
                if (currentPosition.getOpeningAt(0) == currentPosition.getOpeningAt(2)){
                    legalMoves.add(board.getTile(playerX, playerY));
                }
                if (currentPosition.getOpeningAt(3) == currentPosition.getOpeningAt(1)){
                    legalMoves.add(board.getTile(playerX, playerY));
                }
            }
            else if (currentPosition.getX()==0 && currentPosition.getY()== board.getWidth() - 1) {
                if (currentPosition.getOpeningAt(1) == currentPosition.getOpeningAt(3)){
                    legalMoves.add(board.getTile(playerX, playerY));
                }
                if (currentPosition.getOpeningAt(2) == currentPosition.getOpeningAt(0)){
                    legalMoves.add(board.getTile(playerX, playerY));
                }
            }
            else if (currentPosition.getX()== board.getHeight() + 1 && currentPosition.getY()== board.getWidth() - 1) {
                if (currentPosition.getOpeningAt(0) == currentPosition.getOpeningAt(2)){
                    legalMoves.add(board.getTile(playerX, playerY));
                }
                if (currentPosition.getOpeningAt(1) == currentPosition.getOpeningAt(3)){
                    legalMoves.add(board.getTile(playerX, playerY));
                }
            }
            else if (currentPosition.getX()==0) {
                if (currentPosition.getOpeningAt(1) == currentPosition.getOpeningAt(3)) {
                    legalMoves.add(board.getTile(playerX, playerY));
                }
                if (currentPosition.getOpeningAt(2) == currentPosition.getOpeningAt(0)) {
                    legalMoves.add(board.getTile(playerX, playerY));
                }
                if (currentPosition.getOpeningAt(3) == currentPosition.getOpeningAt(1)) {
                    legalMoves.add(board.getTile(playerX, playerY));
                }
            }
            else if (currentPosition.getX()==board.getWidth()) {
                if (currentPosition.getOpeningAt(0) == currentPosition.getOpeningAt(2)) {
                    legalMoves.add(board.getTile(playerX, playerY));
                }
                if (currentPosition.getOpeningAt(1) == currentPosition.getOpeningAt(3)) {
                    legalMoves.add(board.getTile(playerX, playerY));
                }
                if (currentPosition.getOpeningAt(3) == currentPosition.getOpeningAt(1)) {
                    legalMoves.add(board.getTile(playerX, playerY));
                }
            }
            else if (currentPosition.getY()==0) {
                if (currentPosition.getOpeningAt(0) == currentPosition.getOpeningAt(2)) {
                    legalMoves.add(board.getTile(playerX, playerY));
                }
                if (currentPosition.getOpeningAt(1) == currentPosition.getOpeningAt(3)) {
                    legalMoves.add(board.getTile(playerX, playerY));
                }
                if (currentPosition.getOpeningAt(2) == currentPosition.getOpeningAt(0)) {
                    legalMoves.add(board.getTile(playerX, playerY));
                }
            }
            else if (currentPosition.getY()==board.getHeight()) {
                if (currentPosition.getOpeningAt(0) == currentPosition.getOpeningAt(2)) {
                    legalMoves.add(board.getTile(playerX, playerY));
                }
                if (currentPosition.getOpeningAt(3) == currentPosition.getOpeningAt(1)) {
                    legalMoves.add(board.getTile(playerX, playerY));
                }
                if (currentPosition.getOpeningAt(2) == currentPosition.getOpeningAt(0)) {
                    legalMoves.add(board.getTile(playerX, playerY));
                }
            }
            else if  (currentPosition.getX() > 0 && currentPosition.getY() > 0) {
                if (currentPosition.getOpeningAt(0) == currentPosition.getOpeningAt(2)) {
                    legalMoves.add(board.getTile(playerX, playerY));
                }
                if (currentPosition.getOpeningAt(1) == currentPosition.getOpeningAt(3)) {
                    legalMoves.add(board.getTile(playerX, playerY));
                }
                if (currentPosition.getOpeningAt(2) == currentPosition.getOpeningAt(0)) {
                    legalMoves.add(board.getTile(playerX, playerY));
                }
                if (currentPosition.getOpeningAt(3) == currentPosition.getOpeningAt(1)) {
                    legalMoves.add(board.getTile(playerX, playerY));
                }
            }

        } else {
            //throw exception here
        }

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
