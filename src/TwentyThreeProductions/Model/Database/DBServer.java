package TwentyThreeProductions.Model.Database;

import org.h2.tools.Server;

import java.sql.SQLException;

public class DBServer {

    Server sqlServer;
    Server webServer;
    private static DBServer dbServer = null;


    public DBServer() {
        startServer();
    }

    public static DBServer getInstance() {
        if (dbServer == null) {
            dbServer = new DBServer();
        }
        return dbServer;
    }

    private void startServer() {
        try {
            sqlServer = Server.createTcpServer().start();
            System.out.println("Starting SQL Server...");
            System.out.println(sqlServer.getStatus());
            webServer = Server.createWebServer("-webPort", "8082", "-tcpAllowOthers");
            webServer.start();
            System.out.println(webServer.getStatus());
            System.out.println(webServer.getURL());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void restartServer() {
        try {
            // Stop the server
            sqlServer.stop();
            webServer.stop();

            // Wait for 5 seconds
            Thread.sleep(5000);

            // Start the server
            startServer();

        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

}
