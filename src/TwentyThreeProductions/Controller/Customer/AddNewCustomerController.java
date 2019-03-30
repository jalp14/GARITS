package TwentyThreeProductions.Controller.Customer;

import TwentyThreeProductions.Domain.Vehicle;
import TwentyThreeProductions.Domain.Customer;
import TwentyThreeProductions.Domain.Vehicle;
import TwentyThreeProductions.Model.Database.DAO.VehicleDAO;
import TwentyThreeProductions.Model.Database.DAO.CustomerDAO;
import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import TwentyThreeProductions.Model.SystemAlert;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AddNewCustomerController {

    private SceneSwitch sceneSwitch;
    private Customer customer;
    private VehicleDAO vehicleDAO;
    private CustomerDAO customerDAO;
    private ArrayList<Vehicle> vehicles;
    private HashMap<String,Vehicle> vehicleHashMap;

    @FXML
    private StackPane AddNewCustomerStackPane;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text usertypeLbl;

    @FXML
    private Label welcomeMessage;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private Label discountHeading;

    @FXML
    private Label fNameHeading;

    @FXML
    private Label address1Heading;

    @FXML
    private Label latePaymentHeading;

    @FXML
    private JFXCheckBox latePaymentCheckbox;

    @FXML
    private JFXRadioButton casualCustomerRadio;

    @FXML
    private JFXRadioButton accountHolderRadio;

    @FXML
    private Label lNameHeading;

    @FXML
    private Label postcodeHeading;

    @FXML
    private Label cityHeading;

    @FXML
    private Label emailHeading;

    @FXML
    private Label phoneNoHeading;

    @FXML
    private JFXTextField firstNameField;

    @FXML
    private JFXTextField lastNameField;

    @FXML
    private JFXTextField addressOneField;

    @FXML
    private JFXTextField postcodeField;

    @FXML
    private ToggleGroup Type;

    @FXML
    private JFXTextField cityField;

    @FXML
    private JFXTextField emailField;

    @FXML
    private JFXTextField phoneNoField;

    @FXML
    private Label availableVehiclesHeading;

    @FXML
    private Label selectedVehiclesHeading;

    @FXML
    private JFXListView<Label> selectedVehicleList;

    @FXML
    private JFXComboBox<Label> availableVehiclesCombi;

    @FXML
    private JFXButton addNewVehicleBtn;

    @FXML
    private JFXButton removeVehicleBtn;


    @FXML
    void backBtnClicked(ActionEvent event) {
        sceneSwitch.switchScene(NavigationModel.CUSTOMER_MAIN_ID);
    }


    @FXML
    void saveBtnClicked(ActionEvent event) {
        String customerRowCount;
        customerDAO = new CustomerDAO();
        customer = new Customer();
        vehicleDAO = new VehicleDAO();
        customer.setFirstName(firstNameField.getText());
        customer.setLastName(lastNameField.getText());
        customer.setCustomerAddress(addressOneField.getText());
        customer.setCustomerPostcode(postcodeField.getText());
        customer.setCustomerPhone(phoneNoField.getText());
        customer.setCustomerEmail(emailField.getText());
        customer.setCustomerType(determineType());
        customer.setLatePayment(latePaymentCheckbox.isSelected());
        customerDAO.save(customer);
        customerRowCount = Integer.toString(customerDAO.getCount());
        System.out.println(customerRowCount);
        for (int j = 0; j < selectedVehicleList.getItems().size(); j++) {
            String regID = String.valueOf(vehicleHashMap.get(selectedVehicleList.getItems().get(j).getText()).getRegistrationID());
            System.out.println("Reg ID : " + regID);
            vehicleDAO.updateCustomer(customerRowCount, regID);
            System.out.println(vehicles.get(j).getRegistrationID());
        }
        SystemAlert alert = new SystemAlert(AddNewCustomerStackPane, "Success!", "Added customer to the db");

    }

    @FXML
    void addNewVehicleBtnClicked(ActionEvent event) throws IOException {
        int i = availableVehiclesCombi.getSelectionModel().getSelectedIndex();
        selectedVehicleList.getItems().add((availableVehiclesCombi.getItems().get(i)));
        availableVehiclesCombi.getItems().remove(i);
    }

    @FXML
    void removeVehicleBtnClicked(ActionEvent event) {
        int j = selectedVehicleList.getSelectionModel().getSelectedIndex();
        availableVehiclesCombi.getItems().add(selectedVehicleList.getItems().get(j));
        selectedVehicleList.getItems().remove(j);
    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(AddNewCustomerStackPane, NavigationModel.ADD_NEW_CUSTOMER_ID);
        vehicleHashMap = new HashMap<>();
        loadVehicles();
    }

    public void loadVehicles() {
        vehicleDAO = new VehicleDAO();
        vehicles = vehicleDAO.getAvailableVehicles();
        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle tmpVehicle = vehicles.get(i);
            Label tmpLabel = new Label(tmpVehicle.getName());
            availableVehiclesCombi.getItems().add(tmpLabel);
            vehicleHashMap.put(tmpLabel.getText(), tmpVehicle);
            System.out.println(" Vehicle Hash Map Size : " + vehicleHashMap.size());
        }

    }

    public String determineType() {
        String type;
        if (accountHolderRadio.isSelected()) {
            type = "ACCOUNT";
        } else {
            type = "CASUAL";
        }
        return type;
    }

}

