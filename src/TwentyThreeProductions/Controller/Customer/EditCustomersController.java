package TwentyThreeProductions.Controller.Customer;

import TwentyThreeProductions.Domain.Vehicle;
import TwentyThreeProductions.Domain.Customer;
import TwentyThreeProductions.Model.Database.DAO.VehicleDAO;
import TwentyThreeProductions.Model.Database.DAO.CustomerDAO;
import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import TwentyThreeProductions.Model.SystemAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class EditCustomersController {

    private SceneSwitch sceneSwitch;
    private CustomerDAO customerDAO;
    private VehicleDAO vehicleDAO;
    private ArrayList<Vehicle> existingVehicles;
    private ArrayList<Vehicle> availableVehicles;
    private ArrayList<String> removedVehicles;
    private ArrayList<Customer> customers;
    private HashMap<String, Customer> customerMap;
    private HashMap<String, Vehicle> tmpMap;
    private HashMap<String, Vehicle> vehicleMap;
    private String currentCustomerID;

    @FXML
    private StackPane EditCustomerStackPane;

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
    private ToggleGroup Type;

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
    private JFXTextField cityField;

    @FXML
    private JFXTextField emailField;

    @FXML
    private JFXTextField phoneNoField;

    @FXML
    private Label selectCustomerHeading;

    @FXML
    private JFXComboBox<Label> selectCustomerCombi;

    @FXML
    private JFXButton addNewVehicleBtn;

    @FXML
    private JFXButton removeVehicleBtn;

    @FXML
    private JFXComboBox<Label> discountCombi;

    @FXML
    private Label discountHeading;

    @FXML
    private JFXListView<Label> selectedVehicleList;

    @FXML
    private Label availableVehiclesHeading;

    @FXML
    private JFXComboBox<Label> availableVehiclesCombi;

    @FXML
    private Label selectedVehiclesHeading;

    @FXML
    void selectCustomerBtnClicked(ActionEvent event) {
        System.out.println("Customer Selected");
        Customer customer = customerMap.get(selectCustomerCombi.getSelectionModel().getSelectedItem().getText());
        currentCustomerID = customer.getCustomerID();
        // Set all the fields
        firstNameField.setText(customer.getFirstName());
        lastNameField.setText(customer.getLastName());
        addressOneField.setText(customer.getCustomerAddress());
        postcodeField.setText(customer.getCustomerPostcode());
        phoneNoField.setText(customer.getCustomerPhone());
        emailField.setText(customer.getCustomerEmail());

        // Type Determination
        if (customer.getCustomerType().equals("ACCOUNT")) {
            accountHolderRadio.setSelected(true);
        } else {
            casualCustomerRadio.setSelected(true);
        }

        latePaymentCheckbox.setDisable(customer.getLatePayment());

        if (!(availableVehiclesCombi.getItems().isEmpty())) {
            availableVehiclesCombi.getItems().clear();
        }

        if (!(selectedVehicleList.getItems().isEmpty())) {
            selectedVehicleList.getItems().clear();
        }

        loadExistingVehicles(customer.getCustomerID());
        loadAvailableVehicles();

    }

    @FXML
    void addNewVehicleBtnClicked(ActionEvent event) {
        int i = availableVehiclesCombi.getSelectionModel().getSelectedIndex();
        selectedVehicleList.getItems().add((availableVehiclesCombi.getItems().get(i)));
        String tmp = availableVehiclesCombi.getItems().get(i).getText();
        vehicleMap.put(tmp, tmpMap.get(tmp));
        availableVehiclesCombi.getItems().remove(i);
    }

    @FXML
    void backBtnClicked(ActionEvent event) {
        sceneSwitch.switchScene(NavigationModel.CUSTOMER_MAIN_ID);
    }

    @FXML
    void removeVehicleBtnClicked(ActionEvent event) {
        int j = selectedVehicleList.getSelectionModel().getSelectedIndex();
        availableVehiclesCombi.getItems().add(selectedVehicleList.getItems().get(j));
        String tmp = selectedVehicleList.getItems().get(j).getText();
        removedVehicles = new ArrayList<>();
        System.out.println(vehicleMap.get(tmp).getRegistrationID());
        removedVehicles.add(String.valueOf(vehicleMap.get(tmp).getRegistrationID()));
        vehicleMap.remove(tmp);
        selectedVehicleList.getItems().remove(j);
    }

    @FXML
    void saveBtnClicked(ActionEvent event) {
        customerDAO = new CustomerDAO();
        vehicleDAO = new VehicleDAO();
        Customer customer = new Customer();
        customer.setCustomerID(currentCustomerID);
        customer.setFirstName(firstNameField.getText());
        customer.setLastName(lastNameField.getText());
        customer.setCustomerType(determineType());
        customer.setCustomerAddress(addressOneField.getText());
        customer.setCustomerPostcode(postcodeField.getText());
        customer.setCustomerPhone(phoneNoField.getText());
        customer.setCustomerEmail(emailField.getText());
        customer.setLatePayment(latePaymentCheckbox.isSelected());
        customerDAO.update(customer);

        if (removedVehicles.size() > 0) {
            for (int l = 0; l < removedVehicles.size(); l++) {
                vehicleDAO.updateCustomer(null, removedVehicles.get(l));
            }
        }

        for (int j = 0; j < selectedVehicleList.getItems().size(); j++) {
            String regID = String.valueOf(vehicleMap.get(selectedVehicleList.getItems().get(j).getText()).getRegistrationID());
            System.out.println("Reg ID : " + regID);
            vehicleDAO.updateCustomer(currentCustomerID, regID);
        }

        SystemAlert alert = new SystemAlert(EditCustomerStackPane, "Success!", "Updated customer");


    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(EditCustomerStackPane, NavigationModel.EDIT_CUSTOMER_ID);
        customerMap = new HashMap<>();
        vehicleMap = new HashMap<>();
        tmpMap = new HashMap<>();
        loadCustomers();
    }


    // Loads all the customers in the db
    public void loadCustomers() {
        customerDAO = new CustomerDAO();
        customers = customerDAO.getAll();

        // Add all the customers to the combi box
        for (int i = 0; i < customers.size(); i++) {
            Customer tmpCustomer = customers.get(i);
            String combiLabel = tmpCustomer.getFirstName() + " " + tmpCustomer.getLastName();
            customerMap.put(combiLabel, tmpCustomer);
            selectCustomerCombi.getItems().add(new Label(combiLabel));
        }

    }

    // Loads Existing Vehicles in the Selected Field
    public void loadExistingVehicles(String customerID) {
        vehicleDAO = new VehicleDAO();
        existingVehicles = new ArrayList<>();
        existingVehicles = vehicleDAO.getExistingVehicles(customerID);
        for (int p = 0; p < existingVehicles.size(); p++) {
            Vehicle tmpVehicle = existingVehicles.get(p);
            Label tmpLabel = new Label(tmpVehicle.getName());
            selectedVehicleList.getItems().add(tmpLabel);
            vehicleMap.put(tmpLabel.getText(), tmpVehicle);
        }
    }

    public void loadAvailableVehicles() {
        vehicleDAO = new VehicleDAO();
        availableVehicles = new ArrayList<>();
        availableVehicles = vehicleDAO.getAvailableVehicles();
        for (int i = 0; i < availableVehicles.size(); i++) {
            Vehicle tmpVehicle = availableVehicles.get(i);
            Label tmpLabel = new Label(tmpVehicle.getName());
            availableVehiclesCombi.getItems().add(tmpLabel);
            tmpMap.put(tmpLabel.getText(), tmpVehicle);
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

