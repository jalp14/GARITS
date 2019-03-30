package TwentyThreeProductions.Controller.Jobs.NewJob;

import TwentyThreeProductions.Domain.Vehicle;
import TwentyThreeProductions.Domain.Job;
import TwentyThreeProductions.Domain.Manufacturer;
import TwentyThreeProductions.Domain.Mechanic;
import TwentyThreeProductions.Model.CustomerReference;
import TwentyThreeProductions.Model.Database.DAO.VehicleDAO;
import TwentyThreeProductions.Model.Database.DAO.JobDAO;
import TwentyThreeProductions.Model.Database.DAO.ManufacturerDAO;
import TwentyThreeProductions.Model.Database.DAO.MechanicDAO;
import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import TwentyThreeProductions.Model.SystemAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.sql.Date;


public class NewJobNewVehicleController {

    private SceneSwitch sceneSwitch;

    private CustomerReference customerReference;

    @FXML
    private StackPane newJobNewVehicleStackPane;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text usertypeLbl;

    @FXML
    private Label welcomeMessage;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton addVehicleBtn;

    @FXML
    private Label customerNameLbl;

    @FXML
    private Label regHeading;

    @FXML
    private Label nameHeading;

    @FXML
    private Label colourHeading;

    @FXML
    private Label vehicleDateHeading;

    @FXML
    private JFXTextField registrationField;

    @FXML
    private JFXTextField nameField;

    @FXML
    private JFXTextField colourField;

    @FXML
    private JFXTextField vehicleDateField;

    @FXML
    void addVehicleBtnClicked(ActionEvent event) {
        if (registrationField.getText().isEmpty() || nameField.getText().isEmpty() ||
                colourField.getText().isEmpty() || vehicleDateField.getText().isEmpty()) {
            SystemAlert systemAlert = new SystemAlert(newJobNewVehicleStackPane,
                    "Failure", "Blank field(s)");
        } else {
            Vehicle vehicle = new Vehicle();
            VehicleDAO vehicleDAO = new VehicleDAO();
            Job job = new Job();
            JobDAO jobDAO = new JobDAO();
            MechanicDAO mechanicDAO = new MechanicDAO();
            boolean doesRegistrationIDExist = false;
            for (Vehicle c : vehicleDAO.getAll()) {
                if (registrationField.getText().equals(vehicle.getRegistrationID())) {
                    doesRegistrationIDExist = true;
                    break;
                }
            }
            if (doesRegistrationIDExist) {
                SystemAlert systemAlert = new SystemAlert(newJobNewVehicleStackPane,
                        "Failure", "Registration ID already exists");
            } else {
                try {
                    vehicle.setName(nameField.getText());
                    vehicle.setColour(colourField.getText());
                    vehicle.setRegistrationNumber(registrationField.getText());
                    vehicle.setVehicleDate(Date.valueOf(vehicleDateField.getText()));
                    vehicle.setCustomerID(Integer.parseInt(customerReference.getCustomer().getCustomerID()));
                    vehicleDAO.save(vehicle);
                    int jobID = 1;
                    if(!(jobDAO.getAll().isEmpty())) {
                        for (Job j : jobDAO.getAll()) {
                            jobID++;
                        }
                    }
                    job.setJobID(jobID);
                    boolean isMechanicTableEmpty = false;
                    if (usertypeLbl.getText().equals("Mechanic") || usertypeLbl.getText().equals("Foreperson")) {
                        job.setUsername(usernameLbl.getText().substring(8));
                    } else if (mechanicDAO.getAll().isEmpty()) {
                        isMechanicTableEmpty = true;
                    } else {
                        for (Mechanic m : mechanicDAO.getAll()) {
                            job.setUsername(m.getUsername());
                            break;
                        }
                    }
                    if (isMechanicTableEmpty) {
                        SystemAlert systemAlert = new SystemAlert(newJobNewVehicleStackPane,
                                "Failure", "No mechanic in system database");
                    } else {
                        job.setCustomerID(Integer.parseInt(customerReference.getCustomer().getCustomerID()));
                        job.setRegistrationID(String.valueOf(vehicle.getRegistrationID()));
                        java.util.Date currentDate = new java.util.Date();
                        java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
                        job.setDateBookedIn(sqlDate);
                        job.setDescription("Work done on vehicle");
                        job.setStatus("Pending");
                        job.setPaidFor("False");
                        jobDAO.save(job);
                        SystemAlert systemAlert = new SystemAlert(newJobNewVehicleStackPane,
                                "Success", "Job added for new vehicle");
                        clearInputs();
                    }
                } catch (Exception e) {
                    SystemAlert systemAlert = new SystemAlert(newJobNewVehicleStackPane,
                            "Failure", "Invalid input type");
                }
            }
        }
    }

    @FXML
    void backBtnClicked(ActionEvent event) {
        clearInputs();
        sceneSwitch.switchScene(NavigationModel.NEW_JOB_CAR_MENU_ID);
    }

    public void initialize() {
        System.out.println("AddVehicletoCustomer controller init");
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(newJobNewVehicleStackPane, NavigationModel.NEW_JOB_NEW_VEHICLE_ID);
        customerReference = customerReference.getInstance();
        customerNameLbl.setText("Name: " + customerReference.getCustomer().getFirstName() + " " + customerReference.getCustomer().getLastName());
        clearInputs();
    }

    public void clearInputs() {
        registrationField.clear();
        nameField.clear();
        colourField.clear();
        vehicleDateField.clear();
    }
}

