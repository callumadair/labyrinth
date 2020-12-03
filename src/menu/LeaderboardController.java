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
public class LeaderboardController {
    @FXML
    private TableView<PlayerProfile> tableView;
    private BorderPane pane;
    private String databaseName;

    public LeaderboardController(String databaseName) {
        this.databaseName = databaseName;
    }


    public void start(String databaseName) {
        this.databaseName = databaseName;
        BorderPane root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Leaderboard.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        tableView = (TableView<PlayerProfile>) root.getCenter();
        addColumns();
    }


    @FXML
    private void addColumns() {
        PlayerDatabase playerDatabase = new PlayerDatabase();
        playerDatabase.start(getDatabaseName());
        ObservableList<PlayerProfile> data = FXCollections.observableArrayList(playerDatabase.getAllActiveProfiles());

        tableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("playerName"));
        tableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("victories"));
        tableView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("losses"));
        tableView.setItems(data);
    }


    public String getDatabaseName() {
        return databaseName;
    }
}