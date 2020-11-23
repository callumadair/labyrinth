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


public class MainController extends Application {

    /**
     * Declaring the variables
     */


    @FXML
    public Button quitButton;
    public Button playButton;
    public Button instructionsButton;
    public Button highScoreButton;
    public Button takeMeBackButton;


    /**
     * Creates the stage (Main menu screen)
     * @param primaryStage
     * @throws IOException
     */


    public void start(Stage primaryStage) throws IOException {
        try {
            Pane root = (Pane) FXMLLoader.load(getClass().getResource("Main Menu.fxml"));
            Scene scene = new Scene(root, 400, 200);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Will close the window
     * @param actionEvent
     */
    @FXML
    private void handleQuitButtonAction(javafx.event.ActionEvent actionEvent){
        Stage primaryStage = (Stage) quitButton.getScene().getWindow();
        primaryStage.close();
    }

    /**
     * This will create a second window that you will be taken to when you click the play button
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void handlePlayButtonAction(ActionEvent actionEvent) throws IOException {
        try {
            Parent root1 = FXMLLoader.load(getClass().getResource("Test Scene.fxml"));
            Scene secondScene = new Scene(root1);
            Stage secondaryStage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            secondaryStage.setScene(secondScene);
            secondaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * Will be used to go back to main menu
     * @param actionEvent
     */

    @FXML
    private void handleTakeMeBackButtonAction(javafx.event.ActionEvent actionEvent){
        Stage secondaryStage = (Stage) takeMeBackButton.getScene().getWindow();
        secondaryStage.close();

       // primaryStage.show();

    }

    public static void main(String[] args){
        launch(args);
    }

}
