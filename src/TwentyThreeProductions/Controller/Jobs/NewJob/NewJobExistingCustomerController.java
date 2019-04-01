package TwentyThreeProductions.Controller.Jobs.NewJob;

import TwentyThreeProductions.Domain.Customer;
import TwentyThreeProductions.Model.CustomerReference;
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

    @FXML
    void backBtnClicked(ActionEvent event) {
        customerList.getSelectionModel().select(null);
        customerList.getItems().clear();
        customerHashMap.clear();
        searchField.setText("");
        refreshList();
        sceneSwitch.switchScene(NavigationModel.NEW_JOB_MENU_ID);
    }

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
        String searchTerm = searchField.getText();
        customerList.getItems().clear();
        customerHashMap.clear();
        if(searchTerm.isEmpty()) {
            refreshList();
        }
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

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        customerReference = CustomerReference.getInstance();
        sceneSwitch.addScene(newJobExistingCustomerStackPane, NavigationModel.NEW_JOB_EXISTING_CUSTOMER_ID);
        customerHashMap = new HashMap<>();
        refreshList();
    }

    public void refreshList() {
        CustomerDAO customerDAO = new CustomerDAO();
        for(Customer c: customerDAO.getAll()) {
            Label customerLabel = new Label("ID: " + c.getCustomerID() + " / Name: " + c.getFirstName() + " " + c.getLastName());
            customerHashMap.put(customerLabel.getText(), c);
            customerList.getItems().add(customerLabel);
        }
    }
}


