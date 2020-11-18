package menu;

import objects.*;

import java.sql.*;
import java.util.*;

/**
 * The type Database.
 */
public class Database {
    private String url;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws SQLException the sql exception
     */
    public static void main(String[] args) throws SQLException {
        Database database = new Database();
        database.start("test");
        /*String createTable = "CREATE TABLE PLAYER " +
                "(player_name VARCHAR(255)," +
                "victories INTEGER," +
                "losses INTEGER," +
                "id INTEGER," +
                "PRIMARY KEY (id))";*/

        for (PlayerProfile profile : database.getAllData()) {
            System.out.println(profile.toString());
        }
        database.deletePlayer(9998);
        for (PlayerProfile profile : database.getAllData()) {
            System.out.println(profile.toString());
        }
    }

    /**
     * Create new database.
     *
     * @param databaseName the database name
     * @throws SQLException the sql exception
     */
    public void start(String databaseName) throws SQLException {
        url = "jdbc:mysql://localhost:3306/" + databaseName;
        try (Connection conn = DriverManager.getConnection(url, DatabaseAccess.USER.value,
                DatabaseAccess.PASSWORD.value)) {
            if (conn != null) {
                System.out.println("Connection successful.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Creating new database.");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/?user="
                    + DatabaseAccess.USER.value + "&password=" + DatabaseAccess.PASSWORD.value);
            Statement stmt = conn.createStatement();
            String sql = "CREATE DATABASE " + databaseName;
            stmt.executeUpdate(sql);
        }
    }

    /**
     * Execute sql operation.
     *
     * @param sql the sql
     * @throws SQLException the sql exception
     */
    public void executeSQL(String sql) throws SQLException {
        Connection conn = DriverManager.getConnection(url, DatabaseAccess.USER.value,
                DatabaseAccess.PASSWORD.value);
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
        String sql = "insert into PLAYER (PLAYER_NAME, VICTORIES, LOSSES, ID) " +
                "values ('" + playerProfile.getPlayerName() + "', " + playerProfile.getVictories() + ", "
                + playerProfile.getLosses() + ", " + playerProfile.getPlayerID() + ")";
        executeSQL(sql);
    }

    /**
     * Insert data.
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
     * Delete player.
     *
     * @param playerProfile the player profile
     * @throws SQLException the sql exception
     */
    public void deletePlayer(PlayerProfile playerProfile) throws SQLException {
        String sql = "DELETE FROM player WHERE id =" + playerProfile.getPlayerID() + ";";
        executeSQL(sql);
    }
    /**
     * Delete player.
     *
     * @param id the id
     * @throws SQLException the sql exception
     */
    public void deletePlayer(int id) throws SQLException {
        String sql = "DELETE FROM player WHERE id =" + id + ";";
        executeSQL(sql);
    }

    /**
     * Gets data.
     *
     * @return the all data
     * @throws SQLException the sql exception
     */
    public ArrayList<PlayerProfile> getAllData() throws SQLException {
        Connection conn = DriverManager.getConnection(url, DatabaseAccess.USER.value,
                DatabaseAccess.PASSWORD.value);
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM player;";
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