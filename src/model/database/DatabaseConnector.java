package model.database;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public final class DatabaseConnector {

    private static Connection connection;

    private static final String SELECT_ALL_PLAYERS_QUERY = "select * from Players";
    private static final String SELECT_PLAYERS_BY_NAME_QUERY = "select * from Players where name = ?";
    private static final String SELECT_PLAYERS_BY_LEVEL_NAME_QUERY = "select * from Players where levelName = ?";
    private static final String INSERT_PLAYER_QUERY = "insert into Players(name, levelName, steps, seconds) values(?, ?, ?, ?)";

    public DatabaseConnector(String dbName) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + dbName + ".db");
        createPlayersTable();
    }

    private static void createPlayersTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("create table if not exists Players (id integer primary key AUTOINCREMENT, name text not null, levelName text, steps integer not null, seconds integer not null)");
    }

    public static void addPlayer(DbPlayer newPlayer) throws SQLException {
        PreparedStatement pStatement = connection.prepareStatement(INSERT_PLAYER_QUERY);
        pStatement.setString(1, newPlayer.getName());
        pStatement.setString(2, newPlayer.getLevelName());
        pStatement.setInt(3, newPlayer.getStepsCounter());
        pStatement.setInt(4, newPlayer.getElapsedTime());

        pStatement.execute();
    }

    public static List<DbPlayer> getAllPlayers() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet results = statement.executeQuery(SELECT_ALL_PLAYERS_QUERY);

        return populateListFrom(results);
    }

    public static List<DbPlayer> getPlayersByName(String name) throws SQLException {
        PreparedStatement pStatement = connection.prepareStatement(SELECT_PLAYERS_BY_NAME_QUERY);
        pStatement.setString(1, name);
        ResultSet results = pStatement.executeQuery();

        return populateListFrom(results);
    }

    public static List<DbPlayer> getPlayersByLevelName(String levelName) throws SQLException {
        PreparedStatement prepared = connection.prepareStatement(SELECT_PLAYERS_BY_LEVEL_NAME_QUERY);
        prepared.setString(1, levelName);
        ResultSet resultSet = prepared.executeQuery();

        return populateListFrom(resultSet);
    }

    private static List<DbPlayer> populateListFrom(ResultSet results) throws SQLException {
        List<DbPlayer> list = new LinkedList<>();
        while(results.next()) {
            String playerName = results.getString("name");
            String levelName = results.getString("levelName");
            int steps = results.getInt("steps");
            int time = results.getInt("seconds");

            list.add(new DbPlayer(playerName, levelName, steps, time));
        }

        return list;
    }
}
