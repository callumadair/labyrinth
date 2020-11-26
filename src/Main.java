import javafx.application.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.*;
import objects.*;



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
