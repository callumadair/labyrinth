package objects;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

import java.util.ArrayList;

public class Game {

    private Controller controller;
    private BorderPane pane;

    //Left
    private ArrayList<Label> playerTags;
    private Label highlightedPlayer;
    private Button skipActionState;

    //Bottom
    private HBox bottom;
    private ImageView playingCardImage;
    private Label label;

    //Right
    private VBox right;
    private ArrayList<ImageView> cardsDisplayed;

    public Game(Board board) {
        this.controller = new Controller(board);

        pane = new BorderPane();
        pane.setCenter(controller.getCanvas());

        this.createBottomPane();
        this.createLeftPane();
        this.createRightPane();

        this.listenForPlayerChange();
        this.listenForCardChange();
        this.listenForStateChange();
        this.enableActionCardSelection();
        this.enableRotatingInsertionTile();
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
        cardsDisplayed = new ArrayList<>();

        skipActionState = new Button();
        skipActionState.setText("Skip Playing");
        skipActionState.setOnAction((event) -> {
            if(controller.getCurrentState() == Controller.GameState.ACTION_CARD){
                controller.changeState(Controller.GameState.MOVING);
            }
        });
        skipActionState.setVisible(false);

        VBox rightPane = new VBox();
        rightPane.getChildren().add(right);
        rightPane.getChildren().add(skipActionState);
        showPlayersActionCard();

        pane.setRight(rightPane);
    }

    private void showPlayersActionCard() {
        Glow glow = new Glow();
        glow.setLevel(0.7);

        clearDisplayedCards();

        if(!controller.getCurrentPlayer().getCardsHeld().isEmpty()){
            for (ActionCard card : controller.getCurrentPlayer().getCardsHeld()) {
                ImageView cardDisplay = new ImageView();
                cardDisplay.setImage(card.getImage());
                cardDisplay.setPickOnBounds(true);
                cardDisplay.setCursor(Cursor.HAND);

                if (card.canBeUsed()) {
                    cardDisplay.setEffect(glow);
                }
                cardsDisplayed.add(cardDisplay);
                right.getChildren().add(cardDisplay);
            }

        }
    }

    private void clearDisplayedCards() {
        if (!cardsDisplayed.isEmpty()) {
            for (ImageView imageView : cardsDisplayed) {
                right.getChildren().remove(imageView);
            }
            cardsDisplayed.clear();
        }
    }

    private void createBottomPane() {
        bottom = new HBox();
        bottom.setAlignment(Pos.CENTER);

        Glow glow = new Glow();
        glow.setLevel(0.9);

        label = new Label();
        bottom.getChildren().add(label);
        label.setVisible(true);

        playingCardImage = new ImageView();
        playingCardImage.setPickOnBounds(true);
        playingCardImage.setEffect(glow);
        bottom.getChildren().add(playingCardImage);
        playingCardImage.setImage(controller.getPlayingCard().getImage());

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


    private void listenForStateChange() {
        controller.getStateChangeFlag().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (controller.getCurrentState() == Controller.GameState.DRAWING) {
                    label.setText("Card Drawn:");
                    label.setVisible(true);
                    showPlayersActionCard();
                }
                if (controller.getCurrentState() == Controller.GameState.ACTION_CARD) {
                    skipActionState.setVisible(true);
                }
                if (controller.getCurrentState() == Controller.GameState.MOVING) {
                    skipActionState.setVisible(false);
                    label.setVisible(false);
                }
                if (controller.getCurrentState() == Controller.GameState.VICTORY) {
                    //end game
                    //display the winners name
                    //change the leaderboard for the given board
                    //display two buttons on screen 'go back to menu' and 'quit game'
                }
            }
        });
    }


    private void enableActionCardSelection() {
        right.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double y = event.getY();

                selectActionCard(y);
            }
        });
    }

    private void enableRotatingInsertionTile(){
        playingCardImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                rotateCurrentTile();
            }
        });
    }

    private void rotateCurrentTile() {
        if(controller.getCurrentState() == Controller.GameState.INSERTING){
            FloorCard card = (FloorCard)controller.getPlayingCard();
            card.nextRotation();
            playingCardImage.setImage(controller.getPlayingCard().getImage());
        }
    }

    private void selectActionCard(double y) {
        if (controller.getCurrentState() == Controller.GameState.ACTION_CARD) {
            int cordY = (int) (y / ActionCard.CARD_SIZE);

            if (controller.getCurrentPlayer().getCardsHeld().size() > cordY) {
                label.setText("Playing Card:");
                controller.setPlayingCard(controller.getCurrentPlayer().getCardsHeld().get(cordY));
            }
        }
    }

    public BorderPane getPane() {
        return pane;
    }
}




