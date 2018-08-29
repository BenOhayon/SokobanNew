package controller.commands;

import model.Model;
import model.SokobanPolicy;
import model.entities.Level;
import model.entities.Position;
import model.utils.CoordinatePack;
import model.utils.MessageType;
import view.View;

public class MoveCommand extends SokobanCommand {

    public MoveCommand(Model m, View v) {
        setModel(m);
        setView(v);
    }

    @Override
    public void execute() {
        String direction = getParams().remove(0);
        Level level = getModel().getLevel();
        String lowerDirection = direction.toLowerCase();

        Position charPos = level.getCharacterPosition();

        if(isValidDirection(lowerDirection) && SokobanPolicy.isValidMove(level, lowerDirection, charPos)) {
            move(lowerDirection, level, charPos.getX(), charPos.getY());
        }
    }

    private void move(String lowerDirection, Level level, int charX, int charY) {

        CoordinatePack pack = CoordinatePack.calculateNextCoordinates(lowerDirection, charX, charY);
        int nextX = pack.getNext().getX();
        int nextY = pack.getNext().getY();
        int boxX = pack.getBox().getX();
        int boxY = pack.getBox().getY();
        int nextBoxX = pack.getNextBox().getX();
        int nextBoxY = pack.getNextBox().getY();

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

        getModel().setLevel(level);
        if(level.hasWon()) {
            getView().win();
        }
    }

    // accepts the direction as lower-case string.
    private boolean isValidDirection(String direction) {
        return direction.equals("right") ||
                direction.equals("left") ||
                direction.equals("up") ||
                direction.equals("down");
    }
}
