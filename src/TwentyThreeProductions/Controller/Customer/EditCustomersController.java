package TwentyThreeProductions.Controller.Customer;

import TwentyThreeProductions.Domain.Car;
import TwentyThreeProductions.Domain.Customer;
import TwentyThreeProductions.Model.Database.DAO.CarDAO;
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

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class EditCustomersController {

    private SceneSwitch sceneSwitch;
    private CustomerDAO customerDAO;
    private CarDAO carDAO;
    private ArrayList<Car> existingCars;
    private ArrayList<Car> availableCars;
    private ArrayList<String> removedCars;
    private ArrayList<Customer> customers;
    private HashMap<String, Customer> customerMap;
    private HashMap<String, Car> tmpMap;
    private HashMap<String, Car> carMap;
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
    private JFXButton addNewCarBtn;

    @FXML
    private JFXButton removeCarBtn;

    @FXML
    private JFXComboBox<Label> discountCombi;

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
        carMap.put(tmp, tmpMap.get(tmp));
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
        removedCars = new ArrayList<>();
        System.out.println(carMap.get(tmp).getRegistrationID());
        removedCars.add(carMap.get(tmp).getRegistrationID());
        carMap.remove(tmp);
        selectedCarList.getItems().remove(j);
    }

    @FXML
    void saveBtnClicked(ActionEvent event) {
        customerDAO = new CustomerDAO();
        carDAO = new CarDAO();
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

        if (removedCars.size() > 0) {
            for (int l = 0; l < removedCars.size(); l++) {
                carDAO.updateCustomer(null, removedCars.get(l));
            }
        }

        for (int j = 0; j < selectedCarList.getItems().size(); j++) {
            String regID = carMap.get(selectedCarList.getItems().get(j).getText()).getRegistrationID();
            System.out.println("Reg ID : " + regID);
            carDAO.updateCustomer(currentCustomerID, regID);
        }

        SystemAlert alert = new SystemAlert(EditCustomerStackPane, "Success!", "Updated customer");


    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(EditCustomerStackPane, NavigationModel.EDIT_CUSTOMER_ID);
        customerMap = new HashMap<>();
        carMap = new HashMap<>();
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

    // Loads Existing Cars in the Selected Field
    public void loadExistingCars(String customerID) {
        carDAO = new CarDAO();
        existingCars = new ArrayList<>();
        existingCars = carDAO.getExistingCars(customerID);
        for (int p = 0; p < existingCars.size(); p++) {
            Car tmpCar = existingCars.get(p);
            Label tmpLabel = new Label(tmpCar.getModel());
            selectedCarList.getItems().add(tmpLabel);
            carMap.put(tmpLabel.getText(), tmpCar);
        }
    }

    public void loadAvailableCars() {
        carDAO = new CarDAO();
        availableCars = new ArrayList<>();
        availableCars = carDAO.getAvailableCars();
        for (int i = 0; i < availableCars.size(); i++) {
            Car tmpCar = availableCars.get(i);
            Label tmpLabel = new Label(tmpCar.getModel());
            availableCarsCombi.getItems().add(tmpLabel);
            tmpMap.put(tmpLabel.getText(), tmpCar);
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

