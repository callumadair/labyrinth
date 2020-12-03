package menu;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LeaderboardTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(Leaderboard.getLeaderboard("profiles"));
        PlayerDatabase database = new PlayerDatabase();
        database.start("profiles");
        System.out.println(database.getAllActiveProfiles());

        Leaderboard.getLeaderboard("profiles");
        primaryStage.setScene(new Scene(borderPane, 500, 500));
        primaryStage.show();
    }
}
