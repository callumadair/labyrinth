
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
import objects.Controller;

import menu.*;
import objects.*;


import java.util.*;

public class Main extends Application {

    public static void main(String[] args) {
        System.out.println("Starting app");

        launch(args);
    }

    public void init() {

    }

    @Override
    public void start(Stage stage) throws Exception {
        ArrayList<PlayerController> players = new ArrayList<PlayerController>(); // testing only
        players.add(new PlayerController(null, 0)); // testing only
        players.add(new PlayerController(null, 1)); // testing only
        players.add(new PlayerController(null, 2)); // testing only
        Board board = FileManager.loadBoard(1);
        players.get(0).setX(5);
        players.get(0).setY(6);
        players.get(1).setX(5);
        players.get(1).setY(0);
        board.setPlayers(players);

        Game game = new Game(board);
        Scene scene = new Scene(game.getPane(), 800, 600, Color.WHITE);

        stage.setScene(scene);
        stage.show();
    }

}
