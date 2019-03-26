package TwentyThreeProductions.Model.Database.Interfaces;

import TwentyThreeProductions.Domain.PartJob;

import java.util.ArrayList;

public interface IPartJob {
    ArrayList<PartJob> getAll();
    void save(PartJob partJob);
    void update(PartJob partJob);
    void delete(PartJob partJob);
}
