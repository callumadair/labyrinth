package objects;

import javafx.scene.canvas.*;

import java.util.*;

/**
 * This class represents the board.
 */
public class Board {

    private int width;
    private int height;
    private int[][] spawnPoints;
    private SilkBag silkBag;
    private FloorCard[][] map;
    private FloorCard[] fixedTiles;
    private ArrayList<PlayerController> players;

    private ArrayList<FloorCard> frozenTiles = new ArrayList<>();
    private ArrayList<FloorCard> tilesOnFire = new ArrayList<>();

    private ArrayList<Integer> columnsToPlace = new ArrayList<>();
    private ArrayList<Integer> rowsToPlace = new ArrayList<>();

    /**
     * Instantiates a new Board.
     *
     * @param width       the width of the board.
     * @param height      the height of the board.
     * @param spawnPoints the spawn points
     * @param fixedTiles  the fixed tiles
     * @param silkBag     the silk bag
     * @param players     the players
     */
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

    /**
     * Instantiates a new Board.
     *
     * @param width       the width of the board.
     * @param height      the height of the board.
     * @param spawnPoints the spawn points
     * @param silkBag     the silk bag
     * @param players     the players
     */
     //Testing
    public Board(int width, int height, int[][] spawnPoints, SilkBag silkBag, ArrayList<PlayerController> players) {
        this.width = width;
        this.height = height;
        this.spawnPoints = spawnPoints;
        this.silkBag = silkBag;
        this.players = players;
    }

    /**
     * Instantiates a new Board.
     *
     * @param width              the width of the board.
     * @param height             the height of the board.
     * @param spawnPoints        the spawn points
     * @param fixedTiles         the fixed tiles
     * @param silkBag            the silk bag
     * @param players            the players
     * @param existingFloorCards the existing floor cards
     */
     //for loading board in progress
    public Board(int width, int height, int[][] spawnPoints, FloorCard[] fixedTiles,
                 SilkBag silkBag, ArrayList<PlayerController> players, ArrayList<FloorCard> existingFloorCards) {
        this.width = width;
        this.height = height;
        this.spawnPoints = spawnPoints;
        this.silkBag = silkBag;
        this.fixedTiles = fixedTiles;
        this.players = players;
        setup();
    }

    /**
     * Instantiates a new Board.
     *
     * @param width       the width of the board.
     * @param height      the height of the board.
     * @param spawnPoints the spawn points
     * @param fixedTiles  the fixed tiles
     * @param silkBag     the silk bag
     */
     //testing only
    public Board(int width, int height, int[][] spawnPoints, FloorCard[] fixedTiles, SilkBag silkBag) {
        this.width = width;
        this.height = height;
        this.spawnPoints = spawnPoints;
        this.silkBag = silkBag;
        this.fixedTiles = fixedTiles;
        setup();
    }

    /**
     *Setup the board.
     */
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

    /**
     * Assigns the insert positions.
     */
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
     * Gets insertion points.
     *
     * @return ArrayList floorcards.
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
                if (!frozenRows.contains(rowsToPlace.get(i))) {
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
                if (!frozenColumns.contains(columnsToPlace.get(i))) {
                    if (!insertionTiles.contains(map[columnsToPlace.get(i)][0])) {
                        insertionTiles.add(map[columnsToPlace.get(i)][0]);
                    }
                    if (!insertionTiles.contains(map[columnsToPlace.get(i)][height - 1])) {
                        insertionTiles.add(map[columnsToPlace.get(i)][height - 1]);
                    }
                }
            } else {
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

    /**
     * Insert tile.
     *
     * @param tile the tile.
     * @param x    the x position.
     * @param y    the y position.
     */
    public void insertTile(FloorCard tile, int x, int y) {
        if (x == 0 || x == width - 1) {
            if (x == 0) {
                map[width - 1][y].setStateToNormal();
                silkBag.addACard(map[width - 1][y]);
                for (int i = width - 1; i > 0; i--) {
                    map[i][y] = map[i - 1][y];
                    map[i - 1][y].setX(i);
                    if (checkPlayerPosition(i - 1, y)) {
                        getPlayer(i - 1, y).movePlayer(i, y);
                    }
                }
                if (this.checkPlayerPosition(width - 1, y)) {
                    getPlayer(width - 1, y).movePlayer(0, y);
                }
                map[0][y] = tile;
            } else if (x == width - 1) {
                map[0][y].setStateToNormal();
                silkBag.addACard(map[0][y]);
                for (int i = 0; i < width - 1; i++) {
                    map[i][y] = map[i + 1][y];
                    map[i + 1][y].setX(i);
                    if (checkPlayerPosition(i + 1, y)) {
                        getPlayer(i + 1, y).movePlayer(i, y);
                    }
                }
                if (this.checkPlayerPosition(0, y)) {
                    getPlayer(0, y).movePlayer(width - 1, y);
                }
                map[width - 1][y] = tile;
            }
        } else if (y == 0 || y == height - 1) {
            if (y == 0) {
                map[x][height - 1].setStateToNormal();
                silkBag.addACard(map[x][height - 1]);
                for (int i = height - 1; i > 0; i--) {
                    map[x][i] = map[x][i - 1];
                    map[x][i - 1].setY(i);
                    if (checkPlayerPosition(x, i - 1)) {
                        getPlayer(x, i - 1).movePlayer(x, i);
                    }
                }
                if (this.checkPlayerPosition(x, height - 1)) {
                    getPlayer(x, height - 1).movePlayer(x, 0);
                }
                map[x][0] = tile;
            } else if (y == height - 1) {
                map[x][0].setStateToNormal();
                silkBag.addACard(map[x][0]);
                for (int i = 0; i < height - 1; i++) {
                    map[x][i] = map[x][i + 1];
                    map[x][i + 1].setY(i);
                    if (checkPlayerPosition(x, i + 1)) {
                        getPlayer(x, i + 1).movePlayer(x, i);
                    }
                }
                if (this.checkPlayerPosition(x, 0)) {
                    getPlayer(x, 0).movePlayer(x, height - 1);
                }
                map[x][height - 1] = tile;
            }
        }
    }

    /**
     * Gets width.
     *
     * @return the width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets height.
     *
     * @return the height.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Draw board.
     *
     * @param gc the gc.
     */
    public void drawBoard(GraphicsContext gc) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                map[j][i].drawTile(gc, j, i);
            }
        }
        for (PlayerController player : players) {
            player.drawPlayer(gc);
        }
    }

    /**
     * Gets tile from canvas.
     *
     * @param x the x position.
     * @param y the y position.
     * @return the tile from canvas.
     */
    public FloorCard getTileFromCanvas(double x, double y) {
        int cordX = (int) (x / FloorCard.TILE_SIZE);
        int cordY = (int) (y / FloorCard.TILE_SIZE);

        return map[cordX][cordY];
    }

    /**
     * Gets tile.
     *
     * @param x the x position.
     * @param y the y position.
     * @return the tile.
     */
    public FloorCard getTile(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return null;
        }
        return map[x][y];
    }

    /**
     * Gets silkbag.
     *
     * @return the silkbag
     */
    public SilkBag getSilkBag() {
        return silkBag;
    }

    /**
     * Change player position.
     *
     * @param player the player.
     * @param x      the x new position.
     * @param y      the y new position.
     */
    public void changePlayerPosition(PlayerController player, int x, int y) {
        player.movePlayer(x, y);
    }

    /**
     * Sets player position.
     *
     * @param player the player.
     * @param x      the x new position.
     * @param y      the y new position.
     */
    //use only when loading a board from a save file
    public void setPlayerPosition(PlayerController player, int x, int y) {
        player.setX(x);
        player.setY(y);
    }

    /**
     * Check player position.
     *
     * @param x the x position.
     * @param y the y position.
     * @return true if the player is on this position, false otherwise.
     */
    public boolean checkPlayerPosition(int x, int y) {
        for (PlayerController player : players) {
            if (player.getX() == x && player.getY() == y) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check board boundary.
     *
     * @param x width to be compared.
     * @param y the height to be compared.
     * @return true if it's in boundary, false otherwise.
     */
    public boolean checkBoardBoundary(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Gets the number of fixed tiles.
     *
     * @return the fixed tiles number.
     */
    public int getFixedTilesNum() {
        return this.fixedTiles.length;
    }

    /**
     * Get fixed tiles.
     *
     * @return the fixed tiles.
     */
    public FloorCard[] getFixedTiles() {
        return this.fixedTiles;
    }

    /**
     * Gets players.
     *
     * @return the players
     */
    public ArrayList<PlayerController> getPlayers() {
        return players;
    }

    /**
     * Gets player.
     *
     * @param x the x position of the player.
     * @param y the y position of the player.
     * @return the player.
     */
    public PlayerController getPlayer(int x, int y) {
        for (PlayerController player : players) {
            if (player.getX() == x && player.getY() == y) {
                return player;
            }
        }
        return null;
    }

    /**
     * Gets frozen tiles.
     *
     * @return the frozen tiles
     */
    public ArrayList<FloorCard> getFrozenTiles() {
        return frozenTiles;
    }

    /**
     * Sets players.
     *
     * @param players the players
     */
    public void setPlayers(ArrayList<PlayerController> players) {
        this.players = players;
    }

    /**
     * Get spawn points.
     *
     * @return the spawn points.
     */
    public int[][] getSpawnPoints() {
        return this.spawnPoints;
    }

    /**
     * Gets tiles on fire.
     *
     * @return the tiles on fire.
     */
    public ArrayList<FloorCard> getTilesOnFire() {
        return tilesOnFire;
    }
}
