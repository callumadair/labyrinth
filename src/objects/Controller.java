package objects;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class Controller {

    private IntegerProperty currentPlayerIndex;
    private BooleanProperty cardSelectionFlag;
    private BooleanProperty stateChangeFlag;

    private ArrayList<PlayerController> players;
    private int playerIndex = 0;
    private int numOfPlayers = 0;
    private Board board;
    private Canvas canvas;

    private FloorCard selectedTile;
    private Card playingCard;
    private GameState currentState;

    private PlayerController currentPlayer;
    private ArrayList<FloorCard> tilesToCompare;

    public Controller(Board b) {
        board = b;
        this.players = b.getPlayers();

        canvas = new Canvas(board.getWidth() * FloorCard.TILE_SIZE,
                board.getHeight() * FloorCard.TILE_SIZE);
        enableRetrievingTilesFromCanvas();

        draw();
        startGame();
    }

    enum GameState {
        DRAWING, INSERTING, ACTION_CARD, MOVING, END_TURN, VICTORY;
    }

    public void startGame() {
        numOfPlayers = players.size() - 1;
        currentPlayer = players.get(playerIndex);
        currentPlayerIndex = new SimpleIntegerProperty(playerIndex);
        cardSelectionFlag = new SimpleBooleanProperty(false);
        stateChangeFlag = new SimpleBooleanProperty(false);
        changeState(GameState.DRAWING);
    }

    public void changeState(GameState state) {
        currentState = state;
        getStateChangeFlag().set(!getStateChangeFlag().getValue());
        startState(currentState);
    }

    private void startState(GameState state) {
        switch (state) {
            case DRAWING:
                drawCard();
                break;
            case INSERTING:
                getInsertionList();
                break;
            case ACTION_CARD:
                showActionCards();
                break;
            case MOVING:
                getLegalMoves();
                break;
            case END_TURN:
                endTurn();
                break;
            case VICTORY:
                endGame();
                break;
        }
    }

    private void playState() {
        switch (currentState) {
            case INSERTING:
                insert();
                break;
            case ACTION_CARD:
                playActionCard();
                break;
            case MOVING:
                movePlayer();
                break;
        }
    }

    private void drawCard() {
        setPlayingCard(board.getSilkBag().drawACard());
        currentPlayerIndex.set(currentPlayer.getPlayerIndex());
        currentPlayer.setCurrentPlayer(!currentPlayer.isCurrentPlayer());

        if(!board.getFrozenTiles().isEmpty()){
            ArrayList<FloorCard> frozenTilesToRemove = new ArrayList<>();
            for(FloorCard card : board.getFrozenTiles()){
                card.decrementEffectTimer();
                if(!card.isEffectActive()){
                    frozenTilesToRemove.add(card);
                }
            }
            board.getFrozenTiles().removeAll(frozenTilesToRemove);
        }

        if(!board.getTilesOnFire().isEmpty()){
            ArrayList<FloorCard> tilesOnFireToRemove = new ArrayList<>();
            for(FloorCard card : board.getTilesOnFire()){
                card.decrementEffectTimer();
                if(!card.isEffectActive()){
                    tilesOnFireToRemove.add(card);
                }
            }
            board.getTilesOnFire().removeAll(tilesOnFireToRemove);
        }

        draw();

        if (playingCard instanceof FloorCard) {
            changeState(GameState.INSERTING);
        } else if (playingCard instanceof ActionCard) {
            changeState(GameState.ACTION_CARD);
        }
    }

    private void getInsertionList() {
        tilesToCompare = board.getInsertionPoints();

        if(tilesToCompare.isEmpty()){
            playingCard = null;
            changeState(GameState.ACTION_CARD);
        } else {
            highlightTiles();
        }
    }

    private void insert() {
        if (tilesToCompare.contains(selectedTile)) {
            playingCard.useCard(board, selectedTile.getX(), selectedTile.getY());
            clearSelection();
            draw();
            changeState(GameState.ACTION_CARD);
        } else {
            selectedTile = null;
        }
    }

    public void playActionCard() {
        if (playingCard != null && selectedTile != null) {
            if (playingCard.useCard(board, selectedTile.getX(), selectedTile.getY())) {
                currentPlayer.getCardsHeld().remove((ActionCard) playingCard);
                clearSelection();
                draw();
                changeState(GameState.MOVING);
            } else {
                selectedTile = null;
            }
        }
    }

    private void showActionCards() {
        ArrayList<ActionCard> cardHeldByCurrentPlayer = currentPlayer.getCardsHeld();

        /*
        set the last drawn card by a player so that it can be used this turn
         */
        if (!cardHeldByCurrentPlayer.isEmpty()) {
            cardHeldByCurrentPlayer.get(cardHeldByCurrentPlayer.size() - 1).setCanBeUsed();
        }

        /*
        if playingCard is not null it means player has drawn action card
        add it at the end of the list so that it is not usable yet
         */
        if (cardHeldByCurrentPlayer.isEmpty() && playingCard == null) {
            changeState(GameState.MOVING);
        } else if (playingCard != null) {
            cardHeldByCurrentPlayer.add((ActionCard) playingCard);
        }

    }

    private void getLegalMoves() {
        tilesToCompare = currentPlayer.determineLegalMoves(board);
        if (tilesToCompare.isEmpty()) {
            //notify player what happened
            changeState(GameState.END_TURN);
        } else {
            highlightTiles();
        }
    }

    private void movePlayer() {
        if (tilesToCompare.contains(selectedTile)) {
            if (selectedTile.checkGoal()) {
                board.changePlayerPosition(currentPlayer, selectedTile.getX(), selectedTile.getY());
                draw();
                clearSelection();
                changeState(GameState.VICTORY);
            } else {
                board.changePlayerPosition(currentPlayer, selectedTile.getX(), selectedTile.getY());
                draw();
                clearSelection();
                if (currentPlayer.checkDoubleMove()) {
                    currentPlayer.setDoubleMove(false);
                    changeState(GameState.MOVING);
                } else {
                    changeState(GameState.END_TURN);
                }
            }
        } else {
            selectedTile = null;
        }

    }

    private void highlightTiles() {
        /*
        canvas.getGraphicsContext2D().setStroke(Color.GREEN);
        canvas.getGraphicsContext2D().setFill(Color.GREEN);
        canvas.getGraphicsContext2D().setLineWidth(5);

        for(FloorCard f : tilesToCompare){
            canvas.getGraphicsContext2D().strokeRect(f.getX() * FloorCard.TILE_SIZE, f.getY() * FloorCard.TILE_SIZE,
                    FloorCard.TILE_SIZE, FloorCard.TILE_SIZE);
        }
        */
        for (FloorCard f : tilesToCompare) {
            canvas.getGraphicsContext2D().drawImage(new Image("markup.png"),
                    f.getX() * FloorCard.TILE_SIZE, f.getY() * FloorCard.TILE_SIZE);
        }
    }

    private void endTurn() {
        clearSelection();
        if (playerIndex == numOfPlayers) {
            playerIndex = 0;
        } else {
            playerIndex++;
        }
        currentPlayer.setCurrentPlayer(!currentPlayer.isCurrentPlayer());

        currentPlayer = players.get(playerIndex);
        changeState(GameState.DRAWING);
    }

    private void endGame() {
        for (PlayerController player : players) {
            if (player.equals(currentPlayer)) {
                player.getProfile().incrementVictories();
            } else {
                player.getProfile().incrementLoses();
            }
        }
    }

    private void enableRetrievingTilesFromCanvas() {
        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double x = event.getX();
                double y = event.getY();
                selectedTile = board.getTileFromCanvas(x, y);
                System.out.println("x: " + selectedTile.getX() + " y: " + selectedTile.getY() +
                        " | " + selectedTile.getType() + " | " + currentState);
                System.out.println(currentPlayer.getPlayerIndex());
                System.out.println("Left: " + selectedTile.getOpeningAt(FloorCard.Direction.LEFT) +
                        " Up: " + selectedTile.getOpeningAt(FloorCard.Direction.UP) +
                        " Right: " + selectedTile.getOpeningAt(FloorCard.Direction.RIGHT) +
                        " Down: " + selectedTile.getOpeningAt(FloorCard.Direction.DOWN));
                playState();
            }
        });
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public Card getPlayingCard() {
        return playingCard;
    }

    public void setPlayingCard(Card card) {
        playingCard = card;
        getCardSelectionFlag().set(!getCardSelectionFlag().getValue());
    }

    private void clearSelection() {
        setPlayingCard(null);
        selectedTile = null;
        tilesToCompare.clear();
    }

    public void draw() {
        board.drawBoard(canvas.getGraphicsContext2D());
    }

    public ArrayList<PlayerController> getPlayers() {
        return players;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public PlayerController getCurrentPlayer() {
        return currentPlayer;
    }

    public IntegerProperty getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public BooleanProperty getCardSelectionFlag() {
        return cardSelectionFlag;
    }

    public BooleanProperty getStateChangeFlag() {
        return stateChangeFlag;
    }
}

