package view;

import controller.observer.Notifier;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import model.entities.Level;
import model.utils.MessageType;

import java.io.File;
import java.util.Optional;
import java.util.function.Consumer;

public class SUI extends Notifier implements View {

    // TODO continue with developing the SUI controller logic.

    @FXML
    private LevelDrawer levelDrawer;

    @FXML
    public void openFile()
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
        File choosedFile = fc.showOpenDialog(null);
        if(choosedFile != null)
        {
            String cmd = "load " + choosedFile.getAbsolutePath();
            setChange(cmd);
            notifyObservers();
        }
    }

    @Override
    public void displayLevel(Level lvl) {
        levelDrawer.setLevel(lvl);
        levelDrawer.draw();
    }

    @Override
    public void displayMessage(String content, MessageType messageType, Consumer<Boolean> callback) {

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
                callback.accept(result.get() == ButtonType.YES);
                break;
        }
    }

    @Override
    public void launch() {

    }

    @FXML
    @Override
    public void quit() {
        displayMessage("Are you sure you want to exit?", MessageType.CONFIRMATION,
                (Boolean condition) -> {
                    if (condition) {
                        System.exit(0);
                    }
                });
    }

    @Override
    public void win() {

    }
}
