package controller;

import controller.commands.*;
import controller.observer.Notifier;
import controller.observer.Observer;
import model.Model;
import view.View;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class SokobanHQ implements Controller, Observer {

    private Map<String, Command> commandMap;
    private Model model;
    private View view;

    public SokobanHQ(Model model, View view) {
        this.model = model;
        this.view = view;
        this.commandMap = new HashMap<>();
        populateMap();
    }

    private void populateMap() {
        this.commandMap.put("exit", new ExitCommand(view));
        this.commandMap.put("move", new MoveCommand(model, view));
        this.commandMap.put("load", new LoadCommand(model, view));
        this.commandMap.put("save", new SaveCommand(model, view));
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void update(Notifier notifier, Object notifiedArg) {

        if(notifier instanceof Model) {
            this.view.displayLevel(this.model.getLevel());
        } else {
            // in Sokoban, we accept here the commands to operate by SokobanHQ as strings.
            String command = (String) notifiedArg;
            // we split the command to extract the command type and its parameters, then extracting the appropriate
            // command object and execute it.
            String[] commandSplit = command.split(" ");
            String commandType = commandSplit[0];

            Command c;
            if ((c = this.commandMap.get(commandType)) != null) {
                LinkedList<String> params = new LinkedList<>();
                for(int i = 1 ; i < commandSplit.length ; i++) {
                    params.add(commandSplit[i]);
                }
                c.setParams(params);
                c.execute();
            } else {
                System.err.println("There's no such command. Please re-type.");
            }
        }
    }
}
