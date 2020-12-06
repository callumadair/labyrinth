package menu;

import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import objects.*;

import java.io.*;

/**
 * Used to create a Leaderboard pane filled with the relevant data for a
 * specified game board.
 *
 * @author Callum Adair
 */
public class Leaderboard {
    @FXML
    private static TableView<PlayerProfile> tableView;
    private static String databaseName;

    /**
     * Gets leaderboard.
     *
     * @param databaseName the database name
     * @return the leaderboard
     */
    public static BorderPane getLeaderboard(String databaseName) {
        Leaderboard.databaseName = databaseName;
        BorderPane root = null;
        try {
            root = FXMLLoader.load(
                    Leaderboard.class.getResource("Leaderboard.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        tableView = (TableView<PlayerProfile>) root.getCenter();
        addColumns();
        return root;
    }

    /**
     * Creates a new player database and adds columns
     */
    @FXML
    private static void addColumns() {
        PlayerDatabase playerDatabase = new PlayerDatabase(getDatabaseName());
        playerDatabase.start();
        ObservableList<PlayerProfile> data =
                FXCollections.observableArrayList(
                        playerDatabase.getAllActiveProfiles());

        tableView.getColumns().get(0).setCellValueFactory(
                new PropertyValueFactory<>("playerName"));
        tableView.getColumns().get(1).setCellValueFactory(
                new PropertyValueFactory<>("victories"));
        tableView.getColumns().get(2).setCellValueFactory(
                new PropertyValueFactory<>("losses"));
        tableView.setItems(data);
    }


    /**
     * Gets database name.
     *
     * @return the database name
     */
    public static String getDatabaseName() {
        return databaseName;
    }


}