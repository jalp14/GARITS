package TwentyThreeProductions.Model.Database.DAO;

import TwentyThreeProductions.Domain.Manufacturer;
import TwentyThreeProductions.Model.Database.DBConnectivity;
import TwentyThreeProductions.Model.Database.Interfaces.IManufacturer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ManufacturerDAO implements IManufacturer {

    private ArrayList<Manufacturer> manufacturers;
    private DBConnectivity dbConnectivity;
    private Connection connection;

    public ManufacturerDAO(){
        manufacturers = new ArrayList<>();
        dbConnectivity = new DBConnectivity();
    }

    @Override
    public ArrayList<Manufacturer> getAll() {
        // This method creates an ArrayList and populates it with every entry for the Manufacturer table currently within the system
        // database, through the use of SQL statements.
        Statement statement;
        String query = "SELECT * FROM GARAGE.MANUFACTURER";
        ResultSet result;
        connection = dbConnectivity.connection(connection);
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            while (result.next()) {
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setManufacturerID(result.getInt("MANUFACTURERID"));
                manufacturer.setCompanyName(result.getString("COMPANYNAME"));
                manufacturer.setCompanyAddress(result.getString("COMPANYADRESS"));
                manufacturer.setPhoneNumber(result.getString("PHONE"));
                manufacturer.setFaxNumber(result.getString("FAX"));
                manufacturers.add(manufacturer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnectivity.closeConnection(connection);
        }

        return manufacturers;
    }

    @Override
    public void save(Manufacturer manufacturer) {

    }

    @Override
    public void update(Manufacturer manufacturer) {

    }

    @Override
    public void delete(Manufacturer manufacturer) {

    }
}
