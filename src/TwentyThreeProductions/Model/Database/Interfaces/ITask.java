package TwentyThreeProductions.Model.Database.Interfaces;

import TwentyThreeProductions.Domain.Customer;
import TwentyThreeProductions.Domain.Task;

import java.util.ArrayList;

public interface ITask {
    ArrayList<Task> getAll();
    void save(Task task);
    void update(Task task);
    void delete(Task task);
}
