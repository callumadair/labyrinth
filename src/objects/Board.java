package objects;

import javafx.scene.canvas.*;

import java.util.*;

public class Board {

    private int width;
    private int height;
    private int fixedTilesNum;
    private int[][] spawnPoints = new int[4][2];
    private int[][] fixedTiles; // [fixedTileNum][2];
    private SilkBag silkBag;
    private FloorCard[][] map;
    private ArrayList<PlayerController> players;

    private ArrayList<FloorCard> frozenTiles = new ArrayList<>();
    private ArrayList<Integer> columnsToPlace = new ArrayList<>();
    private ArrayList<Integer> rowsToPlace = new ArrayList<>();

    public Board(String[][] data, ArrayList<PlayerController> players) {
        this.players = players;
        setup(data);
    }

    public Board(int width, int height, int[][] spawnPoints, FloorCard[] fixedTiles, SilkBag silkBag) {
        this.width = width;
        this.height = height;
        this.spawnPoints = spawnPoints;
        this.silkBag = silkBag;
        setup(fixedTiles);
    }

    // Just for testing
    public Board() {
        setup();
    }

    // testing only
    private void setup() {
        width = 5;
        height = 6;
        map = new FloorCard[width][height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                map[j][i] = new FloorCard("STRAIGHT");
                map[j][i].setX(j);
                map[j][i].setY(i);
            }
        }
        assignInsertPositions();
        silkBag = new SilkBag(4);

        map[1][1].nextRotation();
        map[4][4].nextRotation();
        map[4][3].nextRotation();
        frozenTiles.add(map[4][3]);
        map[4][3].setOnIce();
        map[2][2].setOnIce();
        frozenTiles.add(map[2][2]);
        map[1][0].setOnFire();
        silkBag.addACard(new FloorCard("CORNER"));
        silkBag.addACard(new FloorCard("CORNER"));
        silkBag.addACard(new FloorCard("CORNER"));
        silkBag.addACard(new FloorCard("CORNER"));
    }

    private void setup(String[][] data) {
        // Handle assigning all the data
        map = new FloorCard[width][height];
    }

    private void setup(FloorCard[] fixedTiles) {
        map = new FloorCard[width][height];
        for (FloorCard fixed : fixedTiles) {
            map[fixed.getX()][fixed.getY()] = fixed;
        }

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == null) {
                    map[i][j] = silkBag.drawFloorCard();
                }
            }
        }
    }

    private void assignInsertPositions() {

        for (int i = 0; i < height - 1; i++) {
            rowsToPlace.add(i);
        }
        for (int i = 0; i < width - 1; i++) {
            columnsToPlace.add(i);
        }

        for (int i = 0; i < fixedTilesNum - 1; i++) {
            int[] cord = fixedTiles[i];
            rowsToPlace.remove(cord[0]);
            columnsToPlace.remove(cord[1]);
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

        for (int i = 0; i < height - 1; i++) {
            if (!frozenRows.contains(rowsToPlace.get(i))) {
                insertionTiles.add(map[0][rowsToPlace.get(i)]);
                insertionTiles.add(map[width - 1][rowsToPlace.get(i)]);
            }
        }

        for (int i = 0; i < width - 1; i++) {
            if (!frozenColumns.contains(columnsToPlace.get(i))) {
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
                map[0][y] = tile;
            } else if (x == width - 1) {
                silkBag.addACard(map[0][y]);
                for (int i = 0; i < width - 1; i++) {
                    map[i][y] = map[i + 1][y];
                    map[i + 1][y].setX(i);
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
                map[x][0] = tile;
            } else if (y == height - 1) {
                silkBag.addACard(map[x][0]);
                for (int i = 0; i < height - 1; i++) {
                    map[x][i] = map[x][i + 1];
                    map[x][i + 1].setY(i);
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

    public int getFixedTilesNum() {
        return fixedTilesNum;
    }

    public int[][] getFixedTiles() {
        return fixedTiles;
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
}
