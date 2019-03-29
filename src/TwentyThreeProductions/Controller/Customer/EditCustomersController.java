package TwentyThreeProductions.Controller.Customer;

import TwentyThreeProductions.Domain.Customer;
import TwentyThreeProductions.Domain.Vehicle;
import TwentyThreeProductions.Model.Database.DAO.DiscountDAO;
import TwentyThreeProductions.Model.Database.DAO.VehicleDAO;
import TwentyThreeProductions.Model.Database.DAO.CustomerDAO;
import TwentyThreeProductions.Model.HelperClasses.CustomerHelper;
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

import java.io.IOException;
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
    private DiscountDAO discountDAO;

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
    private JFXTextField houseNameField;

    @FXML
    private JFXTextField streetNameField;

    @FXML
    private JFXTextField buildingNameField;

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
    private JFXButton addNewCarBtn;

    @FXML
    private JFXButton removeCarBtn;

    @FXML
    private JFXButton checkDiscountBtn;

    @FXML
    private Label discountHeading;

    @FXML
    private JFXListView<Label> selectedCarList;

    @FXML
    private Label availableCarsHeading;

    @FXML
    private JFXComboBox<Label> availableCarsCombi;

    @FXML
    private Label selectedCarsHeading;

    @FXML
    void checkDiscountBtnClicked(ActionEvent event) throws IOException {
        System.out.println("Current customer id : " + currentCustomerID);
        CustomerHelper.getInstance().setCustomerCasual(casualCustomerRadio.isSelected());
        CustomerHelper.getInstance().setCurrentCustomerID(Integer.parseInt(currentCustomerID));
        sceneSwitch.activateScene(NavigationModel.EDIT_DISCOUNT_ID, backBtn.getScene());
    }

    @FXML
    void accountHolderRadioSelected(ActionEvent event) {
        checkDiscountBtn.setDisable(false);
        checkDiscountBtn.setDisableVisualFocus(false);
    }

    @FXML
    void casualCustomerRadioSelected(ActionEvent event) {
        checkDiscountBtn.setDisable(true);
        checkDiscountBtn.setDisableVisualFocus(true);
    }

    @FXML
    void selectCustomerBtnClicked(ActionEvent event) {
        System.out.println("Customer Selected");
        Customer customer = customerMap.get(selectCustomerCombi.getSelectionModel().getSelectedItem().getText());
        currentCustomerID = customer.getCustomerID();
        // Set all the fields
        firstNameField.setText(customer.getFirstName());
        lastNameField.setText(customer.getLastName());
        houseNameField.setText(customer.getCustomerHouseName());
        buildingNameField.setText(customer.getCustomerBuildingName());
        streetNameField.setText(customer.getCustomerStreetName());
        postcodeField.setText(customer.getCustomerPostcode());
        phoneNoField.setText(customer.getCustomerPhone());
        emailField.setText(customer.getCustomerEmail());
        cityField.setText(customer.getCustomerCity());
        // Type Determination
        if (customer.getCustomerType().equals("ACCOUNT")) {
            accountHolderRadio.setSelected(true);
            checkDiscountBtn.setDisable(false);
            checkDiscountBtn.setDisableVisualFocus(false);
        } else if (customer.getCustomerType().equals("CASUAL")){
            casualCustomerRadio.setSelected(true);
            checkDiscountBtn.setDisable(true);
            checkDiscountBtn.setDisableVisualFocus(true);
        }

        latePaymentCheckbox.setDisable(customer.getLatePayment());

        if (!(availableCarsCombi.getItems().isEmpty())) {
            availableCarsCombi.getItems().clear();
        }

        if (!(selectedCarList.getItems().isEmpty())) {
            selectedCarList.getItems().clear();
        }

        loadExistingCars(customer.getCustomerID());
        loadAvailableCars();

    }

    @FXML
    void addNewCarBtnClicked(ActionEvent event) {
        int i = availableCarsCombi.getSelectionModel().getSelectedIndex();
        selectedCarList.getItems().add((availableCarsCombi.getItems().get(i)));
        String tmp = availableCarsCombi.getItems().get(i).getText();
        vehicleMap.put(tmp, tmpMap.get(tmp));
        availableCarsCombi.getItems().remove(i);
    }

    @FXML
    void backBtnClicked(ActionEvent event) {
        sceneSwitch.switchScene(NavigationModel.CUSTOMER_MAIN_ID);
    }

    @FXML
    void removeCarBtnClicked(ActionEvent event) {
        int j = selectedCarList.getSelectionModel().getSelectedIndex();
        availableCarsCombi.getItems().add(selectedCarList.getItems().get(j));
        String tmp = selectedCarList.getItems().get(j).getText();
        removedVehicles = new ArrayList<>();
        System.out.println(vehicleMap.get(tmp).getRegistrationID());
        removedVehicles.add(vehicleMap.get(tmp).getRegistrationID());
        vehicleMap.remove(tmp);
        selectedCarList.getItems().remove(j);
    }

    @FXML
    void saveBtnClicked(ActionEvent event) {
        discountDAO = new DiscountDAO();
        customerDAO = new CustomerDAO();
        vehicleDAO = new VehicleDAO();
        Customer customer = new Customer();
        customer.setCustomerID(currentCustomerID);
        customer.setFirstName(firstNameField.getText());
        customer.setLastName(lastNameField.getText());
        customer.setCustomerType(determineType());
        customer.setCustomerCity(cityField.getText());
        customer.setCustomerHouseName(houseNameField.getText());
        customer.setCustomerBuildingName(buildingNameField.getText());
        customer.setCustomerStreetName(streetNameField.getText());
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

        for (int j = 0; j < selectedCarList.getItems().size(); j++) {
            String regID = vehicleMap.get(selectedCarList.getItems().get(j).getText()).getRegistrationID();
            System.out.println("Reg ID : " + regID);
            vehicleDAO.updateCustomer(currentCustomerID, regID);
        }

        if (accountHolderRadio.isSelected()) {
            if (CustomerHelper.getInstance().getI() == 1) {
                discountDAO.update(CustomerHelper.getInstance().getDiscount());
                System.out.println("Updating Discount");
            } else if (CustomerHelper.getInstance().getI() == 0) {
                discountDAO.save(CustomerHelper.getInstance().getDiscount());
                System.out.println("Adding new discount");
            }
        } else {
            discountDAO.delete(Integer.parseInt(currentCustomerID));
        }


        SystemAlert alert = new SystemAlert(EditCustomerStackPane, "Success!", "Updated customer");


    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(EditCustomerStackPane, NavigationModel.EDIT_CUSTOMER_ID);
        customerMap = new HashMap<>();
        vehicleMap = new HashMap<>();
        tmpMap = new HashMap<>();
        removedVehicles = new ArrayList<>();
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

    // Loads Existing Cars in the Selected Field
    public void loadExistingCars(String customerID) {
        vehicleDAO = new VehicleDAO();
        existingVehicles = new ArrayList<>();
        existingVehicles = vehicleDAO.getExistingVehicles(customerID);
        for (int p = 0; p < existingVehicles.size(); p++) {
            Vehicle tmpVehicle = existingVehicles.get(p);
            Label tmpLabel = new Label(tmpVehicle.getName());
            selectedCarList.getItems().add(tmpLabel);
            vehicleMap.put(tmpLabel.getText(), tmpVehicle);
        }
    }

    public void loadAvailableCars() {
        vehicleDAO = new VehicleDAO();
        availableVehicles = new ArrayList<>();
        availableVehicles = vehicleDAO.getAvailableVehicles();
        for (int i = 0; i < availableVehicles.size(); i++) {
            Vehicle tmpVehicle = availableVehicles.get(i);
            Label tmpLabel = new Label(tmpVehicle.getName());
            availableCarsCombi.getItems().add(tmpLabel);
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

