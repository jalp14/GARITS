package TwentyThreeProductions.Model.Database.DAO;

import TwentyThreeProductions.Domain.JobPart;
import TwentyThreeProductions.Model.Database.DBConnectivity;
import TwentyThreeProductions.Model.Database.Interfaces.IJobPart;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class JobPartDAO implements IJobPart {


    private ArrayList<JobPart> jobParts;
    private DBConnectivity dbConnectivity;
    private Connection connection;

    public JobPartDAO() {
        jobParts = new ArrayList<>();
        dbConnectivity = new DBConnectivity();
    }

    @Override
    public ArrayList<JobPart> getAll() {
        Statement statement;
        String query = "SELECT * FROM GARAGE.JOB_PART";
        ResultSet result;
        connection = dbConnectivity.connection(connection);
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            while (result.next()) {
                JobPart jobPart = new JobPart();
                jobPart.setJobID(result.getInt("JOBID"));
                jobPart.setPartID(result.getString("PARTID"));
                jobPart.setStockUsed(result.getString("STOCKUSED"));
                jobParts.add(jobPart);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnectivity.closeConnection(connection);
        }
        return jobParts;
    }

    @Override
    public void save(JobPart jobPart) {
        String[] args = {String.valueOf(jobPart.getJobID()), jobPart.getPartID(), jobPart.getStockUsed()};
        String saveQuery = "INSERT INTO GARAGE.JOB_PART (JOBID, PARTID, STOCKUSED)\n" +
                "VALUES (?, ?, ?)";
        connection = dbConnectivity.connection(connection);
        dbConnectivity.writePrepared(saveQuery, connection, args);
    }

    @Override
    public void update(JobPart jobPart) {

    }

    @Override
    public void delete(JobPart jobPart) {

    }
}
