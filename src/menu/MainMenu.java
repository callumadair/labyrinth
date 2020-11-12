package menu;

public class MainMenu {
    private boolean isQuit = false;

    public void quitGame() {
        isQuit = true;
    }

    public int getHighScore() {
        return -1;
    }

    private boolean isInstructionsOpen() {
        return false;
    }

    private void startGame() {
    }
}
