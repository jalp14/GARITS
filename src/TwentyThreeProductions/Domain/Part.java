package TwentyThreeProductions.Domain;

import java.util.List;

public class Part {

    private String partID;
    private int manufacturerID;
    private String name;
    private String vehicleType;
    private String year;
    private String price;
    private String stockLevel;
    private List<JobPart> jobParts;
    private List<OrderPart> orderParts;

    public Part(){}

    public String getPartID() {
        return partID;
    }

    public void setPartID(String partID) {
        this.partID = partID;
    }

    public int getManufacturerID() {
        return manufacturerID;
    }

    public void setManufacturerID(int manufacturerID) {
        this.manufacturerID = manufacturerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStockLevel() {
        return stockLevel;
    }

    public void setStockLevel(String stockLevel) {
        this.stockLevel = stockLevel;
    }

    public List<JobPart> getJobParts() {
        return jobParts;
    }

    public void setJobParts(List<JobPart> jobParts) {
        this.jobParts = jobParts;
    }

    public List<OrderPart> getOrderParts() {
        return orderParts;
    }

    public void setOrderParts(List<OrderPart> orderParts) {
        this.orderParts = orderParts;
    }
}
