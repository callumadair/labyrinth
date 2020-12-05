package menu;

import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import objects.*;

import java.io.*;
import java.util.*;

/**
 * The type Profiles.
 */
public class Profiles {

    @FXML
    private static final Button deleteButton = new Button("Delete");
    @FXML
    private static TableView<PlayerProfile> tableView = new TableView<>();
    private static ArrayList<PlayerDatabase> databases;

    /**
     * Gets all profiles.
     *
     * @param newDatabases the new databases
     * @return the all profiles
     */
    public static BorderPane getAllProfiles(ArrayList<PlayerDatabase> newDatabases) {
        Profiles.databases = newDatabases;
        BorderPane root = null;
        try {
            FXMLLoader profileLoader = new FXMLLoader(Profiles.class.getResource("Profiles.fxml"));
            root = profileLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        tableView = (TableView<PlayerProfile>) root.getCenter();

        HashMap<Integer, PlayerProfile> allProfiles = new HashMap<>();
        for (PlayerDatabase database : newDatabases) {
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
        ObservableList<PlayerProfile> profiles = FXCollections.observableArrayList(
                new ArrayList<>(allProfiles.values()));
        addColumns(profiles);

        return root;
    }

    /**
     * Fill out the columns with data from the databases
     *
     * @param profiles
     */
    private static void addColumns(ObservableList<PlayerProfile> profiles) {
        tableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("playerName"));
        tableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("playerID"));
        tableView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("gamesPlayed"));

        tableView.setItems(profiles);

    }

    @FXML
    private void addPlayer(ActionEvent actionEvent) {
        String name = ((TextField)
                ((Button) actionEvent.getSource()).getParent().getChildrenUnmodifiable().get(1)).getText();
        int id = Integer.parseInt(((TextField)
                ((Button) actionEvent.getSource()).getParent().getChildrenUnmodifiable().get(2)).getText());

        PlayerProfile newProfile = new PlayerProfile(name, 0, 0, id);
        for (PlayerDatabase database : databases) {
            database.storePlayer(newProfile);
        }
    }

    @FXML
    private void setDeleteButton(ActionEvent actionEvent) {
        PlayerProfile profile = tableView.getSelectionModel().getSelectedItem();
        tableView.getItems().remove(profile);
        for (PlayerDatabase database : databases) {
            database.deletePlayer(profile);
        }
    }
}

