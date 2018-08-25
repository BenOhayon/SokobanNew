package controller.commands;

import model.Model;
import model.entities.Level;
import model.entities.Position;
import view.View;

public class MoveCommand extends SokobanCommand {

    // TODO Add the policy logic for the Sokoban game.

    public MoveCommand(Model m, View v) {
        setModel(m);
        setView(v);
    }

    @Override
    public void execute() {
        String direction = getParams().removeFirst();
        Level level = getModel().getLevel();
        String lowerDirection = direction.toLowerCase();

        Position charPos = level.getCharacterPosition();

        if(isValidDirection(lowerDirection)) {
            move(lowerDirection, level, charPos.getX(), charPos.getY());
        }
        else {
            System.out.println("This is not a valid direction. Please retype it.");
        }
    }

    private void move(String lowerDirection, Level level, int charX, int charY) {

        // initialization for temporary values:
        int nextX = charX;
        int nextY = charY;
        int boxX = charX;
        int boxY = charY;
        int nextBoxX = nextX;
        int nextBoxY = nextY;

        switch(lowerDirection) {
            case "right":  // going right
                nextY = charY + 1;
                boxY = nextY;
                nextBoxY = charY + 2;
                break;

            case "left":  // going left
                nextY = charY - 1;
                boxY = nextY;
                nextBoxY = charY - 2;
                break;

            case "up":  // going up
                nextX = charX - 1;
                boxX = nextX;
                nextBoxX = charX - 2;
                break;

            case "down":  // going down
                nextX = charX + 1;
                boxX = nextX;
                nextBoxX = charX + 2;
                break;
        }

        // moving the box, if exists:
        if(level.getItemAt(boxX, boxY) == '@') { // There's a box in front of the player.
            // saving the item before moving the box on top of it.
            level.changeBottomBoardAt(nextBoxX, nextBoxY, level.getItemAt(nextBoxX, nextBoxY));
            // moving the box to the next position.
            level.changeBoardAt(nextBoxX, nextBoxY, '@');
            // restore the item that was at the box's previous position.
            level.changeBoardAt(boxX, boxY, level.getBottomItemAt(boxX, boxY));
        }

        // moving the character:
        // saving the item before moving the player on top of it.
        level.changeBottomBoardAt(nextX, nextY, level.getItemAt(nextX, nextY));
        // moving the player.
        level.changeBoardAt(nextX, nextY, 'A');
        // restore the item that was at the player's previous position.
        level.changeBoardAt(charX, charY, level.getBottomItemAt(charX, charY));

        if(level.hasWon()) {
            getView().win();
        }
    }

    // accepts the direction lower-case string.
    private boolean isValidDirection(String direction) {
        return direction.equals("right") ||
                direction.equals("left") ||
                direction.equals("up") ||
                direction.equals("down");
    }
}
