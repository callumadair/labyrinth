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
        Scene scene = new Scene(new Group());
        stage = primaryStage;
        stage.setTitle("Leaderboard");
        stage.setWidth(350);
        stage.setHeight(500);

        PlayerDatabase playerDatabase = new PlayerDatabase();
        playerDatabase.start(getDatabaseName());
        data = FXCollections.observableArrayList(playerDatabase.getAllActiveProfiles());

        final Label label = new Label("Leaderboard");
        label.setFont(new Font("Quicksand", 20));
        addColumns();

        ((Group) scene.getRoot()).getChildren().addAll(createVBox(label));

        stage.setScene(scene);
        stage.show();
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
        tableView.getColumns().addAll(nameCol, vicCol, lossColumn);
        for (TableColumn<PlayerProfile, ?> column : tableView.getColumns()) {
            column.setMinWidth(100);
            column.setStyle("-fx-alignment: CENTER;");
        }
    }

    /**
     * Create v box v box.
     *
     * @param label the label
     * @return the v box
     */
    private VBox createVBox(Label label) {
        final VBox vBox = new VBox();
        vBox.setSpacing(5);
        vBox.setPadding(new Insets(10, 0, 0, 10));
        vBox.getChildren().addAll(label, tableView);
        vBox.setAlignment(Pos.CENTER);

        return vBox;
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