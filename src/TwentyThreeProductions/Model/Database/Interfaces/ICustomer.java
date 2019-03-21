package TwentyThreeProductions.Model.Database.Interfaces;

import TwentyThreeProductions.Domain.Car;
import TwentyThreeProductions.Domain.Customer;

import java.util.ArrayList;

public interface ICustomer {
    ArrayList<Customer> getAll();
    ArrayList<Car> getCustomerCars(String customerID);
    void save(Customer customer);
    void update(Customer customer);
    void delete(Customer customer);
}
