package TwentyThreeProductions.Controller.Jobs.NewJob;

import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class NewJobCarMenuController {

    private SceneSwitch sceneSwitch;

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

    }

    @FXML
    void existingCarBtnClicked(ActionEvent event) {

    }

    @FXML
    void newCarBtnClicked(ActionEvent event) {

    }

    @FXML
    void partOnlyBtnClicked(ActionEvent event) {

    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(newJobCarMenuStackPane, NavigationModel.NEW_JOB_CAR_MENU_ID);
    }

}