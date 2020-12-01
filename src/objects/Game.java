package objects;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
import menu.FileManager;

import java.util.ArrayList;

public class Game {

    private Controller controller;
    private BorderPane pane;

    //Left
    private ArrayList<Label> playerTags;
    private Label highlightedPlayer;

    //Bottom
    private ImageView playingCardImage;

    //Right
    private VBox right;

    public Game(Board board) {
        this.controller = new Controller(board);

        pane = new BorderPane();
        pane.setCenter(controller.getCanvas());
        this.createBottomPane();
        this.createLeftPane();
        this.createRightPane();

        this.listenForPlayerChange();
        this.listenForCardChange();
        this.enableActionCardSelection();
    }

    private void createLeftPane() {
        playerTags = new ArrayList<>();
        VBox left = new VBox();
        for (int i = 0; i < controller.getPlayers().size(); i++) {
            playerTags.add(new Label(controller.getPlayers().get(i).toString()));
            playerTags.get(i).setFont(Font.font("Cambria", FontPosture.REGULAR, 20));
            playerTags.get(i).setTextFill(Color.BLACK);
            left.getChildren().add(playerTags.get(i));
        }
        pane.setLeft(left);
        highlightPlayer();
    }

    private void highlightPlayer() {
        if (highlightedPlayer != null) {
            highlightedPlayer.setTextFill(Color.BLACK);
        }
        highlightedPlayer = playerTags.get(controller.getCurrentPlayerIndex().getValue());
        highlightedPlayer.setTextFill(Color.DEEPPINK);
    }

    private void createRightPane() {
        right = new VBox();
        right.setAlignment(Pos.TOP_CENTER);
        showPlayersActionCard();

        pane.setRight(right);
    }

    private void showPlayersActionCard() {
        Glow glow = new Glow();
        glow.setLevel(0.7);

        for (ActionCard card : controller.getCurrentPlayer().getCardsHeld()) {
            ImageView cardDisplay = new ImageView();
            cardDisplay.setImage(card.getImage());
            cardDisplay.setPickOnBounds(true);
            cardDisplay.setCursor(Cursor.HAND);

            if(card.canBeUsed()){
                cardDisplay.setEffect(glow);
            }

            right.getChildren().add(cardDisplay);
        }
    }

    private void createBottomPane() {
        VBox bottom = new VBox();
        bottom.setAlignment(Pos.CENTER);

        Glow glow = new Glow();
        glow.setLevel(0.9);

        playingCardImage = new ImageView();
        playingCardImage.setEffect(glow);
        bottom.getChildren().add(playingCardImage);

        pane.setBottom(bottom);
    }

    private void listenForPlayerChange() {
        controller.getCurrentPlayerIndex().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                highlightPlayer();
                showPlayersActionCard();
            }
        });
    }

    private void listenForCardChange() {
        controller.getCardSelectionFlag().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (controller.getPlayingCard() != null) {
                    playingCardImage.setImage(controller.getPlayingCard().getImage());
                } else {
                    playingCardImage.setImage(null);
                }
            }
        });
    }

    private void enableActionCardSelection(){
        right.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double y = event.getY();

                selectActionCard(y);
            }
        });
    }

    private void selectActionCard(double y){
        int cordY = (int) (y / ActionCard.CARD_SIZE);

        if(controller.getCurrentPlayer().getCardsHeld().size() > cordY){
            controller.setPlayingCard(controller.getCurrentPlayer().getCardsHeld().get(cordY));
        }
    }

    public BorderPane getPane(){
        return pane;
    }
}




