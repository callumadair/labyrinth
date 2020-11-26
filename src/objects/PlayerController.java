package objects;


import java.util.ArrayList;
//

/**
 * The type Player controller.
 */
public class PlayerController {
    private int playerX;
    private int playerY;
    private boolean isGoalReached;
    private ArrayList<Card> cardsHeld;
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
        if((getBoard().getTile((playerX), playerY)).get(FloorCard.FloorType.GOAL)){
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

<<<<<<< HEAD
    /**
     * stores position of the player
     *
     * @param playerX
     * @param playerY
     */
    public void storePosition(int playerX, int playerY) {

        int[][] store = new int[board.getWidth()][board.getHeight()];
        int position = store[playerX][playerY];
    }

    /**
     * moves the player across the board
     *
     */
    public void movePlayer(int x, int y) {
        this.x = x;
        this.y = y;

        storePosition(playerX, playerY);
=======
    /*
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
>>>>>>> parent of 698cf2d... Merge branch 'menu' of https://github.com/CS230-Group-07/labyrinth into menu
    }
    */

    /**
     * Determine legal moves floor card [ ].
     *
     * @return the floor card [ ]
     */
    public ArrayList<FloorCard> determineLegalMoves() {
<<<<<<< HEAD
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

    /**
     * Use card.
     */
    public void useCard(Card card) {

    }
=======
        return null;
    }

    /*
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
    } */

>>>>>>> parent of 698cf2d... Merge branch 'menu' of https://github.com/CS230-Group-07/labyrinth into menu
}
