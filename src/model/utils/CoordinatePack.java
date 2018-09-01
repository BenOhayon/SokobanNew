package model.utils;

import model.entities.Position;

public class CoordinatePack {

    private Position next, box, nextBox;
    private static CoordinatePack instance;

    private CoordinatePack() {}

    public Position getNext() {
        return this.next;
    }

    public Position getBox() {
        return this.box;
    }

    public Position getNextBox() {
        return this.nextBox;
    }

    public static CoordinatePack calculateNextCoordinates(String direction, int charX, int charY) {
        instance = new CoordinatePack();
        instance.next = new Position(charX, charY);
        instance.box = new Position(charX, charY);
        instance.nextBox = new Position(charX, charY);
        switch(direction) {
            case "right":  // going right
                instance.next.setY(charY + 1);
                instance.box.setY(instance.next.getY());
                instance.nextBox.setY(charY + 2);
                break;

            case "left":  // going left
                instance.next.setY(charY - 1);
                instance.box.setY(instance.next.getY());
                instance.nextBox.setY(charY - 2);
                break;

            case "up":  // going up
                instance.next.setX(charX - 1);
                instance.box.setX(instance.next.getX());
                instance.nextBox.setX(charX - 2);
                break;

            case "down":  // going down
                instance.next.setX(charX + 1);
                instance.box.setX(instance.next.getX());
                instance.nextBox.setX(charX + 2);
                break;
        }

        return instance;
    }
}
