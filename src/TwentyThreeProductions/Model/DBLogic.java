package TwentyThreeProductions.Model;

import java.sql.*;
import org.h2.tools.Server;

public class DBLogic {
///////////// Logic of the database for connection \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    // JDBC Drivers
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:tcp://localhost/~/test";

    // DB Creds
    static final String user = "root";
    static final String pass = "alpine";

    // User Inputs
    String username;
    String password;
    
    // DB Connection
    Connection dbConnection;
    Statement dbStatement;

    // Query Result
    ResultSet result;

    // User Type
    String user_type;

    // DB Instance
    private static DBLogic dbLogic = null;

    // Server
    Server sqlServer;
    Server webServer;

    private DBLogic() {}


    public static DBLogic getDBInstance() {
        if (dbLogic == null) {
            dbLogic = new DBLogic();
        }
        return dbLogic;
    }

    public String getUsername() {
        return username;
    }



    public void setLoginDetails(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean verifyUser() throws SQLException, ClassNotFoundException {
        Boolean accountFound = false;
        String dbUsername = "";
        String dbPassword = "";
        System.out.println("Attempting to connect to the db");
        dbConnection = DriverManager.getConnection(DB_URL, user, pass);
        System.out.println("Connected to db");
        dbStatement = dbConnection.createStatement();
        String sql1 = "SELECT * FROM GARAGE.\"User\"";
        result = dbStatement.executeQuery(sql1);
        while (result.next()) {
         // Retrieve by column name
           dbUsername = result.getString("USERNAME");
           dbPassword = result.getString("PASSWORD");
           if ((username.equals(dbUsername)) && (password.equals(dbPassword))) {
               System.out.println("User details matched");
               user_type = result.getString("ROLE");
               accountFound = true;
           }
        }
        result.close();
        dbStatement.close();
        dbConnection.close();
        System.out.println("User is " + username + " Role is " + user_type);
        return accountFound;
    }

    public String getUser_type() {
        return user_type;
    }


}


