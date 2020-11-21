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
        setProfileNames();
        setProfileVictories();
        setProfileLosses();
    }

    public ArrayList<String> getProfileNames() {
        return profileNames;
    }

    private void setProfileNames() {
        ArrayList<String> names = new ArrayList<>();
        for (PlayerProfile playerProfile : playerProfiles) {
            names.add(playerProfile.getPlayerName());
        }
        this.profileNames = names;
    }

    public ArrayList<Integer> getProfileVictories() {
        return profileVictories;
    }

    private void setProfileVictories() {
        ArrayList<Integer> victories = new ArrayList<>();
        for (PlayerProfile playerProfile : playerProfiles) {
            victories.add(playerProfile.getVictories());
        }
        this.profileVictories = victories;
    }

    public ArrayList<Integer> getProfileLosses() {
        return profileLosses;
    }

    private void setProfileLosses() {
        ArrayList<Integer> losses = new ArrayList<>();
        for (PlayerProfile playerProfile : playerProfiles) {
            losses.add(playerProfile.getVictories());
        }
        this.profileLosses = losses;
    }
}