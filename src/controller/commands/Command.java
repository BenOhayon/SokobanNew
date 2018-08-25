package controller.commands;

import java.util.LinkedList;

public interface Command {
    void execute();
    void setParams(LinkedList<String> params);
}
