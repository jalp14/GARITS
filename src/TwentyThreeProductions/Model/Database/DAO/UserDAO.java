package TwentyThreeProductions.Model.Database.DAO;

import TwentyThreeProductions.Domain.User;
import TwentyThreeProductions.Model.Database.DBConnectivity;
import TwentyThreeProductions.Model.Database.Interfaces.IUser;

import java.sql.*;
import java.util.ArrayList;

public class UserDAO implements IUser {

    private ArrayList<User> users;
    private DBConnectivity dbConnectivity;
    private Connection connection;


    public UserDAO() {
        users = new ArrayList<>();
        dbConnectivity = new DBConnectivity();
    }


    @Override
    public ArrayList<User> getAll() {
        Statement statement;
        String query = "SELECT * FROM GARAGE.\"User\"";
        ResultSet result;
        connection = dbConnectivity.connection(connection);
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            while (result.next()) {
                User user = new User();
                user.setUsername(result.getString("USERNAME"));
                user.setPassword(result.getString("PASSWORD"));
                user.setFirstName(result.getString("FIRSTNAME"));
                user.setLastName(result.getString("LASTNAME"));
                user.setUserRole(result.getString("ROLE"));
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnectivity.closeConnection(connection);
        }

        return users;
    }

    @Override
    public ArrayList<User> getMechanics() {
        Statement statement;
        String query = "SELECT * FROM GARAGE.\"User\" WHERE ROLE='MECHANIC' OR ROLE='FOREPERSON'";
        ResultSet result;
        connection = dbConnectivity.connection(connection);
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            while (result.next()) {
                User user = new User();
                user.setUsername(result.getString("USERNAME"));
                user.setPassword(result.getString("PASSWORD"));
                user.setFirstName(result.getString("FIRSTNAME"));
                user.setLastName(result.getString("LASTNAME"));
                user.setUserRole(result.getString("ROLE"));
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnectivity.closeConnection(connection);
        }
        return users;
    }

    @Override
    public void save(User user) {
        String[] args = {user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getUserRole()};
        connection = dbConnectivity.connection(connection);
        String saveQuery = "INSERT INTO Garage.\"User\" (USERNAME, PASSWORD, FIRSTNAME, LASTNAME, ROLE)\n" +
                "VALUES (?, ?, ?, ?,?)";
        connection = dbConnectivity.connection(connection);
        dbConnectivity.writePrepared(saveQuery, connection, args);
    }


    @Override
    public void update(User user) {
        String updateQuery = "UPDATE GARAGE.\"User\" SET PASSWORD=?, FIRSTNAME=?, LASTNAME=?, ROLE=? WHERE USERNAME=?";
        connection = dbConnectivity.connection(connection);
        String args[] = {user.getPassword(), user.getFirstName(), user.getLastName(), user.getUserRole(), user.getUsername()};
        dbConnectivity.writePrepared(updateQuery, connection, args);
    }

    @Override
    public void delete(User user) {
        String deleteUser = "DELETE FROM GARAGE.\"User\" WHERE USERNAME=?";
        connection = dbConnectivity.connection(connection);
        String args[] = {user.getUsername()};
        dbConnectivity.writePrepared(deleteUser, connection, args);
        users.remove(user);
    }
}
