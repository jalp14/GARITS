package TwentyThreeProductions.Controller.Jobs.NewJob;

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

public class NewJobMenuController {

    private SceneSwitch sceneSwitch;

    @FXML
    private StackPane NewJobMenuStackPane;

    @FXML
    private JFXButton newCustomerBtn;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text usertypeLbl;

    @FXML
    private Label welcomeMessage;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton existingCustomerBtn;

    // The system moves to the main job page.
    @FXML
    void backBtnClicked(ActionEvent event) {
        sceneSwitch.switchScene(NavigationModel.JOBS_MAIN_ID);
    }

    // The system moves to the page for creating a job using an existing customer.
    @FXML
    void existingCustomerBtnClicked(ActionEvent event) throws IOException {
        sceneSwitch.activateSceneAlways(NavigationModel.NEW_JOB_EXISTING_CUSTOMER_ID, backBtn.getScene());
    }

    // The system moves to the page for creating a job using a new customer.
    @FXML
    void newCustomerBtnClicked(ActionEvent event) throws IOException {
        sceneSwitch.activateScene(NavigationModel.NEW_JOB_NEW_CUSTOMER_ID, backBtn.getScene());
    }

    // This function is called when the page is opened, and simply adds the scene to the list of active scenes and
    // sets the labels for the username and usertype to the appropriate values from within the system
    // database.
    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(NewJobMenuStackPane, NavigationModel.NEW_JOB_MENU_ID);
        usernameLbl.setText(DBLogic.getDBInstance().getUsername());
        usertypeLbl.setText(DBLogic.getDBInstance().getUser_type());
    }

}
