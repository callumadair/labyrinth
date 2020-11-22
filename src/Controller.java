import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
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
        switch (state){
            case DRAWING:
                break;
            case INSERTING:
                break;
            case ACTION_CARD:
                break;
            case MOVING:
                break;
            case END_TURN:
                break;
            case VICTORY:
                break;
        }
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
