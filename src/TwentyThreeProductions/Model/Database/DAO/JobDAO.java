package TwentyThreeProductions.Model.Database.DAO;

import TwentyThreeProductions.Domain.Job;
import TwentyThreeProductions.Model.Database.DBConnectivity;
import TwentyThreeProductions.Model.Database.Interfaces.IJob;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class JobDAO implements IJob {

    private ArrayList<Job> jobs;
    private DBConnectivity dbConnectivity;
    private Connection connection;

    public JobDAO() {
        jobs = new ArrayList<>();
        dbConnectivity = new DBConnectivity();
    }

    @Override
    public ArrayList<Job> getAll() {
        Statement statement;
        String query = "SELECT * FROM GARAGE.JOB";
        ResultSet result;
        connection = dbConnectivity.connection(connection);
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            while (result.next()) {
                Job job = new Job();
                job.setJobID(result.getInt("JOBID"));
                job.setUsername(result.getString("USERNAME"));
                job.setCustomerID(result.getInt("CUSTOMERID"));
                job.setRegistrationID(result.getString("CARREGISTRATIONID"));
                job.setDateBookedIn(result.getDate("DATEBOOKEDIN"));
                job.setDescription(result.getString("DESCRIPTION"));
                job.setSparePartsUsed(result.getString("SPAREPARTSUSED"));
                job.setStatus(result.getString("STATUS"));
                job.setPaidFor(result.getString("PAIDFOR"));
                jobs.add(job);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnectivity.closeConnection(connection);
        }
        return jobs;
    }

    @Override
    public void save(Job job) {
        String[] args = {String.valueOf(job.getJobID()), job.getUsername(), String.valueOf(job.getCustomerID()), job.getRegistrationID(), String.valueOf(job.getDateBookedIn()), job.getDescription(), job.getSparePartsUsed(), job.getStatus(), job.getPaidFor()};
        String saveQuery = "INSERT INTO GARAGE.JOB (JOBID, USERNAME, CUSTOMERID, CARREGISTRATIONID, DATEBOOKEDIN, DESCRIPTION, SPAREPARTSUSED, STATUS, PAIDFOR)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        connection = dbConnectivity.connection(connection);
        dbConnectivity.writePrepared(saveQuery, connection, args);
    }

    @Override
    public void update(Job job) {
        String updateQuery = "UPDATE GARAGE.JOB SET USERNAME=?, STATUS=?, PAIDFOR=? WHERE JOBID=?";
        connection = dbConnectivity.connection(connection);
        String args[] = {job.getUsername(), job.getStatus(), job.getPaidFor(), String.valueOf(job.getJobID())};
        dbConnectivity.writePrepared(updateQuery, connection, args);
        System.out.println("Successfully updated ");
    }

    @Override
    public void delete(Job job) {

    }
}
