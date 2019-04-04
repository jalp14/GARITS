package TwentyThreeProductions.Model.Database.DAO;

import TwentyThreeProductions.Domain.JobTask;
import TwentyThreeProductions.Model.Database.DBConnectivity;
import TwentyThreeProductions.Model.Database.Interfaces.IJobTask;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class JobTaskDAO implements IJobTask {
    private ArrayList<JobTask> jobTasks;
    private DBConnectivity dbConnectivity;
    private Connection connection;

    public JobTaskDAO() {
        jobTasks = new ArrayList<>();
        dbConnectivity = new DBConnectivity();
    }

    // This method creates an ArrayList and populates it with every entry for the juction table between Job and Task
    // currently within the system database, through the use of SQL statements.
    @Override
    public ArrayList<JobTask> getAll() {
        Statement statement;
        String query = "SELECT * FROM GARAGE.JOB_TASK";
        ResultSet result;
        connection = dbConnectivity.connection(connection);
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            while (result.next()) {
                JobTask jobTask = new JobTask();
                jobTask.setJobID(result.getInt("JOBJOBID"));
                jobTask.setTaskID(result.getInt("TASKTASK_ID"));
                jobTask.setAlteredDuration(result.getTime("ALTEREDDURATION"));
                jobTasks.add(jobTask);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnectivity.closeConnection(connection);
        }
        return jobTasks;
    }

    // This method sends an SQL query to the system database in which a series of fields are added to a new entry within
    // the junction table for Job and Task
    @Override
    public void save(JobTask jobTask) {
        String[] args = {String.valueOf(jobTask.getJobID()), String.valueOf(jobTask.getTaskID()), String.valueOf(jobTask.getAlteredDuration())};
        String saveQuery = "INSERT INTO GARAGE.JOB_TASK (JOBJOBID, TASKTASK_ID, ALTEREDDURATION)\n" +
                "VALUES (?, ?, ?)";
        connection = dbConnectivity.connection(connection);
        dbConnectivity.writePrepared(saveQuery, connection, args);
    }

    // This method sends an SQL query to the system database in which the stock level is updated for an existing entry
    // with the specified job and part ID within the junction table for Job and Task
    @Override
    public void update(JobTask jobTask) {
        String updateQuery = "UPDATE GARAGE.JOB_TASK SET ALTEREDDURATION=? WHERE JOBJOBID=? AND TASKTASK_ID=?";
        connection = dbConnectivity.connection(connection);
        String args[] = {String.valueOf(jobTask.getAlteredDuration()), String.valueOf(jobTask.getJobID()), String.valueOf(jobTask.getTaskID())};
        dbConnectivity.writePrepared(updateQuery, connection, args);
    }

    @Override
    public void delete(JobTask jobTask) {

    }
}
