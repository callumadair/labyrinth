package menu;

import objects.*;

import java.util.*;

/**
 * The type Leaderboard.
 */
public class Leaderboard {
    private ArrayList<PlayerProfile> playerProfiles;
    private ArrayList<String> profileNames;
    private ArrayList<Integer> profileVictories;
    private ArrayList<Integer> profileLosses;

    /**
     * Instantiates a new Leaderboard.
     *
     * @param database the database
     */
    public Leaderboard(PlayerDatabase database) {
        setPlayerProfiles(database.getAllData());
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
                p2.getPlayerID().compareTo(p1.getVictories()));
    }

    public ArrayList<String> getProfileNames() {
        return profileNames;
    }

    public void setProfileNames() {
        ArrayList<String> names = new ArrayList<>();
        for (PlayerProfile playerProfile : playerProfiles) {
            names.add(playerProfile.getPlayerName());
        }
        this.profileNames = names;
    }

    public ArrayList<Integer> getProfileVictories() {
        return profileVictories;
    }

    public void setProfileVictories() {
        ArrayList<Integer> victories = new ArrayList<>();
        for (PlayerProfile playerProfile : playerProfiles) {
            victories.add(playerProfile.getVictories());
        }
        this.profileVictories = victories;
    }

    public ArrayList<Integer> getProfileLosses() {
        return profileLosses;
    }

    public void setProfileLosses(ArrayList<Integer> profileLosses) {
        ArrayList<Integer> losses = new ArrayList<>();
        for (PlayerProfile playerProfile : playerProfiles) {
            losses.add(playerProfile.getVictories());
        }
        this.profileLosses  = losses;
    }
}