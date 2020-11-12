package menu;

import java.sql.*;

/**
 * The type Database.
 */
public class Database {
    private String url;


    /**
     * Create new database.
     *
     * @param fileName the file name
     */
    public void createNewDatabase(String fileName) {

        this.url = "jdbc:sqlite:" + fileName;

        try (Connection conn = DriverManager.getConnection(this.url)) {
            if (conn != null) {
                System.out.println("Successfully connected to the database");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
