package menu;

/**
 * The type Main menu.
 */
public class MainMenu {
    private RunningState state = RunningState.IN_MENU;
    /**
     * Instantiates a new Main menu.
     */
    public MainMenu() {

    }

    public static void start() {
         MainMenu menu = new MainMenu();
    }

    /**
     * Quit game.
     */
    public void exitApplication() {
        state = RunningState.EXIT;
    }

    /**
     * Gets high score.
     *
     * @return the high score
     */
    public String getScores() {
        return null;
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
enum RunningState {
    IN_MENU,
    IN_INSTRUCTIONS,
    IN_GAME,
    EXIT;
}