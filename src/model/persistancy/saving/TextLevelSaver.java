package model.persistancy.saving;

import model.entities.Level;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

public class TextLevelSaver implements LevelSaver {

    @Override
    public void save(Level levelToSave, String path) {
        try {
            PrintWriter pw = new PrintWriter(path);
            char[][] board = levelToSave.getBoard();
            char[][] bottomBoard = levelToSave.getBottomBoard();
            StringBuilder sb = new StringBuilder();

            for(char[] row : board) {
                sb.append(row).append("\n");
            }
            pw.write(sb.toString());
            pw.flush();

            String[] pathSplit = path.split(Pattern.quote("\\"));
            String name = pathSplit[pathSplit.length - 1].split(Pattern.quote("."))[0];

            sb = new StringBuilder();
            pw = new PrintWriter("resources\\" + name + "_bottom.txt");

            for(char[] row : bottomBoard) {
                sb.append(row).append("\n");
            }
            pw.write(sb.toString());
            pw.flush();

        } catch(IOException ioe) {
            System.err.println("Error saving the level file");
        }
    }

}
