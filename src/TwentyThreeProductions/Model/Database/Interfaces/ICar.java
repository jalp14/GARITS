package TwentyThreeProductions.Model.Database.Interfaces;

import TwentyThreeProductions.Domain.Car;
import TwentyThreeProductions.Domain.Mechanic;

import java.util.ArrayList;

public interface ICar {
    ArrayList<Car> getAll();
    ArrayList<Car> getAvailableCars();
    ArrayList<Car> getExistingCars(String customerID);
    void save(Car car);
    void updateCustomer(String customerID, String registraionID);
    void update(Car car);
    void delete(Car car);
}
