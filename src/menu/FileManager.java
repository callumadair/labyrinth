package menu;

import objects.*;

import java.io.*;
import java.util.*;

/**
 * The type File manager.
 */
public class FileManager {
    private static String saveFileDirectory = "src/resources/";

    /**
     * Sets the save file directory.
     *
     * @param saveFileDirectory the save file directory
     */
    public static void setSaveFileDirectory(String saveFileDirectory) {
        FileManager.saveFileDirectory = saveFileDirectory;
    }

    /**
     * Gets the save file directory.
     *
     * @return the save file directory
     */
    public static String getSaveFileDirectory() {
        return saveFileDirectory;
    }

    /**
     * Save the game in it's current state
     *
     * @param board    the board
     * @param gameName the game name
     * @throws IOException the io exception
     */
    public static void saveGame(Board board, String gameName) throws IOException {
        File gameFile = new File(getSaveFileDirectory() + gameName + ".txt");
        FileWriter fileWriter = new FileWriter(gameFile);

        fileWriter.write(board.getWidth() + " " + board.getHeight() + "\n");

        for (int i = 0; i < board.getSpawnPoints().length; i++) {
            for (int j = 0; j < board.getSpawnPoints()[i].length; j++) {
                fileWriter.write(board.getSpawnPoints()[i][j] + " ");
            }
        }
        fileWriter.write("\n");

        //Save tiles in board currently.
        for (int k = 0; k < board.getWidth(); k++) {
            for (int l = 0; l < board.getHeight(); l++) {
                FloorCard curTile = board.getTile(k, l);
                fileWriter.write(curTile.getType().toString() + " " + k + " " + l + " " + curTile.getRotation()
                        + " " + curTile.isFixed() + " " + curTile.getState().toString() + " "
                        + curTile.getEffectTimer() + "\n");
            }
        }
        fileWriter.write("\n");

        //Save tiles in silkbag currently.
        ArrayList<Card> cardsInBag = board.getSilkBag().getListOfCards();
        for (int m = 0; m < countSilkBagCards(cardsInBag).length; m++) {
            fileWriter.write(countSilkBagCards(cardsInBag)[m] + " ");
            if (m == 2) {
                fileWriter.write("\n");
            }
        }
        fileWriter.write("\n");

        ArrayList<PlayerController> players = board.getPlayers();
        for (PlayerController player : players) {
            fileWriter.write(getPlayerDetails(player) + "\n");
        }
        fileWriter.close();
    }

    /**
     * Counts the number of each type of card in the silk bag
     * @param cardsInBag
     * @return
     */
    private static int[] countSilkBagCards(ArrayList<Card> cardsInBag) {
        int[] values = new int[7];
        for (Card curCard : cardsInBag) {
            if (curCard.getClass().getSimpleName().equals("FloorCard")) {
                switch (((FloorCard) curCard).getType().toString()) {
                    case "STRAIGHT":
                        values[0]++;
                        break;
                    case "CORNER":
                        values[1]++;
                        break;
                    case "T_SHAPED":
                        values[2]++;
                        break;
                }
            } else {
                switch (((ActionCard) curCard).getType().toString()) {
                    case "FIRE":
                        values[3]++;
                        break;
                    case "ICE":
                        values[4]++;
                        break;
                    case "BACKTRACK":
                        values[5]++;
                        break;
                    case "DOUBLE_MOVE":
                        values[6]++;
                        break;
                }
            }
        }
        return values;
    }

    /**
     * Gets the player's details
     * @param player
     * @return
     */
    private static String getPlayerDetails(PlayerController player) {
        int[] cardValues = new int[4];
        for (Card curCard : player.getCardsHeld()) {
            switch (((ActionCard) curCard).getType().toString()) {
                case "FIRE":
                    cardValues[0]++;
                    break;
                case "ICE":
                    cardValues[1]++;
                    break;
                case "BACKTRACK":
                    cardValues[2]++;
                    break;
                case "DOUBLE_MOVE":
                    cardValues[3]++;
                    break;
            }
        }
        StringBuilder vals = new StringBuilder();
        for (int i = 0; i < cardValues.length; i++) {
            if (i == cardValues.length - 1) {
                vals.append(cardValues[i]);
            } else {
                vals.append(cardValues[i]).append(" ");
            }
        }
        return player.getProfile().getPlayerName() + " " + player.getProfile().getVictories() + " "
                + player.getProfile().getLosses() + " " + player.getProfile().getPlayerID() + " "
                + player.getPlayerIndex() + " " + player.getX() + " " + player.getY() + " " + player.isCurrentPlayer()
                + " " + vals.toString();
    }

    /**
     * Loads the game board.
     *
     * @param gameName the game name
     * @return the board
     * @throws FileNotFoundException the file not found exception
     */
    public static Board loadGame(String gameName) throws FileNotFoundException {
        File gameFile = new File(getSaveFileDirectory() + gameName + ".txt");
        Scanner scanner = new Scanner(gameFile);

        int width = scanner.nextInt();
        int height = scanner.nextInt();
        scanner.nextLine();

        int[][] spawnPoints = new int[4][2];
        for (int i = 0; i < spawnPoints.length; i++) {
            for (int j = 0; j < spawnPoints[i].length; j++) {
                spawnPoints[i][j] = scanner.nextInt();
            }
        }
        scanner.nextLine();
        ArrayList<FloorCard> insertedCards = new ArrayList<>();
        int fixedCount = 0;
        for (int k = 0; k < width; k++) {
            for (int l = 0; l < height; l++) {
                FloorCard newFloorCard = new FloorCard(scanner.next());
                newFloorCard.setX(scanner.nextInt());
                newFloorCard.setY(scanner.nextInt());
                newFloorCard.setRotation(scanner.nextInt());
                newFloorCard.setFixed(scanner.nextBoolean());
                setEffect(newFloorCard, scanner.next(), scanner.nextInt());

                if (newFloorCard.isFixed()) {
                    fixedCount++;
                }
                insertedCards.add(newFloorCard);
            }
        }

        FloorCard[] fixed = new FloorCard[fixedCount];
        int index = 0;
        for (FloorCard insertedCard : insertedCards) {
            if (insertedCard.isFixed()) {
                fixed[index] = insertedCard;
                index++;
            }
        }

        ArrayList<Card> silkBagCards = new ArrayList<>();
        loadSilkBagCards(silkBagCards, scanner);
        SilkBag silkBag = new SilkBag(silkBagCards.size());
        silkBag.setListOfCards(silkBagCards);
        scanner.nextLine();

        ArrayList<PlayerController> players = new ArrayList<>();
        while (scanner.hasNextLine()) {
            players.add(createPlayerController(scanner.nextLine()));
        }

        return new Board(width, height, spawnPoints, fixed, silkBag, players, insertedCards);
    }

    /**
     * Allows the fire and Ice action cards to have an effect
     * @param floorCard
     * @param effect
     * @param time
     */
    private static void setEffect(FloorCard floorCard, String effect, int time) {
        switch (effect) {
            case "FIRE":
                floorCard.setOnFire(time);
                break;
            case "ICE":
                floorCard.setOnIce(time);
                break;
        }
    }

    /**
     * Method to control the player around the board
     * @param info
     * @return
     */
    private static PlayerController createPlayerController(String info) {
        Scanner playerScanner = new Scanner(info);
        PlayerProfile newProfile = new PlayerProfile(playerScanner.next(), playerScanner.nextInt(),
                playerScanner.nextInt(), playerScanner.nextInt());

        PlayerController newController = new PlayerController(newProfile, playerScanner.nextInt());
        newController.setX(playerScanner.nextInt());
        newController.setY(playerScanner.nextInt());
        newController.setCurrentPlayer(playerScanner.nextBoolean());

        int[] cardValues = new int[4];
        for (int i = 0; i < cardValues.length; i++) {
            cardValues[i] = playerScanner.nextInt();
            String type = "";
            switch (i) {
                case 0:
                    type = "FIRE";
                    break;
                case 1:
                    type = "ICE";
                    break;
                case 2:
                    type = "BACKTRACK";
                    break;
                case 3:
                    type = "DOUBLE_MOVE";
                    break;
            }

            for (int j = 0; j < cardValues[i]; j++) {
                newController.addInCardsHeld(new ActionCard(type));
            }
        }
        return newController;
    }

    /**
     * Load the cards so that they are in the silk bag
     * @param silkBagCards
     * @param scanner
     * @return
     */
    private static int loadSilkBagCards(ArrayList<Card> silkBagCards, Scanner scanner) {
        int straightCount = scanner.nextInt();
        createFloorCards(straightCount, "STRAIGHT", silkBagCards);
        int cornerCunt = scanner.nextInt();
        createFloorCards(cornerCunt, "CORNER", silkBagCards);
        int tShapedCount = scanner.nextInt();
        createFloorCards(tShapedCount, "T_SHAPED", silkBagCards);
        int floorCardCount = 0;
        floorCardCount += straightCount + cornerCunt + tShapedCount;

        scanner.nextLine();
        createActionCards(scanner.nextInt(), "FIRE", silkBagCards);
        createActionCards(scanner.nextInt(), "ICE", silkBagCards);
        createActionCards(scanner.nextInt(), "BACKTRACK", silkBagCards);
        createActionCards(scanner.nextInt(), "DOUBLE_MOVE", silkBagCards);

        return floorCardCount;
    }

    /**
     * Loads a board
     *
     * @param boardName      the board name
     * @param playerProfiles the player profiles
     * @return the board
     * @throws FileNotFoundException the file not found exception
     */
    public static Board loadBoard(String boardName, ArrayList<PlayerProfile> playerProfiles) throws FileNotFoundException {
        File boardFile = new File(getSaveFileDirectory() + boardName + ".txt");
        Scanner scanner = new Scanner(boardFile);

        int width = scanner.nextInt();
        int height = scanner.nextInt();
        scanner.nextLine();

        int[][] spawnPoints = new int[4][2];
        for (int i = 0; i < spawnPoints.length; i++) {
            for (int j = 0; j < spawnPoints[i].length; j++) {
                spawnPoints[i][j] = scanner.nextInt();
            }
        }
        scanner.nextLine();
        int numFixed = scanner.nextInt();
        FloorCard[] fixed = new FloorCard[numFixed];
        for (int k = 0; k < fixed.length; k++) {
            String type = scanner.next();
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            int rotation = scanner.nextInt();
            fixed[k] = new FloorCard(type, x, y, rotation);
        }
        ArrayList<Card> silkBagCards = new ArrayList<>();
        int floorCardCount = loadSilkBagCards(silkBagCards, scanner);

        if ((width * height) - numFixed >= floorCardCount) {
            throw new IllegalArgumentException("Not enough floor cards in this file, please try again with more than "
                    + width * height + " floor cards.");
        }
        SilkBag silkBag = new SilkBag(silkBagCards.size());
        silkBag.setListOfCards(silkBagCards);

        ArrayList<PlayerController> players = new ArrayList<>();
        for (int i = 0; i < playerProfiles.size(); i++) {
            players.add(new PlayerController(playerProfiles.get(i), i));
        }
        return new Board(width, height, spawnPoints, fixed, silkBag, players);
    }

    /**
     * Creates the floor cards
     * @param num
     * @param type
     * @param cards
     */
    private static void createFloorCards(int num, String type, ArrayList<Card> cards) {
        for (int i = 0; i < num; i++) {
            cards.add(new FloorCard(type));
        }
    }

    /**
     * Creates the action cards
     * @param num
     * @param type
     * @param cards
     */
    private static void createActionCards(int num, String type, ArrayList<Card> cards) {
        for (int i = 0; i < num; i++) {
            cards.add(new ActionCard(type));
        }
    }
}
