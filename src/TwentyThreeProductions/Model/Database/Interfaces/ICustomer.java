package TwentyThreeProductions.Model.Database.Interfaces;

import TwentyThreeProductions.Domain.Vehicle;
import TwentyThreeProductions.Domain.Customer;

import java.util.ArrayList;

public interface ICustomer {
    ArrayList<Customer> getAll();
    ArrayList<Vehicle> getCustomerVehicle(String customerID);
    int getCount();
    void save(Customer customer);
    void update(Customer customer);
    void delete(Customer customer);
}
