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

/**
 * The Controller class represents the game controller, enabling much of the
 * functionality of running the game itself.
 *
 * @author Kacper Lisikiewicz
 */
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

    private String tileHighlightImagePath = "resources/markup.png";

    /**
     * Instantiates a new Controller.
     *
     * @param board the board.
     */
    public Controller(Board board) {
        this.board = board;
        this.players = board.getPlayers();

        canvas = new Canvas(board.getWidth() * FloorCard.TILE_SIZE,
                board.getHeight() * FloorCard.TILE_SIZE);
        enableRetrievingTilesFromCanvas();

        draw();
        startGame();
    }

    /**
     * The enum Game state.
     */
    enum GameState {
        DRAWING,
        INSERTING,
        ACTION_CARD,
        MOVING,
        END_TURN,
        VICTORY;
    }

    /**
     * Start game.
     */
    public void startGame() {
        numOfPlayers = players.size() - 1;
        for (PlayerController player : players) {
            if (player.isCurrentPlayer()) {
                this.playerIndex = player.getPlayerIndex();
            }
        }
        currentPlayer = players.get(playerIndex);
        currentPlayerIndex = new SimpleIntegerProperty(playerIndex);
        cardSelectionFlag = new SimpleBooleanProperty(false);
        stateChangeFlag = new SimpleBooleanProperty(false);
        changeState(GameState.DRAWING);
    }

    /**
     * Change state.
     *
     * @param state the state.
     */
    public void changeState(GameState state) {
        currentState = state;
        getStateChangeFlag().set(!getStateChangeFlag().getValue());
        startState(currentState);
    }

    /**
     * Start a state.
     *
     * @param state the state.
     */
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

    /**
     * Play a state.
     */
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

    /**
     * Draws a card from SilkBag.
     */
    private void drawCard() {
        setPlayingCard(board.getSilkBag().drawACard());
        currentPlayerIndex.set(currentPlayer.getPlayerIndex());
        currentPlayer.setCurrentPlayer(!currentPlayer.isCurrentPlayer());

        //Change the effect timer on frozen tiles
        if (!board.getFrozenTiles().isEmpty()) {
            ArrayList<FloorCard> frozenTilesToRemove = new ArrayList<>();
            for (FloorCard card : board.getFrozenTiles()) {
                card.decrementEffectTimer();
                if (!card.isEffectActive()) {
                    frozenTilesToRemove.add(card);
                }
            }
            board.getFrozenTiles().removeAll(frozenTilesToRemove);
        }

        //Change the effect timer on fire tiles
        if (!board.getTilesOnFire().isEmpty()) {
            ArrayList<FloorCard> tilesOnFireToRemove = new ArrayList<>();
            for (FloorCard card : board.getTilesOnFire()) {
                card.decrementEffectTimer();
                if (!card.isEffectActive()) {
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

    /**
     * Gets insertion list.
     */
    private void getInsertionList() {
        tilesToCompare = board.getInsertionPoints();

        if (tilesToCompare.isEmpty()) {
            playingCard = null;
            changeState(GameState.ACTION_CARD);
        } else {
            highlightTiles();
        }
    }

    /**
     * Inserts a tile.
     */
    private void insert() {
        if (tilesToCompare.contains(selectedTile)) {
            playingCard.useCard(board, selectedTile.getX(),
                    selectedTile.getY());
            clearSelection();
            draw();
            changeState(GameState.ACTION_CARD);
        } else {
            selectedTile = null;
        }
    }

    /**
     * Play action card.
     */
    public void playActionCard() {
        if (playingCard != null && selectedTile != null) {
            if (playingCard.useCard(board, selectedTile.getX(),
                    selectedTile.getY())) {
                currentPlayer.getCardsHeld().remove((ActionCard) playingCard);
                clearSelection();
                draw();
                changeState(GameState.MOVING);
            } else {
                selectedTile = null;
            }
        }
    }

    /**
     * Shows action cards.
     */
    private void showActionCards() {
        ArrayList<ActionCard> cardHeldByCurrentPlayer =
                currentPlayer.getCardsHeld();

        //set the last drawn card by a player so that it can be used this turn
        if (!cardHeldByCurrentPlayer.isEmpty()) {
            cardHeldByCurrentPlayer.get(cardHeldByCurrentPlayer.size() - 1).setCanBeUsed();
        }

        if (cardHeldByCurrentPlayer.isEmpty() && playingCard == null) {
            changeState(GameState.MOVING);
        } else if (playingCard != null) {
            cardHeldByCurrentPlayer.add((ActionCard) playingCard);
        }

    }

    /**
     * Gets the legal moves.
     */
    private void getLegalMoves() {
        tilesToCompare = currentPlayer.determineLegalMoves(board);
        if (tilesToCompare.isEmpty()) {
            changeState(GameState.END_TURN);
        } else {
            highlightTiles();
        }
    }

    /**
     * Moves the player.
     */
    private void movePlayer() {
        if (tilesToCompare.contains(selectedTile)) {
            if (selectedTile.checkGoal()) {
                board.changePlayerPosition(currentPlayer, selectedTile.getX()
                        , selectedTile.getY());
                draw();
                clearSelection();
                changeState(GameState.VICTORY);
            } else {
                board.changePlayerPosition(currentPlayer, selectedTile.getX()
                        , selectedTile.getY());
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

    /**
     * Highlights the tiles.
     */
    private void highlightTiles() {
        for (FloorCard f : tilesToCompare) {
            canvas.getGraphicsContext2D().drawImage(
                    new Image(tileHighlightImagePath),
                    f.getX() * FloorCard.TILE_SIZE,
                    f.getY() * FloorCard.TILE_SIZE);
        }
    }

    /**
     * Ends the turn.
     */
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

    /**
     * Ends the game.
     */
    private void endGame() {
        for (PlayerController player : players) {
            if (player.equals(currentPlayer)) {
                player.getProfile().incrementVictories();
            } else {
                player.getProfile().incrementLoses();
            }
        }
    }

    /**
     * Enables the retrieving of tiles.
     */
    private void enableRetrievingTilesFromCanvas() {
        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double x = event.getX();
                double y = event.getY();
                selectedTile = board.getTileFromCanvas(x, y);
                System.out.println("x: " + selectedTile.getX()
                        + " y: " + selectedTile.getY() +
                        " | " + selectedTile.getType() + " | " + currentState);
                System.out.println(currentPlayer.getPlayerIndex());
                System.out.println(
                        "Left: " + selectedTile.getOpeningAt(
                                FloorCard.Direction.LEFT) +
                                " Up: " + selectedTile.getOpeningAt(
                                FloorCard.Direction.UP) +
                                " Right: " + selectedTile.getOpeningAt(
                                FloorCard.Direction.RIGHT) +
                                " Down: " + selectedTile.getOpeningAt(
                                FloorCard.Direction.DOWN));
                playState();
            }
        });
    }

    /**
     * Gets canvas.
     *
     * @return the canvas.
     */
    public Canvas getCanvas() {
        return canvas;
    }

    /**
     * Gets playing card.
     *
     * @return the playing card.
     */
    public Card getPlayingCard() {
        return playingCard;
    }

    /**
     * Sets playing card.
     *
     * @param card the card.
     */
    public void setPlayingCard(Card card) {
        playingCard = card;
        getCardSelectionFlag().set(!getCardSelectionFlag().getValue());
    }

    /**
     * Clears the cache of selected items.
     */
    private void clearSelection() {
        setPlayingCard(null);
        selectedTile = null;
        tilesToCompare.clear();
    }

    /**
     * Draws a board.
     */
    public void draw() {
        board.drawBoard(canvas.getGraphicsContext2D());
    }

    /**
     * Gets players.
     *
     * @return the players.
     */
    public ArrayList<PlayerController> getPlayers() {
        return players;
    }

    /**
     * Gets current state.
     *
     * @return the current state.
     */
    public GameState getCurrentState() {
        return currentState;
    }

    /**
     * Gets current player.
     *
     * @return the current player.
     */
    public PlayerController getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Gets current player index.
     *
     * @return the current player index.
     */
    public IntegerProperty getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    /**
     * Gets card selection flag.
     *
     * @return the card selection flag.
     */
    public BooleanProperty getCardSelectionFlag() {
        return cardSelectionFlag;
    }

    /**
     * Gets state change flag.
     *
     * @return the state change flag.
     */
    public BooleanProperty getStateChangeFlag() {
        return stateChangeFlag;
    }
}

