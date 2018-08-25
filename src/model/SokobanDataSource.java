package model;

import model.entities.Level;

public class SokobanDataSource implements Model {

    private Level level;

    public SokobanDataSource(Level lvl) {
        setLevel(lvl);
    }

    @Override
    public Level getLevel() {
        return this.level;
    }

    @Override
    public void setLevel(Level lvl) {
        this.level = lvl;
    }
}
