package menu;

import objects.*;

import java.sql.*;
import java.util.*;

/**
 * The type Database.
 *
 * @author Cal
 */
public class PlayerDatabase {
    private String url;

    /**
     * Create new database.
     *
     * @param databaseName the database name
     */
    public void start(String databaseName) {
        url = "jdbc:sqlite:resources/" + databaseName;
        try (Connection conn = connect()) {
            if (conn != null) {
                System.out.println("Connection successful.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

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
        Connection conn = connect();
        try {
            Statement stmt = conn.createStatement();
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
    public void storePlayer(String playerName, int victories, int losses, int id){
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
        Connection conn = this.connect();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
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
     * Gets all data in the table.
     *
     * @return the all data
     */
    public ArrayList<PlayerProfile> getAllData() {
        Connection conn = connect();
        ArrayList<PlayerProfile> storedProfiles = null;
        try {
            Statement stmt = conn.createStatement();
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
}