package TwentyThreeProductions.Model.Database;

public class DBHelper {

    // JDBC Drivers
    public static final String JDBC_DRIVER = "org.h2.Driver";
    public static final String DB_URL = "jdbc:h2:tcp://localhost/~/test";

    // DB Creds
    public static final String user = "root";
    public static final String pass = "alpine";

    public static String OS = null;
    public static String getOSName() {
        if (OS == null) {
            OS = System.getProperty("os.name");
        }
        return OS;
    }


}
