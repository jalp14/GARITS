package TwentyThreeProductions.Model.Database.DAO;

import TwentyThreeProductions.Domain.Report;
import TwentyThreeProductions.Model.Database.DBConnectivity;
import TwentyThreeProductions.Model.Database.Interfaces.IReport;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ReportDAO implements IReport {

    ArrayList<Report> reports;
    Connection connection;
    DBConnectivity dbConnectivity;

    public ReportDAO() {
        dbConnectivity = new DBConnectivity();
        reports = new ArrayList<>();
    }

    @Override
    public ArrayList<Report> getAll() {
        Statement statement;
        String query = "SELECT * FROM GARAGE.REPORT";
        ResultSet result;
        connection = dbConnectivity.connection(connection);
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            while (result.next()) {
                Report report = new Report();
                report.setReportID(result.getInt("REPORTID"));
                report.setUsername(result.getString("USERNAME"));
                report.setReportType(result.getString("REPORTTYPE"));
                report.setStatus(result.getString("STATUS"));
                report.setFileLocation(result.getString("FILELOCATION"));
                report.setHtmlLocation(result.getString("HTMLLOCATION"));
                reports.add(report);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnectivity.closeConnection(connection);
        }
        return reports;
    }

    @Override
    public void save(Report report) {
        String[] args = {report.getUsername(), report.getReportType(), report.getStatus(), report.getFileLocation(), report.getHtmlLocation()};
        connection = dbConnectivity.connection(connection);
        String saveQuery = "INSERT INTO Garage.REPORT (USERNAME, REPORTTYPE, STATUS, FILELOCATION, HTMLLOCATION)\n" +
                "VALUES (?, ?, ?, ?, ?)";
        connection = dbConnectivity.connection(connection);
        dbConnectivity.writePrepared(saveQuery, connection, args);
    }

    @Override
    public void update(Report report) {

    }

    @Override
    public void delete(Report report) {

    }
}
