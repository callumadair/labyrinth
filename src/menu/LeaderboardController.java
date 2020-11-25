package menu;

import javafx.application.Application;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import objects.*;

import java.io.*;

/**
 * The type Leaderboard app.
 *
 * @author Cal
 */
public class LeaderboardController extends Application {
    @FXML
    private final TableView<PlayerProfile> tableView = new TableView<>();
    private ObservableList<PlayerProfile> data;
    private Stage stage;
    private String databaseName;

    public LeaderboardController(String databaseName) {
        this.databaseName = databaseName;
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        Pane root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Leaderboard.fxml"));
            Scene scene = new Scene(root, 350, 500);
            PlayerDatabase playerDatabase = new PlayerDatabase();
            playerDatabase.start(getDatabaseName());
            data = FXCollections.observableArrayList(playerDatabase.getAllActiveProfiles());
            stage.setScene(scene);
            addColumns();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add columns.
     */
    @FXML
    private void addColumns() {
        TableColumn<PlayerProfile, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("playerName"));

        TableColumn<PlayerProfile, Integer> vicCol = new TableColumn<>("Victories");
        vicCol.setCellValueFactory(new PropertyValueFactory<>("victories"));

        TableColumn<PlayerProfile, Integer> lossColumn = new TableColumn<>("Losses");
        lossColumn.setCellValueFactory(new PropertyValueFactory<>("losses"));

        tableView.setItems(data);
    }

    /**
     * Exit.
     */
    public void exit() {
        if (stage != null) {
            stage.close();
        }
    }

    public String getDatabaseName() {
        return databaseName;
    }
}