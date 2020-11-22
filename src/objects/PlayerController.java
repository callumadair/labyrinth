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

    /**
     * Is goal reached boolean.
     *
     * @return the boolean
     */
    public boolean isGoalReached() {
        if((Board.getTile((playerX), playerY)).equals(FloorCard.FloorType.GOAL)){
            return isGoalReached;
        } else {
            return false;
        }
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
        if ((Board.getTile((playerX + 1), playerY)).equals(FloorCard.FloorTileState.NORMAL)) { //determine legal moves
            this.playerX += 1;
        }
        if ((Board.getTile((playerX - 1), playerY)).equals(FloorCard.FloorTileState.NORMAL)) {
            this.playerX -= 1;
        }
        if ((Board.getTile((playerX), playerY + 1)).equals(FloorCard.FloorTileState.NORMAL)) {
            this.playerY += 1;
        }
        if ((Board.getTile((playerX), playerY - 1)).equals(FloorCard.FloorTileState.NORMAL)) {
            this.playerY -= 1;
        }
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
        Card card = SilkBag.drawACard();

        if (card.equals(FloorCard.FloorType.CORNER) || card.equals(FloorCard.FloorType.STRAIGHT)
            || card.equals(FloorCard.FloorType.T_SHAPED)) {
            Board.insertTile(card, x , y);// the player picks an available edge
        } else if(card.equals(ActionCard) {
                cardsHeld.add(card);
                //if player decides to use another card
                     if(cardsHeld.contains(ActionCard)) {
                    //player chooses a card to use
                    useCard();
                    movePlayer();
                }else{
                movePlayer();
                }
        }
    }

    /**
     * Use card.
     */
    public void useCard(Card card){
            if (card.equals(ActionCard.ActionType.IceCard)) {
                //chooses a tile on board
                FloorCard.setOnIce();
            }
            if (card.equals(ActionCard.ActionType.FireCard)) {
                //chooses a tile on board
                FloorCard.setOnFire();
            }
            if (card.equals(ActionCard.ActionType.DoubleMoveCard)) {
                movePlayer();
                movePlayer();
            }
            if (card.equals(ActionCard.ActionType.BackTrackCard)) {

            }
        }
}
