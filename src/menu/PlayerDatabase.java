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
    private String url;

    /**
     * Instantiates a new Player database.
     *
     * @param boardName the board name
     */
    protected PlayerDatabase(String boardName) {
        this.url = "jdbc:sqlite:src/resources/" + boardName + ".db";
    }

    /**
     * Start.
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
     * Create new database.
     *
     * @param boardName the database name
     */
    public void start(String boardName) {
        url = "jdbc:sqlite:src/resources/" + boardName + ".db";
        start();
    }

    /**
     * Connect connection.
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
     * Execute sql operation.
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
     * Store player.
     *
     * @param playerProfile the player profile
     */
    public void storePlayer(PlayerProfile playerProfile) {
        storePlayer(playerProfile.getPlayerName(), playerProfile.getVictories(), playerProfile.getLosses(),
                playerProfile.getPlayerID());
    }

    /**
     * Stores a player profile.
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
     * Update player.
     *
     * @param playerProfile the player profile
     */
    public void updatePlayer(PlayerProfile playerProfile) {
        updatePlayer(playerProfile.getPlayerName(), playerProfile.getVictories(), playerProfile.getLosses(),
                playerProfile.getPlayerID());
    }

    /**
     * Update player.
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
     * Delete player.
     *
     * @param playerProfile the player profile
     */
    public void deletePlayer(PlayerProfile playerProfile) {
        deletePlayer(playerProfile.getPlayerID());
    }

    /**
     * Delete player.
     *
     * @param id the id
     */
    public void deletePlayer(int id) {
        String sql = "delete from PLAYER where ID =" + id + ";";
        executeSQL(sql);
    }

    /**
     * Delete all players from the database.
     */
    public void deleteAllPlayer() {
        for (PlayerProfile playerProfile : getAllData()) {
            deletePlayer(playerProfile.getPlayerID());
        }
    }

    /**
     * Gets player by id.
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
     * Gets all data in the table.
     *
     * @return all the data
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