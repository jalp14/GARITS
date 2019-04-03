package TwentyThreeProductions.Model.Database.DAO;

import TwentyThreeProductions.Domain.Payment;
import TwentyThreeProductions.Model.Database.DBConnectivity;
import TwentyThreeProductions.Model.Database.Interfaces.IPart;
import TwentyThreeProductions.Model.Database.Interfaces.IPayment;

import java.sql.*;
import java.util.ArrayList;

public class PaymentDAO implements IPayment {

    private ArrayList<Payment> payments;
    private Connection connection;
    private DBConnectivity dbConnectivity;

    public PaymentDAO() {
        payments = new ArrayList<>();
        dbConnectivity = new DBConnectivity();
    }

    @Override
    public ArrayList<Payment> getAll() {
        Statement statement;
        String query = "SELECT * FROM GARAGE.PAYMENT";
        ResultSet result;
        connection = dbConnectivity.connection(connection);
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            while (result.next()) {
                Payment payment = new Payment();
                payment.setPaymentID(result.getInt("PAYMENTID"));
                payment.setJobID(result.getInt("JOBID"));
                payment.setCustomerID(result.getInt("CUSTOMERID"));
                payment.setType(result.getString("TYPE"));
                payment.setDate(result.getDate("Date"));
                payment.setAmount(result.getFloat("AMOUNT"));
                payment.setCardNumber(result.getInt("CARDNUMBER"));
                payment.setCvv(result.getInt("CVV"));
                payment.setCardName(result.getString("CARDNAME"));
                payment.setExpiryDate(result.getString("EXPIRYDATE"));
                payments.add(payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnectivity.closeConnection(connection);
        }
        return payments;
    }

    @Override
    public void save(Payment payment) {
        String[] args = {String.valueOf(payment.getJobID()), String.valueOf(payment.getCustomerID()), payment.getType(), String.valueOf(payment.getDate()),
                String.valueOf(payment.getAmount()), String.valueOf(payment.getCardNumber()), String.valueOf(payment.getCvv()),
                payment.getCardName(), payment.getExpiryDate()};
        String saveQuery = "INSERT INTO GARAGE.PAYMENT (JOBID, CUSTOMERID, TYPE, \"Date\", AMOUNT, CARDNUMBER, CVV, CARDNAME, EXPIRYDATE)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        connection = dbConnectivity.connection(connection);
        dbConnectivity.writePrepared(saveQuery, connection, args);
    }

    @Override
    public void saveCash(Payment payment) {
        String[] args = {String.valueOf(payment.getJobID()), String.valueOf(payment.getCustomerID()), payment.getType(), String.valueOf(payment.getDate()),
                String.valueOf(payment.getAmount())};
        String saveQuery = "INSERT INTO GARAGE.PAYMENT (JOBID, CUSTOMERID, TYPE, \"Date\", AMOUNT)\n" +
                "VALUES (?, ?, ?, ?, ?)";
        connection = dbConnectivity.connection(connection);
        dbConnectivity.writePrepared(saveQuery, connection, args);
    }

    @Override
    public void update(Payment payment) {

    }

    @Override
    public void delete(Payment payment) {

    }
}
