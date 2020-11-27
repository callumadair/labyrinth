import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import objects.Controller;


public class Main extends Application {

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
        Controller c = new Controller();

        root.setCenter(c.getCanvas());
        stage.setScene(scene);
        stage.show();
    }
}
