package TwentyThreeProductions.Model.Database.DAO;
import TwentyThreeProductions.Domain.Car;
import TwentyThreeProductions.Domain.Customer;
import TwentyThreeProductions.Model.Database.DBConnectivity;
import TwentyThreeProductions.Model.Database.Interfaces.DBConnectivityInterface;
import TwentyThreeProductions.Model.Database.Interfaces.ICustomer;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDAO implements ICustomer {

    private ArrayList<Customer> customers;
    private DBConnectivityInterface dbConnectivity;
    private Connection connection;
    private ArrayList<Car> customerCars;

    public CustomerDAO() {
        customers = new ArrayList<>();
        dbConnectivity = new DBConnectivity();
        customerCars = new ArrayList<>();
    }

    @Override
    public ArrayList<Car> getCustomerCars(String customerID) {
        PreparedStatement statement;
        String query = "SELECT * FROM GARAGE.CAR WHERE CUSTOMERID=?";
        ResultSet result;
        connection = dbConnectivity.connection(connection);
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, customerID);
            result = statement.executeQuery();
            while (result.next()) {
                Car car = new Car();
                car.setColour(result.getString("COLOUR"));
                car.setChassisNumber(result.getString("CHASSISNUMBER"));
                car.setEngSerial(result.getString("ENGSERIAL"));
                car.setMake(result.getString("MAKE"));
                car.setManufacturerID(result.getString("MANUFACTURERID"));
                car.setModel(result.getString("MODEL"));
                customerCars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerCars;
    }

    @Override
    public ArrayList<Customer> getAll() {
        Statement statement;
        String query = "SELECT * FROM GARAGE.CUSTOMER";
        ResultSet result;
        connection = dbConnectivity.connection(connection);
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            while (result.next()) {
                Customer customer = new Customer();
                customer.setCustomerID(result.getString("CUSTOMERID"));
                customer.setFirstName(result.getString("FIRSTNAME"));
                customer.setLastName(result.getString("LASTNAME"));
                customer.setCustomerType(result.getString("TYPE"));
                customer.setCustomerEmail(result.getString("EMAIL"));
                customer.setCustomerAddress(result.getString("ADDRESS"));
                customer.setCustomerPostcode(result.getString("POSTCODE"));
                customer.setCustomerPhone(result.getString("PHONE"));
                customer.setCurrentDate(result.getDate("Date"));
                customer.setCustomerType(result.getString("TYPE"));
                customer.setLatePayment(result.getBoolean("LATEPAYMENT"));
                customers.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnectivity.closeConnection(connection);
        }
        return customers;
    }

    @Override
    public void save(Customer customer) {
        customers.add(customer);
        java.util.Date currentDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
        String args[] = {customer.getFirstName(), customer.getLastName(), customer.getCustomerType(), customer.getCustomerAddress(), customer.getCustomerPostcode(), customer.getCustomerPhone(), customer.getCustomerEmail(), sqlDate.toString(), customer.isLatePayment()};
        String query = "INSERT INTO GARAGE.CUSTOMER (FIRSTNAME, LASTNAME, TYPE, ADDRESS, POSTCODE, PHONE, EMAIL, \"Date\", LATEPAYMENT)\n" +
                "VALUES (?,?,?,?,?,?,?,?,?)";
        connection = dbConnectivity.connection(connection);
        dbConnectivity.writePrepared(query, connection, args);
    }

    @Override
    public int getCount() {
        Statement statement;
        ResultSet resultSet;
        int tmp = 0;
        String query = "SELECT COUNT(*) AS TOTAL FROM GARAGE.CUSTOMER";
        connection = dbConnectivity.connection(connection);
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            resultSet.next();
            tmp = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tmp;
    }

    @Override
    public void update(Customer customer) {
        String updateQuery = "UPDATE GARAGE.CUSTOMER SET FIRSTNAME=?, LASTNAME=?, TYPE=?, ADDRESS=?, POSTCODE=?, PHONE=?, EMAIL=?, LATEPAYMENT=? WHERE CUSTOMERID=?";
        connection = dbConnectivity.connection(connection);
        String args[] = {customer.getFirstName(), customer.getLastName(), customer.getCustomerType(), customer.getCustomerAddress(), customer.getCustomerPostcode(), customer.getCustomerPhone(), customer.getCustomerEmail(), customer.isLatePayment(), customer.getCustomerID()};
        dbConnectivity.writePrepared(updateQuery, connection, args);
        System.out.println("Successfully updated ");
    }

    @Override
    public void delete(Customer customer) {
        customers = getAll();
        // Add -> Reset Auto Incremet
        String deleteCustomer = "DELETE FROM GARAGE.CUSTOMER WHERE CUSTOMERID=?";
        connection = dbConnectivity.connection(connection);
        String args[] = {customer.getCustomerID()};
        dbConnectivity.writePrepared(deleteCustomer, connection, args);
        customers.remove(customer);
        customers.remove(customer);
    }
}
