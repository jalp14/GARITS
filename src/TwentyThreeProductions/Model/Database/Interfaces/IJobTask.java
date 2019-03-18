package TwentyThreeProductions.Model.Database.Interfaces;

import TwentyThreeProductions.Domain.Car;
import TwentyThreeProductions.Domain.JobTask;

import java.util.ArrayList;

public interface IJobTask {
    ArrayList<JobTask> getAll();
    void save(JobTask jobTask);
    void update(JobTask jobTask);
    void delete(JobTask jobTask);
}
