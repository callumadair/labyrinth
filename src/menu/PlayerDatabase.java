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
     * @throws SQLException the sql exception
     */
    public void start(String databaseName) throws SQLException {
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
     * @throws SQLException the sql exception
     */
    public void executeSQL(String sql) throws SQLException {
        Connection conn = connect();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql);
    }

    /**
     * Store player.
     *
     * @param playerProfile the player profile
     * @throws SQLException the sql exception
     */
    public void storePlayer(PlayerProfile playerProfile) throws SQLException {
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
     * @throws SQLException the sql exception
     */
    public void storePlayer(String playerName, int victories, int losses, int id) throws SQLException {
        String sql = "insert into PLAYER (PLAYER_NAME, VICTORIES, LOSSES, ID) " +
                "values ('" + playerName + "', " + victories + ", " + losses + ", " + id + ")";
        executeSQL(sql);
    }

    /**
     * Update player.
     *
     * @param playerProfile the player profile
     * @throws SQLException the sql exception
     */
    public void updatePlayer(PlayerProfile playerProfile) throws SQLException {
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
     * @throws SQLException the sql exception
     */
    public void updatePlayer(String playerName, int victories, int losses, int id) throws SQLException {
        String sql = "update PLAYER set PLAYER_NAME = ? , VICTORIES = ? , LOSSES = ? where ID = ?";

        Connection conn = this.connect();
        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setString(1, playerName);
        preparedStatement.setInt(2, victories);
        preparedStatement.setInt(3, losses);
        preparedStatement.setInt(4, id);

        preparedStatement.executeUpdate();
    }

    /**
     * Delete player.
     *
     * @param playerProfile the player profile
     * @throws SQLException the sql exception
     */
    public void deletePlayer(PlayerProfile playerProfile) throws SQLException {
        deletePlayer(playerProfile.getPlayerID());
    }

    /**
     * Delete player.
     *
     * @param id the id
     * @throws SQLException the sql exception
     */
    public void deletePlayer(int id) throws SQLException {
        String sql = "delete from PLAYER where ID =" + id + ";";
        executeSQL(sql);
    }

    /**
     * Gets all data in the table.
     *
     * @return the all data
     * @throws SQLException the sql exception
     */
    public ArrayList<PlayerProfile> getAllData() throws SQLException {
        Connection conn = connect();
        Statement stmt = conn.createStatement();
        String sql = "select * from PLAYER;";
        ResultSet rs = stmt.executeQuery(sql);

        ArrayList<PlayerProfile> storedProfiles = new ArrayList<>();
        while (rs.next()) {
            String name = rs.getString("PLAYER_NAME");
            int victories = rs.getInt("VICTORIES");
            int losses = rs.getInt("LOSSES");
            int id = rs.getInt("ID");
            storedProfiles.add(new PlayerProfile(name, victories, losses, id));
        }
        return storedProfiles;
    }
}