
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import objects.Controller;

import javafx.application.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.*;
import menu.*;
import objects.*;


import java.util.*;

public class Main extends Application {

    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

    // FlowPane for menu
    // BorderPane for game
    // TilePane for the board
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

        ArrayList<PlayerController> players = new ArrayList<PlayerController>(); // testing only
        players.add(new PlayerController(null, 0)); // testing only
        players.add(new PlayerController(null, 1)); // testing only
        players.add(new PlayerController(null, 2)); // testing only
        Board board = FileManager.loadBoard(1);
        board.setPlayers(players);

        Controller c = new Controller(board);

        root.setCenter(c.getCanvas());

        stage.setScene(scene);
        stage.show();
    }
}


