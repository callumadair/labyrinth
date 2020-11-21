import java.util.ArrayList;
import objects.*;

public class Board {

    private int width;
    private int height;
    private FloorCard goal;
    private int fixedTilesNum;
    private int[][] spawnPoints = new int[4][2];
    private int[][] fixedTiles; //[fixedTileNum][2];
    private SilkBag bag;
    private FloorCard[][] map;

    private ArrayList<Integer> columnsToPlace = new ArrayList<>();
    private ArrayList<Integer> rowsToPlace = new ArrayList<>();

    public Board(String[][] data) {
        setup();
    }

    //testing only
    private void setup(){
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){

            }
        }
    }

    private void setup(String[][] data) {
        //Handle assigning all the data
        map = new FloorCard[width][height];
    }

    private void assignInsertPositions() {
        //call the function within setup
        //assign numbers from 0 to height to columnsToPlace
        //assign numbers from 0 to width to rowsToPlace
        for(int i = 0; i < width; i++){
            rowsToPlace.add(i);
        }
        for(int i = 0; i < height; i++){
            columnsToPlace.add(i);
        }

        for (int i = 0; i < fixedTilesNum; i++) {
            int[] cord = fixedTiles[i];
            rowsToPlace.remove(cord[0]);
            columnsToPlace.remove(cord[1]);
        }
    }

    /*
    public boolean checkTileInsert(int x, int y) {
        boolean rightPlace = false;

        if ((x == 0 || x == width - 1) && columnsToPlace.contains(y)){
            for(int i = 0; i < width; i++){
                if(map[i][y].getState() == FloorCard.TileState.FROZEN){
                    return false;
                }
            }
            rightPlace = true;
        } else if((y == 0 || y == height - 1) && rowsToPlace.contains(x)){
            for(int i = 0; i < width; i++){
                if(map[x][i].getState() == FloorCard.TileState.FROZEN){
                    return false;
                }
            }
            rightPlace = true;
        }

        return rightPlace;
    }
    */

    
}
