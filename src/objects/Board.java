package objects;

import java.util.ArrayList;
import java.util.HashSet;
import javafx.scene.canvas.GraphicsContext;

public class Board {

//
    private int width;
    private int height;
    private int fixedTilesNum;
    private int[][] spawnPoints = new int[4][2];
    private int[][] fixedTiles; //[fixedTileNum][2];
    private SilkBag silkBag;
    private FloorCard[][] map;
    private int[][] playersMap;

    private ArrayList<FloorCard> frozenTiles = new ArrayList<>();
    private ArrayList<Integer> columnsToPlace = new ArrayList<>();
    private ArrayList<Integer> rowsToPlace = new ArrayList<>();

    public Board(String[][] data) {
        setup(data);
    }

    //Just for testing
    public Board() { setup(); }

    //testing only
    private void setup(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                map[i][j] = new FloorCard("type");
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

    /**
     *
     * @return ArrayList
     */
    public ArrayList<FloorCard> getInsertionPoints() {
        HashSet<FloorCard> insertionTiles = new HashSet<>();

        ArrayList<Integer> frozenRows = new ArrayList<>();
        ArrayList<Integer> frozenColumns = new ArrayList<>();
        for(FloorCard tile : frozenTiles){
            frozenRows.add(tile.getX());
            frozenColumns.add(tile.getY());
        }

        for(int i = 0; i < height; i++){
            if(!frozenRows.contains(rowsToPlace.get(i))){
                insertionTiles.add(map[rowsToPlace.get(i)][0]);
                insertionTiles.add(map[rowsToPlace.get(i)][width - 1]);
            }
        }

        for(int i = 0; i < width; i++){
            if(!frozenColumns.contains(columnsToPlace.get(i))){
                insertionTiles.add(map[0][columnsToPlace.get(i)]);
                insertionTiles.add(map[height - 1][columnsToPlace.get(i)]);
            }
        }

        return getInsertionPoints();
    }

    public void insertTile(FloorCard tile, int x, int y){
        if(x == 0 || x == height - 1){
            if(x == 0){
                for(int i = 0; i < width - 1; i++){
                    map[i+1][y] = map[i][y];
                }
                map[0][y] = tile;
            } else if(x == height - 1){
                for(int i = width - 1; i > 1; i--){
                    map[i-1][y] = map[i][y];
                }
                map[width - 1][y] = tile;
            }
        } else if(y == 0 || y == width - 1){
            if(y == 0){
                for(int i = 0; i < height - 1; i++){
                    map[x][i+1] = map[x][i];
                }
                map[x][0] = tile;
            } else if(y == width - 1){
                for (int i = height - 1; i > 1; i--){
                    map[x][i-1] = map[x][i];
                }
                map[x][height - 1] = tile;
            }
        }
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public void drawBoard(GraphicsContext gc){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                gc.drawImage(map[i][j].getImage(), i * FloorCard.TILE_SIZE, j * FloorCard.TILE_SIZE);
            }
        }
    }

    public FloorCard getTileFromCanvas(double x, double y){
        int cordX = (int)(x / FloorCard.TILE_SIZE);
        int cordY = (int)(y / FloorCard.TILE_SIZE);

        return map[cordX][cordY];
    }

    public FloorCard getTile(int x, int y){
        return map[x][y];
    }

    public SilkBag getSilkBag(){
        return silkBag;
    }

    public void changePlayerPosition(PlayerController player, int x, int y, int playerIndex){
        player.movePlayer(x, y);
        playersMap[x][y] = playerIndex;
    }

    public boolean checkPlayerPosition(int x, int y){
        return playersMap[x][y] == 0 || playersMap[x][y] == 1 ||
                playersMap[x][y] ==  2 || playersMap[x][y] ==  3;
    }
}
