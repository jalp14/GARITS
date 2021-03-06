package TwentyThreeProductions.Domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Customer {

    private String customerID;
    private String firstName;
    private String lastName;
    private String customerType;
    private String customerHouseName;
    private String customerStreetName;
    private String customerBuildingName;
    private String customerPostcode;
    private String customerPhone;
    private String customerEmail;
    private boolean latePayment;
    private String customerCity;
    private Date currentDate;
    private ArrayList<Vehicle> vehicles;
    private ArrayList<Invoice> invoices;
    private ArrayList<Job> jobs;
    private ArrayList<Payment> payments;

    public Customer(){}
    // missing customerID and Vechicle from the constructor
    public Customer(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setCustomerPostcode(String customerPostcode) {
        this.customerPostcode = customerPostcode;
    }

    public String getCustomerPostcode() {
        return customerPostcode;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setLatePayment(boolean latePayment) {
        this.latePayment = latePayment;
    }

    public String isLatePayment() {
        String answer;
        if (latePayment) {
            answer = "TRUE";
        } else {
            answer = "FALSE";
        }
        return answer;
    }

    public boolean getLatePayment() {
        if (isLatePayment().equals("TRUE")) {
            return true;
        } else {
            return false;
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerHouseName() {
        return customerHouseName;
    }

    public void setCustomerHouseName(String customerHouseName) {
        this.customerHouseName = customerHouseName;
    }

    public String getCustomerStreetName() {
        return customerStreetName;
    }

    public void setCustomerStreetName(String customerStreetName) {
        this.customerStreetName = customerStreetName;
    }

    public String getCustomerBuildingName() {
        return customerBuildingName;
    }

    public void setCustomerBuildingName(String customerBuildingName) {
        this.customerBuildingName = customerBuildingName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(ArrayList<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(ArrayList<Invoice> invoices) {
        this.invoices = invoices;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(ArrayList<Job> jobs) {
        this.jobs = jobs;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(ArrayList<Payment> payments) {
        this.payments = payments;
    }
}
