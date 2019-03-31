package TwentyThreeProductions.Model.Database.DAO;

import TwentyThreeProductions.Domain.Backup;
import TwentyThreeProductions.Model.Database.DBConnectivity;
import TwentyThreeProductions.Model.Database.Interfaces.IBackup;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class BackupDAO implements IBackup {

    private ArrayList<Backup> backups;
    private DBConnectivity dbConnectivity;
    private Connection connection;

    public BackupDAO() {
        backups = new ArrayList<>();
        dbConnectivity = new DBConnectivity();
    }

    @Override
    public ArrayList<Backup> getAll() {
        Statement statement;
        ResultSet result;
        String query = "SELECT * FROM GARAGE.\"Backup\"";
        connection = dbConnectivity.connection(connection);
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            while (result.next()) {
                Backup backup = new Backup();
                backup.setBackupID(result.getInt("BACKUPID"));
                backup.setDate(result.getString("BACKUPDATE"));
                backup.setFileLocation(result.getString("FILELOCATION"));
                backups.add(backup);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnectivity.closeConnection(connection);
        }
        return backups;
    }

    @Override
    public void save(Backup backup) {
        backups.add(backup);
        String args[] = {backup.getDate(), backup.getFileLocation()};
        String query = "INSERT INTO GARAGE.\"Backup\" (BACKUPDATE, FILELOCATION)\n" +
                " VALUES (?, ?)";
        connection = dbConnectivity.connection(connection);
        dbConnectivity.writePrepared(query,connection,args);
    }

    @Override
    public void delete(Backup backup) {
        backups = getAll();
        connection = dbConnectivity.connection(connection);
        String args[] = {Integer.toString(backup.getBackupID())};
        String query = "DELETE FROM GARAGE.\"Backup\" WHERE BACKUPID=?";
        dbConnectivity.writePrepared(query, connection, args);
        backups.remove(backup);
    }
}
