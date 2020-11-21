package menu;

import objects.*;

import java.util.*;

public class Leaderboard {
    private ArrayList<PlayerProfile> playerProfiles;

    public Leaderboard(PlayerDatabase database) {
        setPlayerProfiles(database.getAllData());
    }

    public ArrayList<PlayerProfile> getPlayerProfiles() {
        return playerProfiles;
    }

    public void setPlayerProfiles(ArrayList<PlayerProfile> playerProfiles) {
        this.playerProfiles = playerProfiles;
    }
}
