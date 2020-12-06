package menu;

import javafx.animation.*;
import javafx.application.*;
import javafx.beans.value.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import javafx.scene.media.MediaPlayer.*;
import javafx.stage.*;
import javafx.util.Duration;
import objects.*;

import java.io.*;
import java.util.*;

/**
 * The type Menu controller.
 *
 * @author Luke Young
 * @author Callum Adair
 * @author Jeffrey
 */
public class MenuController extends Application {


    private Stage stage;
    private Stage leaderboardStage;
    private Game game;
    private String boardName;
    private Board board;
    private ArrayList<PlayerProfile> players;
    private FXMLLoader menuLoader;
    @FXML
    private static TableView<PlayerProfile> tableView = new TableView<>();
    @FXML
    private StackPane root;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Pane mainView;
    @FXML
    private ImageView imageView;
    @FXML
    private Button musicOnOffButton;
    @FXML
    private BorderPane profileView;
    @FXML
    public ListView playerProfilesList;
    @FXML
    public Label boardSelectionLabel;

    private static MediaPlayer menuMusic;
    private ArrayList<PlayerDatabase> databases = new ArrayList<>();

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public enum MenuWindow {
        MAIN(0), PLAY(1), PROFILES(2);

        public final int index;

        private MenuWindow(int index){
            this.index = index;
        }
    }

    /**
     * Creates the Stage for the scenes and loads the MainMenu
     *
     * @param primaryStage
     */
    @FXML
    @Override
    public void start(Stage primaryStage) {
        playMusic("src\\resources\\MenuMusic.wav");

        stage = primaryStage;
        root = null;
        menuLoader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        try {
            root = menuLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        borderPane = (BorderPane) root.getChildren().get(1);
        mainView = (StackPane) borderPane.getCenter();
        profileView = (BorderPane) mainView.getChildren().get(2);
        tableView = (TableView<PlayerProfile>) profileView.getCenter();
        disableVisibility(MenuWindow.MAIN);
        Label message = (Label) ((HBox) borderPane.getBottom()).getChildren().get(0);
        try {
            message.setText(MessageOfTheDay.finalMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        setBackgroundEffects();
        addDatabases();

        Scene primaryScene = new Scene(root, 1125, 700);
        stage.setScene(primaryScene);
        stage.show();
    }

    /**
     * Will play the music on the main menu screen
     *
     * @param filepath the filepath
     */
    public static void playMusic(String filepath) {
        Media music = new Media(new File(filepath).toURI().toString());
        menuMusic = new MediaPlayer(music);
        menuMusic.setCycleCount(MediaPlayer.INDEFINITE);
        menuMusic.play();
    }

    /**
     * Button for turning the music on or off
     */
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
     * Sets background effects.
     */
    private void setBackgroundEffects() {
        imageView = (ImageView) root.getChildren().get(0);
        imageView.fitWidthProperty().bind(root.widthProperty().multiply(1.1));
        imageView.fitHeightProperty().bind(root.heightProperty().multiply(1.1));
        imageView.setPreserveRatio(false);

        TranslateTransition backgroundMove = new TranslateTransition();

        backgroundMove.setDuration(Duration.millis(5000));
        backgroundMove.setNode(imageView);
        backgroundMove.setFromX(0);
        backgroundMove.setToX(30);
        backgroundMove.setAutoReverse(true);
        backgroundMove.setCycleCount(Animation.INDEFINITE);
        backgroundMove.play();
    }

    private void addDatabases() {
        databases.add(new PlayerDatabase("board1"));
        databases.add(new PlayerDatabase("board2"));
        databases.add(new PlayerDatabase("board3"));
    }

    /**
     * This will create a second window that you will be taken to when you click the play button
     *
     * @param actionEvent the action event
     */
    @FXML
    private void handlePlayButtonAction(ActionEvent actionEvent) {
        disableVisibility(MenuWindow.PLAY);
        players = new ArrayList<>();

        ObservableList<PlayerProfile> profilesList =
                FXCollections.observableArrayList(getProfiles());

        playerProfilesList.setItems(profilesList);

        MultipleSelectionModel<PlayerProfile> selectionModel = playerProfilesList.getSelectionModel();

        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);

        selectionModel.selectedItemProperty().addListener(new ChangeListener<PlayerProfile>() {
            @Override
            public void changed(ObservableValue<? extends PlayerProfile> observable, PlayerProfile oldValue, PlayerProfile newValue) {
                for(PlayerProfile playerProfile : selectionModel.getSelectedItems()){
                    if(!players.contains(playerProfile)){
                        players.add(playerProfile);
                    }
                }
                players.retainAll(selectionModel.getSelectedItems());
            }
        });
    }


    /**
     * Handle quit button action.
     *
     * @param actionEvent the action event
     */
    @FXML
    private void handleQuitButtonAction(ActionEvent actionEvent) {
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
        System.exit(100);
    }


    /**
     * Handle menu button action.
     *
     * @param actionEvent the action event
     */
    @FXML
    private void handleMenuButton(ActionEvent actionEvent) {
        if(game != null){
            mainView.getChildren().remove(game.getPane());
        }
        if(!players.isEmpty()){
            players.clear();
        }
        disableVisibility(MenuWindow.MAIN);
    }

    /**
     * Creates a new game for a board
     *
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void handleNewGame(ActionEvent actionEvent) throws IOException {
        boardName = ((Button) actionEvent.getSource()).getText();
        boardSelectionLabel.setText(boardName);

    }

    @FXML
    private void startGame() throws IOException {
        if(!players.isEmpty() && players.size() <= 4 && players.size() >= 2 && boardName != null){
            board = FileManager.loadBoard(boardName, players);
            game = new Game(board);
            gameFinishedListener();
            disableVisibility();
            mainView.getChildren().add(game.getPane());
        }
    }

    @FXML
    public void onStartGame(ActionEvent actionEvent) throws IOException {
        startGame();
    }

    /**
     * Handle button to load game
     *
     * @param actionEvent
     * @throws FileNotFoundException
     */
    @FXML
    private void handleLoadGame(ActionEvent actionEvent) throws FileNotFoundException {
        String fileName = ((TextField) ((HBox) ((Button)
                actionEvent.getSource()).getParent()).getChildren().get(1)).getText();

        board = FileManager.loadGame(fileName);
        //startGame();
    }

    @FXML
    private void disableVisibility(){
        for(MenuWindow menuWindow : MenuWindow.values()){
            mainView.getChildren().get(menuWindow.index).setVisible(false);
        }
    }

    @FXML
    private void disableVisibility(MenuWindow window){
        for(MenuWindow menuWindow : MenuWindow.values()){
            if(menuWindow == window){
                mainView.getChildren().get(menuWindow.index).setVisible(true);
            } else {
                mainView.getChildren().get(menuWindow.index).setVisible(false);
            }
        }
    }

    /**
     * Button to save the game
     *
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void handleSaveGame(ActionEvent actionEvent) throws IOException {
        if(game != null){
            System.out.println(board);
            System.out.println(board.getHeight());
            System.out.println(board.getWidth());

            FileManager.saveGame(board, boardName + "save");
        } else {
            System.out.println("No game");
        }

    }

    /**
     * Opens up leaderboard
     *
     * @param actionEvent
     */
    @FXML
    private void openLeaderboard(ActionEvent actionEvent) {
        if (leaderboardStage == null) {
            leaderboardStage = new Stage();
        } else {
            leaderboardStage.close();
        }

        boardName = ((Button) actionEvent.getSource()).getText();
        BorderPane leaderboardPane = Leaderboard.getLeaderboard(boardName);
        Scene leaderboard = new Scene(leaderboardPane, 350, 450);
        leaderboardStage.setScene(leaderboard);
        leaderboardStage.show();
    }

    /**
     * Returns all player profiles
     *
     * @param actionEvent
     */
    @FXML
    private void getAllProfiles(ActionEvent actionEvent) {
        getProfiles();
        disableVisibility(MenuWindow.PROFILES);
    }


    private void gameFinishedListener() {
        game.getIsGameFinished().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (game.getIsGameFinished().getValue()) {
                    System.out.println("Game finished");
                    PlayerDatabase curDatabase = new PlayerDatabase(boardName);
                    curDatabase.start();
                    for (PlayerController playerController : board.getPlayers()) {
                        curDatabase.updatePlayer(playerController.getProfile());
                    }
                }
            }
        });
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

    /**
     * Gets all profiles.
     *
     * @return the all profiles
     */
    public ObservableList<PlayerProfile> getProfiles() {
        addDatabases();

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
        addColumns(profiles);

        return profiles;
    }

    /**
     * Fill out the columns with data from the databases
     *
     * @param profiles
     */
    private void addColumns(ObservableList<PlayerProfile> profiles) {
        tableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("playerName"));
        tableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("playerID"));
        tableView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("gamesPlayed"));

        tableView.setItems(profiles);
    }

}
