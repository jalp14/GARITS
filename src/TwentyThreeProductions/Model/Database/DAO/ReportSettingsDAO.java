package TwentyThreeProductions.Model.Database.DAO;

import TwentyThreeProductions.Domain.ReportSettings;
import TwentyThreeProductions.Model.Database.DBConnectivity;
import TwentyThreeProductions.Model.Database.Interfaces.IReportSettings;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReportSettingsDAO implements IReportSettings {

    private DBConnectivity dbConnectivity;
    private Connection connection;
    private ReportSettings reportSettings;

    public ReportSettingsDAO() {
        dbConnectivity = new DBConnectivity();
    }

    @Override
    public ReportSettings getStockSettings() {
        reportSettings = new ReportSettings();
        Statement statement;
        ResultSet resultSet;
        String query = "SELECT * FROM GARAGE.REPORTSETTINGS WHERE REPORTTYPE = 'STOCK'";
        connection = dbConnectivity.connection(connection);
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                reportSettings.setFrequency(resultSet.getString("FREQUENCY"));
                reportSettings.setReportType(resultSet.getString("REPORTTYPE"));
                reportSettings.setNextDate(resultSet.getDate("NEXTDATE"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnectivity.closeConnection(connection);
        }
        return reportSettings;
    }

    @Override
    public ReportSettings getMOTSettings() {
        reportSettings = new ReportSettings();
        Statement statement;
        ResultSet resultSet;
        String query = "SELECT * FROM GARAGE.REPORTSETTINGS WHERE REPORTTYPE = MOT";
        connection = dbConnectivity.connection(connection);
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                reportSettings.setFrequency(resultSet.getString("FREQUENCY"));
                reportSettings.setReportType(resultSet.getString("REPORTTYPE"));
                reportSettings.setNextDate(resultSet.getDate("NEXTDATE"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnectivity.closeConnection(connection);
        }
        return reportSettings;
    }

    @Override
    public void updateDate(ReportSettings reportSettings){
        String updateQuery = "UPDATE GARAGE.REPORTSETTINGS SET NEXTDATE=?, FREQUENCY=? WHERE REPORTTYPE = 'STOCK'";
        String args[] = {reportSettings.getNextDate().toString(), reportSettings.getFrequency()};
        connection = dbConnectivity.connection(connection);
        dbConnectivity.writePrepared(updateQuery, connection, args);
    }

}
