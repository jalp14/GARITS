package TwentyThreeProductions.Controller.Customer;
import TwentyThreeProductions.Domain.Vehicle;
import TwentyThreeProductions.Model.HelperClasses.CustomerHelper;
import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import java.sql.Date;
import java.time.LocalDate;

public class AddCarToCustomerController {
/////////////////////////// Add new vehicle to a customer account \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    // Scene switch variable to allow switching between different forms
    private SceneSwitch sceneSwitch;
    // Access the vehicle object
    private Vehicle vehicle;

    @FXML
    private StackPane newJobNewVehicleStackPane; // Stack pane of the current form

    @FXML
    private Text usernameLbl; // username label

    @FXML
    private Text usertypeLbl; // account type label

    @FXML
    private Label welcomeMessage; // welcome label

    @FXML
    private JFXButton backBtn; // back button to go to the previous form

    @FXML
    private JFXButton addVehicleBtn; // adds a new vehicle

    @FXML
    private Label customerNameLbl; // name of the selected customer

    @FXML
    private Label regHeading; // registration label

    @FXML
    private JFXTextField registrationField; // current registration of the car

    @FXML
    private Label nameHeading; // car name label

    @FXML
    private Label vehicleDateHeading; // date of purchase label

    @FXML
    private Label colourHeading; // color of the  car label

    @FXML
    private JFXTextField nameField; // name of the car

    @FXML
    private JFXTextField colourField; // color of the car

    @FXML
    private JFXDatePicker vehicleDate; // date of purchase of the vehicle

    @FXML
    private JFXRadioButton firstMOT; // first MoT of the car if any

    @FXML
    private Label vehicleDateHeading1;

    @FXML
    private JFXDatePicker lastMOTDate; // date when the car had its last MoT

    @FXML
    void firstMOTClicked(ActionEvent event) { // When the firstMoT button is clicked
        if (firstMOT.isSelected()) { // check if it's already disabled
            lastMOTDate.setDisable(true); // if it is then disable the lastMoT date field
        } else {
            lastMOTDate.setDisable(false); // if no then enable the lastMoT date field
        }
    }


    @FXML
    void addVehicleBtnClicked(ActionEvent event) { // when the add vehicle button is clicked
        setupVehicle(); // setup the vehicle details
        CustomerHelper.getInstance().setVehicle(vehicle); // use the helper class to assign the vehicle object to the customer
    }

    public void setupVehicle() { // take all the details entered in the field and add them to the vehicle object
        vehicle = new Vehicle();
        LocalDate purchaseDate = vehicleDate.getValue();
        vehicle.setVehicleDate(Date.valueOf(purchaseDate));
        if (firstMOT.isSelected()) { // if firstMoT is selected then set lastMoT to null
            vehicle.setLastMOT(null);
            vehicle.setRegNo(registrationField.getText());
            vehicle.setColour(colourField.getText());
            vehicle.setName(nameField.getText());
        } else {
            LocalDate motDate = lastMOTDate.getValue();
            vehicle.setLastMOT(Date.valueOf(motDate));
            vehicle.setRegNo(registrationField.getText());
            vehicle.setColour(colourField.getText());
            vehicle.setName(nameField.getText());
        }

    }


    @FXML
    void backBtnClicked(ActionEvent event) { // back button clicked
        // check which form the user came from by getting the form name from the helper class
        if (CustomerHelper.getInstance().getLastCall().equals(NavigationModel.ADD_NEW_CUSTOMER_ID)) {
            sceneSwitch.switchScene(NavigationModel.ADD_NEW_CUSTOMER_ID);
        } else {
            sceneSwitch.switchScene(NavigationModel.EDIT_CUSTOMER_ID);
        }
    }

    public void initialize() {
        // Initialize the current scene
        sceneSwitch = SceneSwitch.getInstance();
    }

}
