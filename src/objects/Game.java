package objects;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.util.ArrayList;

public class Game{

    private Scene scene;

    public void Game(String[][] boardData, ArrayList<PlayerController> players){
        BorderPane pane = new BorderPane();
        scene = new Scene(pane, 800, 600);
        Controller controller = new Controller(boardData, players);
        pane.setCenter(controller.getCanvas());


        pane.setBottom(getBottom());
        pane.setLeft(getLeft());
        pane.setRight(getRight());

        Button button = new Button("Draw a card");
        getBottom().getChildren().add(button);

        button.setOnAction(e ->  {
            controller.startGame();
        });

    }

    private Pane getLeft(){
        HBox left = new HBox();
        left.setPrefWidth(40);
        return left;
    }

    private Pane getRight(){
        Pane right = new Pane();
        right.setPrefWidth(40);
        return right;
    }

    private Pane getBottom(){
        VBox bottom = new VBox();
        bottom.setPrefHeight(40);


        return bottom;
    }
}



