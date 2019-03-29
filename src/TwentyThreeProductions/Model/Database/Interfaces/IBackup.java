package TwentyThreeProductions.Model.Database.Interfaces;

import TwentyThreeProductions.Domain.Backup;


import java.util.ArrayList;

public interface IBackup {
    ArrayList<Backup> getAll();
    void save(Backup backup);
    void delete(Backup backup);
}
