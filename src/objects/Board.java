package objects;

import javafx.scene.canvas.*;

import java.util.*;

public class Board {

    private int width;
    private int height;
    private int[][] spawnPoints;
    private SilkBag silkBag;
    private FloorCard[][] map;
    private FloorCard[] fixedTiles;
    private ArrayList<PlayerController> players;

    private ArrayList<FloorCard> frozenTiles = new ArrayList<>();
    private ArrayList<Integer> columnsToPlace = new ArrayList<>();
    private ArrayList<Integer> rowsToPlace = new ArrayList<>();

    public Board(int width, int height, int[][] spawnPoints, FloorCard[] fixedTiles,
                 SilkBag silkBag, ArrayList<PlayerController> players) {
        this.width = width;
        this.height = height;
        this.spawnPoints = spawnPoints;
        this.silkBag = silkBag;
        this.fixedTiles = fixedTiles;
        this.players = players;
        setup();
    }

    public Board(int width, int height, int[][] spawnPoints, FloorCard[] fixedTiles, SilkBag silkBag) {
        this.width = width;
        this.height = height;
        this.spawnPoints = spawnPoints;
        this.silkBag = silkBag;
        this.fixedTiles = fixedTiles;
        setup();
    }

    private void setup() {
        map = new FloorCard[width][height];
        for (FloorCard fixed : fixedTiles) {
            map[fixed.getX()][fixed.getY()] = fixed;
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (map[j][i] == null) {
                    map[j][i] = silkBag.drawFloorCard();
                    map[j][i].setX(j);
                    map[j][i].setY(i);
                }
            }
        }

//        for(int i = 0; i < players.size(); i++){
//            players.get(i).movePlayer(spawnPoints[i][0], spawnPoints[i][1]);
//        }
        assignInsertPositions();
    }

    private void assignInsertPositions() {

        for (int i = 0; i < height; i++) {
            rowsToPlace.add(i);
        }
        for (int i = 0; i < width; i++) {
            columnsToPlace.add(i);
        }

        for (FloorCard tile : fixedTiles) {
            rowsToPlace.remove(Integer.valueOf(tile.getY()));
            columnsToPlace.remove(Integer.valueOf(tile.getX()));
        }
    }

    /**
     * @return ArrayList
     */
    public ArrayList<FloorCard> getInsertionPoints() {
        ArrayList<FloorCard> insertionTiles = new ArrayList<>();

        ArrayList<Integer> frozenRows = new ArrayList<>();
        ArrayList<Integer> frozenColumns = new ArrayList<>();
        for (FloorCard tile : frozenTiles) {
            frozenRows.add(tile.getY());
            frozenColumns.add(tile.getX());
        }

        for (int i = 0; i < rowsToPlace.size(); i++) {
            if (!frozenRows.isEmpty()) {
                if(!frozenRows.contains(rowsToPlace.get(i))){
                    insertionTiles.add(map[0][rowsToPlace.get(i)]);
                    insertionTiles.add(map[width - 1][rowsToPlace.get(i)]);
                }
            } else {
                insertionTiles.add(map[0][rowsToPlace.get(i)]);
                insertionTiles.add(map[width - 1][rowsToPlace.get(i)]);
            }
        }

        for (int i = 0; i < columnsToPlace.size(); i++) {
            if (!frozenColumns.isEmpty()) {
                if(!frozenColumns.contains(columnsToPlace.get(i))){
                    if (!insertionTiles.contains(map[columnsToPlace.get(i)][0])) {
                        insertionTiles.add(map[columnsToPlace.get(i)][0]);
                    }
                    if (!insertionTiles.contains(map[columnsToPlace.get(i)][height - 1])) {
                        insertionTiles.add(map[columnsToPlace.get(i)][height - 1]);
                    }
                }
            } else{
                if (!insertionTiles.contains(map[columnsToPlace.get(i)][0])) {
                    insertionTiles.add(map[columnsToPlace.get(i)][0]);
                }
                if (!insertionTiles.contains(map[columnsToPlace.get(i)][height - 1])) {
                    insertionTiles.add(map[columnsToPlace.get(i)][height - 1]);
                }
            }
        }

        insertionTiles.remove(map[0][0]);
        insertionTiles.remove(map[width - 1][0]);
        insertionTiles.remove(map[0][height - 1]);
        insertionTiles.remove(map[width - 1][height - 1]);
        return insertionTiles;
    }

    public void insertTile(FloorCard tile, int x, int y) {
        if (x == 0 || x == width - 1) {
            if (x == 0) {
                silkBag.addACard(map[width - 1][y]);
                for (int i = width - 1; i > 0; i--) {
                    map[i][y] = map[i - 1][y];
                    map[i - 1][y].setX(i);
                }
                if(this.checkPlayerPosition(width - 1, y)){
                    getPlayer(width - 1, y).movePlayer(0, y);
                }
                map[0][y] = tile;
            } else if (x == width - 1) {
                silkBag.addACard(map[0][y]);
                for (int i = 0; i < width - 1; i++) {
                    map[i][y] = map[i + 1][y];
                    map[i + 1][y].setX(i);
                }
                if(this.checkPlayerPosition(0, y)){
                    getPlayer(width - 1, y).movePlayer(width - 1, y);
                }
                map[width - 1][y] = tile;
            }
        } else if (y == 0 || y == height - 1) {
            if (y == 0) {
                silkBag.addACard(map[x][height - 1]);
                for (int i = height - 1; i > 0; i--) {
                    map[x][i] = map[x][i - 1];
                    map[x][i - 1].setY(i);
                }
                if(this.checkPlayerPosition(x, height - 1)){
                    getPlayer(x, height - 1).movePlayer(x, 0);
                }
                map[x][0] = tile;
            } else if (y == height - 1) {
                silkBag.addACard(map[x][0]);
                for (int i = 0; i < height - 1; i++) {
                    map[x][i] = map[x][i + 1];
                    map[x][i + 1].setY(i);
                }
                if(this.checkPlayerPosition(x, 0)){
                    getPlayer(x, 0).movePlayer(x, height - 1);
                }
                map[x][height - 1] = tile;
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void drawBoard(GraphicsContext gc) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                map[j][i].drawTile(gc, j, i);
            }
        }
    }

    public FloorCard getTileFromCanvas(double x, double y) {
        int cordX = (int) (x / FloorCard.TILE_SIZE);
        int cordY = (int) (y / FloorCard.TILE_SIZE);

        return map[cordX][cordY];
    }

    public FloorCard getTile(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return null;
        }
        return map[x][y];
    }

    public SilkBag getSilkBag() {
        return silkBag;
    }

    public void changePlayerPosition(PlayerController player, int x, int y) {
        player.movePlayer(x, y);
    }

    public boolean checkPlayerPosition(int x, int y) {
        for (PlayerController player : players) {
            if (player.getX() == x && player.getY() == y) {
                return true;
            }
        }
        return false;
    }

    public boolean checkBoardBoundary(int x, int y){
        if(x < 0 || x >= width || y < 0 || y >= height){
            return false;
        } else {
            return true;
        }
    }

    public int getFixedTilesNum() {
        return this.fixedTiles.length;
    }

    public FloorCard[] getFixedTiles() {
        return this.fixedTiles;
    }

    public ArrayList<PlayerController> getPlayers() {
        return players;
    }

    public PlayerController getPlayer(int x, int y) {
        for (PlayerController player : players) {
            if (player.getX() == x && player.getY() == y) {
                return player;
            }
        }
        return null;
    }

    public ArrayList<FloorCard> getFrozenTiles(){
        return frozenTiles;
    }

    public void setPlayers(ArrayList<PlayerController> players){
        this.players = players;
    }

    public int[][] getSpawnPoints(){
        return this.spawnPoints;
    }
}
