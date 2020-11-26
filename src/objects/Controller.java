package objects;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Controller {

    enum GameState {
        DRAWING, INSERTING, ACTION_CARD, MOVING, END_TURN, VICTORY;
    }

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
        this.players = players;
        board = new Board(); //testing only
        this.players = new ArrayList<PlayerController>(); //testing only
        this.players.add(new PlayerController()); //testing only

        canvas = new Canvas(board.getWidth() * FloorCard.TILE_SIZE,
                board.getHeight() * FloorCard.TILE_SIZE);
        enableRetrievingTilesFromCanvas();

        board.drawBoard(canvas.getGraphicsContext2D());
        startGame();
    }

    public void startGame() {
        numOfPlayers = players.size() - 1;
        currentPlayer = players.get(playerIndex);
        changeState(GameState.DRAWING);
    }

    private void changeState(GameState state) {
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

        //show the card to the player
        //? maybe animate as well

        if (playingCard instanceof FloorCard){
            changeState(GameState.INSERTING);
        } else if (playingCard instanceof ActionCard){
            changeState(GameState.ACTION_CARD);
        }
    }

    private void getInsertionList() {
        tilesToCompare = board.getInsertionPoints();
        highlightTiles();
        //highlight tiles where to insert
        //enable rotating the card
    }

    private void insert() {
        if (tilesToCompare.contains(selectedTile)) {
            playingCard.useCard(board, selectedTile.getX(), selectedTile.getY());
            tilesToCompare.clear();
            selectedTile = null;
            playingCard = null;
            board.drawBoard(canvas.getGraphicsContext2D());
            changeState(GameState.ACTION_CARD);
        } else {
            selectedTile = null;
        }
    }

    private void playActionCard() {
        //player needs to choose action card
        //player needs to select a tile and it needs to be validated
    }

    private void showActionCards() {
        //add playingCard to players action cards
        //show players action cards if none go to moving
        //give player the ability to skip this state
    }

    private void getLegalMoves() {
        tilesToCompare = currentPlayer.determineLegalMoves();
        //highlight tiles on which player can move
    }

    private void movePlayer() {
        if (tilesToCompare.contains(selectedTile)) {
            if (selectedTile.checkGoal()) {
                changeState(GameState.VICTORY);
            } else {
                //move player on the board
                tilesToCompare.clear();
                selectedTile = null;
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
        for(FloorCard f : tilesToCompare){
            canvas.getGraphicsContext2D().drawImage(new Image("markup.png"),
                    f.getX() * FloorCard.TILE_SIZE, f.getY() * FloorCard.TILE_SIZE);
        }
    }

    private void endTurn() {
        selectedTile = null;
        tilesToCompare = null;
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
                System.out.println("x: " + selectedTile.getX() + " y: " + selectedTile.getY() + "| " + currentState);
                System.out.println(selectedTile.getType());
                playState();
            }
        });
    }

    public Canvas getCanvas(){
        return canvas;
    }
}
