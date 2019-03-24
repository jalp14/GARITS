package TwentyThreeProductions.Controller.Jobs.NewJob;

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

    @FXML
    void backBtnClicked(ActionEvent event) {
        sceneSwitch.switchScene(NavigationModel.JOBS_MAIN_ID);
    }

    @FXML
    void existingCustomerBtnClicked(ActionEvent event) throws IOException {
        sceneSwitch.activateScene(NavigationModel.NEW_JOB_EXISTING_CUSTOMER_ID, backBtn.getScene());
    }

    @FXML
    void newCustomerBtnClicked(ActionEvent event) throws IOException {
        sceneSwitch.activateScene(NavigationModel.NEW_JOB_NEW_CUSTOMER_ID, backBtn.getScene());
    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(NewJobMenuStackPane, NavigationModel.NEW_JOB_MENU_ID);
    }

}
