import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;


public class MainController extends Application {

    @FXML
    public Button quitButton;

    public void start(Stage primaryStage) throws IOException {
        try {
            Pane root = (Pane) FXMLLoader.load(getClass().getResource("Main Menu.fxml"));
            Scene scene = new Scene(root, 400, 200);
            primaryStage.setScene(scene);
            primaryStage.show(); }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void quitButton(ActionEvent event){
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
    }

    public static void main(String[] args){
        launch(args);
    }
}
