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
        players.add(new PlayerProfile("Cal", 1, 3, 1));
        players.add(new PlayerProfile("Luke", 3, 1, 2));
        Board board = FileManager.loadBoard("board1", players);

        Game game = new Game(board);
        Scene scene = new Scene(game.getPane(), 800, 600, Color.WHITE);

        stage.setScene(scene);
        stage.show();
    }
}

