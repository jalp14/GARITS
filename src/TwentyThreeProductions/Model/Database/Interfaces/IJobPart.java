package TwentyThreeProductions.Model.Database.Interfaces;

import TwentyThreeProductions.Domain.JobPart;

import java.util.ArrayList;

public interface IJobPart {
    ArrayList<JobPart> getAll();
    void save(JobPart jobPart);
    void update(JobPart jobPart);
    void delete(JobPart jobPart);
}
