package objects;


import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import javafx.event.*;
import javafx.scene.canvas.*;
import javafx.scene.image.*;
import javafx.scene.input.*;

import java.util.*;

public class Controller {

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

    /**
     * @param boardData
     * @param players
     */
    public Controller(String[][] boardData, ArrayList<PlayerController> players) {
        board = new Board(boardData, players);

        canvas = new Canvas(board.getWidth() * FloorCard.TILE_SIZE,
                board.getHeight() * FloorCard.TILE_SIZE);
        enableRetrievingTilesFromCanvas();

        draw();
        startGame();
    }

    public Controller(Board b){
        board = b;
        this.players = b.getPlayers();

        canvas = new Canvas(board.getWidth() * FloorCard.TILE_SIZE,
                board.getHeight() * FloorCard.TILE_SIZE);
        enableRetrievingTilesFromCanvas();

        draw();
        startGame();
    }
    //testing only
    public Controller() {
        board = new Board();
        this.players = new ArrayList<PlayerController>(); //testing only
        this.players.add(new PlayerController(null, 0)); //testing only
        this.players.add(new PlayerController(null, 1)); //testing only
        this.players.add(new PlayerController(null, 2)); //testing only
        board.changePlayerPosition(players.get(0), 0, 0); //testing only
        board.changePlayerPosition(players.get(1), 4, 4); //testing only
        board.changePlayerPosition(players.get(2), 2, 2); //testing only

        players.get(0).getCardsHeld().add(new ActionCard("FIRE"));//testing
        players.get(0).getCardsHeld().add(new ActionCard("ICE"));//testing


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
        changeState(GameState.DRAWING);
    }

    public void changeState(GameState state) {
        currentState = state;
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
        playingCard = board.getSilkBag().drawACard();
        //drawingACard();
        //show the card to the player
        //? maybe animate as well
        if (playingCard instanceof FloorCard) {
            changeState(GameState.INSERTING);
        } else if (playingCard instanceof ActionCard) {
            changeState(GameState.ACTION_CARD);
        }
    }

    private void getInsertionList() {
        tilesToCompare = board.getInsertionPoints();
        highlightTiles();
        //enable rotating the card
    }

    private void insert() {
        if (tilesToCompare.contains(selectedTile)) {
            playingCard.useCard(board, selectedTile.getX(), selectedTile.getY());
            tilesToCompare.clear();
            selectedTile = null;
            playingCard = null;
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
                changeState(GameState.MOVING);
            } else {
                selectedTile = null;
                playingCard.useCard(board, currentPlayer.getX(), currentPlayer.getY());
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
        } else if (cardHeldByCurrentPlayer.isEmpty() && playingCard != null) {
            //if skipping the state notify player that he had no action cards
            cardHeldByCurrentPlayer.add((ActionCard) playingCard);
            changeState(GameState.MOVING);
        } else if (playingCard != null) {
            cardHeldByCurrentPlayer.add((ActionCard) playingCard);
        }

        //give player the ability to skip this state
    }

    private void getLegalMoves() {
        tilesToCompare = currentPlayer.determineLegalMoves(board);
        if (tilesToCompare.isEmpty()) {
            changeState(GameState.END_TURN);
        } else {
            highlightTiles();
        }
    }

    private void movePlayer() {
        if (tilesToCompare.contains(selectedTile)) {
            if (selectedTile.checkGoal()) {
                changeState(GameState.VICTORY);
            } else {
                board.changePlayerPosition(currentPlayer, selectedTile.getX(), selectedTile.getY());
                draw();
                tilesToCompare.clear();
                selectedTile = null;
                if (currentPlayer.checkDoubleMove()) {
                    currentPlayer.setDoubleMove(false);
                    changeState(GameState.MOVING);
                }
                changeState(GameState.END_TURN);
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
        selectedTile = null;
        tilesToCompare.clear();
        playingCard = null;
        if (playerIndex == numOfPlayers) {
            playerIndex = 0;
        } else {
            playerIndex++;
        }
        currentPlayer = players.get(playerIndex);
        changeState(GameState.DRAWING);
    }

    private void endGame() {
        //set current player to be the winner
        //display the winners name
        //change the leaderboard for the given board
        //display two buttons on screen 'go back to menu' and 'quit game'
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

    public PlayerController getCurrentPlayer() {
        return currentPlayer;
    }

    public Card getPlayingCard() {
        return playingCard;
    }

    public void setPlayingCard(Card card) {
        playingCard = card;
    }

    public void draw() {
        board.drawBoard(canvas.getGraphicsContext2D());
        for (PlayerController player : players) {
            player.drawPlayer(canvas.getGraphicsContext2D());
        }
    }

    public ArrayList<PlayerController> getPlayers() {
        return players;
    }

}

