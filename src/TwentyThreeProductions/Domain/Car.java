package TwentyThreeProductions.Domain;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class Car extends RecursiveTreeObject<Car> {

    private String registrationID;
    private String customerID;
    private String manufacturerID;
    private String make;
    private String model;
    private String engSerial;
    private String chassisNumber;
    private String colour;

    public Car(){}

    public String getRegistrationID() {
        return registrationID;
    }

    public void setRegistrationID(String registrationID) {
        this.registrationID = registrationID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getManufacturerID() {
        return manufacturerID;
    }

    public void setManufacturerID(String manufacturerID) {
        this.manufacturerID = manufacturerID;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getEngSerial() {
        return engSerial;
    }

    public void setEngSerial(String engSerial) {
        this.engSerial = engSerial;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }
}
