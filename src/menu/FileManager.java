package menu;

import objects.*;

import java.io.*;
import java.util.*;

public class FileManager {
    private static String saveFileDirectory = "src/resources/";

    public static void setSaveFileDirectory(String saveFileDirectory) {
        FileManager.saveFileDirectory = saveFileDirectory;
    }

    public static String getSaveFileDirectory() {
        return saveFileDirectory;
    }

    public static void deleteSaveFile(File saveFile) {
    }

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
                        + " " + curTile.isFixed() + "\n");
            }
        }
        fileWriter.write("\n");
        fileWriter.write("silkbagCards\n");

        //Save tiles in silkbag currently.
        ArrayList<Card> cardsInBag = board.getSilkBag().getListOfCards();
        for (int curVal : countSilkBagCards(cardsInBag)) {
            fileWriter.write(curVal + " ");
        }
        fileWriter.write("\n");

        ArrayList<PlayerController> players = board.getPlayers();
        for (PlayerController player : players) {
            fileWriter.write(getPlayerDetails(player) + "\n");
        }
    }

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
                        values[7]++;
                        break;
                }
            }
        }
        return values;
    }

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
        return player.getPlayerIndex() + " " + player.getProfile().getPlayerName() + " " + player.getX() + " "
                + player.getY() + " " + vals.toString();
    }

    public static Board loadGame(String gameName) throws FileNotFoundException {
        File gameFile = new File(getSaveFileDirectory() + gameName + ".txt");
        Scanner scanner = new Scanner(gameFile);
        return null;
    }

    public static Board loadBoard(int boardNum) throws FileNotFoundException {
        File boardFile = new File(getSaveFileDirectory() + "board" + boardNum + ".txt");
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
        int straightCount = scanner.nextInt();
        createFloorCards(straightCount, "STRAIGHT", silkBagCards);
        int cornerCunt = scanner.nextInt();
        createFloorCards(cornerCunt, "CORNER", silkBagCards);
        int tShapedCount = scanner.nextInt();
        createFloorCards(tShapedCount, "T_SHAPED", silkBagCards);
        int floorCardCount = straightCount + cornerCunt + tShapedCount;

        if ((width * height) - numFixed >= floorCardCount) {
            throw new IllegalArgumentException("Not enough floor cards in this file, please try again with more than "
                    + width * height + " floor cards.");
        }

        scanner.nextLine();
        createActionCards(scanner.nextInt(), "FIRE", silkBagCards);
        createActionCards(scanner.nextInt(), "ICE", silkBagCards);
        createActionCards(scanner.nextInt(), "BACKTRACK", silkBagCards);
        createActionCards(scanner.nextInt(), "DOUBLE_MOVE", silkBagCards);

        SilkBag silkBag = new SilkBag(silkBagCards.size());
        silkBag.setListOfCards(silkBagCards);

        return new Board(width, height, spawnPoints, fixed, silkBag);
    }

    private static void createFloorCards(int num, String type, ArrayList<Card> cards) {
        for (int i = 0; i < num; i++) {
            cards.add(new FloorCard(type));
        }
    }

    private static void createActionCards(int num, String type, ArrayList<Card> cards) {
        for (int i = 0; i < num; i++) {
            cards.add(new ActionCard(type));
        }
    }
}
