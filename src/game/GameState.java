package game;

import objects.PlayerController;

import java.io.File;
import java.util.ArrayList;

public class GameState {

    private Board board;
    private ArrayList<PlayerController> players;

    public GameState(File board, ArrayList<PlayerController> players){
        //extract data about a board using file manager
        //this.board = new game.Board(board);
        this.players = players;
    }



    enum State{
        DRAWING, PLACING, ACTION, MOVING, END_TURN, END;
    }
}