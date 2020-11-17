package menu;

import java.sql.*;

/**
 * The type Database.
 */
public class Database {
    private String url;

    public static void main(String[] args) throws SQLException {
        Database database = new Database();
        database.start("Test");
    }

    /**
     * Create new database.
     *
     * @param fileName the file name
     */
    public void start(String fileName) throws SQLException {
        url = "jdbc:mysql://localhost:3306/" + fileName;
        try (Connection conn = DriverManager.getConnection(url, DatabaseAccess.USER.value,
                DatabaseAccess.PASSWORD.value)) {
            if (conn != null) {
                System.out.println("Connection successful.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Creating new database.");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/?user="
                    + DatabaseAccess.USER.value + "&password=" + DatabaseAccess.PASSWORD.value);
            Statement stmt = conn.createStatement();
            String sql = "CREATE DATABASE " + fileName;
            stmt.executeUpdate(sql);
        }
    }

    /**
     * Create new table.
     *
     * @param sqlTableInfo the sql table info
     */
    public void createNewTable(String sqlTableInfo) {
        try (Connection conn = DriverManager.getConnection(this.url);
             Statement statement = conn.createStatement()) {
            statement.execute(sqlTableInfo);
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
}


