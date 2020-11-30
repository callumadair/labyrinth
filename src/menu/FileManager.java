package menu;

import objects.*;

import java.io.*;
import java.util.*;

public class FileManager {
    private String saveFileDirectory;

    public FileManager(String saveFileDirectory) {
        setSaveFileDirectory(saveFileDirectory);
    }

    public String getSaveFileDirectory() {
        return saveFileDirectory;
    }

    public void setSaveFileDirectory(String saveFileDirectory) {
        this.saveFileDirectory = saveFileDirectory;
    }

    public void deleteSaveFile(File saveFile) {
    }


    public void saveBoard(Board board, int boardNum) throws IOException {
        File boardFile = new File(getSaveFileDirectory() + "board" + boardNum + ".txt");
        FileWriter fileWriter = new FileWriter(boardFile);

        fileWriter.write(board.getWidth() + " " + board.getHeight() + "\n");

        for (int i = 0; i < board.getSpawnPoints().length; i++) {
            for (int j = 0; j < board.getSpawnPoints()[i].length; j++) {
                fileWriter.write(board.getSpawnPoints()[i][j] + " ");
            }
        }
        fileWriter.write("\n");

        fileWriter.write(board.getFixedTilesNum() + "\n");
        for (int k = 0; k < board.getFixedTiles().length; k++) {
            int x = board.getFixedTiles()[k][0];
            int y = board.getFixedTiles()[k][1];
            fileWriter.write(board.getTile(x, y).getType().toString() + " " + x + " " + y + " "
                    + board.getTile(x, y).getRotation());
        }
        fileWriter.write("\n");
        //add non fixed cards
    }

    public void loadBoard(int boardNum) throws FileNotFoundException {
        File boardFile = new File(getSaveFileDirectory() + "board" + boardNum + ".txt");
        Scanner scanner = new Scanner(boardFile);

        int width = scanner.nextInt();
        int length = scanner.nextInt();
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
        createFloorCards(scanner.nextInt(), "STRAIGHT", silkBagCards);
        createFloorCards(scanner.nextInt(), "CORNER", silkBagCards);
        createFloorCards(scanner.nextInt(), "T_SHAPED", silkBagCards);
        scanner.nextLine();
        silkBagCards.addAll(createActionCards(scanner.nextInt(), "FIRE"));
        silkBagCards.addAll(createActionCards(scanner.nextInt(), "ICE"));
        silkBagCards.addAll(createActionCards(scanner.nextInt(), "BACKTRACK"));
        silkBagCards.addAll(createActionCards(scanner.nextInt(), "DOUBLE_MOVE"));

        SilkBag silkBag = new SilkBag(silkBagCards.size());
        silkBag.setListOfCards(silkBagCards);
    }

    private void createFloorCards(int num, String type, ArrayList<Card> cards) {
        for (int i = 0; i < num; i++) {
            cards.add(new FloorCard(type));
        }
    }

    private ArrayList<Card> createActionCards(int num, String type) {
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            cards.add(new ActionCard(type));
        }
        return cards;
    }
}
