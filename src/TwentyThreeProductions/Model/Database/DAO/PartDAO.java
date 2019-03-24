package TwentyThreeProductions.Model.Database.DAO;

import TwentyThreeProductions.Domain.Part;
import TwentyThreeProductions.Domain.User;
import TwentyThreeProductions.Model.Database.DBConnectivity;
import TwentyThreeProductions.Model.Database.Interfaces.IPart;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PartDAO implements IPart {

    private ArrayList<Part> parts;
    private DBConnectivity dbConnectivity;
    private Connection connection;

    public PartDAO() {
        parts = new ArrayList<>();
        dbConnectivity = new DBConnectivity();
    }

    @Override
    public ArrayList<Part> getAll() {
        // This method creates an ArrayList and populates it with every entry for the Part table currently within the system
        // database, through the use of SQL statements.
        Statement statement;
        String query = "SELECT * FROM GARAGE.PART";
        ResultSet result;
        connection = dbConnectivity.connection(connection);
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            while (result.next()) {
                Part part = new Part();
                part.setPartID(result.getString("PARTID"));
                part.setManufacturerID(result.getInt("MANUFACTURERID"));
                part.setName(result.getString("NAME"));
                part.setVehicleType(result.getString("VEHICLETYPE"));
                part.setYear(result.getString("YEAR"));
                part.setPrice(result.getString("PRICE"));
                part.setStockLevel(result.getString("STOCK_LEVEL"));
                parts.add(part);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnectivity.closeConnection(connection);
        }
        return parts;
    }

    @Override
    public void save(Part part) {
        // This method sends an SQL query to the system database in which a series of fields are added to a new entry
        // within the Part table.
        String[] args = {part.getPartID(), String.valueOf(part.getManufacturerID()), part.getName(), part.getVehicleType(), String.valueOf(part.getYear()), String.valueOf(part.getPrice()), String.valueOf(part.getStockLevel())};
        String saveQuery = "INSERT INTO GARAGE.PART (PARTID, MANUFACTURERID, NAME, VEHICLETYPE, YEAR, PRICE, STOCK_LEVEL)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        connection = dbConnectivity.connection(connection);
        dbConnectivity.writePrepared(saveQuery, connection, args);
}

    @Override
    public void update(Part part) {
        // This method sends an SQL query to the system database in which the stock level field is updated for an
        // existing entry within the Part table, determined by the part ID.
        String updateQuery = "UPDATE GARAGE.PART\n" +
                "SET STOCK_LEVEL = " + part.getStockLevel() +
                "WHERE PARTID = '" + part.getPartID() + "'";
        connection = dbConnectivity.connection(connection);
        dbConnectivity.write(updateQuery, connection);
    }

    @Override
    public void delete(Part part) {
        // This method sends an SQL query to the system database in which an entry within the Part table is removed,
        // determined by the part ID.
        String deleteQuery = "DELETE FROM GARAGE.PART\n" +
                "WHERE PARTID = '" + part.getPartID() + "'";
        connection = dbConnectivity.connection(connection);
        dbConnectivity.write(deleteQuery, connection);
    }
}
