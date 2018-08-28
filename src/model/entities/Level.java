package model.entities;

import java.util.LinkedList;
import java.util.List;

public class Level {

    private String name;
    private char[][] board;
    private char[][] bottomBoard;
    private List<Position> targets;
//    private int steps;
//    private int seconds;

    public Level(String name, char[][] board) {
//        this.steps = 0;
//        this.seconds = 0;
        this.name = name;
        this.board = board.clone();
        this.bottomBoard = new char[this.board.length][this.board[0].length];
        // initializing the bottom board with only the borders of the level ('#' signs).
        for(int i = 0; i < bottomBoard.length ; i++) {
            for(int j = 0; j < bottomBoard[0].length ; j++) {
                if(this.board[i][j] == '#')
                    this.bottomBoard[i][j] = this.board[i][j];
                else
                    this.bottomBoard[i][j] = ' ';
            }
        }

        this.targets = new LinkedList<>();
        locateTargets();
    }

    public Level(String name, char[][] board, char[][] bottomBoard) {
//        this.steps = 0;
//        this.seconds = 0;
        this.name = name;
        this.board = board.clone();
        this.bottomBoard = new char[this.board.length][this.board[0].length];
        this.bottomBoard = bottomBoard;

        this.targets = new LinkedList<>();
        locateTargets();
    }

    public int getNumberOfRows() {
        return this.board.length;
    }

    public int getNumberOfColumns() {
        int maxColumn = 0;

        for(char[] row : this.board) {
            if(row.length > maxColumn)
                maxColumn = row.length;
        }

        return maxColumn;
    }

    private void locateTargets() {
        for(int i = 0 ; i < this.board.length ; i++) {
            for(int j = 0 ; j < this.board[0].length ; j++) {
                if(this.board[i][j] == 'X')
                    this.targets.add(new Position(i, j));
            }
        }
    }

    public boolean hasWon() {
        for(Position pos : this.targets) {
            if(getItemAt(pos.getX(), pos.getY()) != '@') {
                return false;
            }
        }

        return true;
    }

    public Position getCharacterPosition() {
        for(int i = 0 ; i < board.length ; i++) {
            for(int j = 0 ; j < board[0].length ; j++) {
                if(board[i][j] == 'A')
                    return new Position(i, j);
            }
        }

        return null;
    }

    public char getBottomItemAt(int x, int y) {
        return bottomBoard[x][y];
    }

    public char getItemAt(int x, int y) {
        return board[x][y];
    }

    public String getName() {
        return this.name;
    }

    public char[][] getBoard() {
        return this.board;
    }

    public char[][] getBottomBoard() {
        return this.bottomBoard;
    }

    public void changeBoardAt(int i, int j, char newItem) {
        this.board[i][j] = newItem;
    }

    public void changeBottomBoardAt(int i, int j, char newItem) {
        this.bottomBoard[i][j] = newItem;
    }
}
