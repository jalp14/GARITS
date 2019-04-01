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
        String query = "SELECT * FROM GARAGE.MECHANIC";
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

    }

    @Override
    public void update(Mechanic mechanic) {

    }

    @Override
    public void delete(Mechanic mechanic) {

    }
}
