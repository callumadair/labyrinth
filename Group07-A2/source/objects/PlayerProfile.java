package objects;

/**
 * The type Player profile stores data about a specific player profile.
 *
 * @author Callum Adair
 */
public class PlayerProfile {

    private String playerName;
    private int victories;
    private int losses;
    private int playerID;
    private int gamesPlayed;

    /**
     * Instantiates a new Player profile.
     *
     * @param playerName the player name
     * @param victories  the victories
     * @param losses     the losses
     * @param playerID   the player id
     */
    public PlayerProfile(String playerName, int victories, int losses,
                         int playerID) {
        setPlayerName(playerName);
        setVictories(victories);
        setLosses(losses);
        setGamesPlayed();
        setPlayerID(playerID);
    }

    /**
     * Gets player name.
     *
     * @return the player name
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Sets player name.
     *
     * @param playerName the player name
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Gets victories.
     *
     * @return the victories
     */
    public Integer getVictories() {
        return victories;
    }

    /**
     * Sets victories.
     *
     * @param victories the victories
     */
    public void setVictories(int victories) {
        this.victories = victories;
    }

    /**
     * increment player's victories
     */
    public void incrementVictories() {
        victories++;
        setGamesPlayed();
    }

    /**
     * increment player's loses
     */
    public void incrementLoses() {
        losses++;
        setGamesPlayed();
    }

    /**
     * Gets losses.
     *
     * @return the losses
     */
    public int getLosses() {
        return losses;
    }

    /**
     * Sets losses.
     *
     * @param losses the losses
     */
    public void setLosses(int losses) {
        this.losses = losses;
    }

    /**
     * Sets the games played from the sum of wins and loses
     */
    private void setGamesPlayed() {
        this.gamesPlayed = victories + losses;
    }

    /**
     * @return number of games played
     */
    public int getGamesPlayed() {
        return gamesPlayed;
    }

    /**
     * Gets player id.
     *
     * @return the player id
     */
    public int getPlayerID() {
        return playerID;
    }

    /**
     * Sets player id.
     *
     * @param playerID the player id
     */
    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    /**
     * To string string.
     *
     * @return the details of the player as a string
     */
    @Override
    public String toString() {
        return "Player name: '" + playerName + '\'' +
                ", victories: " + victories +
                ", losses: " + losses +
                ", player ID: " + playerID + "\n";
    }
}
