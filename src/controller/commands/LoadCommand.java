package controller.commands;

import model.Model;
import model.entities.Level;
import model.persistancy.loading.LevelLoader;
import model.persistancy.loading.TextLevelLoader;
import model.utils.MessageType;
import view.View;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class LoadCommand extends SokobanCommand {

    private Map<String, LevelLoader> loaderMap;

    public LoadCommand(Model model, View view) {
        setModel(model);
        setView(view);
        this.loaderMap = new HashMap<>();
        populateMap();
    }

    private void populateMap() {
        this.loaderMap.put("txt", new TextLevelLoader());
        // we can add more file type loaders when implemented.
    }

    @Override
    public void execute() {
        String fileName = getParams().remove(0);
        // we split the file name to extract the file extension and load from the corresponding file type.
        String[] fileNameSplit = fileName.split(Pattern.quote("."));
        String extension = fileNameSplit[fileNameSplit.length - 1];
        LevelLoader loader;
        if((loader = this.loaderMap.get(extension)) != null) {
            Level loadedLevel = loader.load(fileName);
            getModel().setLevel(loadedLevel);
            getView().displayMessage("level loaded successfully!", MessageType.INFORMATION);
        } else {
            getView().displayMessage("Cannot load the specified file. There's no such file type.", MessageType.ERROR);
        }
    }
}
