package objects;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;

public class Game {

    private Scene scene;

    public Game(String[][] boardData, ArrayList<PlayerController> players){
        BorderPane pane = new BorderPane();
        scene = new Scene(pane, 800, 600);
        Controller controller = new Controller(boardData, players);
        pane.setCenter(controller.getCanvas());
    }
}
