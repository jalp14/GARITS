package TwentyThreeProductions.Model;

import TwentyThreeProductions.Domain.Job;

public class JobReference {

    Job job;

    private static JobReference jobReference = null;

    private JobReference() {}

    public static JobReference getInstance() {
        if(jobReference == null) {
            jobReference = new JobReference();
        }
        return jobReference;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
