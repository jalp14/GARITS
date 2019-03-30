package TwentyThreeProductions.Model.Database.DAO;

import TwentyThreeProductions.Domain.Vehicle;
import TwentyThreeProductions.Model.Database.DBConnectivity;
import TwentyThreeProductions.Model.Database.Interfaces.IVehicle;

import java.sql.*;
import java.util.ArrayList;

public class VehicleDAO implements IVehicle {

    private ArrayList<Vehicle> vehicles;
    private DBConnectivity dbConnectivity;
    private Connection connection;

    public VehicleDAO() {
        vehicles = new ArrayList<>();
        dbConnectivity = new DBConnectivity();
    }

    @Override
    public ArrayList<Vehicle> getAll() {
        Statement statement;
        String query = "SELECT * FROM GARAGE.VEHICLE";
        vehicles = new ArrayList<>();
        ResultSet result;
        connection = dbConnectivity.connection(connection);
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            while (result.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setRegistrationID(result.getInt("REGISTRATIONID"));
                vehicle.setCustomerID(result.getInt("CUSTOMERID"));
                vehicle.setName(result.getString("NAME"));
                vehicle.setRegistrationNumber(result.getString("REGNO"));
                vehicle.setColour(result.getString("COLOUR"));
                vehicle.setVehicleDate(result.getDate("VEHICLEDATE"));
                vehicles.add(vehicle);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnectivity.closeConnection(connection);
        }

        return vehicles;
    }

    @Override
    public ArrayList<Vehicle> getExistingVehicles(String customerID) {
        ResultSet result;
        String query = "SELECT * FROM GARAGE.VEHICLE WHERE CUSTOMERID=?";
        String args[] = {customerID};
        vehicles = new ArrayList<>();
        connection = dbConnectivity.connection(connection);
        result = dbConnectivity.queryPrepared(query, connection, args);
        try {
            while (result.next()) {
                Vehicle tmpVehicle = new Vehicle();
                tmpVehicle.setRegistrationID(result.getInt("REGISTRATIONID"));
                tmpVehicle.setCustomerID(result.getInt("CUSTOMERID"));
                tmpVehicle.setName(result.getString("NAME"));
                tmpVehicle.setRegistrationNumber(result.getString("REGNO"));
                tmpVehicle.setColour(result.getString("COLOUR"));
                tmpVehicle.setVehicleDate(result.getDate("VEHICLEDATE"));
                vehicles.add(tmpVehicle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnectivity.closeConnection(connection);
        }
        return vehicles;
    }

    @Override
    public ArrayList<Vehicle> getAvailableVehicles() {
        Statement statement;
        String query = "SELECT * FROM GARAGE.VEHICLE WHERE CUSTOMERID IS NULL";
        ResultSet result;
        vehicles = new ArrayList<>();
        connection = dbConnectivity.connection(connection);
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            while (result.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setRegistrationID(result.getInt("REGISTRATIONID"));
                vehicle.setCustomerID(result.getInt("CUSTOMERID"));
                vehicle.setName(result.getString("NAME"));
                vehicle.setRegistrationNumber(result.getString("REGNO"));
                vehicle.setColour(result.getString("COLOUR"));
                vehicle.setVehicleDate(result.getDate("VEHICLEDATE"));
                vehicles.add(vehicle);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnectivity.closeConnection(connection);
        }
        return vehicles;
    }

    @Override
    public void save(Vehicle vehicle) {
        String[] args = {String.valueOf(vehicle.getCustomerID()), vehicle.getName(), vehicle.getRegistrationNumber(), vehicle.getColour(), String.valueOf(vehicle.getVehicleDate())};
        String saveQuery = "INSERT INTO GARAGE.VEHICLE (CUSTOMERID, NAME, REGNO, COLOUR, VEHICLEDATE)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        connection = dbConnectivity.connection(connection);
        dbConnectivity.writePrepared(saveQuery, connection, args);
    }

    @Override
    public void updateCustomer(String customerID, String registrationID) {
        String[] args = {customerID, registrationID};
        connection = dbConnectivity.connection(connection);
        String updateCustomerQuery = "UPDATE GARAGE.VEHICLE SET CUSTOMERID=? WHERE REGISTRATIONID=?";
        dbConnectivity.writePrepared(updateCustomerQuery, connection, args);
    }

    @Override
    public void removeCustomer(String customerID) {
        String[] args = {customerID};
        connection = dbConnectivity.connection(connection);
        String removeCustomerQuery = "UPDATE GARAGE.VEHICLE SET CUSTOMERID=null WHERE CUSTOMERID=?";
    }

    @Override
    public void update(Vehicle vehicle) {
        String updateQuery = "UPDATE GARAGE.VEHICLE SET CUSTOMERID=?, NAME=?, REGNO=?, COLOUR=?, VEHICLEDATE=? WHERE REGISTRATIONID=?";
        connection = dbConnectivity.connection(connection);
        String args[] = {String.valueOf(vehicle.getCustomerID()), vehicle.getName(), vehicle.getRegistrationNumber(), vehicle.getColour(), String.valueOf(vehicle.getVehicleDate()), String.valueOf(vehicle.getRegistrationID())};
        dbConnectivity.writePrepared(updateQuery, connection, args);
        System.out.println("Successfully updated ");
    }


    @Override
    public void delete(Vehicle vehicle) {
        vehicles = getAll();
        String deleteUser = "DELETE FROM GARAGE.VEHICLE WHERE REGISTRATIONID=?";
        connection = dbConnectivity.connection(connection);
        String args[] = {String.valueOf(vehicle.getRegistrationID())};
        dbConnectivity.writePrepared(deleteUser, connection, args);
        vehicles.remove(vehicle);
    }
}
