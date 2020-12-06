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
 * The class Menu controller allows the running of the game, night crawlers,
 * and the associated admin functionality, including adding and deleting
 * profiles, saving and loading games, exiting the program.
 *
 * @author Luke Young
 * @author Callum Adair
 * @author Jeffrey
 * @author Kacper Lisikiewicz
 * @version 78.6
 */
public class MenuController extends Application {

    private final ArrayList<PlayerDatabase> databases = new ArrayList<>();
    private final String MUSIC_URL = "src\\resources\\MenuMusic.wav";

    @FXML
    private static TableView<PlayerProfile> tableView = new TableView<>();
    private static MediaPlayer menuMusic;

    private Stage stage;
    private Stage leaderboardStage;
    private Game game;
    private String boardName;
    private Board board;
    private ArrayList<PlayerProfile> players;
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
    public ListView<PlayerProfile> playerProfilesList;
    @FXML
    public Label boardSelectionLabel;


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The enum Menu window.
     */
    public enum MenuWindow {
        MAIN(0), PLAY(1), PROFILES(2);

        public final int index;

        /**
         * Instantiates a new Menu window.
         *
         * @param index the index
         */
        MenuWindow(int index) {
            this.index = index;
        }
    }

    /**
     * Creates the Stage for the scenes and loads the MainMenu
     *
     * @param primaryStage the primary stage
     */
    @FXML
    @Override
    public void start(Stage primaryStage) {
        playMusic(MUSIC_URL);

        stage = primaryStage;
        root = null;
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource(
                "MainMenu.fxml"));
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
        Label message =
                (Label) ((HBox) borderPane.getBottom()).getChildren().get(0);
        try {
            message.setText(MessageOfTheDay.finalMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        setBackgroundEffects();
        addDatabases();

        final int WINDOW_WIDTH = 1125;
        final int WINDOW_HEIGHT = 700;

        Scene primaryScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setScene(primaryScene);
        stage.setTitle("Night Crawlers");
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
        final double MULTIPLICATION_FACTOR = 1.1;


        imageView = (ImageView) root.getChildren().get(0);
        imageView.fitWidthProperty().bind(root.widthProperty().multiply(
                MULTIPLICATION_FACTOR));
        imageView.fitHeightProperty().bind(root.heightProperty().multiply(
                MULTIPLICATION_FACTOR));
        imageView.setPreserveRatio(false);

        TranslateTransition backgroundMove = new TranslateTransition();
        final int DURATION = 5000;
        final int FIRST_X = 0;
        final int LAST_X = 30;

        backgroundMove.setDuration(Duration.millis(DURATION));
        backgroundMove.setNode(imageView);
        backgroundMove.setFromX(FIRST_X);
        backgroundMove.setToX(LAST_X);
        backgroundMove.setAutoReverse(true);
        backgroundMove.setCycleCount(Animation.INDEFINITE);
        backgroundMove.play();
    }

    /**
     * Add databases.
     */
    private void addDatabases() {
        databases.add(new PlayerDatabase("board1"));
        databases.add(new PlayerDatabase("board2"));
        databases.add(new PlayerDatabase("board3"));
    }

    /**
     * This will create a second window that you will be taken to when you
     * click the play button
     */
    @FXML
    private void handlePlayButtonAction() {
        disableVisibility(MenuWindow.PLAY);
        players = new ArrayList<>();

        boardSelectionLabel.setText("Board");

        ObservableList<PlayerProfile> profilesList =
                FXCollections.observableArrayList(getProfiles());

        playerProfilesList.setItems(profilesList);

        MultipleSelectionModel<PlayerProfile> selectionModel =
                playerProfilesList.getSelectionModel();

        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);

        selectionModel.selectedItemProperty().addListener(
                new ChangeListener<PlayerProfile>() {
                    @Override
                    public void changed(
                            ObservableValue<? extends PlayerProfile> observable,
                            PlayerProfile oldValue,
                            PlayerProfile newValue) {

                        for (PlayerProfile playerProfile :
                                selectionModel.getSelectedItems()) {
                            if (!players.contains(playerProfile)) {
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
        final int GOOD_EXIT = 200;
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
        System.exit(GOOD_EXIT);
    }


    /**
     * Handle menu button action.
     */
    @FXML
    private void handleMenuButton() {
        if (game != null) {
            mainView.getChildren().remove(game.getPane());
            game = null;
            boardName = null;
        }
        if (players != null && !players.isEmpty()) {
            players.clear();
        }
        disableVisibility(MenuWindow.MAIN);
    }

    /**
     * Creates a new game for a board
     *
     * @param actionEvent the action event
     */
    @FXML
    private void handleNewGame(ActionEvent actionEvent) {
        boardName = ((Button) actionEvent.getSource()).getText();
        boardSelectionLabel.setText(boardName);
    }

    /**
     * On start game.
     *
     * @throws IOException the io exception
     */
    @FXML
    public void onStartGame() throws IOException {
        final int MIN_PLAYERS = 2;
        final int MAX_PLAYERS = 4;

        if (!players.isEmpty() && players.size() <= MAX_PLAYERS
                && players.size() >= MIN_PLAYERS && boardName != null) {
            board = FileManager.loadBoard(boardName, players);
            game = new Game(board);
            gameFinishedListener();
            disableVisibility();
            mainView.getChildren().add(game.getPane());
        }
    }

    /**
     * Handle button to load game
     *
     * @param actionEvent the action event
     * @throws FileNotFoundException the file not found exception
     */
    @FXML
    private void handleLoadGame(ActionEvent actionEvent)
            throws FileNotFoundException {
        String fileName = ((TextField) ((HBox) ((Button)
                actionEvent.getSource())
                .getParent())
                .getChildren()
                .get(1))
                .getText();

        board = FileManager.loadGame(fileName);
        game = new Game(board);
        gameFinishedListener();
        disableVisibility();
        mainView.getChildren().add(game.getPane());
    }

    /**
     * Disable visibility.
     */
    @FXML
    private void disableVisibility() {
        for (MenuWindow menuWindow : MenuWindow.values()) {
            mainView.getChildren().get(menuWindow.index).setVisible(false);
        }
    }

    /**
     * Disable visibility.
     *
     * @param window the window
     */
    @FXML
    private void disableVisibility(MenuWindow window) {
        for (MenuWindow menuWindow : MenuWindow.values()) {
            if (menuWindow == window) {
                mainView.getChildren().get(menuWindow.index).setVisible(true);
            } else {
                mainView.getChildren().get(menuWindow.index).setVisible(false);
            }
        }
    }

    /**
     * Button to save the game
     *
     * @throws IOException the io exception
     */
    @FXML
    private void handleSaveGame() throws IOException {
        if (game != null) {
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
     * @param actionEvent the action event
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
        final int LEADERBOARD_WIDTH = 350;
        final int LEADERBOARD_HEIGHT = 450;

        Scene leaderboard = new Scene(leaderboardPane, LEADERBOARD_WIDTH,
                LEADERBOARD_HEIGHT);
        leaderboardStage.setScene(leaderboard);
        leaderboardStage.show();
    }

    /**
     * Returns all player profiles
     */
    @FXML
    private void getAllProfiles() {
        getProfiles();
        disableVisibility(MenuWindow.PROFILES);
    }


    /**
     * Game finished listener.
     */
    private void gameFinishedListener() {
        game.getIsGameFinished().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable
                    , Boolean oldValue, Boolean newValue) {
                if (game.getIsGameFinished().getValue()) {
                    System.out.println("Game finished");
                    PlayerDatabase curDatabase = new PlayerDatabase(boardName);
                    curDatabase.start();
                    for (PlayerController playerController :
                            board.getPlayers()) {
                        if (playerController.equals(
                                game.getController().getCurrentPlayer())) {
                            curDatabase.incrementVictories(
                                    playerController.getProfile());
                        } else {
                            curDatabase.incrementLosses(
                                    playerController.getProfile());
                        }
                    }
                }
            }
        });
    }

    /**
     * Adds a new player profile.
     *
     * @param actionEvent the action event
     */
    @FXML
    private void addPlayer(ActionEvent actionEvent) {
        String name = null;
        int id = 0;
        try {
            name = ((TextField)
                    ((Button) actionEvent.getSource())
                            .getParent()
                            .getChildrenUnmodifiable()
                            .get(1))
                    .getText();
            id = Integer.parseInt(((TextField)
                    ((Button) actionEvent.getSource())
                            .getParent()
                            .getChildrenUnmodifiable()
                            .get(2))
                    .getText());
        } catch (Exception e) {
            System.out.println("Invalid Input");
        }

        PlayerProfile newProfile = new PlayerProfile(name, 0, 0, id);

        for (PlayerDatabase database : databases) {
            database.storePlayer(newProfile);
        }
    }

    /**
     * Sets delete button.
     */
    @FXML
    private void setDeleteButton() {
        try {
            PlayerProfile profile = tableView.getSelectionModel().getSelectedItem();
            tableView.getItems().remove(profile);
            for (PlayerDatabase database : databases) {
                database.deletePlayer(profile);
            }
        } catch (Exception e) {
            System.out.println("No profile Selected");
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
                    PlayerProfile curProfile =
                            allProfiles.get(profile.getPlayerID());
                    profile.setVictories(profile.getVictories()
                            + curProfile.getVictories());
                    profile.setLosses(profile.getLosses()
                            + curProfile.getLosses());
                }
            }
        }
        ObservableList<PlayerProfile> profiles =
                FXCollections.observableArrayList(
                        new ArrayList<>(allProfiles.values()));
        addColumns(profiles);

        return profiles;
    }

    /**
     * Fill out the columns with data from the databases
     *
     * @param profiles the profiles
     */
    private void addColumns(ObservableList<PlayerProfile> profiles) {
        tableView.getColumns().get(0).setCellValueFactory(
                new PropertyValueFactory<>("playerName"));
        tableView.getColumns().get(1).setCellValueFactory(
                new PropertyValueFactory<>("playerID"));
        tableView.getColumns().get(2).setCellValueFactory(
                new PropertyValueFactory<>("gamesPlayed"));

        tableView.setItems(profiles);
    }

}
