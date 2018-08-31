package controller.commands;

import model.Model;
import view.View;

import java.util.List;

public abstract class SokobanCommand implements Command {
    private List<String> params;
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

    List<String> getParams() {
        return this.params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }
}
