package TwentyThreeProductions.Model.Database.DAO;

import TwentyThreeProductions.Domain.Car;
import TwentyThreeProductions.Domain.User;
import TwentyThreeProductions.Model.Database.DBConnectivity;
import TwentyThreeProductions.Model.Database.Interfaces.ICar;

import java.sql.*;
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
        cars = new ArrayList<>();
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
    public ArrayList<Car> getExistingCars(String customerID) {
        ResultSet resultSet;
        String query = "SELECT * FROM GARAGE.CAR WHERE CUSTOMERID=?";
        String args[] = {customerID};
        cars = new ArrayList<>();
        connection = dbConnectivity.connection(connection);
        resultSet = dbConnectivity.queryPrepared(query, connection, args);
        try {
            while (resultSet.next()) {
                Car tmpCar = new Car();
                tmpCar.setRegistrationID(resultSet.getString("REGISTRATIONID"));
                tmpCar.setModel(resultSet.getString("MODEL"));
                tmpCar.setMake(resultSet.getString("MAKE"));
                tmpCar.setEngSerial(resultSet.getString("ENGSERIAL"));
                tmpCar.setChassisNumber(resultSet.getString("CHASSISNUMBER"));
                tmpCar.setColour(resultSet.getString("COLOUR"));
                tmpCar.setManufacturerID(resultSet.getString("MANUFACTURERID"));
                tmpCar.setCustomerID(resultSet.getString("CUSTOMERID"));
                cars.add(tmpCar);
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
        cars = new ArrayList<>();
        connection = dbConnectivity.connection(connection);
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            while (result.next()) {
                Car car = new Car();
                car.setColour(result.getString("COLOUR"));
                car.setChassisNumber(result.getString("CHASSISNUMBER"));
                car.setRegistrationID(result.getString("REGISTRATIONID"));
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
    public void removeCustomer(String customerID) {
        String[] args = {customerID};
        connection = dbConnectivity.connection(connection);
        String removeCustomerQuery = "UPDATE GARAGE.CAR SET CUSTOMERID=null WHERE CUSTOMERID=?";
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
        cars = getAll();
        String deleteUser = "DELETE FROM GARAGE.CAR WHERE REGISTRATIONID=?";
        connection = dbConnectivity.connection(connection);
        String args[] = {car.getRegistrationID()};
        dbConnectivity.writePrepared(deleteUser, connection, args);
        cars.remove(car);
    }
}
