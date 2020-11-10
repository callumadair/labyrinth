import java.io.File;
import java.util.ArrayList;

public class GameState {

    private Board board;
    private ArrayList<Player> players;

    public GameState(File board, ArrayList<Player> players){
        //extract data about a board using file manager
        //this.board = new Board(board);
        this.players = players;
    }



    enum State{
        DRAWING, PLACING, ACTION, MOVING, END_TURN, END;
    }
}
