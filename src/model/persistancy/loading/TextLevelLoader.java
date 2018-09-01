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
        BufferedReader br;
        try {
            String[] pathSplit = path.split(Pattern.quote("\\"));
            String name = pathSplit[pathSplit.length - 1].split(Pattern.quote("."))[0];

            br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));

            // loading the main game board
            String line;
            while((line = br.readLine()) != null) {
                if(line.startsWith("*")){
                    bottomRows.add(line.substring(1));
                    break;
                }

                rows.add(line);
            }

            // loading the bottom game board
            while((line = br.readLine()) != null) {
                bottomRows.add(line);
            }

            char[][] loadedBoard = new char[rows.size()][];
            for(int i = 0 ; i < loadedBoard.length ; i++) {
                loadedBoard[i] = rows.get(i).toCharArray();
            }

            char[][] loadedBottomBoard;
            if(bottomRows.size() != 0) {
                loadedBottomBoard = new char[bottomRows.size()][];

                for(int i = 0 ; i < bottomRows.size() ; i++) {
                    loadedBottomBoard[i] = bottomRows.get(i).toCharArray();
                }

                newLevel = new Level(path, loadedBoard, loadedBottomBoard);
            } else {
                newLevel = new Level(path, loadedBoard);
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newLevel;
    }

}
