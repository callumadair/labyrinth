package menu;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.stage.Stage;
import javafx.util.Duration;
import jdk.internal.util.xml.impl.Input;
import menu.DailyMessage.GetFinalMessage;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import javafx.animation.*;
import javafx.application.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.util.*;
import sun.audio.*;

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
public class MenuController extends Application implements Initializable{

    private Stage stage;
    private Scene primaryScene;
    private final ArrayList<LeaderboardController> leaderboardControllers = new ArrayList<>();
    @FXML
    private StackPane stackPane;
    @FXML
    private BorderPane borderPane;
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
        //playMusiclevanPolkaa("src\\resources\\music.wav");
        playMusicNyanCat("src\\resources\\MenuMusic.wav");
        launch(args);

    }

    @FXML
    public void start(Stage primaryStage) {
        stage = primaryStage;
        try {
            Pane root = FXMLLoader.load(getClass().getResource("MainMenu2.fxml"));
            primaryScene = new Scene(root, 700, 450);
            stage.setScene(primaryScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void musicOnOffButtonClick(ActionEvent actionEvent) {
    	if(menuMusic.getStatus().equals(Status.PLAYING)) {
    		menuMusic.pause();
    		musicOnOffButton.setText("Music Off");
    	}else {
    		menuMusic.play();
    		musicOnOffButton.setText("Music On");
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
     */
    @FXML
    private void handlePlayButtonAction(ActionEvent actionEvent) {

        //AudioPlayer.player.stop(InputStream, levanPolkaaMusic);
        try {
          //  playMusicHEYYEYAAEYAAAEYAEYAA("src\\resources\\HEYYEYAAEYAAAEYAEYAA.wav");
          //  playMusicMegalovania("src\\resources\\megalovania.wav");
            BorderPane root = FXMLLoader.load(getClass().getResource("LeaderBoard.fxml"));
            stackPane.getChildren().add(root);
            stackPane.getChildren().remove(borderPane);
            makeFadeOut(root);
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
            Pane root = FXMLLoader.load(getClass().getResource("MainMenu2.fxml"));
            primaryScene = new Scene(root, 700, 450);
            stage.setScene(primaryScene);
            stage.show();
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
     * Handle leaderboard action.
     *
     * @param actionEvent the action event
     */

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
    private void makeFadeOut(Pane fadeOut) {
    	TranslateTransition windowTransition =new TranslateTransition();
    	windowTransition.setDuration(Duration.millis(500));
		windowTransition.setNode(fadeOut);
		windowTransition.setFromX(700);
		windowTransition.setToX(0);
		windowTransition.play();
    }

    public static void playMusiclevanPolkaa (String filepath){
        InputStream music;
        try
        {
            music = new FileInputStream(new File(filepath));
            AudioStream audio = new AudioStream(music);
            AudioPlayer.player.start(audio);
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }
    public static void playMusicMegalovania (String filepath){
        InputStream megalovaniaMusic;
        try
        {
            megalovaniaMusic = new FileInputStream(new File(filepath));
            AudioStream megalovaniaAudio = new AudioStream(megalovaniaMusic);
            AudioPlayer.player.start(megalovaniaAudio);
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }
    public static void playMusicHEYYEYAAEYAAAEYAEYAA(String filepath){
        InputStream HEYYEYAAEYAAAEYAEYAAMusic;
        try
        {
            HEYYEYAAEYAAAEYAEYAAMusic = new FileInputStream(new File(filepath));
            AudioStream HEYYEYAAEYAAAEYAEYAAAudio = new AudioStream(HEYYEYAAEYAAAEYAEYAAMusic);
            AudioPlayer.player.start(HEYYEYAAEYAAAEYAEYAAAudio);
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }
    
    public static void playMusicNyanCat(String filepath){
    		Media music = new Media(new File(filepath).toURI().toString());
    		menuMusic = new MediaPlayer(music);
    		menuMusic.play();
        /*InputStream nyanCatMusic;
        try
        {
            nyanCatMusic = new FileInputStream(new File(filepath));
            AudioPlayer nyanCatAudio = new AudioPlayer(nyanCatMusic);
            AudioPlayer.player.start(nyanCatAudio);
        }
        catch(IOException e){
            e.printStackTrace();
        }
		*/
    }
}
