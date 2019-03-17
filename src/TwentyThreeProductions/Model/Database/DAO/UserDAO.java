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
        String query = "SELECT * FROM GARAGE.USER";
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
        PreparedStatement preparedStatement;
        connection = dbConnectivity.connection(connection);
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO Garage.\"User\" (USERNAME, PASSWORD, FIRSTNAME, LASTNAME, ROLE)\n" +
                    "VALUES (?, ?, ?, ?,?)");
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setString(5, user.getUserRole());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnectivity.closeConnection(connection);
        }
    }


    @Override
    public void update(User user) {
        PreparedStatement preparedStatement = null;
        connection = dbConnectivity.connection(connection);
        try {
            preparedStatement = connection.prepareStatement("UPDATE GARAGE.\"User\" SET PASSWORD=?, FIRSNAME=?, LASTNAME=?, ROLE=? WHERE USERNAME=?");
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getUserRole());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnectivity.closeConnection(connection);
        }
    }

    @Override
    public void delete(User user) {
        users.remove(user);
    }
}
