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
<<<<<<< HEAD

import java.awt.*;
import java.awt.event.ActionEvent;
=======
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
>>>>>>> origin/Stefani

import java.util.ArrayList;

public class Game extends Application{

    private Scene scene;


    public void Game(String[][] boardData, ArrayList<PlayerController> players){

<<<<<<< HEAD
        BorderPane pane = new BorderPane();
=======
       /* BorderPane pane = new BorderPane();
>>>>>>> origin/Stefani
        scene = new Scene(pane, 800, 600);
        Controller controller = new Controller(boardData, players);
        pane.setCenter(controller.getCanvas());

*/

<<<<<<< HEAD
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

=======
    }

    public static void main(String[] args){
        System.out.println("Starting app");

        launch(args);
    }

    public void init(){

    }
    public void start(Stage stage) throws Exception {

        stage.setTitle("Labyrinth");
        BorderPane root = new BorderPane ();
        Scene scene = new Scene(root, 800, 600, Color.WHITE);
        int width = 5;
        int height = 5;
        Controller c = new Controller();
        root.setCenter(c.getCanvas());
>>>>>>> origin/Stefani

        VBox bottom = new VBox();
        Button button = new Button("Draw a card");
        bottom.getChildren().add(button);

<<<<<<< HEAD
=======
        button.setOnAction(e ->  {
            ImageView image = new ImageView();
           // image.setImage(c.getPlayingCard().);
            c.getPlayingCard();
        });
        root.setBottom(bottom);


        VBox left = new VBox();
        for (PlayerController player : c.getPlayers()) {
            if(player.equals(c.getCurrentPlayer())){
                Label playersInGame = new Label(player.toString());
                playersInGame.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
                left.getChildren().add(playersInGame);
            }else {
                Label playersInGame = new Label(player.toString());
                playersInGame.setFont(Font.font("Verdana", FontPosture.REGULAR, 18));
                left.getChildren().add(playersInGame);
            }

        }

        root.setLeft(left);

        VBox right = new VBox();
        for(Card cards: c.getCurrentPlayer().getCardsHeld()){
            ImageView image = new ImageView();
            if(cards instanceof ActionCard){
            image.setImage(((ActionCard) cards).getImage());
            right.getChildren().add(image);
            }
        }
        root.setRight(right);


        stage.setScene(scene);
        stage.show();
>>>>>>> origin/Stefani
    }

}



