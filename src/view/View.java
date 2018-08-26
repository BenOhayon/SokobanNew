package view;

import model.entities.Level;
import model.utils.MessageType;

public interface View {
    void displayLevel(Level lvl);
    void displayMessage(String content, MessageType messageType);
    void launch();
    void quit();
    void win();
}
