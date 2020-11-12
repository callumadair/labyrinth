package menu;

import java.io.*;

/**
 * The type File manager.
 */
public class FileManager {
    private String saveFileDirectory;
    private String playerProfileDirectory;

    /**
     * Instantiates a new File manager.
     */
    public FileManager() {

    }

    /**
     * Gets save file directory.
     *
     * @return the save file directory
     */
    public String getSaveFileDirectory() {
        return saveFileDirectory;
    }

    /**
     * Sets save file directory.
     *
     * @param saveFileDirectory the save file directory
     */
    public void setSaveFileDirectory(String saveFileDirectory) {
        this.saveFileDirectory = saveFileDirectory;
    }

    /**
     * Gets player profile directory.
     *
     * @return the player profile directory
     */
    public String getPlayerProfileDirectory() {
        return playerProfileDirectory;
    }

    /**
     * Sets player profile directory.
     *
     * @param playerProfileDirectory the player profile directory
     */
    public void setPlayerProfileDirectory(String playerProfileDirectory) {
        this.playerProfileDirectory = playerProfileDirectory;
    }

    /**
     * Delete save file.
     *
     * @param saveFile the save file
     */
    public void deleteSaveFile(File saveFile) {

    }

    /**
     * Delete player profile.
     *
     * @param playerNumber the player number
     */
    public void deletePlayerProfile(int playerNumber) {

    }
}
