package TwentyThreeProductions.Domain;

import java.sql.Time;
import java.util.List;

public class Task {

    private int taskID;
    private Time defaultDuration;
    private String name;
    private List<JobTask> jobTasks;

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

    public List<JobTask> getJobTasks() {
        return jobTasks;
    }

    public void setJobTasks(List<JobTask> jobTasks) {
        this.jobTasks = jobTasks;
    }
}
