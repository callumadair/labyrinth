package objects;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
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


public class Game{

    private Controller controller;
    private BorderPane pane;


    public void Game(String[][] boardData, ArrayList<PlayerController> players){


        launch(args);
    }

    public void Game(Board board) {
        controller = new Controller(board);
        pane = new BorderPane();

        this.createBottomPane();
        this.createLeftPane();
        this.createRightPane();
    }

    public void init() {

    }

    private void createLeftPane() {
        VBox left = new VBox();
        left.setAlignment(Pos.CENTER);

        for (PlayerController player : controller.getPlayers()) {
            if (player.equals(controller.getCurrentPlayer())) {

                Label playersInGame = new Label(player.toString());
                playersInGame.setFont(Font.font("Cambria", FontPosture.REGULAR, 20));
                playersInGame.setTextFill(Color.DEEPPINK);
                playersInGame.setOnMouseEntered(e -> {
                    playersInGame.setScaleX(1.5);
                    playersInGame.setScaleY(1.5);
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

        pane.setLeft(left);

    }

    private void createRightPane() {
        VBox right = new VBox();
        right.setAlignment(Pos.CENTER);


        for (ActionCard card : controller.getCurrentPlayer().getCardsHeld()) {
            ImageView image = new ImageView();
            image.setImage(card.getImage());
            Glow glow = new Glow();
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


        pane.setRight(right);

    }

    private void createBottomPane() {
        Glow glow = new Glow();
        glow.setLevel(0.9);

        VBox bottom = new VBox();
        bottom.setAlignment(Pos.CENTER);

        ImageView playingCard = new ImageView();
        playingCard.setImage(controller.getPlayingCard().getImage());
        playingCard.setEffect(glow);
        bottom.getChildren().add(playingCard);

        pane.setBottom(bottom);
    }

    public void start(Stage stage) throws Exception {

        BorderPane root = new BorderPane();


    }

    }
}



