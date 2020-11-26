<<<<<<< HEAD
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import objects.Controller;


public class Main extends Application {

    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

    //FlowPane for menu
    //BorderPane for game
    //TilePane for the board
    public static void main(String[] args){
        System.out.println("Starting app");

        launch(args);
    }

    public void init(){

    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Labyrinth");
        BorderPane root = new BorderPane ();
        Scene scene = new Scene(root, 800, 600, Color.WHITE);
        int width = 5;
        int height = 5;
        Controller c = new Controller(null, null);

        root.setCenter(c.getCanvas());
        stage.setScene(scene);
        stage.show();
    }
}
=======
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import objects.FloorCard;


public class Main extends Application {

    //

    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

    //FlowPane for menu
    //BorderPane for game
    //TilePane for the board
    public static void main(String[] args){
        System.out.println("Starting app");

        launch(args);
    }

    public void init(){

    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Labyrinth");
        BorderPane root = new BorderPane ();
        Scene scene = new Scene(root, 800, 600, Color.WHITE);
        int width = 5;
        int height = 5;
        Canvas canvas = new Canvas(width * FloorCard.TILE_SIZE, height * FloorCard.TILE_SIZE);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Image im = new Image("tile.png");

        root.setCenter(canvas);
        stage.setScene(scene);
        stage.show();
    }
}
>>>>>>> parent of 698cf2d... Merge branch 'menu' of https://github.com/CS230-Group-07/labyrinth into menu
