package TwentyThreeProductions.Model.Database.DAO;

import TwentyThreeProductions.Domain.Car;
import TwentyThreeProductions.Domain.User;
import TwentyThreeProductions.Model.Database.DBConnectivity;
import TwentyThreeProductions.Model.Database.Interfaces.ICar;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CarDAO implements ICar {

    private ArrayList<Car> cars;
    private DBConnectivity dbConnectivity;
    private Connection connection;

    public CarDAO() {
        cars = new ArrayList<>();
        dbConnectivity = new DBConnectivity();
    }

    @Override
    public ArrayList<Car> getAll() {
        Statement statement;
        String query = "SELECT * FROM GARAGE.CAR";
        ResultSet result;
        connection = dbConnectivity.connection(connection);
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            while (result.next()) {
                Car car = new Car();
                car.setCustomerID(result.getString("CUSTOMERID"));
                car.setColour(result.getString("COLOUR"));
                car.setChassisNumber(result.getString("CHASSISNUMBER"));
                car.setEngSerial(result.getString("ENGSERIAL"));
                car.setMake(result.getString("MAKE"));
                car.setManufacturerID(result.getString("MANUFACTURERID"));
                car.setRegistrationID(result.getString("REGISTRATIONID"));
                car.setMake(result.getString("MAKE"));
                cars.add(car);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnectivity.closeConnection(connection);
        }

        return cars;
    }

    @Override
    public ArrayList<Car> getAvailableCars() {
        Statement statement;
        String query = "SELECT * FROM GARAGE.CAR WHERE CUSTOMERID IS NULL";
        ResultSet result;
        connection = dbConnectivity.connection(connection);
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            while (result.next()) {
                Car car = new Car();
                car.setColour(result.getString("COLOUR"));
                car.setChassisNumber(result.getString("CHASSISNUMBER"));
                car.setEngSerial(result.getString("ENGSERIAL"));
                car.setMake(result.getString("MAKE"));
                car.setManufacturerID(result.getString("MANUFACTURERID"));
                car.setModel(result.getString("MODEL"));
                cars.add(car);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnectivity.closeConnection(connection);
        }
        return cars;
    }

    @Override
    public void save(Car car) {
        String[] args = {car.getCustomerID(), car.getRegistrationID(), car.getManufacturerID(), car.getColour(), car.getChassisNumber(), car.getEngSerial(), car.getMake(), car.getModel()};
        connection = dbConnectivity.connection(connection);
        String saveQuery = "INSERT INTO GARAGE.CAR (CUSTOMERID, MANUFACTURERID, COLOUR, CHASSISNUMBER, ENGSERIAL, MAKE, MODEL)\n" +
                "VALUES (?, ?, ?,? , ?, ?)";
        connection = dbConnectivity.connection(connection);
        dbConnectivity.writePrepared(saveQuery, connection, args);
    }

    @Override
    public void updateCustomer(String customerID, String registrationID) {
        String[] args = {customerID, registrationID};
        connection = dbConnectivity.connection(connection);
        String updateCustomerQuery = "UPDATE GARAGE.CAR SET CUSTOMERID=? WHERE REGISTRATIONID=?";
        dbConnectivity.writePrepared(updateCustomerQuery, connection, args);
    }

    @Override
    public void update(Car car) {
        String updateQuery = "UPDATE GARAGE.CAR SET CUSTOMERID=?, MANUFACTURERID=?, COLOUR=?, CHASSISNUMBER=?, ENGSERIAL=?, MAKE=?, MODEL=? WHERE REGISTRATIONID=?";
        connection = dbConnectivity.connection(connection);
        String args[] = {car.getCustomerID(), car.getManufacturerID(), car.getColour(), car.getChassisNumber(), car.getEngSerial(), car.getMake(), car.getModel(), car.getRegistrationID()};
        dbConnectivity.writePrepared(updateQuery, connection, args);
        System.out.println("Successfully updated ");
    }

    @Override
    public void delete(Car car) {
        String deleteUser = "DELETE FROM GARAGE.CAR WHERE REGISTRATIONID=?";
        connection = dbConnectivity.connection(connection);
        String args[] = {car.getRegistrationID()};
        dbConnectivity.writePrepared(deleteUser, connection, args);
        cars.remove(car);
    }
}
