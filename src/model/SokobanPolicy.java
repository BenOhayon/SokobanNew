package model;

import model.entities.Level;
import model.entities.Position;
import model.utils.CoordinatePack;

public class SokobanPolicy {

    // this class acts as a service class for methods that give permission for the player
    // to perform the requested move. No class is allowed to extend this class nor to make instances of it.
    private SokobanPolicy() {}

    public static boolean isValidMove(Level level, String direction, Position charPos) {

        CoordinatePack pack = CoordinatePack.calculateNextCoordinates(direction, charPos.getX(), charPos.getY());
        int nextX = pack.getNext().getX();
        int nextY = pack.getNext().getY();
        int nextNextX = pack.getNextBox().getX();
        int nextNextY = pack.getNextBox().getY();

        // check if there is a wall in front of the player
        if(level.getItemAt(nextX, nextY) == '#')
            return false;
        // check if there are a box and a wall in front of the player
        else if(level.getItemAt(nextX, nextY) == '@' && level.getItemAt(nextNextX, nextNextY) == '#')
            return false;
        // check if there are two boxes in front of the player
        else return level.getItemAt(nextX, nextY) != '@' || level.getItemAt(nextNextX, nextNextY) != '@';

    }
}
