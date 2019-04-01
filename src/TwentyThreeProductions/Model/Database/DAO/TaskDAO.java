package TwentyThreeProductions.Model.Database.DAO;

import TwentyThreeProductions.Domain.Task;
import TwentyThreeProductions.Model.Database.DBConnectivity;
import TwentyThreeProductions.Model.Database.Interfaces.ITask;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TaskDAO implements ITask {
    private ArrayList<Task> tasks;
    private DBConnectivity dbConnectivity;
    private Connection connection;

    public TaskDAO() {
        tasks = new ArrayList<>();
        dbConnectivity = new DBConnectivity();
    }

    @Override
    public ArrayList<Task> getAll() {
        Statement statement;
        String query = "SELECT * FROM GARAGE.TASK";
        ResultSet result;
        connection = dbConnectivity.connection(connection);
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            while (result.next()) {
                Task task = new Task();
                task.setTaskID(result.getInt("TASKID"));
                task.setDefaultDuration(result.getTime("DEFAULTDURATION"));
                task.setName(result.getString("NAME"));
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnectivity.closeConnection(connection);
        }
        return tasks;
    }

    @Override
    public void save(Task task) {

    }

    @Override
    public void update(Task task) {

    }

    @Override
    public void delete(Task task) {

    }
}
