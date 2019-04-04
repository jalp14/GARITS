package TwentyThreeProductions.Controller.Jobs;

import TwentyThreeProductions.Model.DBLogic;
import TwentyThreeProductions.Model.Database.DBServer;
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

    // The system simply moves back to the main screen for the user, whether they are a mechanic or one of the other users
    // with access to the Jobs page.
    @FXML
    void backBtnClicked(ActionEvent event) {
        if(usertypeLbl.getText().equals("MECHANIC")) {
            sceneSwitch.switchScene(NavigationModel.MAIN_MECHANIC_ID);
        }
        else {
            sceneSwitch.switchScene(NavigationModel.MAIN_FFR_ID);
        }
    }

    // The system moves to the page for monitoring/editing an existing job.
    @FXML
    void monitorEditJobBtnClicked(ActionEvent event) throws IOException {
        sceneSwitch.activateSceneAlways(NavigationModel.EDIT_MONITOR_CHOOSE_ID, backBtn.getScene());
    }

    // The system moves to the page for creating a new job.
    @FXML
    void newJobBtnClicked(ActionEvent event) throws IOException {
        sceneSwitch.activateScene(NavigationModel.NEW_JOB_MENU_ID, backBtn.getScene());
    }

    // This function is called when the page is opened, and simply adds the scene to the list of active scenes and
    // sets the labels for the username and usertype to the appropriate values from within the system database.
    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(JobsMainStackPane, NavigationModel.JOBS_MAIN_ID);
        usernameLbl.setText(DBLogic.getDBInstance().getUsername());
        usertypeLbl.setText(DBLogic.getDBInstance().getUser_type());

    }

}
