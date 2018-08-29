package view;

import model.entities.Level;
import model.utils.MessageType;

import java.util.function.Consumer;

public interface View {
    void displayLevel(Level lvl);
    void displayMessage(String content, String title, MessageType messageType, Consumer<Boolean> callback);
    void quit();
    void win();
}
