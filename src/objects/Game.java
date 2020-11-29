package objects;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Font;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.awt.event.ActionEvent;

import java.util.ArrayList;

public class Game{

    private Scene scene;
    //private Image;


    public void Game(String[][] boardData, ArrayList<PlayerController> players){

        BorderPane pane = new BorderPane();
        scene = new Scene(pane, 800, 600);
        Controller controller = new Controller(boardData, players);
        pane.setCenter(controller.getCanvas());


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



    }
}



