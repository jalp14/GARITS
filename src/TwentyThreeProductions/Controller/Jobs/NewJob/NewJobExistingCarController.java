package TwentyThreeProductions.Controller.Jobs.NewJob;

import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class NewJobExistingCarController {

    private SceneSwitch sceneSwitch;

    @FXML
    private StackPane newJobExistingCarStackPane;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text usertypeLbl;

    @FXML
    private Label welcomeMessage;

    @FXML
    private JFXButton backBtn;

    @FXML
    private Label customerNameLbl;

    @FXML
    private JFXButton nextBtn;

    @FXML
    private JFXListView<?> customerCarList;

    @FXML
    void backBtnClicked(ActionEvent event) {

    }

    @FXML
    void nextBtnClicked(ActionEvent event) {

    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(newJobExistingCarStackPane, NavigationModel.NEW_JOB_EXISTING_CAR_ID);
    }

}
