package controller;

import controller.commands.*;
import controller.observer.Notifier;
import controller.observer.Observer;
import model.Model;
import model.database.DatabaseConnector;
import model.database.DbPlayer;
import model.entities.Level;
import model.utils.MessageType;
import view.View;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.regex.Pattern;

public class SokobanHQ implements Controller, Observer {

    private DatabaseConnector db;
    private BlockingQueue<Command> commandQueue;
    private Map<String, Command> commandMap;
    private Model model;
    private View view;
    private boolean stop;

    public SokobanHQ(Model model, View view) {
        this.commandQueue = new ArrayBlockingQueue<>(10);
        this.model = model;
        this.view = view;
        this.commandMap = new HashMap<>();
        this.stop = false;
        populateMap();

        try {
            this.db = new DatabaseConnector("Leaderborads");
        } catch (SQLException e) {
            view.displayMessage(e.getMessage(), "Database Error", MessageType.ERROR, null);
        }
    }

    private void populateMap() {
        this.commandMap.put("exit", new ExitCommand(view));
        this.commandMap.put("move", new MoveCommand(model, view));
        this.commandMap.put("load", new LoadCommand(model, view));
        this.commandMap.put("save", new SaveCommand(model, view));
    }

    private void insertCommand(Command cmd)
    {
        try {
            commandQueue.put(cmd);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start() {
        new Thread(() -> {
            while (!stop)
            {
                try {
                    Command cmd = commandQueue.take();
                    cmd.execute();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void update(Notifier notifier, Object notifiedArg) {

        if(notifier instanceof Model) {
            this.view.displayLevel(this.model.getLevel());
        } else {
            // in Sokoban, we accept here the commands to operate by SokobanHQ as strings.
            String command = (String) notifiedArg;

            if(command.startsWith("join")) {
                String name = command.split(Pattern.quote(" "))[1];
                Level level = model.getLevel();
                DbPlayer newPlayer = new DbPlayer(name, level.getName(), level.getSteps(), level.getElapsedTime());

                try {
                    db.addPlayer(newPlayer);
                    view.displayMessage("You have joined the leaderboards", "Leaderboards", MessageType.INFORMATION, null);
                } catch (SQLException e) {
                    view.displayMessage(e.getMessage(), "Record Insertion Error", MessageType.ERROR, null);
                }

            } else if(command.startsWith("update steps")) {
                int stepsUpdate = Integer.parseInt(command.split(Pattern.quote(" "))[2]);
                model.getLevel().setSteps(stepsUpdate);

            } else if(command.startsWith("update time")) {
                int timeUpdate = Integer.parseInt(command.split(Pattern.quote(" "))[2]);
                model.getLevel().setElapsedTime(timeUpdate);

            }

            else {
                // we split the command to extract the command type and its parameters, then extracting the appropriate
                // command object and execute it.
                String[] commandSplit = command.split(Pattern.quote(" "), 2);
                String commandType = commandSplit[0];

                Command c;
                if ((c = this.commandMap.get(commandType)) != null) {
                    LinkedList<String> params = new LinkedList<>();
                    params.add(commandSplit[commandSplit.length - 1]);
                    c.setParams(params);
                    insertCommand(c);
                } else {
                    view.displayMessage("There's no such command. Please try again.", "Command Error", MessageType.ERROR, null);
                }
            }
        }
    }
}
