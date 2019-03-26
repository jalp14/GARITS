package TwentyThreeProductions.Controller.Jobs;

import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class JobsMainController {

    private SceneSwitch sceneSwitch;

    @FXML
    private StackPane JobsMainStackPane;

    @FXML
    private JFXButton newJobBtn;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text usertypeLbl;

    @FXML
    private Label welcomeMessage;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton monitorEditJobBtn;

    @FXML
    void backBtnClicked(ActionEvent event) {
        if(usertypeLbl.getText().equals("Mechanic")) {
            sceneSwitch.switchScene(NavigationModel.MAIN_MECHANIC_ID);
        }
        else {
            sceneSwitch.switchScene(NavigationModel.MAIN_FFR_ID);
        }
    }

    @FXML
    void monitorEditJobBtnClicked(ActionEvent event) throws IOException {
        sceneSwitch.activateSceneAlways(NavigationModel.EDIT_MONITOR_CHOOSE_ID, backBtn.getScene());
    }

    @FXML
    void newJobBtnClicked(ActionEvent event) throws IOException {
        sceneSwitch.activateScene(NavigationModel.NEW_JOB_MENU_ID, backBtn.getScene());
    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(JobsMainStackPane, NavigationModel.JOBS_MAIN_ID);
    }

}
