package TwentyThreeProductions.Controller.Customer;

import TwentyThreeProductions.Domain.Car;
import TwentyThreeProductions.Domain.Customer;
import TwentyThreeProductions.Model.Database.DAO.CarDAO;
import TwentyThreeProductions.Model.Database.DAO.CustomerDAO;
import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import TwentyThreeProductions.Model.SystemAlert;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
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
    private CarDAO carDAO;
    private CustomerDAO customerDAO;
    private ArrayList<Car> cars;
    private HashMap<String,Car> carHashMap;

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
    void backBtnClicked(ActionEvent event) {
        sceneSwitch.switchScene(NavigationModel.CUSTOMER_MAIN_ID);
    }


    @FXML
    void saveBtnClicked(ActionEvent event) {
        customerDAO = new CustomerDAO();
        customer = new Customer();
        carDAO = new CarDAO();
        customer.setFirstName(firstNameField.getText());
        customer.setLastName(lastNameField.getText());
        customer.setCustomerAddress(addressOneField.getText());
        customer.setCustomerPostcode(postcodeField.getText());
        customer.setCustomerPhone(phoneNoField.getText());
        customer.setCustomerEmail(emailField.getText());
        customer.setCustomerType(determineType());
        // Add selected cars to the array list
        for (int i = 0; i < selectedCarList.getItems().size(); i++) {
            cars.add(getCar(selectedCarList.getItems().get(i).getText()));
        }
        customer.setLatePayment(latePaymentCheckbox.isDisable());
        customerDAO.save(customer);
        SystemAlert alert = new SystemAlert(AddNewCustomerStackPane, "Success!", "Added customer to the db");

    }

    public void assignCarToLabel(String label, Car car) {
        carHashMap.put(label, car);
    }

    public Car getCar(String label) {
        return carHashMap.get(label);
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
        sceneSwitch.addScene(AddNewCustomerStackPane, NavigationModel.ADD_NEW_CUSTOMER_ID);
        carHashMap = new HashMap<>();
        loadCars();
    }

    public void loadCars() {
        carDAO = new CarDAO();
        cars = carDAO.getAvailableCars();
        for (int i = 0; i < cars.size(); i++) {
            Car tmpCar = cars.get(i);
            Label tmpLabel = new Label(tmpCar.getModel());
            availableCarsCombi.getItems().add(tmpLabel);
            assignCarToLabel(tmpLabel.getText(), tmpCar);
            System.out.println(carHashMap.size());
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

