package menu;

/**
 * The type Main menu.
 */
public class MainMenu {
    private boolean isQuit = false;

    /**
     * Quit game.
     */
    public void quitGame() {
        isQuit = true;
    }

    /**
     * Gets high score.
     *
     * @return the high score
     */
    public int getHighScore() {
        return -1;
    }

    private boolean isInstructionsOpen() {
        return false;
    }

    private void startGame() {
    }

    /**
     * Sets game.
     */
    public void setupGame() {

    }

    /**
     * Gets player profiles.
     */
    public void getPlayerProfiles() {

    }
}
