package view;

import model.entities.Level;

public interface View {
    void displayLevel(Level lvl);
    void launch();
    void quit();
    void win();
}
