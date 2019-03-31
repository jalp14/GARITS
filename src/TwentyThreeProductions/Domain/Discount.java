package TwentyThreeProductions.Domain;

public class Discount {

    private int discountID;
    private String type;
    private String band;
    private double value;
    private int customerID;
    private double vatValue;
    private double partValue;

    public Discount() {
    }

    public int getDiscountID() {
        return discountID;
    }

    public void setDiscountID(int discountID) {
        this.discountID = discountID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getVatValue() {
        return vatValue;
    }

    public void setVatValue(double vatValue) {
        this.vatValue = vatValue;
    }

    public double getPartValue() {
        return partValue;
    }

    public void setPartValue(double partValue) {
        this.partValue = partValue;
    }

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
}
