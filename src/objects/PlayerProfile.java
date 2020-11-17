package objects;

/**
 * The type Player profile.
 */
public class PlayerProfile {
    private String playerName;
    private int victories;
    private int losses;

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
    public int getVictories() {
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
}
