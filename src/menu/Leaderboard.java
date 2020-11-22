package menu;

import objects.*;

import java.util.*;

/**
 * The type Leaderboard.
 */
public class Leaderboard {
    private ArrayList<PlayerProfile> playerProfiles;

    /**
     * Instantiates a new Leaderboard.
     *
     * @param database the database
     */
    public Leaderboard(PlayerDatabase database) {
        setPlayerProfiles(database.getAllActiveProfiles());
    }

    /**
     * Gets player profiles.
     *
     * @return the player profiles
     */
    public ArrayList<PlayerProfile> getPlayerProfiles() {
        return playerProfiles;
    }

    /**
     * Sets player profiles.
     *
     * @param playerProfiles the player profiles
     */
    public void setPlayerProfiles(ArrayList<PlayerProfile> playerProfiles) {
        this.playerProfiles = playerProfiles;
        this.playerProfiles.sort((PlayerProfile p1, PlayerProfile p2) ->
                p2.getVictories().compareTo(p1.getVictories()));
    }
}