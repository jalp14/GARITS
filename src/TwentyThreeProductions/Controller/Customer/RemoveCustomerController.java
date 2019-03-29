package TwentyThreeProductions.Controller.Customer;

import TwentyThreeProductions.Domain.Customer;
import TwentyThreeProductions.Model.Database.DAO.VehicleDAO;
import TwentyThreeProductions.Model.Database.DAO.CustomerDAO;
import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import TwentyThreeProductions.Model.SystemAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class RemoveCustomerController {

    private SceneSwitch sceneSwitch;
    private ArrayList<Customer> customers;
    private HashMap<String, Customer> customerHashMap;

    @FXML
    private StackPane RemoveCustomerStackPane;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text usertypeLbl;

    @FXML
    private Label welcomeMessage;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton removeCustomerBtn;

    @FXML
    private JFXTextField searchField;

    @FXML
    private JFXButton searchBtn;

    @FXML
    private JFXListView<Label> customerList;

    @FXML
    void backBtnClicked(ActionEvent event) {
        sceneSwitch.switchScene(NavigationModel.CUSTOMER_MAIN_ID);
    }

    @FXML
    void removeCustomerBtnClicked(ActionEvent event) {
        if (customerList.getSelectionModel().isEmpty()) {
            SystemAlert alert = new SystemAlert(RemoveCustomerStackPane, "Error", "Please select a customer from the list");
        } else {
            VehicleDAO carDAO = new VehicleDAO();
            CustomerDAO customerDAO = new CustomerDAO();
            Customer customer = customerHashMap.get(customerList.getSelectionModel().getSelectedItem().getText());
            customerList.getSelectionModel().select(null);
            carDAO.removeCustomer(customer.getCustomerID());
            customerDAO.delete(customer);
            SystemAlert alert = new SystemAlert(RemoveCustomerStackPane, "Success", "Customer successfully deleted");
        }
    }

    @FXML
    void searchTyped(KeyEvent event) {
        System.out.println("Query typed");
        CustomerDAO customerDAO = new CustomerDAO();
        String searchTerm = searchField.getText();
        customerList.getItems().clear();
        customerHashMap.clear();
        // Check if search term is blank or not
        if (searchTerm.isEmpty()) {
            setupListView();
        } else {
            for (Customer c : customerDAO.getAll()) {
                if (c.getCustomerID().contains(searchTerm) || c.getFirstName().contains(searchTerm) || c.getLastName().contains(searchTerm)) {
                    Label customerLabel = new Label("ID : " + c.getCustomerID() + " / FirstName : " + c.getFirstName() + " / LastName : " + c.getLastName());
                    customerHashMap.put(customerLabel.getText(), c);
                    customerList.getItems().add(customerLabel);
                }
            }
        }

    }




    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(RemoveCustomerStackPane, NavigationModel.REMOVE_CUSTOMER_ID);
        customerHashMap = new HashMap<>();
        setupListView();
    }

    public void setupListView() {
        CustomerDAO customerDAO = new CustomerDAO();
        customerList.setExpanded(true);
        customerList.setVerticalGap(10.0);
        customerList.setDepth(1);
        for (Customer c : customerDAO.getAll()) {
            Label customerLabel = new Label("ID : " + c.getCustomerID() + " / FirstName : " +  c.getFirstName() + " / LastName : " + c.getLastName());
            customerHashMap.put(customerLabel.getText(), c);
            customerList.getItems().add(customerLabel);
        }
    }

}
