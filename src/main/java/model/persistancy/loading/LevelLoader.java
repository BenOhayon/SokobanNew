package model.persistancy.loading;

import model.entities.Level;

public interface LevelLoader {
    Level load(String path);
}
