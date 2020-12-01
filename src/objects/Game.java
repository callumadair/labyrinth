package objects;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;

public class Game extends Application {

    private Controller controller;
    private BorderPane pane;

    private VBox left;
    private VBox right;
    private VBox bottom;

    public void Game(Board board) {
        controller = new Controller(board);


        this.createBottomPane();
        this.createLeftPane();
        this.createRightPane();
    }

    public static void main(String[] args) {
        System.out.println("Starting app");

        launch(args);
    }

    public void init() {

    }

    private void createLeftPane() {

    }

    private void createRightPane() {

    }

    private void createBottomPane() {

    }

    public void start(Stage stage) throws Exception {

        BorderPane root = new BorderPane();

        //Bottom
        Glow glow = new Glow();
        glow.setLevel(0.9);

        bottom = new VBox();
        bottom.setAlignment(Pos.CENTER);

        ImageView playingCard = new ImageView();
        playingCard.setImage(controller.getPlayingCard().getImage());
        playingCard.setEffect(glow);
        bottom.getChildren().add(playingCard);

        root.setBottom(bottom);

//Left
        left = new VBox();
        left.setAlignment(Pos.CENTER);

        for (PlayerController player : controller.getPlayers()) {
            if (player.equals(controller.getCurrentPlayer())) {

                Label playersInGame = new Label(player.toString());
                playersInGame.setFont(Font.font("Cambria", FontPosture.REGULAR, 20));
                playersInGame.setTextFill(Color.DEEPPINK);
                playersInGame.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        playersInGame.setScaleX(1.5);
                        playersInGame.setScaleY(1.5);
                    }
                });
                playersInGame.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
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
                    @Override
                    public void handle(MouseEvent e) {
                        playersInGame.setScaleX(1.5);
                        playersInGame.setScaleY(1.5);
                    }
                });
                playersInGame.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        playersInGame.setScaleX(1);
                        playersInGame.setScaleY(1);
                    }
                });
            }

        }

        root.setLeft(left);

//Right
        right = new VBox();
        right.setAlignment(Pos.CENTER);


        for (ActionCard card : controller.getCurrentPlayer().getCardsHeld()) {
            ImageView image = new ImageView();
            image.setImage(card.getImage());
            image.setEffect(glow);
            image.setPickOnBounds(true);

            image.setCursor(Cursor.HAND);

            image.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    controller.playActionCard();
                }
            });
            right.getChildren().add(image);
        }

        Button button = new Button("Skip Action Card");

        right.getChildren().add(button);

        button.setOnAction(e -> {
            controller.changeState(Controller.GameState.MOVING);
        });


        root.setRight(right);
    }

}




