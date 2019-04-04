package TwentyThreeProductions.Controller.Customer;

import TwentyThreeProductions.Model.DBLogic;
import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class CustomersMainController {
///////////////////////////// Main Customer Form \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    private SceneSwitch sceneSwitch;

    @FXML
    private StackPane CustomersMainStackPane;

    @FXML
    private JFXButton addNewCustomerBtn;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text usertypeLbl;

    @FXML
    private Label welcomeMessage;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton editCustomerBtn;

    @FXML
    private JFXButton removeCustomerBtn;

    @FXML
    void addNewCustomerBtnClicked(ActionEvent event) throws IOException {
        // Take user to the Add New Customer form
        sceneSwitch.activateScene(NavigationModel.ADD_NEW_CUSTOMER_ID, backBtn.getScene());
    }

    @FXML
    void backBtnClicked(ActionEvent event) {
        // Take user to the previous form
        sceneSwitch.switchScene(NavigationModel.MAIN_FFR_ID);
    }

    @FXML
    void editCustomerBtnClicked(ActionEvent event) throws IOException {
        // Take user to the Edit Customer Form
        sceneSwitch.activateScene(NavigationModel.EDIT_CUSTOMER_ID, backBtn.getScene());
    }

    @FXML
    void removeCustomerBtnClicked(ActionEvent event) throws IOException {
        // Take user to the Remove Customer Form
        sceneSwitch.activateScene(NavigationModel.REMOVE_CUSTOMER_ID, backBtn.getScene());
    }

    public void initialize() {
        // Initialise the current form
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(CustomersMainStackPane, NavigationModel.CUSTOMER_MAIN_ID);
        usernameLbl.setText(DBLogic.getDBInstance().getUsername());
        usertypeLbl.setText(DBLogic.getDBInstance().getUser_type());
    }

}

