package model;

import controller.observer.Notifier;
import model.entities.Level;

public class SokobanDataSource extends Notifier implements Model {

    private Level level;

    @Override
    public Level getLevel() {
        return this.level;
    }

    @Override
    public void setLevel(Level lvl) {
        this.level = lvl;
        setChange(this.level);
        notifyObservers();
    }
}
