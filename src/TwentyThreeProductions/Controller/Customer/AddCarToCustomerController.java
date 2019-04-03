package TwentyThreeProductions.Controller.Customer;

import TwentyThreeProductions.Domain.Vehicle;
import TwentyThreeProductions.Model.Database.DAO.VehicleDAO;
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

    private SceneSwitch sceneSwitch;
    private Vehicle vehicle;



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
    private JFXTextField registrationField;

    @FXML
    private Label nameHeading;

    @FXML
    private Label vehicleDateHeading;

    @FXML
    private Label colourHeading;

    @FXML
    private JFXTextField nameField;

    @FXML
    private JFXTextField colourField;

    @FXML
    private JFXDatePicker vehicleDate;

    @FXML
    private JFXRadioButton firstMOT;

    @FXML
    private Label vehicleDateHeading1;

    @FXML
    private JFXDatePicker lastMOTDate;

    @FXML
    void firstMOTClicked(ActionEvent event) {
        if (firstMOT.isSelected()) {
            lastMOTDate.setDisable(true);
        } else {
            lastMOTDate.setDisable(false);
        }
    }


    @FXML
    void addVehicleBtnClicked(ActionEvent event) {
        setupVehicle();
        CustomerHelper.getInstance().setVehicle(vehicle);
    }

    public void setupVehicle() {
        vehicle = new Vehicle();
        LocalDate purchaseDate = vehicleDate.getValue();
        vehicle.setVehicleDate(Date.valueOf(purchaseDate));
        if (firstMOT.isSelected()) {
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
    void backBtnClicked(ActionEvent event) {

        if (CustomerHelper.getInstance().getLastCall().equals(NavigationModel.ADD_NEW_CUSTOMER_ID)) {
            sceneSwitch.switchScene(NavigationModel.ADD_NEW_CUSTOMER_ID);
        } else {
            sceneSwitch.switchScene(NavigationModel.EDIT_CUSTOMER_ID);
        }
    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
    }

}
