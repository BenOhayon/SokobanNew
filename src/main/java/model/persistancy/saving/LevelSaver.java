package model.persistancy.saving;

import model.entities.Level;

public interface LevelSaver {
    void save(Level levelToSave, String path);
}
