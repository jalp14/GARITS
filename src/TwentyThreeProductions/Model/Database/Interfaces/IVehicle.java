package TwentyThreeProductions.Model.Database.Interfaces;

import TwentyThreeProductions.Domain.Mechanic;
import TwentyThreeProductions.Domain.Vehicle;

import java.util.ArrayList;

public interface IVehicle {
    ArrayList<Vehicle> getAll();
    ArrayList<Vehicle> getAvailableVehicles();
    ArrayList<Vehicle> getExistingVehicles(String customerID);
    void save(Vehicle vehicle);
    void saveWithoutMOT(Vehicle vehicle);
    void updateWithoutMOT(Vehicle vehicle);
    void updateCustomer(String customerID, String registraionID);
    void removeCustomer(String customerID);
    void update(Vehicle vehicle);
    void delete(Vehicle vehicle);
}
