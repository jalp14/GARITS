package TwentyThreeProductions.Controller.Jobs;

import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

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

    }

    @FXML
    void monitorEditJobBtnClicked(ActionEvent event) {

    }

    @FXML
    void newJobBtnClicked(ActionEvent event) {

    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(JobsMainStackPane, NavigationModel.JOBS_MAIN_ID);
    }

}
