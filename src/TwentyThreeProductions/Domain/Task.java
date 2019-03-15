package TwentyThreeProductions.Domain;

import java.sql.Time;

public class Task {

    private int taskID;
    private Time defaultDuration;
    private String name;

    public Task(){}

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public Time getDefaultDuration() {
        return defaultDuration;
    }

    public void setDefaultDuration(Time defaultDuration) {
        this.defaultDuration = defaultDuration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
