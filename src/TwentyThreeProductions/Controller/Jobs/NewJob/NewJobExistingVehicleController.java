package TwentyThreeProductions.Controller.Jobs.NewJob;

import TwentyThreeProductions.Domain.*;
import TwentyThreeProductions.Model.*;
import TwentyThreeProductions.Model.Database.DAO.UserDAO;
import TwentyThreeProductions.Model.Database.DAO.VehicleDAO;
import TwentyThreeProductions.Model.Database.DAO.JobDAO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.HashMap;

public class NewJobExistingVehicleController {

    private SceneSwitch sceneSwitch;

    private CustomerReference customerReference;

    private HashMap<String, Vehicle> vehicleHashMap;

    @FXML
    private StackPane newJobExistingVehicleStackPane;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text usertypeLbl;

    @FXML
    private Label welcomeMessage;

    @FXML
    private JFXButton backBtn;

    @FXML
    private Label customerNameLbl;

    @FXML
    private JFXButton nextBtn;

    @FXML
    private JFXListView<Label> customerVehicleList;

    @FXML
    private ToggleGroup type;

    @FXML
    private JFXRadioButton motRadio;

    @FXML
    private JFXRadioButton repairsRadio;

    @FXML
    private JFXRadioButton annualServiceRadio;

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

    // The system clears the currently selected vehicle, the list of vehicles, the hashmap for it and the search term before
    // refreshing the list and moving back to the previous page.
    @FXML
    void backBtnClicked(ActionEvent event) {
        customerVehicleList.getSelectionModel().select(null);
        customerVehicleList.getItems().clear();
        vehicleHashMap.clear();
        motRadio.setSelected(false);
        annualServiceRadio.setSelected(false);
        repairsRadio.setSelected(false);
        refreshList();
        sceneSwitch.switchScene(NavigationModel.NEW_JOB_CAR_MENU_ID);
    }

    // If the customer selection is empty, the system will produce an alert stating as much to the user. Otherwise, the
    // system stores the currently selected vehicle in an object for later use. It then creates a job object that will
    // be used to add a job to the system database, set with an appropriate job ID.
    @FXML
    void nextBtnClicked(ActionEvent event) {
        if(customerVehicleList.getSelectionModel().isEmpty()) {
            SystemAlert systemAlert = new SystemAlert(newJobExistingVehicleStackPane,
                    "Failure", "No vehicle selected");
        }
        else {
            Job job = new Job();
            Vehicle vehicle = vehicleHashMap.get(customerVehicleList.getSelectionModel().getSelectedItem().getText());
            Customer customer = customerReference.getCustomer();
            VehicleDAO vehicleDAO = new VehicleDAO();
            JobDAO jobDAO = new JobDAO();
            UserDAO userDAO = new UserDAO();
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
            if (usertypeLbl.getText().equals("MECHANIC") || usertypeLbl.getText().equals("FOREPERSON")) {
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
                SystemAlert systemAlert = new SystemAlert(newJobExistingVehicleStackPane,
                        "Failure", "No mechanic in system database");
            }
            // After this, the customer ID is set to the customer that was selected earlier, the vehicle ID was set to
            // the ID for the chosen vehicle and the date was set to the current date of the system.
            else {
                job.setCustomerID(Integer.parseInt(customer.getCustomerID()));
                job.setRegistrationID(String.valueOf(vehicle.getRegistrationID()));
                java.util.Date currentDate = new java.util.Date();
                java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
                job.setDateBookedIn(sqlDate);

                // Once that is completed, the job description is set depending on what job type was selected by the user.
                // If no job type is selected, the user is alerted of this.
                if(motRadio.isSelected()) {
                    job.setDescription("MoT job");
                }
                else if(annualServiceRadio.isSelected()) {
                    job.setDescription("Annual Service job");
                }
                else if(repairsRadio.isSelected()) {
                    job.setDescription("Repairs job");
                }
                if(job.getDescription().isEmpty()) {
                    SystemAlert systemAlert = new SystemAlert(newJobExistingVehicleStackPane,
                            "Failure", "No job type selected");
                }
                else {

                    // After this, the job statuses are set to the appropriate values for a new job, and then the job is
                    // saved to the system database. Once this is done, an alert stating that the job is created is generated
                    // and the currently selected vehicle, list of vehicles and hashmap associated with them are cleared
                    // and refreshed.
                    job.setStatus("Pending");
                    job.setPaidFor("False");
                    job.setChecked("False");
                    jobDAO.save(job);
                    SystemAlert systemAlert = new SystemAlert(newJobExistingVehicleStackPane,
                            "Success", "Added job for existing vehicle");
                    customerVehicleList.getSelectionModel().select(null);
                    customerVehicleList.getItems().clear();
                    vehicleHashMap.clear();
                    refreshList();
                }
            }
        }
    }

    // This function is called up when the page is first opened, and it adds the scene to the list of currently
    // active scenes as well as changing the labels for the username and type with the currently logged in user,
    // and then finally it initialises the hashmap for storing the vehicles and refresh the list of currently available
    // vehicles as well as gets the instance of the static class for returning the customer.
    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(newJobExistingVehicleStackPane, NavigationModel.NEW_JOB_EXISTING_VEHICLE_ID);
        usernameLbl.setText(DBLogic.getDBInstance().getUsername());
        usertypeLbl.setText(DBLogic.getDBInstance().getUser_type());
        customerReference = CustomerReference.getInstance();
        vehicleHashMap = new HashMap<>();
        refreshList();
    }

    // This function goes through every vehicle in the system database, assigns them to a label to be put in the list
    // of available items as well as in the hashmap with an appropriate string reference. It also generates a label for
    // the currently selected customer from the appropriate static class.
    public void refreshList() {
        VehicleDAO vehicleDAO = new VehicleDAO();
        Customer customer = customerReference.getCustomer();
        for(Vehicle v: vehicleDAO.getExistingVehicles(customer.getCustomerID())) {
            Label vehicleLabel = new Label("Registration ID: " + v.getRegistrationID() + " / Name: " + v.getName() + " / Reg No: " + v.getRegNo());
            vehicleHashMap.put(vehicleLabel.getText(), v);
            customerVehicleList.getItems().add(vehicleLabel);
        }
        customerNameLbl.setText("Name: " + customerReference.getCustomer().getFirstName() + " " + customerReference.getCustomer().getLastName());
    }
}
