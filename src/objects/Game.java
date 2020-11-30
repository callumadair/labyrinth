package objects;

import javafx.application.Application;


import javafx.event.ActionEvent;


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



import java.awt.*;
import java.awt.event.ActionEvent;


import java.awt.*;
import java.awt.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;



import java.util.ArrayList;



public class Game extends Application {



    private Scene scene;


    public void Game(String[][] boardData, ArrayList<PlayerController> players){


       /* BorderPane pane = new BorderPane();

        scene = new Scene(pane, 800, 600);
        Controller controller = new Controller(boardData, players);
        pane.setCenter(controller.getCanvas());
*/


    }


    public static void main(String[] args){
        System.out.println("Starting app");

        launch(args);
    }

    public void init(){

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


}


