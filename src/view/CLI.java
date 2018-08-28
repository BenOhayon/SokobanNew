//package view;
//
//import controller.observer.Notifier;
//import model.entities.Level;
//import model.utils.MessageType;
//
//import java.util.Scanner;
//
//public class CLI extends Notifier implements View {
//
//    private boolean loop = true;
//
//    @Override
//    public void displayMessage(String content, MessageType messageType) {
//        switch(messageType) {
//            case INFORMATION:
//                System.out.println(content);
//                break;
//
//            case ERROR:
//                System.err.println(content);
//                break;
//        }
//    }
//
//    @Override
//    public void displayLevel(Level level) {
//        // printing the main board
//        System.out.println("main board:");
//        for(int i = 0 ; i < level.getBoard().length ; i++) {
//            for(int j = 0 ; j < level.getBoard()[0].length ; j++) {
//                System.out.print(level.getBoard()[i][j]);
//            }
//            System.out.println();
//        }
//
////        System.out.println("bottom board:");
////        for(int i = 0 ; i < level.getBottomBoard().length ; i++) {
////            for(int j = 0 ; j < level.getBottomBoard()[0].length ; j++) {
////                System.out.print(level.getBottomBoard()[i][j]);
////            }
////            System.out.println();
////        }
//
//    }
//
//    @Override
//    public void quit() {
//        loop = false;
//    }
//
//    @Override
//    public void launch() {
//        // we get the full command from the user, pass it to the controller and then analyse it.
//        Scanner in = new Scanner(System.in);
////        char[][] board = {{'#', '#', '#', '#', '#', '#', '#', '#'},
////                          {'#', ' ', ' ', '@', ' ', ' ', 'X', '#'},
////                          {'#', 'A', ' ', '@', ' ', ' ', 'X', '#'},
////                          {'#', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
////                          {'#', '#', '#', '#', '#', '#', '#', '#'}};
////
////        Level lvl = new Level("level2", board);
//
//        while (loop) {
//            System.out.print("type a full command: ");
//            String fullCommand = in.nextLine();
//            setChange(fullCommand);
//            notifyObservers();
//        }
//    }
//
//    @Override
//    public void win() {
//        quit();
//        System.out.println("Congratulations!! You've won!!");
//    }
//}
