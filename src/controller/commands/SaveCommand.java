package controller.commands;

import model.Model;
import model.entities.Level;
import model.persistancy.saving.LevelSaver;
import model.persistancy.saving.TextLevelSaver;
import model.utils.MessageType;
import view.View;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SaveCommand extends SokobanCommand {

    private Map<String, LevelSaver> saverMap;

    public SaveCommand(Model model, View view) {
        setModel(model);
        setView(view);
        saverMap = new HashMap<>();
        populateMap();
    }

    private void populateMap() {
        this.saverMap.put("txt", new TextLevelSaver());
        // we can add more file type savers when implemented.
    }

    @Override
    public void execute() {
        String fileName = getParams().remove(0);
        String[] fileNameSplit = fileName.split(Pattern.quote("."));
        String extension = fileNameSplit[fileNameSplit.length - 1];

        LevelSaver saver;
        if((saver = this.saverMap.get(extension)) != null) {
            Level lvl = getModel().getLevel();
            saver.save(lvl, fileName);
            getView().displayMessage("level saved successfully!", MessageType.INFORMATION);
        } else {
            getView().displayMessage("Cannot save the file with this extension", MessageType.ERROR);
        }
    }
}
























