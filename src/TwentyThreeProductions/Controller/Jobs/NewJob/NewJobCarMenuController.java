package TwentyThreeProductions.Controller.Jobs.NewJob;

import TwentyThreeProductions.Model.CustomerReference;
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

    @FXML
    void backBtnClicked(ActionEvent event) {
        sceneSwitch.switchScene(NavigationModel.NEW_JOB_MENU_ID);
    }

    @FXML
    void existingCarBtnClicked(ActionEvent event) throws IOException {
        sceneSwitch.activateSceneAlways(NavigationModel.NEW_JOB_EXISTING_VEHICLE_ID, backBtn.getScene());
    }

    @FXML
    void newCarBtnClicked(ActionEvent event) throws IOException {
        sceneSwitch.activateSceneAlways(NavigationModel.NEW_JOB_NEW_VEHICLE_ID, backBtn.getScene());
    }

    @FXML
    void partOnlyBtnClicked(ActionEvent event) throws IOException {
        sceneSwitch.activateSceneAlways(NavigationModel.PART_ONLY_SELECT_ID, backBtn.getScene());
    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(newJobCarMenuStackPane, NavigationModel.NEW_JOB_CAR_MENU_ID);
        customerReference = CustomerReference.getInstance();
        customerNameLbl.setText("Name: " + customerReference.getCustomer().getFirstName() + " " + customerReference.getCustomer().getLastName());
    }

}
