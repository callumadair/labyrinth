package menu;

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
        database.getAllData("test");
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
    public void executeSQLOperation(String sql) throws SQLException {
        Connection conn = DriverManager.getConnection(url, DatabaseAccess.USER.value, DatabaseAccess.PASSWORD.value);
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql);
    }

    /**
     * Gets data.
     *
     * @throws SQLException the sql exception
     */
    public void getAllData() throws SQLException {
        Connection conn = DriverManager.getConnection(url, DatabaseAccess.USER.value, DatabaseAccess.PASSWORD.value);
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM player;";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            String name = rs.getString("PLAYER_NAME");
            int victories = rs.getInt("VICTORIES");
            int losses = rs.getInt("LOSSES");
        }
    }
}


