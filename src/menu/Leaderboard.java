package menu;

import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import objects.*;

import java.io.*;

/**
 * The type Leaderboard app.
 *
 * @author Callum Adair
 */
public class Leaderboard {
    @FXML
    private static TableView<PlayerProfile> tableView;
    private static String databaseName;

    public static BorderPane getLeaderboard(String databaseName) {
        Leaderboard.databaseName = databaseName;
        BorderPane root = null;
        try {
            root = FXMLLoader.load(Leaderboard.class.getResource("Leaderboard.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        tableView = (TableView<PlayerProfile>) root.getCenter();
        addColumns();
        return root;
    }


    @FXML
    private static void addColumns() {
        PlayerDatabase playerDatabase = new PlayerDatabase();
        playerDatabase.start(getDatabaseName());
        ObservableList<PlayerProfile> data = FXCollections.observableArrayList(playerDatabase.getAllActiveProfiles());

        tableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("playerName"));
        tableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("victories"));
        tableView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("losses"));
        tableView.setItems(data);
    }


    public static String getDatabaseName() {
        return databaseName;
    }


}