package controller.commands;

import java.util.List;

public interface Command {
    void execute();
    void setParams(List<String> params);
}
