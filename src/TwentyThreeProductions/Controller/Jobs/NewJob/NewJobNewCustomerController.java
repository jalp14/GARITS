package TwentyThreeProductions.Controller.Jobs.NewJob;

import TwentyThreeProductions.Domain.Customer;
import TwentyThreeProductions.Domain.Vehicle;
import TwentyThreeProductions.Model.CustomerReference;
import TwentyThreeProductions.Model.Database.DAO.DiscountDAO;
import TwentyThreeProductions.Model.Database.DAO.VehicleDAO;
import TwentyThreeProductions.Model.Database.DAO.CustomerDAO;
import TwentyThreeProductions.Model.HelperClasses.CustomerHelper;
import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import TwentyThreeProductions.Model.SystemAlert;
import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class NewJobNewCustomerController {

    private SceneSwitch sceneSwitch;
    private Customer customer;
    private VehicleDAO vehicleDAO;
    private CustomerDAO customerDAO;
    private DiscountDAO discountDAO;
    private ArrayList<Vehicle> vehicles;
    private HashMap<String,Vehicle> vehicleHashMap;
    private CustomerReference customerReference;

    @FXML
    private StackPane newJobNewCustomerStackPane;

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
    private JFXTextField houseNameField;

    @FXML
    private JFXTextField streetNameField;

    @FXML
    private JFXTextField buildingNameField;

    @FXML
    private JFXTextField postcodeField;

    @FXML
    private ToggleGroup Type;

    @FXML
    private JFXButton configureBtn;

    @FXML
    private JFXTextField cityField;

    @FXML
    private JFXTextField emailField;

    @FXML
    private JFXTextField phoneNoField;

    @FXML
    private Label availableCarsHeading;

    @FXML
    private Label selectedCarsHeading;

    @FXML
    private JFXListView<Label> selectedCarList;

    @FXML
    private JFXComboBox<Label> availableCarsCombi;

    @FXML
    private JFXButton addNewCarBtn;

    @FXML
    private JFXButton removeCarBtn;


    @FXML
    void accountHolderRadioSelected(ActionEvent event) {
        configureBtn.setDisable(false);
        configureBtn.setDisableVisualFocus(false);
    }

    @FXML
    void casualCustomerRadioSelected(ActionEvent event) {
        configureBtn.setDisable(true);
        configureBtn.setDisableVisualFocus(true);
    }

    @FXML
    void configureBtnClicked(ActionEvent event) throws IOException {
        try {
            customerDAO = new CustomerDAO();
            CustomerHelper.getInstance().setCurrentCustomerID(customerDAO.getCount() + 1);
            sceneSwitch.activateScene(NavigationModel.CONFIGURE_DISCOUNT_ID, backBtn.getScene());
        }
        catch(Exception e) {
            SystemAlert systemAlert = new SystemAlert(newJobNewCustomerStackPane,
                    "Success", "Job added for new vehicle");
        }
    }

    @FXML
    void backBtnClicked(ActionEvent event) {
        sceneSwitch.switchScene(NavigationModel.NEW_JOB_MENU_ID);
    }


    @FXML
    void saveBtnClicked(ActionEvent event) throws IOException {
        String customerRowCount;
        customerDAO = new CustomerDAO();
        customer = new Customer();
        vehicleDAO = new VehicleDAO();
        discountDAO = new DiscountDAO();
        customer.setFirstName(firstNameField.getText());
        customer.setLastName(lastNameField.getText());
        customer.setCustomerHouseName(houseNameField.getText());
        customer.setCustomerBuildingName(buildingNameField.getText());
        customer.setCustomerStreetName(streetNameField.getText());
        customer.setCustomerPostcode(postcodeField.getText());
        customer.setCustomerPhone(phoneNoField.getText());
        customer.setCustomerCity(cityField.getText());
        customer.setCustomerEmail(emailField.getText());
        customer.setCustomerType(determineType());
        customer.setLatePayment(latePaymentCheckbox.isSelected());
        customerDAO.save(customer);
        customerRowCount = Integer.toString(customerDAO.getCount());
        System.out.println(customerRowCount);
        for (int j = 0; j < selectedCarList.getItems().size(); j++) {
            String regID = vehicleHashMap.get(selectedCarList.getItems().get(j).getText()).getRegistrationID();
            System.out.println("Reg ID : " + regID);
            vehicleDAO.updateCustomer(customerRowCount, regID);
            System.out.println(vehicles.get(j).getRegistrationID());
        }
        if (accountHolderRadio.isSelected()) {
            discountDAO.save(CustomerHelper.getInstance().getDiscount());
        }
        for(Customer c: customerDAO.getAll()) {
            customer.setCustomerID(c.getCustomerID());
        }
        customerReference.setCustomer(customer);
        sceneSwitch.activateSceneAlways(NavigationModel.NEW_JOB_CAR_MENU_ID, backBtn.getScene());
    }

    @FXML
    void addNewCarBtnClicked(ActionEvent event) throws IOException {
        int i = availableCarsCombi.getSelectionModel().getSelectedIndex();
        selectedCarList.getItems().add((availableCarsCombi.getItems().get(i)));
        availableCarsCombi.getItems().remove(i);
    }

    @FXML
    void removeCarBtnClicked(ActionEvent event) {
        int j = selectedCarList.getSelectionModel().getSelectedIndex();
        availableCarsCombi.getItems().add(selectedCarList.getItems().get(j));
        selectedCarList.getItems().remove(j);
    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(newJobNewCustomerStackPane, NavigationModel.NEW_JOB_NEW_CUSTOMER_ID);
        vehicleHashMap = new HashMap<>();
        customerReference = CustomerReference.getInstance();
        loadCars();
    }

    public void loadCars() {
        Platform.runLater(() -> {
            vehicleDAO = new VehicleDAO();
            vehicles = vehicleDAO.getAvailableVehicles();
            for (int i = 0; i < vehicles.size(); i++) {
                Vehicle tmpVehicle = vehicles.get(i);
                Label tmpLabel = new Label(tmpVehicle.getName());
                availableCarsCombi.getItems().add(tmpLabel);
                vehicleHashMap.put(tmpLabel.getText(), tmpVehicle);
                System.out.println(" Car Hash Map Size : " + vehicleHashMap.size());
            }
        });


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

