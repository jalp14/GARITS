package TwentyThreeProductions.Model.Database.Interfaces;

import TwentyThreeProductions.Domain.Job;
import TwentyThreeProductions.Domain.Mechanic;

import java.util.ArrayList;

public interface IJob {
    ArrayList<Job> getAll();
    void save(Job job);
    void update(Job job);
    void delete(Job job);
}
