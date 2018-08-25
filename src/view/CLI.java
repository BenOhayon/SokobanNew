package view;

import controller.commands.MoveCommand;
import model.SokobanDataSource;
import model.entities.Level;
import model.persistancy.loading.TextLevelLoader;
import model.persistancy.saving.TextLevelSaver;

import java.util.LinkedList;
import java.util.Scanner;

public class CLI implements View {

    private boolean loop = true;

    @Override
    public void displayLevel(Level level) {
        // printing the main board
        System.out.println("main board:");
        for(int i = 0 ; i < level.getBoard().length ; i++) {
            for(int j = 0 ; j < level.getBoard()[0].length ; j++) {
                System.out.print(level.getBoard()[i][j]);
            }
            System.out.println();
        }

        // printing the bottom board
//        System.out.println("bottom board:");
//        for(int i = 0 ; i < level.getBottomBoard().length ; i++) {
//            for(int j = 0 ; j < level.getBottomBoard()[0].length ; j++) {
//                System.out.print(level.getBottomBoard()[i][j]);
//            }
//            System.out.println();
//        }
//
    }

    @Override
    public void quit() {
        loop = false;
    }

    @Override
    public void launch() {
        Scanner in = new Scanner(System.in);

        TextLevelLoader levelLoader = new TextLevelLoader();
        Level lvl = levelLoader.load("resources\\level1.txt");
        System.out.println(lvl != null);
        MoveCommand move = new MoveCommand(new SokobanDataSource(lvl), this);
        LinkedList<String> params = new LinkedList<>();

        while (loop) {
            displayLevel(lvl);
            System.out.print("type a direction: ");
            String commandDir = in.next();
            params.addLast(commandDir);
            move.setParams(params);
            move.execute();
        }

        TextLevelSaver levelSaver = new TextLevelSaver();
        levelSaver.save(lvl, "resources\\levelFinish.txt");

    }

    @Override
    public void win() {
        quit();
        System.out.println("Congratulations!! You've won!!");
    }
}
