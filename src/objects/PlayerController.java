package objects;

import javafx.scene.image.Image;

import java.util.ArrayList;

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
    private PlayerProfile profile;
    private int playerIndex;

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

    /**
     * stores position of the player
     *
     * @param playerX
     * @param playerY
     */

    public void storePosition(int playerX, int playerY) {
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

    /**
     * Determine legal moves floor card [].
     *
     * @return the floor card []
     */
    public ArrayList<FloorCard> determineLegalMoves(Board board) {
        ArrayList<FloorCard> legalMoves = new ArrayList<>();
        FloorCard currentPosition = board.getTile(x, y);

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
