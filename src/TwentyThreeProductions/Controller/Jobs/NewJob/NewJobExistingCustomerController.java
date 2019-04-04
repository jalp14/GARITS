package TwentyThreeProductions.Controller.Jobs.NewJob;

import TwentyThreeProductions.Domain.Customer;
import TwentyThreeProductions.Model.*;
import TwentyThreeProductions.Model.Database.DAO.CustomerDAO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.HashMap;

public class NewJobExistingCustomerController {

    private SceneSwitch sceneSwitch;

    private CustomerReference customerReference;

    private HashMap<String, Customer> customerHashMap;

    @FXML
    private StackPane newJobExistingCustomerStackPane;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text usertypeLbl;

    @FXML
    private Label welcomeMessage;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton nextBtn;

    @FXML
    private Label formLabel;

    @FXML
    private JFXTextField searchField;

    @FXML
    private JFXButton searchBtn;

    @FXML
    private JFXListView<Label> customerList;

    // The system clears the currently selected customer, the list of customers, the hashmap for it and the search term before
    // refreshing the list and moving back to the previous page.
    @FXML
    void backBtnClicked(ActionEvent event) {
        customerList.getSelectionModel().select(null);
        customerList.getItems().clear();
        customerHashMap.clear();
        searchField.setText("");
        refreshList();
        sceneSwitch.switchScene(NavigationModel.NEW_JOB_MENU_ID);
    }

    // If the customer selection is empty, the system will produce an alert stating as much to the user. Otherwise, the
    // system stores the customer in the static class before moving to the page for choosing how to create a job.
    @FXML
    void nextBtnClicked(ActionEvent event) throws IOException {
        if(customerList.getSelectionModel().isEmpty()) {
            SystemAlert systemAlert = new SystemAlert(newJobExistingCustomerStackPane,
                    "Failure", "Customer not selected");
        }
        else {
            customerReference.setCustomer(customerHashMap.get(customerList.getSelectionModel().getSelectedItem().getText()));
            customerList.getSelectionModel().select(null);
            customerList.getItems().clear();
            customerHashMap.clear();
            refreshList();
            sceneSwitch.activateSceneAlways(NavigationModel.NEW_JOB_CAR_MENU_ID, backBtn.getScene());
        }
    }

    @FXML
    void searchBtnClick(ActionEvent event) {
        // The system clears both the list and the hashmap and prepares the appropriate items in order to correctly
        // search for available customers.
        String searchTerm = searchField.getText();
        customerList.getItems().clear();
        customerHashMap.clear();

        // If the search term inputted by the user is empty, the system refreshes the list with every value available.
        if(searchTerm.isEmpty()) {
            refreshList();
        }

        // Otherwise, it only adds the customer that contain the currently inputted value as either their ID or name to
        // the list of available customers.
        else {
            CustomerDAO customerDAO = new CustomerDAO();
            for(Customer c: customerDAO.getAll()) {
                if(c.getCustomerID().contains(searchTerm) || (c.getFirstName() + " " + c.getLastName()).contains(searchTerm)) {
                    Label customerLabel = new Label("ID: " + c.getCustomerID() + " / Name: " + c.getFirstName() + " " + c.getLastName());
                    customerHashMap.put(customerLabel.getText(), c);
                    customerList.getItems().add(customerLabel);
                }
            }
        }
    }

    // This function is called up when the page is first opened, and it adds the scene to the list of currently
    // active scenes as well as changing the labels for the username and type with the currently logged in user,
    // and then finally it initialises the hashmap for storing the customers and refresh the list of currently available
    // customers well as gets the instance of the static class for storing the customers.
    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        customerReference = CustomerReference.getInstance();
        sceneSwitch.addScene(newJobExistingCustomerStackPane, NavigationModel.NEW_JOB_EXISTING_CUSTOMER_ID);
        usernameLbl.setText(DBLogic.getDBInstance().getUsername());
        usertypeLbl.setText(DBLogic.getDBInstance().getUser_type());
        customerHashMap = new HashMap<>();
        refreshList();
    }

    // This function goes through every customer in the system database, assigns them to a label to be put in the list
    // of available items as well as in the hashmap with an appropriate string reference.
    public void refreshList() {
        CustomerDAO customerDAO = new CustomerDAO();
        for(Customer c: customerDAO.getAll()) {
            Label customerLabel = new Label("ID: " + c.getCustomerID() + " / Name: " + c.getFirstName() + " " + c.getLastName());
            customerHashMap.put(customerLabel.getText(), c);
            customerList.getItems().add(customerLabel);
        }
    }
}


