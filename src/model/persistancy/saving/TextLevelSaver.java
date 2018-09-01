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
            char[][] bottomBoard = levelToSave.getBottomBoard();
            StringBuilder sb = new StringBuilder();

            // saving the main game board
            for(char[] row : board) {
                sb.append(row).append("\n");
            }
            pw.write(sb.toString());
            pw.flush();

            // saving the bottom board
            sb = new StringBuilder();
            pw.write("*");
            for(char[] row : bottomBoard) {
                sb.append(row).append("\n");
            }
            pw.write(sb.toString());
            pw.flush();

            // saving the game stats


        } catch(IOException ioe) {
            System.err.println("Error saving the level file");
        }
    }
}
