package TwentyThreeProductions.Model.Database.Interfaces;

import TwentyThreeProductions.Domain.Mechanic;

import java.util.ArrayList;

public interface IMechanic {
    ArrayList<Mechanic> getAll();
    void save(Mechanic mechanic);
    void update(Mechanic mechanic);
    void delete(Mechanic mechanic);
}
