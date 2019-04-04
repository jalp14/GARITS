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

    // This method creates an ArrayList and populates it with every entry for the Job table currently within the system
    // database, through the use of SQL statements.
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
                job.setDateCompleted(result.getDate("DATECOMPLETED"));
                job.setChecked(result.getString("CHECKED"));
                jobs.add(job);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnectivity.closeConnection(connection);
        }
        return jobs;
    }

    // This method sends an SQL query to the system database in which a series of fields are added to a new entry
    // within the Job table.
    @Override
    public void save(Job job) {
        String[] args = {String.valueOf(job.getJobID()), job.getUsername(), String.valueOf(job.getCustomerID()), job.getRegistrationID(), String.valueOf(job.getDateBookedIn()), job.getDescription(), job.getSparePartsUsed(), job.getStatus(), job.getPaidFor(), job.getChecked()};
        String saveQuery = "INSERT INTO GARAGE.JOB (JOBID, USERNAME, CUSTOMERID, CARREGISTRATIONID, DATEBOOKEDIN, DESCRIPTION, SPAREPARTSUSED, STATUS, PAIDFOR, CHECKED)\n" +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        connection = dbConnectivity.connection(connection);
        dbConnectivity.writePrepared(saveQuery, connection, args);
    }

    // This method sends an SQL query to the system database in which various different fields are updated for an existing
    // entry within the Job table, determined by the job ID.
    @Override
    public void update(Job job) {
        String updateQuery = "UPDATE GARAGE.JOB SET USERNAME=?, STATUS=?, PAIDFOR=?, DATECOMPLETED=? WHERE JOBID=?";
        connection = dbConnectivity.connection(connection);
        String args[] = {job.getUsername(), job.getStatus(), job.getPaidFor(), String.valueOf(job.getDateCompleted()), String.valueOf(job.getJobID())};
        dbConnectivity.writePrepared(updateQuery, connection, args);
    }

    // This method sends an SQL query to the system database in which the status of whether the job has been
    // checked is updated for an existing entry within the Job table, determined by the job ID.
    @Override
    public void setChecked(Job job) {
        String updateQuery = "UPDATE GARAGE.JOB SET CHECKED=? WHERE JOBID=?";
        connection = dbConnectivity.connection(connection);
        String args[] = {job.getChecked(), String.valueOf(job.getJobID())};
        dbConnectivity.writePrepared(updateQuery, connection, args);
    }

    // This method sends an SQL query to the system database in which the customer ID of all entries in the Job table
    // associated with a specified customer ID are set to null.
    @Override
    public void deleteCustomer(String customerID) {
        String deleteQuery = "UPDATE GARAGE.JOB SET CUSTOMERID=null WHERE CUSTOMERID=?";
        connection = dbConnectivity.connection(connection);
        String args[] = {customerID};
        dbConnectivity.writePrepared(deleteQuery, connection, args);
    }




    @Override
    public void delete(Job job) {

    }
}
