package menu;

import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import objects.*;

import java.awt.event.*;
import java.io.*;
import java.util.*;

public class Profiles {

    @FXML
    private final Button deleteButton = new Button("Delete");
    @FXML
    private static TableView<PlayerProfile> tableView = new TableView<>();

    public static BorderPane getAllProfiles(ArrayList<PlayerDatabase> databases) {
        BorderPane root = null;
        try {
            root = FXMLLoader.load(Profiles.class.getResource("Profiles.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        tableView = (TableView<PlayerProfile>) root.getCenter();

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
        ObservableList<PlayerProfile> profiles = FXCollections.observableArrayList(
                new ArrayList<>(allProfiles.values()));
        //addColumns(profiles);

        return root;
    }

    /*
    private static void addColumns(ObservableList<PlayerProfile> profiles) {
        tableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("playerName"));
        tableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("playerID"));
        tableView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("gamesPlayed"));
        tableView.getColumns().get(3).setCellValueFactory(param -> new TableCell<PlayerProfile, PlayerProfile>() {
            private final Button deleteButton = new Button("Delete");
            @Override
            protected void updateItem(PlayerProfile profile, boolean empty) {
                super.updateItem(profile, empty);

                if (profile == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(deleteButton);
                deleteButton.setOnAction(event -> profiles.remove(profile));
            }
        });
    }*/

    @FXML
    private static void addPlayer(ActionEvent actionEvent) {
        String name = ((TextField) ((HBox) ((Button) actionEvent.getSource()).getParent())
                .getChildren().get(1)).getText();
        int id = Integer.parseInt(((TextField) ((HBox) ((Button) actionEvent.getSource()).getParent())
                .getChildren().get(2)).getText());

        PlayerProfile newPlayer = new PlayerProfile(name, 0, 0, id);
        System.out.println(newPlayer);

        System.out.println(actionEvent.getSource());
    }
}
