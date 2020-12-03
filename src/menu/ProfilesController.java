package menu;

import javafx.application.Application;
import javafx.stage.Stage;
import objects.*;

import java.util.*;

public class ProfilesController extends Application {
    private ArrayList<PlayerDatabase> databases = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
    public ArrayList<PlayerProfile> getProfiles() {
        ArrayList<PlayerProfile> profiles = new ArrayList<>();
        for (PlayerDatabase database : databases) {
            profiles.addAll(database.getAllData());
        }

        return profiles;
    }
}
