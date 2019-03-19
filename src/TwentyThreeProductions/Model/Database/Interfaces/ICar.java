package TwentyThreeProductions.Model.Database.Interfaces;

import TwentyThreeProductions.Domain.Car;
import TwentyThreeProductions.Domain.Mechanic;

import java.util.ArrayList;

public interface ICar {
    ArrayList<Car> getAll();
    void save(Car car);
    void update(Car car);
    void delete(Car car);
}
