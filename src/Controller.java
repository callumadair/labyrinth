import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import objects.Card;
import objects.FloorCard;
import objects.PlayerController;

import java.util.ArrayList;

public class Controller {

    enum GameState{
        DRAWING, INSERTING, ACTION_CARD, MOVING, END_TURN, VICTORY;
    }

    private ArrayList<PlayerController> players;
    private SilkBag silkBag;
    private Board board;
    private Canvas canvas;

    private FloorCard selectedTile;
    private Card playingCard;
    private GameState currentState;
    private PlayerController currentPlayer;

    public Controller(){
        startGame();
    }

    public void startGame(){
        //Create board players etc
        board = new Board();

        canvas = new Canvas(board.getWidth(), board.getHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();
        enableRetrievingTilesFromCanvas();


        board.drawBoard(gc);

        //Choose first player can go to drawing state
    }

    private void changeState(GameState state){
        currentState = state;
        startState(currentState);
    }

    private void startState(GameState state){
        switch (state){
            case DRAWING:
                drawCard();
                break;
            case INSERTING:
                getInsertionList();
                break;
            case ACTION_CARD:
                playingActionCard();
                break;
            case MOVING:
                moving();
                break;
            case END_TURN:
                endTurn();
                break;
            case VICTORY:
                endGame();
                break;
        }
    }

    private void drawCard(){
        playingCard = silkBag.drawACard();

        //do all the checks what state is the game going to be in
        //either inserting tile or action card playing
        //change state based on that
    }

    private void getInsertionList(){
        ArrayList<FloorCard> possibleInsertions = board.getInsertionPoints();
    }

    private void playingActionCard(){

    }

    private void moving() {

    }

    private void endTurn(){

    }

    private void endGame(){

    }

    private void enableRetrievingTilesFromCanvas(){
        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double x = event.getX();
                double y = event.getY();
                selectedTile = board.getTileFromCanvas(x, y);
            }
        });
    }
}
