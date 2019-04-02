package TwentyThreeProductions.Model.Database.DAO;

import TwentyThreeProductions.Domain.Discount;
import TwentyThreeProductions.Model.Database.DBConnectivity;
import TwentyThreeProductions.Model.Database.Interfaces.DBConnectivityInterface;
import TwentyThreeProductions.Model.Database.Interfaces.IDiscount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DiscountDAO implements IDiscount {

    private DBConnectivityInterface dbConnectivity;
    private Connection connection;

    public DiscountDAO() {
        dbConnectivity = new DBConnectivity();
    }


    @Override
    public Discount getDiscount(int customerID) {
        Discount discount = new Discount();
        PreparedStatement statement;
        String query = "SELECT * FROM GARAGE.DISCOUNT WHERE CUSTOMERCUSTOMERID=?";
        ResultSet resultSet;
        connection = dbConnectivity.connection(connection);
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, Integer.toString(customerID));
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                discount.setCustomerID(customerID);
                discount.setBand(resultSet.getString("BAND"));
                discount.setType(resultSet.getString("TYPE"));
                discount.setValue(resultSet.getDouble("VALUE"));
                discount.setVatValue(resultSet.getDouble("VATVALUE"));
                discount.setPartValue(resultSet.getDouble("PARTVALUE"));
                discount.setBand1range1(resultSet.getDouble("BANDONERANGEONE"));
                discount.setBand1range2(resultSet.getDouble("BANDONERANGETWO"));
                discount.setBand1percent(resultSet.getDouble("BANDONEPERCENT"));
                discount.setBand2range1(resultSet.getDouble("BANDTWORANGEONE"));
                discount.setBand2range2(resultSet.getDouble("BANDTWORANGETWO"));
                discount.setBand2percent(resultSet.getDouble("BANDTWOPERCENT"));
                discount.setBand3range1(resultSet.getDouble("BANDTHREERANGEONE"));
                discount.setBand3range2(resultSet.getDouble("BANDTHREERANGETWO"));
                discount.setBand3percent(resultSet.getDouble("BANDTHREEPERCENT"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return discount;
    }

    @Override
    public void save(Discount discount) {
        String args[] = {discount.getType(), discount.getBand(), Double.toString(discount.getValue()), Double.toString(discount.getVatValue()), Double.toString(discount.getPartValue()), Integer.toString(discount.getCustomerID()), Double.toString(discount.getBand1range1()), Double.toString(discount.getBand1range2()), Double.toString(discount.getBand1percent()), Double.toString(discount.getBand2range1()), Double.toString(discount.getBand2range2()), Double.toString(discount.getBand2percent()), Double.toString(discount.getBand3range1()), Double.toString(discount.getBand3range2()), Double.toString(discount.getBand3percent())};
        String query = "INSERT INTO GARAGE.DISCOUNT (TYPE, BAND, VALUE, VATVALUE, PARTVALUE, CUSTOMERCUSTOMERID, BANDONERANGEONE, BANDONERANGETWO, BANDONEPERCENT, BANDTWORANGEONE, BANDTWORANGETWO, BANDTWOPERCENT, BANDTHREERANGEONE, BANDTHREERANGETWO, BANDTHREEPERCENT)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?)";
        connection = dbConnectivity.connection(connection);
        dbConnectivity.writePrepared(query, connection, args);

    }

    @Override
    public void update(Discount discount) {
        String updateQuery = "UPDATE GARAGE.DISCOUNT SET TYPE=?, BAND=?, VALUE=?, VATVALUE=?, PARTVALUE=?, BANDONERANGEONE=?, BANDONERANGETWO=?, BANDONEPERCENT=?, BANDTWORANGEONE=?, BANDTWORANGETWO=?, BANDTWOPERCENT=?, BANDTHREERANGEONE=?, BANDTHREERANGETWO=?, BANDTHREEPERCENT=?";
        connection = dbConnectivity.connection(connection);
        String args[] = {discount.getType(), discount.getBand(), Double.toString(discount.getValue()), Double.toString(discount.getVatValue()), Double.toString(discount.getPartValue()), Double.toString(discount.getBand1range1()), Double.toString(discount.getBand1range2()), Double.toString(discount.getBand1percent()), Double.toString(discount.getBand2range1()), Double.toString(discount.getBand2range2()), Double.toString(discount.getBand2percent()), Double.toString(discount.getBand3range1()), Double.toString(discount.getBand3range2()), Double.toString(discount.getBand3percent())};
        dbConnectivity.writePrepared(updateQuery, connection, args);
        System.out.println("Successfully updated ");
    }

    @Override
    public void delete(int customerID) {
        String deleteCustomer = "DELETE FROM GARAGE.DISCOUNT WHERE CUSTOMERCUSTOMERID=?";
        connection = dbConnectivity.connection(connection);
        String args[] = {Integer.toString(customerID)};
        dbConnectivity.writePrepared(deleteCustomer, connection, args);
    }
}
