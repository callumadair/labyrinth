package menu;

import javafx.animation.*;
import javafx.application.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import javafx.scene.media.MediaPlayer.*;
import javafx.stage.*;
import javafx.util.*;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * The type Menu controller.
 *
 * @author Luke Young
 * @author Callum Adair
 * @author Jeffrey
 */
public class MenuController extends Application implements Initializable {

    private Stage stage;
    private Scene primaryScene;
    private final ArrayList<LeaderboardController> leaderboardControllers = new ArrayList<>();
    @FXML
    private StackPane stackPane;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Pane mainView;
    @FXML
    private Label textLabelID;
    @FXML
    private ImageView imageView;
    @FXML
    private Button musicOnOffButton;
    private static MediaPlayer menuMusic;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        playMusic("src\\resources\\MenuMusic.wav");
        launch(args);

    }

    @FXML
    public void start(Stage primaryStage) {
        stage = primaryStage;
        try {
            Pane root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            primaryScene = new Scene(root, 1125, 650);
            stage.setScene(primaryScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void musicOnOffButtonClick() {
        if (menuMusic.getStatus().equals(Status.PLAYING)) {
            menuMusic.pause();
            musicOnOffButton.setText("Music Off");
        } else {
            menuMusic.play();
            musicOnOffButton.setText("Music On");
        }
    }

    /**
     * This will create a second window that you will be taken to when you click the play button
     *
     * @param actionEvent the action event
     */
    @FXML
    private void handlePlayButtonAction(ActionEvent actionEvent) {
        borderPane.getChildren().remove(mainView);
        try {
            mainView = FXMLLoader.load(getClass().getResource("GameTest.fxml"));
            fadeOut(mainView);
            borderPane.setCenter(mainView);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Handle quit button action.
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
     * This will take a window that you will be taken to when you click the instructions button
     *
     * @param actionEvent the action event
     */
    @FXML
    private void handleInstructionsButtonAction(ActionEvent actionEvent) {
        try {
            mainView = FXMLLoader.load(getClass().getResource("Instructions.fxml"));
            Scene instructionsScene = new Scene(mainView);
            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(instructionsScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Handle take menu button action.
     *
     * @param actionEvent the action event
     */
    @FXML
    private void handleMenuButton(ActionEvent actionEvent) {
        borderPane.getChildren().remove(mainView);
        try {
            StackPane menu = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            mainView = (Pane) ((BorderPane) menu.getChildren().get(1)).getChildren().get(0);
            fadeOut(mainView);
            borderPane.setCenter(mainView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * handles take me back button from the instructions screen
     *
     * @param actionEvent the action event
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
     * Open leaderboard.
     *
     * @param actionEvent the action event
     */
    @FXML
    private void openLeaderboard(ActionEvent actionEvent) {
        String val = actionEvent.getSource().toString();
        int boardNum = Integer.parseInt(String.valueOf(val.charAt(val.length() - 2)));
        int index = boardNum - 1;

        leaderboardControllers.ensureCapacity(boardNum);

        String boardStr = "board" + boardNum + ".db";
        LeaderboardController curLeaderboard = new LeaderboardController(boardStr);

        if (!leaderboardControllers.isEmpty() && leaderboardControllers.get(index) != null) {
            curLeaderboard = leaderboardControllers.get(index);
            curLeaderboard.exit();
        } else {
            leaderboardControllers.add(index, curLeaderboard);
        }
        curLeaderboard.start(new Stage());
        System.out.println(leaderboardControllers.size());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            textLabelID.setText(MessageOfTheDay.finalMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        TranslateTransition backgroundMove = new TranslateTransition();
        backgroundMove.setDuration(Duration.millis(5000));
        backgroundMove.setNode(imageView);
        backgroundMove.setFromX(0);
        backgroundMove.setToX(30);
        backgroundMove.setAutoReverse(true);
        backgroundMove.setCycleCount(Animation.INDEFINITE);
        backgroundMove.play();
    }

    /**
     * Make fade out.
     *
     * @param fadeOut the fade out
     */
    private void fadeOut(Pane fadeOut) {
        TranslateTransition windowTransition = new TranslateTransition();
        windowTransition.setDuration(Duration.millis(500));
        windowTransition.setNode(fadeOut);
        windowTransition.setFromX(700);
        windowTransition.setToX(0);
        windowTransition.play();
    }

    public static void playMusic(String filepath) {
        Media music = new Media(new File(filepath).toURI().toString());
        menuMusic = new MediaPlayer(music);
        menuMusic.setCycleCount(MediaPlayer.INDEFINITE);
        menuMusic.play();
    }
}
