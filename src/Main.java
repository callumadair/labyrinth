import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import menu.FileManager;
import objects.*;

import java.util.ArrayList;

public class Main extends Application {

    public static void main(String[] args) {
        System.out.println("Starting app");

        launch(args);
    }

    public void init() {

    }

    @Override
    public void start(Stage stage) throws Exception {
        ArrayList<PlayerProfile> players = new ArrayList<>(); // testing only
        /*players.add(new PlayerController(null, 0)); // testing only
        players.add(new PlayerController(null, 1)); // testing only
        players.add(new PlayerController(null, 2)); // testing only
        players.get(0).setX(5);
        players.get(0).setY(6);
        players.get(1).setX(5);
        players.get(1).setY(0);*/
        Board board = FileManager.loadBoard(2, players);

        Game game = new Game(board);
        Scene scene = new Scene(game.getPane(), 800, 600, Color.WHITE);

        stage.setScene(scene);
        stage.show();
    }
}

