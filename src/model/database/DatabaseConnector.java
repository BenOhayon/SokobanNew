package model.database;

import java.sql.*;

public final class DatabaseConnector {

    private Connection connection;

    private static final String ADD_PLAYER_QUERY = "insert into Players(name, levelName, steps, seconds) values(?, ?, ?, ?)";

    public DatabaseConnector(String dbName) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + dbName + ".db");
        createPlayersTable();
    }

    private void createPlayersTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("create table if not exists Players (id integer primary key AUTOINCREMENT, name text not null, levelName text, steps integer not null, seconds integer)");
    }

    public void addPlayer(DbPlayer newPlayer) throws SQLException {
        PreparedStatement pStatement = connection.prepareStatement(ADD_PLAYER_QUERY);
        pStatement.setString(1, newPlayer.getName());
        pStatement.setString(2, newPlayer.getLevelName());
        pStatement.setInt(3, newPlayer.getStepsCounter());
        pStatement.setInt(4, newPlayer.getElapsedTime());

        pStatement.execute();
    }
}
