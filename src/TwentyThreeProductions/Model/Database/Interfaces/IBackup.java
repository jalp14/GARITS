package TwentyThreeProductions.Model.Database.Interfaces;

import TwentyThreeProductions.Domain.Backup;
import TwentyThreeProductions.Domain.Car;

import java.util.ArrayList;

public interface IBackup {
    ArrayList<Backup> getAll();
    void save(Backup backup);
    void update(Backup backup);
    void delete(Backup backup);
}
