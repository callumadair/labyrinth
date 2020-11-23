package objects;

import java.util.*;

/*
public class PlayerController {
    private int playerX;
    private int playerY;
    private boolean isGoalReached;
    private ArrayList<Card> cardsHeld;


    public PlayerController() {

    }


    public int getPlayerX() {
        return playerX;
    }


    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }


    public int getPlayerY() {
        return playerY;
    }


    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }


    public boolean isGoalReached() {
        if((Board.getTile((playerX), playerY)).equals(FloorCard.FloorType.GOAL)){
            return isGoalReached;
        } else {
            return false;
        }
    }


    public void setGoalReached(boolean goalReached) {
        isGoalReached = goalReached;
    }


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


    public FloorCard[] determineLegalMoves() {
        return null;
    }


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
}*/
