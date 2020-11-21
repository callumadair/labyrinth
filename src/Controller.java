import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import objects.FloorCard;

public class Controller {

    private Board board;
    private Canvas canvas;
    private Image image;
    private FloorCard selectedTile;

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
    }

    private void enableRetrievingTilesFromCanvas(){
        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double x = event.getX();
                double y = event.getY();
                selectedTile = board.getTile(x, y);
            }
        });
    }
}
