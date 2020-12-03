package menu;

import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import objects.*;

import java.util.*;

public class Profiles {

    @FXML
    private final Button deleteButton = new Button();

    public static BorderPane getAllProfiles(ArrayList<PlayerDatabase> databases) {
        HashMap<Integer, PlayerProfile> allProfiles = new HashMap<>();
        for (PlayerDatabase database : databases) {
            database.start();
            for (PlayerProfile profile : database.getAllData()) {
                if (allProfiles.get(profile.getPlayerID()) == null) {
                    allProfiles.put(profile.getPlayerID(), profile);
                } else {
                    PlayerProfile curProfile = allProfiles.get(profile.getPlayerID());
                    profile.setVictories(profile.getVictories() + curProfile.getVictories());
                    profile.setLosses(profile.getLosses() + curProfile.getLosses());
                }
            }
        }
        ObservableMap<Integer, PlayerProfile> profiles = FXCollections.observableHashMap();
        profiles.putAll(allProfiles);

        addColumns();
        return null;
    }

    private static void addColumns() {

    }
}
