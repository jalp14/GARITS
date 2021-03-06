package TwentyThreeProductions.Controller.Customer;

import TwentyThreeProductions.Domain.Customer;
import TwentyThreeProductions.Domain.Vehicle;
import TwentyThreeProductions.Model.DBLogic;
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
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class EditCustomersController {
///////////////////////////////// Edit existing customer details \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    private SceneSwitch sceneSwitch;
    private CustomerDAO customerDAO;
    private VehicleDAO vehicleDAO;
    private HashMap<String, Vehicle> newVehicles;
    private ArrayList<Vehicle> existingVehicles;
    private ArrayList<Vehicle> availableVehicles;
    private ArrayList<Label> newVehiclesLabel;
    private ArrayList<String> removedVehicles;
    private ArrayList<Customer> customers;
    private HashMap<String, Customer> customerMap;
    private HashMap<String, Vehicle> tmpMap;
    private HashMap<String, Vehicle> vehicleMap;
    private String currentCustomerID;
    private DiscountDAO discountDAO;
    private RequiredFieldValidator fieldValidator;

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
    private AnchorPane editCustomerAnchorPane;

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
    private JFXButton confirmCarBtn;

    @FXML
    private Label selectedCarsHeading;

    @FXML
    void confirmCarBtnClicked(ActionEvent event) throws IOException { // Confirm btn clicked
        // This functions gets the newly created vehicle object and adds it to the list of cars assigned to the customer
        Vehicle vehicle;
        System.out.println("Confirm btn clicked");
        vehicle = CustomerHelper.getInstance().getVehicle();
        System.out.println(vehicle.getName());
        Label label = new Label(vehicle.getName() + ":" + vehicle.getRegNo());
        newVehicles.put(label.getText(), vehicle);
        newVehiclesLabel.add(label);
        selectedCarList.getItems().add(label);
    }

    @FXML
    void checkDiscountBtnClicked(ActionEvent event) throws IOException {
        // get the current customer's ID and pass it to the helper class
        // then take the user to the configure discount form
        System.out.println("Current customer id : " + currentCustomerID);
        CustomerHelper.getInstance().setCustomerCasual(casualCustomerRadio.isSelected());
        CustomerHelper.getInstance().setCurrentCustomerID(Integer.parseInt(currentCustomerID));
        sceneSwitch.activateScene(NavigationModel.EDIT_DISCOUNT_ID, backBtn.getScene());
    }

    @FXML
    void accountHolderRadioSelected(ActionEvent event) {
        // if the customer account is of type account holder then enable the discount options
        checkDiscountBtn.setDisable(false);
        checkDiscountBtn.setDisableVisualFocus(false);
    }

    @FXML
    void casualCustomerRadioSelected(ActionEvent event) {
        // if the customer account is of type casual then disable the discount options
        checkDiscountBtn.setDisable(true);
        checkDiscountBtn.setDisableVisualFocus(true);
    }

    @FXML
    void selectCustomerBtnClicked(ActionEvent event) {// Displays customer details
        System.out.println("Customer Selected");
        // Get the selected customer and get the customer id
        Customer customer = customerMap.get(selectCustomerCombi.getSelectionModel().getSelectedItem().getText());
        // Get the customer object from the hash map
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
        // Account Type Determination
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

        if (!(selectedCarList.getItems().isEmpty())) {
            selectedCarList.getItems().clear();
        }
        // Get all the vehicles associated with customer
        getCars(customer.getCustomerID());
        // Add them to the vehicles list
        loadExistingCars();

    }

    @FXML
    void addNewCarBtnClicked(ActionEvent event) throws IOException {
        // Take user to the Add New Car form
        CustomerHelper.getInstance().setLastCall(NavigationModel.EDIT_CUSTOMER_ID);
        sceneSwitch.activateScene(NavigationModel.ADD_CUSTOMER_TO_CAR_ID, backBtn.getScene());
    }

    @FXML
    void backBtnClicked(ActionEvent event) {
        // Take the user to the previous form
        sceneSwitch.switchScene(NavigationModel.CUSTOMER_MAIN_ID);
        resetView();
    }


    public void resetView() {
        // Remove any unsaved changes
        cityField.setText("");
        buildingNameField.setText("");
        emailField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        houseNameField.setText("");
        postcodeField.setText("");
        streetNameField.setText("");
        phoneNoField.setText("");
        latePaymentCheckbox.setSelected(false);
        casualCustomerRadio.setSelected(false);
        accountHolderRadio.setSelected(false);
        selectedCarList.getItems().clear();
    }

    @FXML
    void removeCarBtnClicked(ActionEvent event) {
        // Remove the selected vehicle by dissassociating with the current customer and remove it from the databse
        int j = selectedCarList.getSelectionModel().getSelectedIndex();
        String tmp = selectedCarList.getItems().get(j).getText();
        removedVehicles = new ArrayList<>();
        System.out.println(vehicleMap.get(tmp).getRegistrationID());
        removedVehicles.add(vehicleMap.get(tmp).getRegistrationID());
        vehicleMap.remove(tmp);
        selectedCarList.getItems().remove(j);
    }

    @FXML
    void saveBtnClicked(ActionEvent event) {
        // Save any changes made to the database
        System.out.println("Save btn clicked");
        discountDAO = new DiscountDAO();
        customerDAO = new CustomerDAO();
        vehicleDAO = new VehicleDAO();
        Customer customer = new Customer();
        // Get all the details from the field and add it to the Customer object
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
        // If any vehicles were removed update the changes to the database
        // Removed vehicles
        if (removedVehicles.size() > 0) {
            for (int l = 0; l < removedVehicles.size(); l++) {
                vehicleDAO.updateCustomer(null, removedVehicles.get(l));
            }
        }
        // If any new vehicles are added then update those in the database
        // New vehicles
        for (int j = 0; j < newVehicles.size(); j++) {
            Vehicle tmpVehicle = newVehicles.get(newVehiclesLabel.get(j).getText());
            tmpVehicle.setCustomerID(currentCustomerID);
            String regID = tmpVehicle.getRegistrationID();
            System.out.println("Reg ID : " + regID);
            if (tmpVehicle.getLastMOT() == null) {
                vehicleDAO.saveWithoutMOT(tmpVehicle);
            } else {
                vehicleDAO.save(tmpVehicle);
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

        // Show a pop up alert to let the user know that customer was updated successfully
            SystemAlert alert = new SystemAlert(EditCustomerStackPane, "Success!", "Updated customer");

        }
    }

    public void setupFieldValidators() {
        // Add Validators
        // Every time a key is pressed this will check that valid data is entered
        fieldValidator = new RequiredFieldValidator();
        fieldValidator.setMessage("Empty field");

        // Add Image
        try {
            Image errorIcon = new Image(new FileInputStream("src/TwentyThreeProductions/GARITSassests/error.png"));
            ImageView view = new ImageView(errorIcon);
            view.setFitHeight(16);
            view.setFitWidth(16);
            fieldValidator.setIcon(view);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (Node node : editCustomerAnchorPane.getChildren()) {
            if (node instanceof JFXTextField) {
                node.focusedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                        ((JFXTextField) node).getValidators().add(fieldValidator);
                        if (!t1) {
                            ((JFXTextField) node).validate();
                        }
                    }
                });
            }
        }


    }

    public void initialize() {
        // Initialise the current form
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(EditCustomerStackPane, NavigationModel.EDIT_CUSTOMER_ID);
        setupFieldValidators();
        usernameLbl.setText(DBLogic.getDBInstance().getUsername());
        usertypeLbl.setText(DBLogic.getDBInstance().getUser_type());
        customerMap = new HashMap<>();
        vehicleMap = new HashMap<>();
        tmpMap = new HashMap<>();
        newVehicles = new HashMap<>();
        removedVehicles = new ArrayList<>();
        newVehiclesLabel = new ArrayList<>();
        // Load all the available customers to the drop down menu
        loadCustomers();
    }


    // Loads all the customers in the db
    public void loadCustomers() { // Load all the available customers to the drop down menu
        customerDAO = new CustomerDAO();
        // Get all the customers
        customers = customerDAO.getAll();
        // Add all the customers to the combi box
        for (int i = 0; i < customers.size(); i++) {
            Customer tmpCustomer = customers.get(i);
            String combiLabel = tmpCustomer.getFirstName() + " " + tmpCustomer.getLastName();
            customerMap.put(combiLabel, tmpCustomer);
            selectCustomerCombi.getItems().add(new Label(combiLabel));
        }

    }

    public void getCars(String customerID) {
        // Get all the vehicles associated with the Customer specified by the customerID
        vehicleDAO = new VehicleDAO();
        // Existing Vehicles
        existingVehicles = new ArrayList<>();
        existingVehicles = vehicleDAO.getExistingVehicles(customerID);
        // Available Vehicles
        availableVehicles = new ArrayList<>();
        availableVehicles = vehicleDAO.getAvailableVehicles();
    }

    // Loads Existing Cars in the Selected Field
    public void loadExistingCars() {
        selectedCarList.getItems().clear();
        for (int p = 0; p < existingVehicles.size(); p++) {
            Vehicle tmpVehicle = existingVehicles.get(p);
            Label tmpLabel = new Label(tmpVehicle.getName());
            selectedCarList.getItems().add(tmpLabel);
            vehicleMap.put(tmpLabel.getText(), tmpVehicle);
        }
    }

    public String determineType() {
        // Determine the customer account type
        String type;
        if (accountHolderRadio.isSelected()) {
            type = "ACCOUNT";
        } else {
            type = "CASUAL";
        }
        return type;
    }



}

