package controller.commands;

import model.Model;
import view.View;

import java.util.LinkedList;

public abstract class SokobanCommand implements Command {
    private LinkedList<String> params;
    private Model model;
    private View view;

    public void setView(View v) {
        this.view = v;
    }

    public void setModel(Model m) {
        this.model = m;
    }

    public Model getModel() {
        return this.model;
    }

    public View getView() {
        return this.view;
    }

    public LinkedList<String> getParams() {
        return this.params;
    }

    public void setParams(LinkedList<String> params) {
        this.params = params;
    }
}
