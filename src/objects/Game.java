package objects;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.awt.event.ActionEvent;

import java.util.ArrayList;

public class Game extends Application {

    private Scene scene;
    private Controller controller;

    public void Game(Board board, ArrayList<PlayerController> players){ }

    public static void main(String[] args) {
        System.out.println("Starting app");

        launch(args);
    }

    public void init() {

        // left pane
        //prints out players from 0 because of player index
        VBox left = new VBox();
        for (PlayerController player : controller.getPlayers()) {
            Label playersInGame = new Label(player.toString());
            if (player.equals(controller.getCurrentPlayer())) {
                playersInGame.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
            } else {
                playersInGame.setFont(Font.font("Verdana", FontPosture.REGULAR, 18));
            }
            left.getChildren().add(playersInGame);
            left.setAlignment(Pos.CENTER_LEFT);
            pane.setLeft(left);
		}
            // right pane
            //

        VBox right = new VBox();
        ImageView selectedImage = new ImageView();
        Image doubleCard = new Image(Game.class.getResourceAsStream("resources/ROAD-CardDouble.png"));
        Image iceCard = new Image(Game.class.getResourceAsStream("resources/ROAD-CardIce.png"));
        Image fireCard = new Image(Game.class.getResourceAsStream("resources/ROAD-CardFire.png"));
        Image backtrackCard = new Image(Game.class.getResourceAsStream("resources/ROAD-CardReverse.png"));
        selectedImage.setImage(doubleCard);
        selectedImage.setImage(iceCard);
        selectedImage.setImage(fireCard);
        selectedImage.setImage(backtrackCard);
        right.getChildren().addAll(selectedImage);
        right.setAlignment(Pos.BOTTOM_CENTER);
        pane.setRight(selectedImage);
	}
	
    public void start(Stage stage) throws Exception {

        stage.setTitle("Labyrinth");
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 600, Color.WHITE);
        int width = 5;
        int height = 5;
        Controller c = new Controller();
        root.setCenter(c.getCanvas());

        //Bottom
        Glow glow = new Glow();
        glow.setLevel(0.9);

        VBox bottom = new VBox();
        bottom.setAlignment(javafx.geometry.Pos.CENTER);

        ImageView playingCard = new ImageView();
        playingCard.setImage(c.getPlayingCard().getImage());
        playingCard.setEffect(glow);
        bottom.getChildren().add(playingCard);

        root.setBottom(bottom);

//Left
        VBox left = new VBox();
        left.setAlignment(javafx.geometry.Pos.CENTER);

        for (PlayerController player : c.getPlayers()) {
            if (player.equals(c.getCurrentPlayer())) {

                Label playersInGame = new Label(player.toString());
                playersInGame.setFont(Font.font("Cambria", FontPosture.REGULAR, 20));
                playersInGame.setTextFill(Color.DEEPPINK);
                playersInGame.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent e) {
                        playersInGame.setScaleX(1.5);
                        playersInGame.setScaleY(1.5);
                    }
                });
                playersInGame.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent e) {
                        playersInGame.setScaleX(1);
                        playersInGame.setScaleY(1);
                    }
                });
                left.getChildren().add(playersInGame);
            } else {
                Label playersInGame = new Label(player.toString());
                playersInGame.setFont(Font.font("Cambria", FontPosture.REGULAR, 20));
                left.getChildren().add(playersInGame);

                playersInGame.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent e) {
                        playersInGame.setScaleX(1.5);
                        playersInGame.setScaleY(1.5);
                    }
                });
                playersInGame.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent e) {
                        playersInGame.setScaleX(1);
                        playersInGame.setScaleY(1);
                    }
                });
            }

        }

        root.setLeft(left);

//Right
        VBox right = new VBox();
        right.setAlignment(javafx.geometry.Pos.CENTER);


        for (ActionCard card : c.getCurrentPlayer().getCardsHeld()) {
            ImageView image = new ImageView();
            image.setImage(card.getImage());
            image.setEffect(glow);
            image.setPickOnBounds(true);

            image.setCursor(Cursor.HAND);

            image.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    c.playActionCard();
                }
            });
            right.getChildren().add(image);
        }

        Button button = new Button("Skip Action");

        right.getChildren().add(button);

        button.setOnAction(e -> {
            c.changeState(Controller.GameState.MOVING);
        });



        }

        root.setRight(right);


        stage.setScene(scene);
        stage.show();

}




