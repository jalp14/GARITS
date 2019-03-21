package TwentyThreeProductions.Model.HelperClasses;

import TwentyThreeProductions.Domain.Car;

public class CustomerHelper {

    private CustomerHelper customerHelper = null;

    private CustomerHelper() {}

    public CustomerHelper getInstance() {
        if (customerHelper == null) {
            customerHelper = new CustomerHelper();
        }
        return customerHelper;
    }

    public void setCar(Car car) {

    }

    public Car getCar() {
        return null;
    }

    public void setDiscount(double discount) {

    }

    public double getDiscount() {
        return 0;
    }


}
