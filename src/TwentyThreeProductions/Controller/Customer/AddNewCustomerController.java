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
import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.application.Platform;
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

public class AddNewCustomerController {

    private SceneSwitch sceneSwitch;
    private Customer customer;
    private VehicleDAO vehicleDAO;
    private CustomerDAO customerDAO;
    private DiscountDAO discountDAO;
    private ArrayList<Vehicle> vehicles;
    private HashMap<String,Vehicle> vehicleHashMap;
    private RequiredFieldValidator fieldValidator;
    private Vehicle vehicle;

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
    private AnchorPane customerMainAnchorPane;

    @FXML
    private JFXTextField phoneNoField;

    @FXML
    private Label availableCarsHeading;

    @FXML
    private Label selectedCarsHeading;

    @FXML
    private JFXListView<Label> selectedCarList;


    @FXML
    private JFXButton addNewCarBtn;

    @FXML
    private JFXButton removeCarBtn;

    @FXML
    private JFXButton confirmCarBtn;

    @FXML
    void confirmCarBtnClicked(ActionEvent event) {
        customerDAO = new CustomerDAO();
        System.out.println("Confirm btn clicked");
        vehicle = CustomerHelper.getInstance().getVehicle();
        System.out.println(vehicle.getName());
        int customerID = customerDAO.getAll().size() + 1;
        vehicle.setCustomerID(Integer.toString(customerID));
        Label label = new Label(vehicle.getName() + ":" + vehicle.getRegNo());
        vehicleHashMap.put(label.getText(), vehicle);
        selectedCarList.getItems().add(label);
    }


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
        customerDAO = new CustomerDAO();
        CustomerHelper.getInstance().setCurrentCustomerID(customerDAO.getCount() + 1);
        sceneSwitch.activateScene(NavigationModel.CONFIGURE_DISCOUNT_ID, backBtn.getScene());
    }

    @FXML
    void backBtnClicked(ActionEvent event) {
        sceneSwitch.switchScene(NavigationModel.CUSTOMER_MAIN_ID);
        resetView();
    }

    public void resetView() {
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
        vehicleHashMap.clear();
    }


    @FXML
    void saveBtnClicked(ActionEvent event) {
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
            Vehicle tmpVehicle = vehicleHashMap.get(selectedCarList.getItems().get(j).getText());
            String regID = vehicleHashMap.get(selectedCarList.getItems().get(j).getText()).getRegistrationID();
            System.out.println("Reg ID : " + regID);
            if (vehicle.getLastMOT() == null) {
                vehicleDAO.saveWithoutMOT(vehicle);
            } else {
                vehicleDAO.save(tmpVehicle);
            }
        }

        if (accountHolderRadio.isSelected()) {
            discountDAO.save(CustomerHelper.getInstance().getDiscount());
        }
        SystemAlert alert = new SystemAlert(AddNewCustomerStackPane, "Success!", "Added customer to the db");

    }

    @FXML
    void addNewCarBtnClicked(ActionEvent event) throws IOException {
        CustomerHelper.getInstance().setLastCall(NavigationModel.ADD_NEW_CUSTOMER_ID);
        sceneSwitch.activateScene(NavigationModel.ADD_CUSTOMER_TO_CAR_ID, backBtn.getScene());
    }

    @FXML
    void removeCarBtnClicked(ActionEvent event) {
        if (selectedCarList.getSelectionModel().getSelectedItem() == null) {
            SystemAlert alert = new SystemAlert(AddNewCustomerStackPane, "Error", "Please select a vehicle from the list");
        } else {
            int j = selectedCarList.getSelectionModel().getSelectedIndex();
            vehicleHashMap.remove(selectedCarList.getItems().get(j).getText());
            selectedCarList.getItems().remove(j);
        }
    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(AddNewCustomerStackPane, NavigationModel.ADD_NEW_CUSTOMER_ID);
        fieldValidator = new RequiredFieldValidator();

        setupFieldValidators();

        usernameLbl.setText(DBLogic.getDBInstance().getUsername());
        usertypeLbl.setText(DBLogic.getDBInstance().getUser_type());
        vehicleHashMap = new HashMap<>();
    }

    public void setupFieldValidators() {
        // Add Validators
       // firstNameField.getValidators().add(fieldValidator);
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

        for (Node node : customerMainAnchorPane.getChildren()) {
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

