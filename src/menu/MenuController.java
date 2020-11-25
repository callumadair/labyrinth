package menu;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * The type Menu controller.
 *
 * @author Luke
 * @author Cal
 */
public class MenuController extends Application {

    private Stage stage;
    private LeaderboardController leaderboardController;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        Pane root = null;
        stage = primaryStage;
        try {
            root = FXMLLoader.load(getClass().getResource("Main Menu.fxml"));
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
     * @param actionEvent the action event
     */
    @FXML
    private void handleQuitButtonAction(ActionEvent actionEvent) {
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        if (leaderboardController != null) {
            leaderboardController.exit();
        }
        stage.close();
    }

    /**
     * This will take a window that you will be taken to when you click the instructions button
     * @param actionEvent
     */
    @FXML
    private void handleInstructionsButtonAction(ActionEvent actionEvent) {
        try {
            Pane root = FXMLLoader.load(getClass().getResource("Instructions.fxml"));
            Scene instructionsScene = new Scene(root);
            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(instructionsScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * This will create a second window that you will be taken to when you click the play button
     *
     * @param actionEvent the action event
     * @throws IOException
     */
    @FXML
    private void handlePlayButtonAction(ActionEvent actionEvent) {
        try {
            Pane root = FXMLLoader.load(getClass().getResource("Test Scene.fxml"));
            Scene secondScene = new Scene(root);
            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(secondScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Handle take me back button action.
     *
     * @param actionEvent the action event
     */
    @FXML
    private void handleTakeMeBackButtonAction(ActionEvent actionEvent) {
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        try {
            Pane root = FXMLLoader.load(getClass().getResource("Main Menu.fxml"));
            Scene scene = new Scene(root, 700, 450);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * handles take me back button from the instructions screen
     * @param actionEvent
     */
    @FXML
    private void handleTakeMeBackButtonActionInstructions(ActionEvent actionEvent) {
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        try {
            Pane root = FXMLLoader.load(getClass().getResource("Main Menu.fxml"));
            Scene scene = new Scene(root, 700, 450);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Handle leaderboard action.
     *
     * @param actionEvent the action event
     */
    @FXML
    private void leaderboardTransition(ActionEvent actionEvent) {
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        try {
            Pane root = FXMLLoader.load(getClass().getResource("LeaderboardScreen.fxml"));
            Scene scene = new Scene(root, 700, 450);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openLeaderboard(ActionEvent actionEvent) {
        if (leaderboardController == null) {
            leaderboardController = new LeaderboardController("profiles.db");
            leaderboardController.start(new Stage());
        }

    }

}
