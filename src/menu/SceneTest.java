package menu;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;


public class SceneTest extends Application
{
    public Button playButton;
    public void start(Stage secondStage) throws IOException {
        try {
            Pane root = (Pane) FXMLLoader.load(getClass().getResource("Test Scene.fxml"));
            Scene secondScene = new Scene(root, 200, 200);
            secondStage.setScene(secondScene);
            secondStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handlePlayButtonAction(javafx.event.ActionEvent actionEvent){
        Stage secondStage = (Stage) playButton.getScene().getWindow();
        secondStage.show();

    }
}
