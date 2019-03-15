package TwentyThreeProductions.Model.Database.DAO;

import TwentyThreeProductions.Domain.Customer;
import TwentyThreeProductions.Model.Database.DBConnectivity;
import TwentyThreeProductions.Model.Database.DBConnectivityInterface;
import TwentyThreeProductions.Model.Database.DataAccess.ICustomer;

import java.sql.Connection;
import java.util.ArrayList;

public class CustomerDAO implements ICustomer {

    private ArrayList<Customer> customers;
    private Connection connection;
    private DBConnectivityInterface dbConnectivity;

    public CustomerDAO() {
        customers = new ArrayList<>();
        dbConnectivity = DBConnectivity.getInstance();

    }


    @Override
    public ArrayList<Customer> getAll() {
        return customers;
    }

    @Override
    public void save(Customer customer) {
        customers.add(customer);
    }

    @Override
    public void update(Customer customer) {


    }

    @Override
    public void delete(Customer customer) {
        customers.remove(customer);
    }
}
