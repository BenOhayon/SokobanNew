package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.database.DatabaseConnector;
import model.database.DbPlayer;

import java.sql.SQLException;
import java.util.List;

public class LeaderboardsController {

    private TableView<DbPlayer> table;

//    public TableView<DbPlayer> loadEntriesBySeconds(int seconds)
//    {
//        qrs.getEntriesBySeconds(seconds);
//        initializeTableElements();
//        setTableContent();
//
//        return table;
//    }
//
//    public TableView<DbPlayer> loadEntriesBySteps(int steps)
//    {
//        qrs.getEntriesBySteps(steps);
//        initializeTableElements();
//        setTableContent();
//
//        return table;
//    }

    public TableView<DbPlayer> loadEntriesByName(String name) throws SQLException {
        initializeTableElements();
        setTableContent(DatabaseConnector.getPlayersByName(name));
        return table;
    }

    public TableView<DbPlayer> loadEntriesByLevel(String level) throws SQLException {
        initializeTableElements();
        setTableContent(DatabaseConnector.getPlayersByLevelName(level));
        return table;
    }

    public TableView<DbPlayer> loadAllEntries() throws SQLException {
        initializeTableElements();
        setTableContent(DatabaseConnector.getAllPlayers());

        return table;
    }

    private void setTableContent(List<DbPlayer> result) {
        ObservableList<DbPlayer> players = FXCollections.observableArrayList();
        players.addAll(result);
        table.setItems(players);
    }

    private void initializeTableElements() {
        table = new TableView<>();
        table.setEditable(true);

        TableColumn<DbPlayer, String> username = new TableColumn<>("Player Name");
        username.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<DbPlayer, String> levelName = new TableColumn<>("Level");
        levelName.setCellValueFactory(new PropertyValueFactory<>("levelName"));

        TableColumn<DbPlayer, Integer> numOfSteps = new TableColumn<>("Steps");
        numOfSteps.setCellValueFactory(new PropertyValueFactory<>("stepsCounter"));

        TableColumn<DbPlayer, Integer> numOfSeconds = new TableColumn<>("Elapsed Time");
        numOfSeconds.setCellValueFactory(new PropertyValueFactory<>("elapsedTime"));

        table.getColumns().addAll(username, levelName, numOfSteps, numOfSeconds);
    }
}
