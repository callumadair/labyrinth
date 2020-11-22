package menu;

import javafx.application.Application;
import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import objects.*;

import java.awt.*;

/**
 * The type Leaderboard app.
 */
public class LeaderboardApp extends Application {
    private Leaderboard leaderboard;
    private TableView tableView = new TableView();
    private ObservableList<PlayerProfile> data;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(new Group());
        primaryStage.setTitle("Leaderboard");
        primaryStage.setWidth(300);
        primaryStage.setHeight(500);

        PlayerDatabase playerDatabase = new PlayerDatabase();
        playerDatabase.start("profiles.db");
        leaderboard = new Leaderboard(playerDatabase);
        data = FXCollections.observableArrayList(leaderboard.getPlayerProfiles());
        System.out.println(data);

        final Label label = new Label("Leaderboard");
        label.setFont(new Font("Quicksand", 20));

        tableView.setEditable(true);
        TableColumn nameCol = new TableColumn("Name");
        nameCol.setMinWidth(100);
        nameCol.setCellFactory(new PropertyValueFactory<PlayerProfile, String>("playerName"));

        TableColumn vicCol = new TableColumn("Victories");
        vicCol.setMinWidth(100);
        vicCol.setCellFactory(new PropertyValueFactory<PlayerProfile, Integer>("victories"));

        TableColumn lossColumn = new TableColumn("Losses");
        lossColumn.setMinWidth(100);
        lossColumn.setCellFactory(new PropertyValueFactory<PlayerProfile, Integer>("losses"));

        tableView.setItems(data);
        tableView.getColumns().addAll(nameCol, vicCol, lossColumn);

        final VBox vBox = new VBox();
        vBox.setSpacing(5);
        vBox.setPadding(new Insets(10, 0, 0, 10));
        vBox.getChildren().addAll(label, tableView);

        ((Group) scene.getRoot()).getChildren().addAll(vBox);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}