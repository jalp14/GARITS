package TwentyThreeProductions.Model.HelperClasses;

import TwentyThreeProductions.Domain.Vehicle;

public class CustomerHelper {

    private CustomerHelper customerHelper = null;

    private CustomerHelper() {}

    public CustomerHelper getInstance() {
        if (customerHelper == null) {
            customerHelper = new CustomerHelper();
        }
        return customerHelper;
    }

    public void setVehicle(Vehicle vehicle) {

    }

    public Vehicle getVehicle() {
        return null;
    }

    public void setDiscount(double discount) {

    }

    public double getDiscount() {
        return 0;
    }


}
