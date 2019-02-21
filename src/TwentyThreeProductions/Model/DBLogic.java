package TwentyThreeProductions.Model;

import java.sql.*;
import org.h2.tools.Server;

public class DBLogic {

    // JDBC Drivers
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:tcp://localhost/~/test";

    // DB Creds
    static final String user = "root";
    static final String pass = "alpine";
    
    // DB Connection
    Connection dbConnection;
    Statement dbStatement;

    // Query Result
    ResultSet result;

    // User Type
    String user_type;

    public DBLogic() {
        init();
    }

    public void init() {
      startSQLServer();
    }

    public void startSQLServer() {
        try {
            Server server1 = Server.createTcpServer().start();
            System.out.println("Starting SQL Server...");
            System.out.println(server1.getStatus());
            Server server2 = Server.createWebServer("-webPort", "8082", "-tcpAllowOthers");
            server2.start();
            System.out.println(server2.getStatus());
            System.out.println(server2.getURL());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertTable(String sqlQuery) {
        try {
            System.out.println("Attempting to connect to the db");
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database");
            dbConnection = DriverManager.getConnection(DB_URL, user, pass);
            dbStatement = dbConnection.createStatement();
            dbStatement.execute(sqlQuery);
            dbStatement.close();
            dbConnection.close();

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (dbStatement != null) {
                    dbStatement.close();
                }
            } catch (SQLException se2) {

            }
        }
    }

    public boolean verifyUser(String username, String password) throws SQLException, ClassNotFoundException {
        String first = "";
        String last = "";
        System.out.println("Attempting to connect to the db");
        dbConnection = DriverManager.getConnection(DB_URL, user, pass);
        System.out.println("Connected to db");
        dbStatement = dbConnection.createStatement();
        String sql1 = "SELECT * FROM GARAGE.USER";
        result = dbStatement.executeQuery(sql1);
        while (result.next()) {
         // Retrieve by column name
           first = result.getString("USERNAME");
           last = result.getString("PASSWORD");
           user_type = result.getString("ROLE");
        }
        result.close();
        dbStatement.close();
        dbConnection.close();
        if ((username.equals(first)) && (password.equals(last))) {
           return true;
        } else {
          return false;
        }
    }

    public String getUser_type() {
        return user_type;
    }

    public ResultSet readFromTable(String sqlQuery) throws SQLException {
        dbConnection = DriverManager.getConnection(DB_URL, user, pass);
        dbStatement = dbConnection.createStatement();
        result = dbStatement.executeQuery(sqlQuery);
        dbStatement.close();
        dbConnection.close();
        return result;
    }

}


