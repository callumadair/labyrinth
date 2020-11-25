package menu;

import javafx.application.Application;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
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
    private TableView<PlayerProfile> tableView;
    @FXML
    private TableColumn<PlayerProfile, String> nameCol;
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
        stage.setTitle("Leaderboard");
        Pane root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Leaderboard.fxml"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        tableView = (TableView<PlayerProfile>) root.getChildren().get(1);
        Scene scene = new Scene(root, 350, 500);
        stage.setScene(scene);

        addColumns();
        stage.show();
    }

    /**
     * Add columns.
     */
    @FXML
    private void addColumns() {
        PlayerDatabase playerDatabase = new PlayerDatabase();
        playerDatabase.start(getDatabaseName());
        data = FXCollections.observableArrayList(playerDatabase.getAllActiveProfiles());

        tableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("playerName"));
        tableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("victories"));
        tableView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("losses"));
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