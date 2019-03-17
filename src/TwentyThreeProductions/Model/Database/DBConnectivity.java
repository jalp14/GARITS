package TwentyThreeProductions.Model.Database;

import TwentyThreeProductions.Model.Database.Interfaces.DBConnectivityInterface;
import org.h2.tools.Server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnectivity implements DBConnectivityInterface {

    private Server dbServer;
    private Server webServer;


    public DBConnectivity() {

        System.out.println("New DBConnectivity instance");
    }

    @Override
    public boolean batchWrite(Connection connection) {
        return false;
    }

    @Override
    public void addToBatch(String sql, Connection connection) {

    }

    @Override
    public void clearBatch() {

    }

    @Override
    public boolean write(String sql, Connection connection) {
        return false;
    }

    @Override
    public ResultSet read(String sql, Connection connection) {
        return null;
    }

    @Override
    public Connection connection(Connection connection) {
        try {
            Class.forName(DBHelper.JDBC_DRIVER);
            connection = DriverManager.getConnection(DBHelper.DB_URL, DBHelper.user, DBHelper.pass);
            connection.setAutoCommit(true);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public boolean closeConnection(Connection connection) {
        try {
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean checkExists(String path) {
        return false;
    }
}
