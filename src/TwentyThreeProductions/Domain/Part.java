package TwentyThreeProductions.Domain;

import java.util.List;

public class Part {

    private String partID;
    private int manufacturerID;
    private String name;
    private String vehicleType;
    private String year;
    private float price;
    private int stockLevel;
    private int thresholdLevel;
    private String desc;
    private List<PartJob> partJobs;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStockLevel() {
        return stockLevel;
    }

    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }

    public int getThresholdLevel() {
        return thresholdLevel;
    }

    public void setThresholdLevel(int thresholdLevel) {
        this.thresholdLevel = thresholdLevel;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<PartJob> getPartJobs() {
        return partJobs;
    }

    public void setPartJobs(List<PartJob> partJobs) {
        this.partJobs = partJobs;
    }

    public List<OrderPart> getOrderParts() {
        return orderParts;
    }

    public void setOrderParts(List<OrderPart> orderParts) {
        this.orderParts = orderParts;
    }
}
