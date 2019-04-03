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

    @FXML
    void motRadioSelected(ActionEvent event) {
        annualServiceRadio.setSelected(false);
        repairsRadio.setSelected(false);
    }

    @FXML
    void annualServiceRadioSelected(ActionEvent event) {
        motRadio.setSelected(false);
        repairsRadio.setSelected(false);
    }

    @FXML
    void repairsRadioSelected(ActionEvent event) {
        motRadio.setSelected(false);
        annualServiceRadio.setSelected(false);
    }

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
            } else {
                job.setCustomerID(Integer.parseInt(customer.getCustomerID()));
                job.setRegistrationID(String.valueOf(vehicle.getRegistrationID()));
                java.util.Date currentDate = new java.util.Date();
                java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
                job.setDateBookedIn(sqlDate);
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

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(newJobExistingVehicleStackPane, NavigationModel.NEW_JOB_EXISTING_VEHICLE_ID);
        usernameLbl.setText(DBLogic.getDBInstance().getUsername());
        usertypeLbl.setText(DBLogic.getDBInstance().getUser_type());
        customerReference = CustomerReference.getInstance();
        vehicleHashMap = new HashMap<>();
        refreshList();
    }

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
