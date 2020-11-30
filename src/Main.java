import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
<<<<<<< HEAD
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
=======
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
>>>>>>> origin/Stefani
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
import objects.Controller;
import objects.Game;
import objects.PlayerController;


public class Main extends Application {

    public static void main(String[] args) {
        System.out.println("Starting app");

        launch(args);
    }

    public void init() {

    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Labyrinth");
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 600, Color.WHITE);
        int width = 5;
        int height = 5;
        Controller c = new Controller();


        root.setCenter(c.getCanvas());

        stage.setScene(scene);
        stage.show();
    }
}
