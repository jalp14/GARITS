package TwentyThreeProductions.Model.Database;

import TwentyThreeProductions.Model.Database.Interfaces.DBConnectivityInterface;
import org.h2.tools.Server;

import java.sql.*;

public class DBConnectivity implements DBConnectivityInterface {
/////////////////////////// Provides a generic interface to connect to the database and perform CRUD operations \\\\\\\\\\\\\\\\\\\\\\\\

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
        PreparedStatement statement;
        connection = connection(connection);
        try {
            statement = connection.prepareStatement(sql);
            statement.execute();
            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        return false;
    }

    @Override
    public boolean writePrepared(String sql, Connection connection, String[] args) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(sql);
            if (args.length <= 0) {
                System.out.println("No need to use writePrepared");
            } else {
                for (int i = 0; i < args.length; i++) {
                    statement.setString(i + 1, args[i]);
                }
                statement.execute();
                connection.commit();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
           // closeConnection(connection);
        }
        return false;
    }

    @Override
    public ResultSet queryPrepared(String sql, Connection connection, String[] args) {
        PreparedStatement statement;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            if (args.length <= 0) {
                System.out.println("No need to user updatePrepared");
            } else {
                for (int j = 0; j < args.length; j++) {
                    statement.setString(j + 1, args[j]);
                }
                resultSet = statement.executeQuery();
                connection.commit();
                return resultSet;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    @Override
    public ResultSet read(String sql, Connection connection) {
        PreparedStatement statement;
        connection = connection(connection);
        try {
            statement = connection.prepareStatement(sql);
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    @Override
    public Connection connection(Connection connection) {
        try {
            Class.forName(DBHelper.JDBC_DRIVER);
            connection = DriverManager.getConnection(DBHelper.DB_URL, DBHelper.user, DBHelper.pass);
            connection.setAutoCommit(false);
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
