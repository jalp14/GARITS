package TwentyThreeProductions.Model.Database;

import TwentyThreeProductions.Model.Database.DataAccess.DBHelper;
import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnectivity implements DBConnectivityInterface {

    private static DBConnectivity dbConnectivity = null;
    private Server dbServer;
    private Server webServer;
    private Connection connection = null;

    private DBConnectivity() {
        startServer();
    }

    private void startServer() {
        try {
            dbServer = Server.createTcpServer().start();
            System.out.println("Starting SQL Server...");
            System.out.println(dbServer.getStatus());
            webServer = Server.createWebServer("-webPort", "8082", "-tcpAllowOthers");
            webServer.start();
            System.out.println(webServer.getStatus());
            System.out.println(webServer.getURL());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static DBConnectivity getInstance() {
        if (dbConnectivity == null) {
            dbConnectivity = new DBConnectivity();
        }
        return dbConnectivity;
    }

    public void connectDB() {
        try {
            System.out.println("Attempting to connect to the database");
            Class.forName(DBHelper.JDBC_DRIVER);
            connection = DriverManager.getConnection(DBHelper.DB_URL, DBHelper.user, DBHelper.pass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void disconnectDB() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    public Connection connection(String con) {
        return null;
    }

    @Override
    public boolean closeConnection(Connection connection) {
        return false;
    }

    @Override
    public boolean checkExists(String path) {
        return false;
    }
}
