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
        String filePath = getParams().remove(0);
        // we split the file name to extract the file extension and load from the corresponding file type.
        String[] fileNameSplit = filePath.split(Pattern.quote("\\"));
        String fileName = fileNameSplit[fileNameSplit.length - 1];
        String extension = fileName.split(Pattern.quote("."))[1];
        LevelLoader loader;
        if((loader = this.loaderMap.get(extension)) != null) {
            Level loadedLevel = loader.load(filePath);
            getModel().setLevel(loadedLevel);
        } else {
            getView().displayMessage("Cannot load the specified file. There's no such file type.", "Load Error", MessageType.ERROR, null);
        }
    }
}
