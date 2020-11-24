package menu;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;


public class MenuController extends Application {

    @FXML
    private Button quitButton;
    private Stage stage;
    private LeaderboardController leaderboardController;

    public void start(Stage primaryStage) throws IOException {
        try {
            stage = primaryStage;
            Pane root = (Pane) FXMLLoader.load(getClass().getResource("Main Menu.fxml"));
            Scene scene = new Scene(root, 700, 450);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Will close the window
     *
     * @param actionEvent
     */
    @FXML
    private void handleQuitButtonAction(javafx.event.ActionEvent actionEvent) {
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        if (leaderboardController != null) {
            leaderboardController.exit();
        }
        stage.close();
    }

    /**
     * This will create a second window that you will be taken to when you click the play button
     *
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void handlePlayButtonAction(ActionEvent actionEvent) throws IOException {
        try {
            Parent root1 = FXMLLoader.load(getClass().getResource("Test Scene.fxml"));
            Scene secondScene = new Scene(root1);
            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(secondScene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void handleTakeMeBackButtonAction(javafx.event.ActionEvent actionEvent) {
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleLeaderboardAction(javafx.event.ActionEvent actionEvent) {
        leaderboardController = new LeaderboardController();
        try {
            leaderboardController.start(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
