package TwentyThreeProductions.Controller.Jobs.NewJob;

import TwentyThreeProductions.Domain.Vehicle;
import TwentyThreeProductions.Domain.Job;
import TwentyThreeProductions.Domain.User;
import TwentyThreeProductions.Model.*;
import TwentyThreeProductions.Model.Database.DAO.VehicleDAO;
import TwentyThreeProductions.Model.Database.DAO.JobDAO;
import TwentyThreeProductions.Model.Database.DAO.UserDAO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.sql.Date;


public class NewJobNewVehicleController {

    private SceneSwitch sceneSwitch;

    private CustomerReference customerReference;

    private Job job;

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
    private ToggleGroup type;

    @FXML
    private JFXRadioButton motRadio;

    @FXML
    private JFXRadioButton repairsRadio;

    @FXML
    private JFXRadioButton annualServiceRadio;

    @FXML
    private JFXRadioButton firstMotRadio;

    @FXML
    private Label lastMotDateHeader;

    @FXML
    private JFXTextField lastMotDateField;

    // Once this is pressed, the other job type buttons are forcibly unpressed.
    @FXML
    void motRadioSelected(ActionEvent event) {
        annualServiceRadio.setSelected(false);
        repairsRadio.setSelected(false);
    }

    // Once this is pressed, the other job type buttons are forcibly unpressed.
    @FXML
    void annualServiceRadioSelected(ActionEvent event) {
        motRadio.setSelected(false);
        repairsRadio.setSelected(false);
    }

    // Once this is pressed, the other job type buttons are forcibly unpressed.
    @FXML
    void repairsRadioSelected(ActionEvent event) {
        motRadio.setSelected(false);
        annualServiceRadio.setSelected(false);
    }

    // If this button was selected when it was clicked, it will set the field for getting date of the last MoT to null and
    // make it and the label invisible. Otherwise, it will set the label and field to visible again.
    @FXML
    void firstMotRadioSelected(ActionEvent event) {
        if(firstMotRadio.isSelected()) {
            lastMotDateHeader.setVisible(false);
            lastMotDateField.setText(null);
            lastMotDateField.setVisible(false);
        }
        else {
            lastMotDateHeader.setVisible(true);
            lastMotDateField.setVisible(true);
        }
    }

    @FXML
    void addVehicleBtnClicked(ActionEvent event) {
        // If any of the fields are left blank, the system produces an alert stating that to the user.
        if (registrationField.getText().isEmpty() || nameField.getText().isEmpty() ||
                colourField.getText().isEmpty() || vehicleDateField.getText().isEmpty()) {
            SystemAlert systemAlert = new SystemAlert(newJobNewVehicleStackPane,
                    "Failure", "Blank field(s)");
        } else {
            // Otherwise, the system creates new objects to hold the new vehicle and job.
            Vehicle vehicle = new Vehicle();
            VehicleDAO vehicleDAO = new VehicleDAO();
            JobDAO jobDAO = new JobDAO();
            UserDAO userDAO = new UserDAO();

            // The system checks if the Registration Number already exists within the database and, if so, it produces an
            // alert informing the user of this.
            boolean doesRegNoExist = false;
            for (Vehicle c : vehicleDAO.getAll()) {
                if (registrationField.getText().equals(vehicle.getRegNo())) {
                    doesRegNoExist = true;
                    break;
                }
            }
            if (doesRegNoExist) {
                SystemAlert systemAlert = new SystemAlert(newJobNewVehicleStackPane,
                        "Failure", "Registration ID already exists");
            } else {
                try {
                    // Otherwise, the system will set the values of the new vehicle to the ones inputted by the user and
                    // proceed to add that vehicle to the system database, setting the last MoT date to null if they have
                    // not yet had their first MoT.
                    vehicle.setName(nameField.getText());
                    vehicle.setColour(colourField.getText());
                    vehicle.setRegNo(registrationField.getText());
                    vehicle.setVehicleDate(Date.valueOf(vehicleDateField.getText()));
                    if(firstMotRadio.isSelected()) {
                        vehicle.setLastMOT(null);
                    }
                    else {
                        vehicle.setLastMOT(Date.valueOf(lastMotDateField.getText()));
                    }
                    vehicle.setCustomerID(customerReference.getCustomer().getCustomerID());
                    vehicleDAO.save(vehicle);

                    // After this, the vehicle ID for the vehicle is retrieved and the ID for the job is generated.
                    for(Vehicle v: vehicleDAO.getAll()) {
                        vehicle.setRegistrationID(v.getRegistrationID());
                    }
                    int jobID = 1;
                    if(!(jobDAO.getAll().isEmpty())) {
                        for (Job j : jobDAO.getAll()) {
                            jobID++;
                        }
                    }
                    job.setJobID(jobID);

                    // After that is completed, the system checks to see if there is a mechanic available for the job, either through
                    // the currently logged in user label or through the system database. If there are no mechanics in the system, an
                    // alert will pop up telling the user this. Otherwise, the mechanic's username is attached to the job object.
                    boolean isMechanicTableEmpty = false;
                    if (usertypeLbl.getText().equals("Mechanic") || usertypeLbl.getText().equals("Foreperson")) {
                        job.setUsername(usernameLbl.getText().substring(8));
                    } else if (userDAO.getMechanics().isEmpty()) {
                        isMechanicTableEmpty = true;
                    } else {
                        for (User u : userDAO.getMechanics()) {
                            job.setUsername(u.getUsername());
                            break;
                        }
                    }
                    if (isMechanicTableEmpty) {
                        SystemAlert systemAlert = new SystemAlert(newJobNewVehicleStackPane,
                                "Failure", "No mechanic in system database");
                    } else {

                        // After this, the customer ID is set to the customer that was selected earlier, the registration ID
                        // for the vehicle is set to the one that was retrieved, the date booked is set to the current date
                        // and the statuses are set to the appropriate values.
                        job.setCustomerID(Integer.parseInt(customerReference.getCustomer().getCustomerID()));
                        job.setRegistrationID(String.valueOf(vehicle.getRegistrationID()));
                        java.util.Date currentDate = new java.util.Date();
                        java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
                        job.setDateBookedIn(sqlDate);
                        job.setStatus("Pending");
                        job.setPaidFor("False");
                        job.setChecked("False");

                        // Once that is completed, the job description is set depending on what job type was selected by the user.
                        // If no job type is selected, the user is alerted of this.
                        if (motRadio.isSelected()) {
                            job.setDescription("MoT job");
                        } else if (annualServiceRadio.isSelected()) {
                            job.setDescription("Annual Service job");
                        } else if (repairsRadio.isSelected()) {
                            job.setDescription("Repairs job");
                        }
                        if (job.getDescription().isEmpty()) {
                            SystemAlert systemAlert = new SystemAlert(newJobNewVehicleStackPane,
                                    "Failure", "No job type selected");
                        } else {

                            // After this, the job is saved in the database and the user is alerted of this, as well as all
                            // the inputted values being cleared for later use.
                            jobDAO.save(job);
                            SystemAlert systemAlert = new SystemAlert(newJobNewVehicleStackPane,
                                    "Success", "Job added for new vehicle");
                            clearInputs();
                        }
                    }
                } catch (Exception e) {
                    // If the format for dates is incorrect, an alert stating as much is generated.
                    SystemAlert systemAlert = new SystemAlert(newJobNewVehicleStackPane,
                            "Failure", "Invalid input type");
                }
            }
        }
    }

    // The inputs are cleared and the system returns to the previous screen.
    @FXML
    void backBtnClicked(ActionEvent event) {
        clearInputs();
        sceneSwitch.switchScene(NavigationModel.NEW_JOB_CAR_MENU_ID);
    }

    // This function is called up when the page is first opened, and it adds the scene to the list of currently
    // active scenes as well as changing the labels for the username and type with the currently logged in user,
    // and then finally it initialises the job object for storing the job and clears the current inputs, as well
    // as gets the instance of the static class for returning the customer.
    public void initialize() {
        System.out.println("AddVehicletoCustomer controller init");
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(newJobNewVehicleStackPane, NavigationModel.NEW_JOB_NEW_VEHICLE_ID);
        usernameLbl.setText(DBLogic.getDBInstance().getUsername());
        usertypeLbl.setText(DBLogic.getDBInstance().getUser_type());
        customerReference = customerReference.getInstance();
        job = new Job();
        customerNameLbl.setText("Name: " + customerReference.getCustomer().getFirstName() + " " + customerReference.getCustomer().getLastName());
        clearInputs();
    }

    // The fields for inputs are all set to null values
    public void clearInputs() {
        registrationField.clear();
        nameField.clear();
        colourField.clear();
        vehicleDateField.clear();
        lastMotDateField.clear();
    }
}

