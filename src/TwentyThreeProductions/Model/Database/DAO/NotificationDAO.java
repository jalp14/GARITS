package TwentyThreeProductions.Model.Database.DAO;

import TwentyThreeProductions.Domain.Notification;
import TwentyThreeProductions.Domain.User;
import TwentyThreeProductions.Model.Database.DBConnectivity;
import TwentyThreeProductions.Model.Database.Interfaces.DBConnectivityInterface;
import TwentyThreeProductions.Model.Database.Interfaces.INotification;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;

public class NotificationDAO implements INotification {

    private ArrayList<Notification> notifications;
    private DBConnectivityInterface dbConnectivity;
    private Connection connection;


    public NotificationDAO() {
        notifications = new ArrayList<>();
        dbConnectivity = new DBConnectivity();
    }


    @Override
    public ArrayList<Notification> getNotification(String user) {
        PreparedStatement statement;
        ResultSet result;
        String query = "SELECT * FROM GARAGE.NOTIFICATION where USER=?";
        connection = dbConnectivity.connection(connection);
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, user);
            result = statement.executeQuery();

            while (result.next()) {
                Notification notification = new Notification();
                notification.setId(result.getInt("ID"));
                notification.setMessage(result.getString("MESSAGE"));
                notification.setPeriod(result.getTimestamp("PERIOD").toLocalDateTime());
                notification.setView(result.getString("VIEW"));
                notification.setUser(result.getString("USER"));
                notifications.add(notification);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notifications;
    }

    @Override
    public void save(Notification notification) {
        notifications.add(notification);
        String args[] = {notification.getMessage(), notification.getUser(), notification.getPeriod().toString(), notification.getView()};
        String query = "INSERT INTO GARAGE.NOTIFICATION (MESSAGE, USER, PERIOD, VIEW)\n" +
                    "VALUES (?,?,?,?)";
        connection = dbConnectivity.connection(connection);
        dbConnectivity.writePrepared(query, connection, args);
    }

    @Override
    public void delete(Notification notification) {
        String deleteNotification = "DELETE FROM GARAGE.NOTIFICATION WHERE ID=?";
        connection = dbConnectivity.connection(connection);
        String args[] = {Integer.toString(notification.getId())};
        dbConnectivity.writePrepared(deleteNotification, connection, args);
        notifications.remove(notification);
    }
}
