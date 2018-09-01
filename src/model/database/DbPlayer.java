package model.database;

public class DbPlayer {

    private String name;
    private String levelName;
    private int stepsCounter;
    private int elapsedTime;

    public DbPlayer(String name, String levelName, int steps, int seconds) {
        this.name = name;
        this.levelName = levelName;
        this.stepsCounter = steps;
        this.elapsedTime = seconds;
    }

    public String getName() {
        return name;
    }

    public String getLevelName() {
        return levelName;
    }

    public int getStepsCounter() {
        return stepsCounter;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }
}
