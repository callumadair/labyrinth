package menu;

import objects.*;

import java.sql.*;
import java.util.*;

/**
 * The type Database.
 *
 * @author Callum Adair
 */
public class PlayerDatabase {
    private final String url;

    /**
     * Instantiates a new Player database.
     *
     * @param boardName the board name
     */
    protected PlayerDatabase(String boardName) {
        this.url = "jdbc:sqlite:src/resources/" + boardName + ".db";
    }

    /**
     * Start the appropriate database
     */
    public void start() {
        try (Connection conn = connect()) {
            if (conn != null) {
                System.out.println("Connection successful.");
            }
            String sql = "create table if not exists PLAYER (" +
                    "PLAYER_NAME varchar(50), " +
                    "VICTORIES int, " +
                    "LOSSES int," +
                    "ID int);";
            executeSQL(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Connects to the database.
     *
     * @return the connection
     */
    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(this.url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Executes the specified sql operation.
     *
     * @param sql the sql
     */
    public void executeSQL(String sql) {
        try {
            Statement stmt = connect().createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Stores a new player profile.
     *
     * @param playerProfile the player profile
     */
    public void storePlayer(PlayerProfile playerProfile) {
        storePlayer(playerProfile.getPlayerName(), playerProfile.getVictories(), playerProfile.getLosses(),
                playerProfile.getPlayerID());
    }

    /**
     * Stores a player profile, using its attributes.
     *
     * @param playerName the player name
     * @param victories  the victories
     * @param losses     the losses
     * @param id         the id
     */
    public void storePlayer(String playerName, int victories, int losses, int id) {
        String sql = "insert into PLAYER (PLAYER_NAME, VICTORIES, LOSSES, ID) " +
                "values ('" + playerName + "', " + victories + ", " + losses + ", " + id + ")";
        executeSQL(sql);
    }

    /**
     * Updates the details of the specified player profile.
     *
     * @param playerProfile the player profile
     */
    public void updatePlayer(PlayerProfile playerProfile) {
        updatePlayer(playerProfile.getPlayerName(), playerProfile.getVictories(), playerProfile.getLosses(),
                playerProfile.getPlayerID());
    }

    /**
     * Updates the specified player profile by its attributes.
     *
     * @param playerName the player name
     * @param victories  the victories
     * @param losses     the losses
     * @param id         the id
     */
    public void updatePlayer(String playerName, int victories, int losses, int id) {
        String sql = "update PLAYER set PLAYER_NAME = ? , VICTORIES = ? , LOSSES = ? where ID = ?";
        try {
            PreparedStatement preparedStatement = connect().prepareStatement(sql);
            preparedStatement.setString(1, playerName);
            preparedStatement.setInt(2, victories);
            preparedStatement.setInt(3, losses);
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Increments victories of the specified player profile.
     *
     * @param id the id
     */
    public void incrementVictories(PlayerProfile playerProfile) {
        if (getProfileByID(playerProfile.getPlayerID()) != null) {
            String sql = "update PLAYER set VICTORIES = VICTORIES + 1 where ID = ? and ID is not null;";
            try {
                PreparedStatement preparedStatement = connect().prepareStatement(sql);
                preparedStatement.setInt(1, playerProfile.getPlayerID());

                preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            storePlayer(playerProfile.getPlayerName(), 1, 0, playerProfile.getPlayerID());
        }
    }

    /**
     * Increments losses of the specified player profile.
     *
     * @param id the id
     */
    public void incrementLosses(PlayerProfile playerProfile) {
        if (getProfileByID(playerProfile.getPlayerID()) != null) {
            String sql = "update PLAYER set LOSSES = LOSSES + 1 where ID = ?;";
            try {
                PreparedStatement preparedStatement = connect().prepareStatement(sql);
                preparedStatement.setInt(1, playerProfile.getPlayerID());

                preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            storePlayer(playerProfile.getPlayerName(), 0, 1, playerProfile.getPlayerID());
        }
    }

    /**
     * Gets the specified player profile by its id.
     *
     * @param id the id
     * @return the player by id
     */
    public PlayerProfile getProfileByID(int id) {
        PlayerProfile playerProfile = null;
        try {
            Statement statement = connect().createStatement();
            String sql = "select * from PLAYER where ID =" + id;
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                String name = rs.getString("PLAYER_NAME");
                int victories = rs.getInt("VICTORIES");
                int losses = rs.getInt("LOSSES");
                playerProfile = new PlayerProfile(name, victories, losses, id);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return playerProfile;
    }

    /**
     * Deletes the specified player profile from the database.
     *
     * @param playerProfile the player profile
     */
    public void deletePlayer(PlayerProfile playerProfile) {
        deletePlayer(playerProfile.getPlayerID());
    }

    /**
     * Deletes the specified player profile by its id from the database.
     *
     * @param id the id
     */
    public void deletePlayer(int id) {
        String sql = "delete from PLAYER where ID =" + id + ";";
        executeSQL(sql);
    }

    /**
     * Gets all data in the table.
     *
     * @return all the player profiles held by this database.
     */
    public ArrayList<PlayerProfile> getAllData() {
        ArrayList<PlayerProfile> storedProfiles = null;
        try {
            Statement stmt = connect().createStatement();
            String sql = "select * from PLAYER;";
            ResultSet rs = stmt.executeQuery(sql);

            storedProfiles = new ArrayList<>();
            while (rs.next()) {
                String name = rs.getString("PLAYER_NAME");
                int victories = rs.getInt("VICTORIES");
                int losses = rs.getInt("LOSSES");
                int id = rs.getInt("ID");
                storedProfiles.add(new PlayerProfile(name, victories, losses, id));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return storedProfiles;
    }

    /**
     * Gets all active profiles.
     *
     * @return all profiles with more than 1 game played.
     */
    public ArrayList<PlayerProfile> getAllActiveProfiles() {
        ArrayList<PlayerProfile> activePlayers = new ArrayList<>();
        for (PlayerProfile playerProfile : getAllData()) {
            if (playerProfile.getVictories() + playerProfile.getLosses() > 0) {
                activePlayers.add(playerProfile);
            }
        }
        activePlayers.sort((PlayerProfile p1, PlayerProfile p2) ->
                p2.getVictories().compareTo(p1.getVictories()));
        return activePlayers;
    }
}