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
import java.util.*;


/**
 * The type Menu controller.
 *
 * @author Luke
 * @author Cal
 */
public class MenuController extends Application {

    private Stage stage;
    private Scene primaryScene;
    private Scene secondaryScene;
    private LeaderboardController leaderboardController;
    private final ArrayList<LeaderboardController> leaderboardControllers = new ArrayList<>();

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
            primaryScene = new Scene(root, 700, 450);
            stage.setScene(primaryScene);
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
        if (!leaderboardControllers.isEmpty()) {
            for (LeaderboardController leaderboardController : leaderboardControllers) {
                leaderboardController.exit();
            }
        }
        stage.close();
    }

    /**
     * This will create a second window that you will be taken to when you click the play button
     *
     * @param actionEvent the action event
     */
    @FXML
    private void handlePlayButtonAction(ActionEvent actionEvent) {
        try {
            Pane root = FXMLLoader.load(getClass().getResource("Test Scene.fxml"));
            secondaryScene = new Scene(root);
            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(secondaryScene);
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
            primaryScene = new Scene(root, 700, 450);
            stage.setScene(primaryScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
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
    }*/

    @FXML
    private void openLeaderboard(ActionEvent actionEvent) {
        String val = actionEvent.getSource().toString();
        int boardNum = Integer.parseInt(String.valueOf(val.charAt(val.length() - 2)));
        int index = boardNum - 1;

        leaderboardControllers.ensureCapacity(boardNum);

        String boardStr = "board" + boardNum + ".db";
        LeaderboardController curLeaderboard = new LeaderboardController(boardStr);

        if (leaderboardControllers.contains(curLeaderboard)) {
            curLeaderboard = leaderboardControllers.get(index);
            curLeaderboard.exit();
        } else {
            leaderboardControllers.add(index, curLeaderboard);
        }
        curLeaderboard.start(new Stage());
        System.out.println(leaderboardControllers.size());
    }
}