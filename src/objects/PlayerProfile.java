package objects;

/**
 * The type Player profile.
 *
 * @author Cal
 */
public class PlayerProfile {
    private String playerName;
    private int victories;
    private int losses;
    private int gamesPlayed;
    private int playerID;

    /**
     * Instantiates a new Player profile.
     *
     * @param playerName the player name
     * @param victories  the victories
     * @param losses     the losses
     * @param playerID   the player id
     */
    public PlayerProfile(String playerName, int victories, int losses, int playerID) {
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

    public void incrementVictories(){
        victories++;
    }

    public void incrementLoses(){
        losses++;
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

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    private void setGamesPlayed() {
        this.gamesPlayed = victories + losses;
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

    @Override
    public String toString() {
        return "Player name: '" + playerName + '\'' +
                ", victories: " + victories +
                ", losses: " + losses +
                ", player ID: " + playerID + "\n";
    }

}
