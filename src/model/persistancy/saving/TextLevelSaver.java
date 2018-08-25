package model.persistancy.saving;

import model.entities.Level;

import java.io.IOException;
import java.io.PrintWriter;

public class TextLevelSaver implements LevelSaver {

    @Override
    public void save(Level levelToSave, String path) {
        try {
            PrintWriter pw = new PrintWriter(path);
            char[][] board = levelToSave.getBoard();
            StringBuilder sb = new StringBuilder();

            for(char[] row : board) {
                sb.append(row).append("\n");
            }

            pw.write(sb.toString());
            pw.flush();

        } catch(IOException ioe) {
            System.err.println("Error saving the level file");
        }
    }

}
