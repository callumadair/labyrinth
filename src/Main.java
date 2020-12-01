
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

    Label highlightedPlayer;
    PlayerController currentPlayer;
    Controller controller;
    ArrayList<Label> playerTags;
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
        /*
        stage.setTitle("Labyrinth");
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 600, Color.WHITE);

        ArrayList<PlayerController> players = new ArrayList<PlayerController>(); // testing only
        players.add(new PlayerController(null, 0)); // testing only
        players.add(new PlayerController(null, 1)); // testing only
        players.add(new PlayerController(null, 2)); // testing only
        Board board = FileManager.loadBoard(1);
        board.setPlayers(players);

        controller = new Controller(board);
        playerTags = new ArrayList<>();
        VBox left = new VBox();
        for (int i = 0; i < controller.getPlayers().size(); i++) {
            playerTags.add(new Label(controller.getPlayers().get(i).toString()));
            playerTags.get(i).setFont(Font.font("Cambria", FontPosture.REGULAR, 20));
            playerTags.get(i).setTextFill(Color.BLACK);
            left.getChildren().add(playerTags.get(i));
        }

        VBox right = new VBox();
        right.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double x = event.getX();
                double y = event.getY();

                System.out.println("VBOX: " + x + "|" + y);
            }
        });
        right.setAlignment(Pos.TOP_CENTER);
        ActionCard card = new ActionCard("FIRE");
        for(int i = 0; i < 3; i++){
            ImageView cardDisplay = new ImageView();
            cardDisplay.setImage(card.getImage());
            cardDisplay.setPickOnBounds(true);
            cardDisplay.setCursor(Cursor.HAND);

            right.getChildren().add(cardDisplay);
        }
        VBox botttom = new VBox();
        ImageView im1 = new ImageView();
        im1.setImage(card.getImage());
        botttom.getChildren().add(im1);
        root.setTop(botttom);
        root.setRight(right);
        highlightPlayer();
        listenForPlayerChange();
        root.setCenter(controller.getCanvas());
        root.setLeft(left);
        stage.setScene(scene);
        stage.show();

         */
        ArrayList<PlayerController> players = new ArrayList<PlayerController>(); // testing only
        players.add(new PlayerController(null, 0)); // testing only
        players.add(new PlayerController(null, 1)); // testing only
        players.add(new PlayerController(null, 2)); // testing only
        Board board = FileManager.loadBoard(1);
        board.setPlayers(players);
        
        Game game = new Game(board);
        Scene scene = new Scene(game.getPane(), 800, 600, Color.WHITE);

        stage.setScene(scene);
        stage.show();
    }

}
