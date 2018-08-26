package model;

import model.entities.Level;
import model.utils.CoordinatePack;

public class SokobanPolicy {

    // this class acts as a service class for methods that give permission for the player
    // to perform the requested move. No class is allowed to extend this class nor to make instances of it.
    private SokobanPolicy() {}

    public static boolean isValidMove(Level level, String direction, int charX, int charY) {

        CoordinatePack pack = CoordinatePack.calculateNextCoordinates(direction, charX, charY);
        int nextX = pack.getNext().getX();
        int nextY = pack.getNext().getY();
        int boxX = pack.getBox().getX();
        int boxY = pack.getBox().getY();
        int nextBoxX = pack.getNextBox().getX();
        int nextBoxY = pack.getNextBox().getY();

        // TODO continue with the logic of the game policy.
        return false;
    }
}
