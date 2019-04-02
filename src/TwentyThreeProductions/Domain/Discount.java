package TwentyThreeProductions.Domain;

public class Discount {

    private int discountID;
    private String type;
    private String band;
    private double value;
    private int customerID;
    private double vatValue;
    private double partValue;
    private double band1range1;
    private double band1range2;
    private double band1percent;
    private double band2range1;
    private double band2range2;
    private double band2percent;
    private double band3range1;
    private double band3range2;
    private double band3percent;

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

    public double getBand1range1() {
        return band1range1;
    }

    public void setBand1range1(double band1range1) {
        this.band1range1 = band1range1;
    }

    public double getBand1range2() {
        return band1range2;
    }

    public void setBand1range2(double band1range2) {
        this.band1range2 = band1range2;
    }

    public double getBand1percent() {
        return band1percent;
    }

    public void setBand1percent(double band1percent) {
        this.band1percent = band1percent;
    }

    public double getBand2range1() {
        return band2range1;
    }

    public void setBand2range1(double band2range1) {
        this.band2range1 = band2range1;
    }

    public double getBand2range2() {
        return band2range2;
    }

    public void setBand2range2(double band2range2) {
        this.band2range2 = band2range2;
    }

    public double getBand2percent() {
        return band2percent;
    }

    public void setBand2percent(double band2percent) {
        this.band2percent = band2percent;
    }

    public double getBand3range1() {
        return band3range1;
    }

    public void setBand3range1(double band3range1) {
        this.band3range1 = band3range1;
    }

    public double getBand3range2() {
        return band3range2;
    }

    public void setBand3range2(double band3range2) {
        this.band3range2 = band3range2;
    }

    public double getBand3percent() {
        return band3percent;
    }

    public void setBand3percent(double band3percent) {
        this.band3percent = band3percent;
    }
}
