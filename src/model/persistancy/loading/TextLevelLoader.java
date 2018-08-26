package model.persistancy.loading;

import model.entities.Level;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class TextLevelLoader implements LevelLoader {

    @Override
    public Level load(String path) {
        Level newLevel = null;
        ArrayList<String> rows = new ArrayList<>();
        ArrayList<String> bottomRows = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));

            String line;
            while((line = br.readLine()) != null) {
                rows.add(line);
            }

            String[] pathSplit = path.split(Pattern.quote("\\"));
            String name = pathSplit[pathSplit.length - 1].split(Pattern.quote("."))[0];
            br = new BufferedReader((new InputStreamReader(new FileInputStream("resources\\" + name + "_bottom.txt"))));

            while((line = br.readLine()) != null) {
                bottomRows.add(line);
            }

            char[][] loadedBoard = new char[rows.size()][];
            char[][] loadedBottomBoard = new char[bottomRows.size()][];
            for(int i = 0 ; i < loadedBoard.length ; i++) {
                loadedBoard[i] = rows.get(i).toCharArray();
                loadedBottomBoard[i] = bottomRows.get(i).toCharArray();
            }

            newLevel = new Level(path, loadedBoard, loadedBottomBoard);

        } catch (FileNotFoundException e) {
            System.err.println("Error opening the level file");
        } catch(IOException e2) {
            System.err.println("Error reading from level file");
        } finally {
            try{
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch(NullPointerException npe) {
                System.err.println("The BufferedReader object is null");
            }
        }

        return newLevel;
    }

}
