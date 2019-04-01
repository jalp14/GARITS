package TwentyThreeProductions.Model;

import TwentyThreeProductions.Domain.Customer;

public class CustomerReference {

    Customer customer;

    private static CustomerReference customerReference = null;

    private CustomerReference() {}

    public static CustomerReference getInstance() {
        if(customerReference == null) {
            customerReference = new CustomerReference();
        }
        return customerReference;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
