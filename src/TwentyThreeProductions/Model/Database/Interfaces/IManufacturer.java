package TwentyThreeProductions.Model.Database.Interfaces;

import TwentyThreeProductions.Domain.Manufacturer;
import TwentyThreeProductions.Domain.Mechanic;

import java.util.ArrayList;

public interface IManufacturer {
    ArrayList<Manufacturer> getAll();
    void save(Manufacturer manufacturer);
    void update(Manufacturer manufacturer);
    void delete(Manufacturer manufacturer);
}
