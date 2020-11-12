import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.canvas.*;

import java.util.ArrayList;
import java.util.Observable;

public class Main extends Application {


    //FlowPane for menu
    //BorderPane for game
    //TilePane for the board
    public static void main(String[] args){
        System.out.println("Starting app");

        launch(args);
    }

    public void init(){

    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Labyrinth");
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 600, Color.WHITE);
        stage.setScene(scene);

        final Canvas canvas = new Canvas(640,480);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image image = new Image("tile.png");
        gc.setFill(Color.BLUE);
        gc.fillRect(75,75,100,100);

        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                gc.drawImage(image, i * 61, j * 61);
            }
        }

        root.setCenter(canvas);

        stage.show();

    }

    private void userInput(){

    }

    private void draw(){

    }

    private void update(){

    }

    public void stop(){

    }
}

        /*
        stage.setTitle("Labyrinth");

        FlowPane rootNode = new FlowPane(Orientation.VERTICAL,5, 5);
        rootNode.setAlignment(Pos.CENTER);

        Scene scene = new Scene(rootNode, 300, 400);

        stage.setScene(scene);

        stage.show();

        Label keyPress = new Label("PressKey");
        rootNode.getChildren().add(keyPress);

        Label mouseCord = new Label("mouse");
        rootNode.getChildren().add(mouseCord);

        Label mousePress = new Label("count");
        rootNode.getChildren().add(mousePress);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent k) {
                keyPress.setText("" + k.getCode());
            }
        });

        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent m) {
                int clickCount = m.getClickCount();
                if(clickCount > 1){
                    mousePress.setText("" + clickCount);
                } else {
                    mousePress.setText("count");
                }
            }
        });

        scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent m) {
                mouseCord.setText("x: " + m.getSceneX() + " y: " + m.getSceneY());
            }
        });

        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.SECONDARY) event.consume();
            }
        });

        //For board selection
        ArrayList<String> boards = new ArrayList<String>();
        for(int i = 0; i < 8; i++){
            boards.add("String" + i);
        }
        ObservableList<String> ss = FXCollections.observableArrayList(boards);

        ChoiceBox<String> choiceBox = new ChoiceBox<String>(ss);
        rootNode.getChildren().add(choiceBox);
        choiceBox.setTooltip(new Tooltip("Choose board"));

        Label choiceLabel = new Label("Choice");
        rootNode.getChildren().add(choiceLabel);

        SingleSelectionModel<String> selectionModel = choiceBox.getSelectionModel();

        selectionModel.selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                choiceLabel.setText("" + newValue);
            }
        });

        //Separator
        Separator separator = new Separator();
        separator.setValignment(VPos.BOTTOM);
        rootNode.getChildren().add(separator);

        //Not for game
        ArrayList<String> players = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            players.add("Guest" + i);
        }
        ObservableList<String> pp = FXCollections.observableArrayList(players);

        ListView<String> listView = new ListView<>(pp);
        rootNode.getChildren().add(listView);
        listView.setPrefSize(50,80);

        Label listLabel = new Label("items");
        rootNode.getChildren().add(listLabel);

        MultipleSelectionModel<String> listViewSelection = listView.getSelectionModel();
        listViewSelection.setSelectionMode(SelectionMode.MULTIPLE);

        listViewSelection.selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String selItems = "";
                ObservableList<String> selected = listViewSelection.getSelectedItems();

                for(String item : selected){
                    selItems += "\n" + item;
                }

                listLabel.setText(selItems);
            }
        });
        */