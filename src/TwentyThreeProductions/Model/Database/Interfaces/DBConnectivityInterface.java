package TwentyThreeProductions.Model.Database.Interfaces;

import java.sql.Connection;
import java.sql.ResultSet;

public interface DBConnectivityInterface {
    boolean batchWrite(Connection connection);
    void addToBatch(String sql, Connection connection);
    void clearBatch();
    boolean write(String sql, Connection connection);
    ResultSet read(String sql, Connection connection);
    Connection connection(Connection connection);
    boolean closeConnection(Connection connection);
    boolean checkExists(String path);
}