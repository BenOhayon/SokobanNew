package model.persistancy.loading;

import model.entities.Level;

import java.io.*;
import java.util.ArrayList;

public class TextLevelLoader implements LevelLoader {

    @Override
    public Level load(String path) {
        Level newLevel = null;
        ArrayList<String> rows = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));

            String line;
            while((line = br.readLine()) != null) {
                rows.add(line);
            }

            char[][] loadedBoard = new char[rows.size()][];
            for(int i = 0 ; i < loadedBoard.length ; i++) {
                loadedBoard[i] = rows.get(i).toCharArray();
            }

            newLevel = new Level(path, loadedBoard);

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
