package TwentyThreeProductions.Domain;

import java.sql.Time;

public class JobTask {

    private int jobID;
    private int taskID;
    private Time alteredDuration;

    public JobTask(){}

    public int getJobID() {
        return jobID;
    }

    public void setJobID(int jobID) {
        this.jobID = jobID;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public Time getAlteredDuration() {
        return alteredDuration;
    }

    public void setAlteredDuration(Time alteredDuration) {
        this.alteredDuration = alteredDuration;
    }
}
