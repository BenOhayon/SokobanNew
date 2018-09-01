package view;

import controller.observer.Notifier;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.database.DatabaseConnector;
import model.database.DbPlayer;
import model.entities.Level;
import model.utils.MessageType;

import java.beans.EventHandler;
import java.io.File;
import java.sql.SQLException;
import java.util.Optional;
import java.util.function.Consumer;

public class SUI extends Notifier implements View {

    private int steps, time;
    private Timeline timer;

    private ChoiceBox<String> ddSearch;
    private TextField searchKeyWord;
    private LeaderboardsController lbc;
    private VBox vbox;
    private TableView<DbPlayer> table;

    @FXML
    private LevelDrawer levelDrawer;

    @FXML
    private Label timeLabel;

    @FXML
    private Label stepsLabel;

    public SUI() {
        timer = new Timeline(new KeyFrame(Duration.seconds(1),
                ev -> timeLabel.setText("" + (++time))));
        time = 0;
        steps = 0;
    }

    @FXML
    public void initialize() {
        levelDrawer.addEventFilter(MouseEvent.ANY, (e) -> levelDrawer.requestFocus());
        levelDrawer.setOnKeyPressed(event -> {
            String moveCmd;
            if (event.getCode() == KeyCode.UP) {
                moveCmd = "move up";
                levelDrawer.setCharDirection("up");
                setChange(moveCmd);
                notifyObservers();
            }

            if (event.getCode() == KeyCode.DOWN) {
                moveCmd = "move down";
                levelDrawer.setCharDirection("down");
                setChange(moveCmd);
                notifyObservers();
            }

            if (event.getCode() == KeyCode.LEFT) {
                moveCmd = "move left";
                levelDrawer.setCharDirection("left");
                setChange(moveCmd);
                notifyObservers();
            }

            if (event.getCode() == KeyCode.RIGHT) {
                moveCmd = "move right";
                levelDrawer.setCharDirection("right");
                setChange(moveCmd);
                notifyObservers();
            }

            stepsLabel.setText("" + (++steps));
        });
    }

    @FXML
    public void showLeaderboards() {
        try
        {
            Stage primaryStage = new Stage();
            StackPane pane = new StackPane();
            lbc = new LeaderboardsController();

            table = lbc.loadAllEntries();

            vbox = new VBox(20);

            searchKeyWord = new TextField();
            searchKeyWord.setPromptText("search value");
            searchKeyWord.setPrefWidth(100);
            searchKeyWord.setEditable(false);

            ddSearch = new ChoiceBox<>();
            ddSearch.getItems().addAll("All", "Name", "Level Name");
            ddSearch.setPrefWidth(100);
            ddSearch.setValue("All");
            ddSearch.getSelectionModel().selectedItemProperty().addListener((v, oldItem, newItem) -> {

                if(newItem.equals("All"))
                    searchKeyWord.setEditable(false);
                else
                    searchKeyWord.setEditable(true);
            });

            Button searchButton = new Button("Filter");
            searchButton.setPrefWidth(100);
            searchButton.setOnAction(this::filterButtonPressed);

            HBox hbox = new HBox(ddSearch, searchKeyWord, searchButton);
            hbox.setSpacing(20);
            hbox.setPadding(new Insets(0,0,0,65));

            vbox.getChildren().addAll(table, hbox);
            pane.getChildren().addAll(vbox);
            Scene scene = new Scene(pane, 500, 450);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Leaderboards");
            primaryStage.setResizable(false);

            primaryStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    // TODO fix the TableView update bug
    private void filterButtonPressed(ActionEvent e) {
        String choice = ddSearch.getValue();
        String inputText = searchKeyWord.getText();
        table.setEditable(true);

        try {
            switch(choice)
            {
                case "All":
                    table = lbc.loadAllEntries();
                    vbox.getChildren().set(0, table);
                    break;

                case "Player Name":
//                    table = lbc.loadEntriesByName(inputText);
//                    vbox.getChildren().set(0, table);
                    ObservableList<DbPlayer> newList = FXCollections.observableArrayList();
                    newList.addAll(DatabaseConnector.getPlayersByName(inputText));
                    table.setItems(newList);
                    break;

                case "Level Name":
                    table = lbc.loadEntriesByLevel(inputText);
                    vbox.getChildren().set(0, table);
                    break;
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        searchKeyWord.clear();

    }

    @FXML
    public void openLevelFile() {
        FileChooser fc = new FileChooser();
        fc.setTitle("open level file");
        fc.setInitialDirectory(new File("./resources/levels"));
        FileChooser.ExtensionFilter ef1 = new FileChooser.ExtensionFilter("All Files", "*.*");
        FileChooser.ExtensionFilter ef2 = new FileChooser.ExtensionFilter("Text Files (.txt)", "*.txt");
        FileChooser.ExtensionFilter ef3 = new FileChooser.ExtensionFilter("XML Files (.xml)", "*.xml");
        FileChooser.ExtensionFilter ef4 = new FileChooser.ExtensionFilter("Binary Files (.obj)", "*.obj");
        fc.getExtensionFilters().add(ef1);
        fc.getExtensionFilters().add(ef2);
        fc.getExtensionFilters().add(ef3);
        fc.getExtensionFilters().add(ef4);
        fc.setSelectedExtensionFilter(ef1);
        File chosenFile = fc.showOpenDialog(null);
        if (chosenFile != null) {
            String cmd = "load " + chosenFile.getAbsolutePath();
            setChange(cmd);
            notifyObservers();

            steps = 0;
            time = 0;
            timeLabel.setText("" + 0);
            stepsLabel.setText("" + 0);

            timer.setCycleCount(Animation.INDEFINITE);
            timer.play();
        }
    }

    @FXML
    public void saveLevelFile() {
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter ef1 = new FileChooser.ExtensionFilter("All Files", "*.*");
        FileChooser.ExtensionFilter ef2 = new FileChooser.ExtensionFilter("Text Files (.txt)", "*.txt");
        FileChooser.ExtensionFilter ef3 = new FileChooser.ExtensionFilter("XML Files (.xml)", "*.xml");
        FileChooser.ExtensionFilter ef4 = new FileChooser.ExtensionFilter("Binary Files (.obj)", "*.obj");
        fc.getExtensionFilters().add(ef1);
        fc.getExtensionFilters().add(ef2);
        fc.getExtensionFilters().add(ef3);
        fc.getExtensionFilters().add(ef4);
        fc.setSelectedExtensionFilter(ef1);
        fc.setTitle("save level file");
        fc.setInitialDirectory(new File("./resources/levels"));
        File fileToSave = fc.showSaveDialog(null);
        if (fileToSave != null) {

            String cmd = "save " + fileToSave.getAbsolutePath();
            setChange(cmd);
            notifyObservers();
        }
    }

    @FXML
    public void showAbout() {
        displayMessage("\t\t\t\tSOKOBAN\n\t\t A revision for Sokoban game\n\n\tDeveloped by: Ben Ohayon",
                "About", MessageType.INFORMATION, null);
    }

    @Override
    public void displayLevel(Level lvl) {
        levelDrawer.setLevel(lvl);
        levelDrawer.draw();
    }

    @Override
    public void displayMessage(String content, String title, MessageType messageType, Consumer<Boolean> callback) {

        switch (messageType) {
            case INFORMATION:
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, content);
                    alert.setTitle(title);
                    alert.setHeaderText(null);
                    alert.show();
                });

                break;

            case ERROR:
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR, content);
                    alert.setTitle(title);
                    alert.setHeaderText(null);
                    alert.show();
                });

                break;

            case CONFIRMATION:
                Alert alertC = new Alert(Alert.AlertType.CONFIRMATION, content, ButtonType.YES, ButtonType.NO);
                alertC.setTitle(title);
                alertC.setHeaderText(null);
                Optional<ButtonType> result = alertC.showAndWait();
                callback.accept(result.isPresent() && result.get() == ButtonType.YES);
                break;
        }
    }

    @FXML
    public void exit() {
        quit();
    }

    @Override
    public void quit() {
        displayMessage("Are you sure you want to exit?", "Exit", MessageType.CONFIRMATION,
                (Boolean condition) -> {
                    if (condition) {
                        System.exit(0);
                    }
                });
    }

    @Override
    public void win() {
        setChange("update steps " + stepsLabel.getText());
        notifyObservers();

        setChange("update time " + timeLabel.getText());
        notifyObservers();

        if (timer != null)
            timer.stop();

        levelDrawer.drawWinImage();
        try {
            Thread.sleep(2000);
        } catch(InterruptedException e) {
            displayMessage("Something went wrong. Please check the error log.",
                    "Unknown Error", MessageType.ERROR, null);
        }

        Platform.runLater(() ->
            displayMessage("Would you like to join the global leaderboards?",
                    "Game Over", MessageType.CONFIRMATION,
                    condition -> {
                        if(condition) {
                            Label label = new Label("Full Name: ");
                            label.setLayoutX(34.0);
                            label.setLayoutY(35.0);
                            label.setPrefWidth(75.0);
                            label.setPrefHeight(35.0);

                            TextField name = new TextField();
                            name.setLayoutX(109.0);
                            name.setLayoutY(40.0);
                            name.setPrefWidth(228.0);
                            name.setPrefHeight(25.0);
                            name.setId("name");

                            Button okButton = new Button("OK");
                            okButton.setOnAction(event -> {
                                setChange("join " + name.getText());
                                notifyObservers();
                                okButton.getScene().getWindow().hide();
                            });
                            okButton.setLayoutX(89.0);
                            okButton.setLayoutY(91.0);
                            okButton.setMnemonicParsing(false);
                            okButton.setPrefWidth(75.0);
                            okButton.setPrefHeight(41.0);

                            Button cancelButton = new Button("CANCEL");
                            cancelButton.setLayoutX(232.0);
                            cancelButton.setLayoutY(91.0);
                            cancelButton.setMnemonicParsing(false);
                            cancelButton.setPrefHeight(41.0);
                            cancelButton.setPrefWidth(75.0);
                            cancelButton.setOnAction(event -> cancelButton.getScene().getWindow().hide());

                            AnchorPane root = new AnchorPane(label, name, okButton, cancelButton);
                            Stage stage = new Stage();
                            Scene scene = new Scene(root, 450, 200);
                            stage.setScene(scene);
                            stage.setTitle("Joining Leaderboards");
                            stage.setResizable(false);
                            stage.show();
                        }
                    }
            ));
    }
}
