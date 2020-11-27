package menu;

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

    /*
    public void saveBoard(Board board, int boardNum) throws IOException {
        File boardFile = new File(getSaveFileDirectory() + "board" + boardNum + ".txt");
        FileWriter fileWriter = new FileWriter(boardFile);

        fileWriter.write(board.getWidth() + " " + board.getHeight() + "\n");
        for (int i = 0; i < );

    }*/
}
