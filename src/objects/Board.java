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

    public Board(int width, int height, int[][] spawnPoints, SilkBag silkBag, ArrayList<PlayerController> players) {
        this.width = width;
        this.height = height;
        this.spawnPoints = spawnPoints;
        this.silkBag = silkBag;
        this.players = players;
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
        map = new FloorCard[5][5];
        width = 5;
        height = 5;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                map[j][i] = new FloorCard("STRAIGHT");
                map[j][i].setX(j);
                map[j][i].setY(i);
            }
        }
        assignInsertPositions();
        silkBag = new SilkBag(4);

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
        // call the function within setup
        // assign numbers from 0 to height to columnsToPlace
        // assign numbers from 0 to width to rowsToPlace
        for (int i = 0; i < width; i++) {
            rowsToPlace.add(i);
        }
        for (int i = 0; i < height; i++) {
            columnsToPlace.add(i);
        }

        for (int i = 0; i < fixedTilesNum; i++) {
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
            frozenRows.add(tile.getX());
            frozenColumns.add(tile.getY());
        }

        for (int i = 0; i < height; i++) {
            if (!frozenRows.contains(rowsToPlace.get(i))) {
                insertionTiles.add(map[rowsToPlace.get(i)][0]);
                insertionTiles.add(map[rowsToPlace.get(i)][width - 1]);
            }
        }

        for (int i = 0; i < width; i++) {
            if (!frozenColumns.contains(columnsToPlace.get(i))) {
                if (!insertionTiles.contains(map[0][columnsToPlace.get(i)])) {
                    insertionTiles.add(map[0][columnsToPlace.get(i)]);
                }
                if (!insertionTiles.contains(map[height - 1][columnsToPlace.get(i)])) {
                    insertionTiles.add(map[height - 1][columnsToPlace.get(i)]);
                }
            }
        }

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
                gc.drawImage(map[i][j].getImage(), i * FloorCard.TILE_SIZE, j * FloorCard.TILE_SIZE);
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

    public int[][] getSpawnPoints() {
        return spawnPoints;
    }

    public void setPlayers(ArrayList<PlayerController> players) {
        this.players = players;
    }
}
