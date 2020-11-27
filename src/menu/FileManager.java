package menu;

import objects.*;

import java.io.*;

public class FileManager {
    private String saveFileDirectory;

    public FileManager(String saveFileDirectory) {
        this.saveFileDirectory = saveFileDirectory;
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
            fileWriter.write("\n");
        }


        fileWriter.write(board.getFixedTilesNum() + "\n");
        for (int k = 0; k < board.getFixedTiles().length; k++) {
            int x = board.getFixedTiles()[k][0];
            int y = board.getFixedTiles()[k][1];
            fileWriter.write(board.getTile(x, y).getType().toString() + " " + x + " " + y + " "
                    + board.getTile(x, y).getRotation());
        }
        //add non fixed cards
    }
}
