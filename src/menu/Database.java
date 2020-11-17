package menu;

import java.sql.*;

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
        database.getData("test");
    }

    /**
     * Create new database.
     *
     * @param fileName the file name
     * @throws SQLException the sql exception
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
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/?user="
                    + DatabaseAccess.USER.value + "&password=" + DatabaseAccess.PASSWORD.value);
            Statement stmt = conn.createStatement();
            String sql = "CREATE DATABASE " + fileName;
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
     * @param sql the sql
     * @throws SQLException the sql exception
     */
    public void getData(String sql) throws SQLException {
        Connection conn = DriverManager.getConnection(url, DatabaseAccess.USER.value, DatabaseAccess.PASSWORD.value);
        Statement stmt = conn.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        
    }
}


