package TwentyThreeProductions.Model.Database.DAO;

import TwentyThreeProductions.Domain.PartJob;
import TwentyThreeProductions.Model.Database.DBConnectivity;
import TwentyThreeProductions.Model.Database.Interfaces.IPartJob;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PartJobDAO implements IPartJob {


    private ArrayList<PartJob> partJobs;
    private DBConnectivity dbConnectivity;
    private Connection connection;

    public PartJobDAO() {
        partJobs = new ArrayList<>();
        dbConnectivity = new DBConnectivity();
    }

    // This method creates an ArrayList and populates it with every entry for the juction table between Part and Job
    // currently within the system database, through the use of SQL statements.
    @Override
    public ArrayList<PartJob> getAll() {
        Statement statement;
        String query = "SELECT * FROM GARAGE.PART_JOB";
        ResultSet result;
        connection = dbConnectivity.connection(connection);
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            while (result.next()) {
                PartJob partJob= new PartJob();
                partJob.setJobID(result.getInt("JOBJOBID"));
                partJob.setPartID(result.getString("PARTPARTID"));
                partJob.setStockUsed(result.getString("STOCKUSED"));
                partJobs.add(partJob);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnectivity.closeConnection(connection);
        }
        return partJobs;
    }

    // This method sends an SQL query to the system database in which a series of fields are added to a new entry within
    // the junction table for Part and Job.
    @Override
    public void save(PartJob partJob) {
        String[] args = {String.valueOf(partJob.getJobID()), partJob.getPartID(), partJob.getStockUsed()};
        String saveQuery = "INSERT INTO GARAGE.PART_JOB (JOBJOBID, PARTPARTID, STOCKUSED)\n" +
                "VALUES (?, ?, ?)";
        connection = dbConnectivity.connection(connection);
        dbConnectivity.writePrepared(saveQuery, connection, args);
    }

    // This method sends an SQL query to the system database in which the stock level is updated for an existing entry
    // with the specified job and part ID within the junction table for Part and Job.
    @Override
    public void update(PartJob partJob) {
        String updateQuery = "UPDATE GARAGE.PART_JOB SET STOCKUSED=? WHERE JOBJOBID=? AND PARTPARTID=?";
        connection = dbConnectivity.connection(connection);
        String args[] = {partJob.getStockUsed(), String.valueOf(partJob.getJobID()), partJob.getPartID()};
        dbConnectivity.writePrepared(updateQuery, connection, args);
    }

    @Override
    public void delete(PartJob partJob) {

    }
}
