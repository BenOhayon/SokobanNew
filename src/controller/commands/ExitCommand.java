package controller.commands;

import view.View;

public class ExitCommand extends SokobanCommand {

    public ExitCommand(View view) {
        setView(view);
    }

    @Override
    public void execute() {
        getView().quit();
    }
}
