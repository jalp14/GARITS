package TwentyThreeProductions.Controller.Jobs.NewJob;

import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

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

    }

    @FXML
    void existingCustomerBtnClicked(ActionEvent event) {

    }

    @FXML
    void newCustomerBtnClicked(ActionEvent event) {

    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(NewJobMenuStackPane, NavigationModel.NEW_JOB_MENU_ID);
    }

}
