package objects;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.util.ArrayList;

public class Game extends Application{

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
        BorderPane root = new BorderPane ();
        Scene scene = new Scene(root, 800, 600, Color.WHITE);
        int width = 5;
        int height = 5;
        Controller c = new Controller();
        root.setCenter(c.getCanvas());

        VBox bottom = new VBox();
        Button button = new Button("Draw a card");
        bottom.getChildren().add(button);

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
    }

}



