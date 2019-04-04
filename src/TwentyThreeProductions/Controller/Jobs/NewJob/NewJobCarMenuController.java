package TwentyThreeProductions.Controller.Jobs.NewJob;

import TwentyThreeProductions.Model.CustomerReference;
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

public class NewJobCarMenuController {

    private SceneSwitch sceneSwitch;

    private CustomerReference customerReference;

    @FXML
    private StackPane newJobCarMenuStackPane;

    @FXML
    private JFXButton newCarBtn;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text usertypeLbl;

    @FXML
    private Label welcomeMessage;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton existingCarBtn;

    @FXML
    private Label customerNameLbl;

    @FXML
    private JFXButton partOnlyBtn;

    // The system goes back to the page for selecting whether to add a new job for an existing customer or a new customer.
    @FXML
    void backBtnClicked(ActionEvent event) {
        sceneSwitch.switchScene(NavigationModel.NEW_JOB_MENU_ID);
    }


    // The system moves to the page for creating a job using an existing vehicle.
    @FXML
    void existingCarBtnClicked(ActionEvent event) throws IOException {
        sceneSwitch.activateSceneAlways(NavigationModel.NEW_JOB_EXISTING_VEHICLE_ID, backBtn.getScene());
    }


    // The system moves to the page for creating a job using a new vehicle.
    @FXML
    void newCarBtnClicked(ActionEvent event) throws IOException {
        sceneSwitch.activateSceneAlways(NavigationModel.NEW_JOB_NEW_VEHICLE_ID, backBtn.getScene());
    }

    // The system moves to the page for creating a job using only parts.
    @FXML
    void partOnlyBtnClicked(ActionEvent event) throws IOException {
        sceneSwitch.activateSceneAlways(NavigationModel.PART_ONLY_SELECT_ID, backBtn.getScene());
    }

    // This function is called when the page is opened, and simply adds the scene to the list of active scenes and
    // sets the labels for the username, usertype and customer name to the appropriate values from within both the system
    // database and the appropriate static class.
    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(newJobCarMenuStackPane, NavigationModel.NEW_JOB_CAR_MENU_ID);
        usernameLbl.setText(DBLogic.getDBInstance().getUsername());
        usertypeLbl.setText(DBLogic.getDBInstance().getUser_type());
        customerReference = CustomerReference.getInstance();
        customerNameLbl.setText("Name: " + customerReference.getCustomer().getFirstName() + " " + customerReference.getCustomer().getLastName());
    }

}
