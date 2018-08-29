package view;

import controller.observer.Notifier;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import model.entities.Level;
import model.utils.MessageType;

import java.io.File;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

import static model.utils.MessageType.INFORMATION;

public class SUI extends Notifier implements View {

    private int steps, time;
//    private Timer timer;
    private Timeline timer;

    @FXML
    private LevelDrawer levelDrawer;

    @FXML
    private Label timeLabel;

    @FXML
    private Label stepsLabel;

    public SUI() {
        timer = new Timeline(new KeyFrame(Duration.seconds(1),
                ev -> timeLabel.setText(""+(time++))));
        time = 0;
        steps = 0;
    }

    @FXML
    public void initialize()
    {
        levelDrawer.addEventFilter(MouseEvent.ANY, (e) -> levelDrawer.requestFocus());
        levelDrawer.setOnKeyPressed(event -> {
            String moveCmd;
            if(event.getCode() == KeyCode.UP)
            {
                moveCmd = "move up";
                levelDrawer.setCharDirection("up");
                setChange(moveCmd);
                notifyObservers();
            }

            if(event.getCode() == KeyCode.DOWN)
            {
                moveCmd = "move down";
                levelDrawer.setCharDirection("down");
                setChange(moveCmd);
                notifyObservers();
            }

            if(event.getCode() == KeyCode.LEFT)
            {
                moveCmd = "move left";
                levelDrawer.setCharDirection("left");
                setChange(moveCmd);
                notifyObservers();
            }

            if(event.getCode() == KeyCode.RIGHT)
            {
                moveCmd = "move right";
                levelDrawer.setCharDirection("right");
                setChange(moveCmd);
                notifyObservers();
            }

            stepsLabel.setText(""+(++steps));
        });
    }

    @FXML
    public void openLevelFile()
    {
        FileChooser fc = new FileChooser();
        fc.setTitle("open level file");
        fc.setInitialDirectory(new File("./resources"));
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
        if(chosenFile != null)
        {
            String cmd = "load " + chosenFile.getAbsolutePath();
            setChange(cmd);
            notifyObservers();

            steps = 0;
            time = 0;

            timer.setCycleCount(Animation.INDEFINITE);
            timer.play();

//            timer.scheduleAtFixedRate(new TimerTask() {
//                @Override
//                public void run() {
//                    Platform.runLater(() -> {
//                        timeLabel.setText(""+(time++));
//                    });
//                }
//            }, 0, 1000);
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
        fc.setInitialDirectory(new File("./resources"));
        File fileToSave = fc.showSaveDialog(null);
        if(fileToSave != null)
        {
            String cmd = "save " + fileToSave.getAbsolutePath();
            setChange(cmd);
            notifyObservers();
        }
    }

    @FXML
    public void showAbout() {
        displayMessage("\t\t\t\tSOKOBAN\n\t\t A revision for Sokoban game\n\n\tDeveloped by: Ben Ohayon",
                "About", INFORMATION, null);
    }

    @Override
    public void displayLevel(Level lvl) {
        levelDrawer.setLevel(lvl);
        levelDrawer.draw();
    }

    @Override
    public void displayMessage(String content, String title, MessageType messageType, Consumer<Boolean> callback) {

        switch(messageType) {
            case INFORMATION:
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, content);
                    alert.setTitle("Sokoban Message");
                    alert.setHeaderText(null);
                    alert.show();
                });

                break;

             case ERROR:
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR, content);
                    alert.setTitle("Sokoban Error");
                    alert.setHeaderText(null);
                    alert.show();
                });

                break;

            case CONFIRMATION:
                Alert alertC = new Alert(Alert.AlertType.CONFIRMATION, content, ButtonType.YES, ButtonType.NO);
                alertC.setTitle("Sokoban Confirmation");
                alertC.setHeaderText(null);
                Optional<ButtonType> result = alertC.showAndWait();
                callback.accept(result.isPresent() && result.get() == ButtonType.YES);
                break;
        }
    }

    @FXML
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
        if(timer != null)
            timer.stop();

        levelDrawer.drawWinImage();
    }
}
