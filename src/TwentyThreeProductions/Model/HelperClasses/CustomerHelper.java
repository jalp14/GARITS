package TwentyThreeProductions.Model.HelperClasses;

import TwentyThreeProductions.Domain.Discount;

public class CustomerHelper {

    private static CustomerHelper customerHelper = null;
    private Discount discount;
    private int currentCustomerID;

    public static String DISCOUNT_FIXED_NAME = "FIXED";
    public static String DISCOUNT_FLEXIBLE_NAME = "FLEXIBLE";
    public static String DISCOUNT_VARIABLE_NAME = "VARIABLE";

    private CustomerHelper() {}

    public static CustomerHelper getInstance() {
        if (customerHelper == null) {
            customerHelper = new CustomerHelper();
        }
        return customerHelper;
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

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public Discount getDiscount() {
        return discount;
    }

    public int getCurrentCustomerID() {
        return currentCustomerID;
    }

    public void setCurrentCustomerID(int currentCustomerID) {
        this.currentCustomerID = currentCustomerID;
    }
}
