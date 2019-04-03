package TwentyThreeProductions.Model.HelperClasses;

import TwentyThreeProductions.Domain.Discount;
import TwentyThreeProductions.Domain.Vehicle;
import TwentyThreeProductions.Model.NavigationModel;

public class CustomerHelper {

    private static CustomerHelper customerHelper = null;
    private Discount discount;
    private int currentCustomerID;
    private boolean isCustomerCasual = false;
    private Vehicle vehicle;

    public static String DISCOUNT_FIXED_NAME = "FIXED";
    public static String DISCOUNT_FLEXIBLE_NAME = "FLEXIBLE";
    public static String DISCOUNT_VARIABLE_NAME = "VARIABLE";

    public String lastCall = "";

    private int i;

    public String getLastCall() {
        return lastCall;
    }

    public void setLastCall(String lastCall) {
        this.lastCall = lastCall;
    }

    private CustomerHelper() {}

    public static CustomerHelper getInstance() {
        if (customerHelper == null) {
            customerHelper = new CustomerHelper();
        }
        return customerHelper;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Double getRate(int i) {
        double discountRate = 0.0;
        if (i == 1) {
            discountRate = 1.0;
        } else if (i == 2) {
            discountRate = 2.0;
        } else if (i == 3) {
            discountRate =  3.0;
        }
        return discountRate;
    }

    public int getBandNo(String bandName) {
        int answer = 0;
        if (bandName.equals("Band 1")) {
            answer = 1;
        } else if (bandName.equals("Band 2")) {
            answer = 2;
        } else if (bandName.equals("Band 3")){
            answer = 3;
        }
        return answer;
    }

    public boolean isCustomerCasual() {
        return isCustomerCasual;
    }


    public void setCustomerCasual(boolean customerCasual) {
        isCustomerCasual = customerCasual;
    }

    public void setDiscount(Discount discount, int i) {
        this.discount = discount;
        this.i = i;
    }

    public Discount getDiscount() {
        return discount;
    }

    public int getI() {
        return i;
    }

    public int getCurrentCustomerID() {
        return currentCustomerID;
    }

    public void setCurrentCustomerID(int currentCustomerID) {
        this.currentCustomerID = currentCustomerID;
    }
}
