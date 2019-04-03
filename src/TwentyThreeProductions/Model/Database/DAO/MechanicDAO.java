package TwentyThreeProductions.Model.Database.DAO;

import TwentyThreeProductions.Domain.Mechanic;
import TwentyThreeProductions.Model.Database.DBConnectivity;
import TwentyThreeProductions.Model.Database.Interfaces.IMechanic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MechanicDAO implements IMechanic {
    private ArrayList<Mechanic> mechanics;
    private DBConnectivity dbConnectivity;
    private Connection connection;

    public MechanicDAO() {
        mechanics = new ArrayList<>();
        dbConnectivity = new DBConnectivity();
    }

    @Override
    public ArrayList<Mechanic> getAll() {
        Statement statement;
        String query = "SELECT * FROM GARAGE.FM";
        ResultSet result;
        connection = dbConnectivity.connection(connection);
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            while (result.next()) {
                Mechanic mechanic = new Mechanic();
                mechanic.setUsername(result.getString("USERNAME"));
                mechanic.setHourlyRate(result.getInt("HOURLYRATE"));
                mechanics.add(mechanic);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnectivity.closeConnection(connection);
        }
        return mechanics;
    }

    @Override
    public void save(Mechanic mechanic) {
        String[] args = {mechanic.getUsername(), Integer.toString(mechanic.getHourlyRate())};
        connection = dbConnectivity.connection(connection);
        String saveQuery = "INSERT INTO GARAGE.FM (USERNAME, HOURLYRATE)\n" +
                "VALUES (?, ?)";
        connection = dbConnectivity.connection(connection);
        dbConnectivity.writePrepared(saveQuery, connection, args);
    }

    @Override
    public void update(Mechanic mechanic) {
        String updateQuery = "UPDATE GARAGE.FM SET HOURLYRATE=? WHERE USERNAME=?";
        connection = dbConnectivity.connection(connection);
        String args[] = {Integer.toString(mechanic.getHourlyRate()), mechanic.getUsername()};
        dbConnectivity.writePrepared(updateQuery, connection, args);
    }

    @Override
    public void delete(Mechanic mechanic) {
        String deleteUser = "DELETE FROM GARAGE.FM WHERE USERNAME=?";
        connection = dbConnectivity.connection(connection);
        String args[] = {mechanic.getUsername()};
        dbConnectivity.writePrepared(deleteUser, connection, args);
    }
}
