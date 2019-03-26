package TwentyThreeProductions.Controller.Jobs.NewJob;

import TwentyThreeProductions.Domain.Car;
import TwentyThreeProductions.Domain.Customer;
import TwentyThreeProductions.Domain.Job;
import TwentyThreeProductions.Domain.Mechanic;
import TwentyThreeProductions.Model.CustomerReference;
import TwentyThreeProductions.Model.Database.DAO.CarDAO;
import TwentyThreeProductions.Model.Database.DAO.JobDAO;
import TwentyThreeProductions.Model.Database.DAO.MechanicDAO;
import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import TwentyThreeProductions.Model.SystemAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.util.HashMap;

public class NewJobExistingCarController {

    private SceneSwitch sceneSwitch;

    private CustomerReference customerReference;

    private HashMap<String, Car> carHashMap;

    @FXML
    private StackPane newJobExistingCarStackPane;

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
    private JFXListView<Label> customerCarList;

    @FXML
    void backBtnClicked(ActionEvent event) {
        customerCarList.getSelectionModel().select(null);
        customerCarList.getItems().clear();
        carHashMap.clear();
        refreshList();
        sceneSwitch.switchScene(NavigationModel.NEW_JOB_CAR_MENU_ID);
    }

    @FXML
    void nextBtnClicked(ActionEvent event) {
        if(customerCarList.getSelectionModel().isEmpty()) {
            SystemAlert systemAlert = new SystemAlert(newJobExistingCarStackPane,
                    "Failure", "No car selected");
        }
        else {
            Car car = carHashMap.get(customerCarList.getSelectionModel().getSelectedItem().getText());
            Customer customer = customerReference.getCustomer();
            Job job = new Job();
            CarDAO carDAO = new CarDAO();
            JobDAO jobDAO = new JobDAO();
            MechanicDAO mechanicDAO = new MechanicDAO();
            int jobID = 1;
            for(Job j: jobDAO.getAll()) {
                jobID++;
            }
            job.setJobID(jobID);
            if(usertypeLbl.getText().equals("Mechanic")) {
                job.setUsername(usernameLbl.getText().substring(8));
            }
            else {
                    for (Mechanic m : mechanicDAO.getAll()) {
                        job.setUsername(m.getUsername());
                        break;
                    }
            }
            job.setCustomerID(Integer.parseInt(customer.getCustomerID()));
            job.setRegistrationID(car.getRegistrationID());
            java.util.Date currentDate = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
            job.setDateBookedIn(sqlDate);
            job.setDescription("Work done on car");
            job.setStatus("Pending");
            job.setPaidFor("False");
            jobDAO.save(job);
            SystemAlert systemAlert = new SystemAlert(newJobExistingCarStackPane,
                    "Success", "Added job for existing car");
            customerCarList.getSelectionModel().select(null);
            customerCarList.getItems().clear();
            carHashMap.clear();
            refreshList();
        }
    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(newJobExistingCarStackPane, NavigationModel.NEW_JOB_EXISTING_CAR_ID);
        customerReference = CustomerReference.getInstance();
        carHashMap = new HashMap<>();
        refreshList();
    }

    public void refreshList() {
        CarDAO carDAO = new CarDAO();
        Customer customer = customerReference.getCustomer();
        for(Car c: carDAO.getExistingCars(customer.getCustomerID())) {
            Label carLabel = new Label("Registration ID: " + c.getRegistrationID() + " / Make: " + c.getMake() + " / Model: " + c.getModel());
            carHashMap.put(carLabel.getText(), c);
            customerCarList.getItems().add(carLabel);
        }
        customerNameLbl.setText("Name: " + customerReference.getCustomer().getFirstName() + " " + customerReference.getCustomer().getLastName());
    }
}
