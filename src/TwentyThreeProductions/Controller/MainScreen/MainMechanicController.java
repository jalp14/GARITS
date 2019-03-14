package TwentyThreeProductions.Controller.MainScreen;


import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMechanicController {

    private SceneSwitch sceneSwitch;

    @FXML
    private StackPane mainScreenMechanicStackPane;

    @FXML
    private JFXButton jobsBtn;

    @FXML
    private JFXButton notifsBtn;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text usertypeLbl;

    @FXML
    private JFXButton logoutBtn;

    @FXML
    private Label welcomeMessage;

    @FXML
    void jobsBtnClicked(ActionEvent event) {

    }

    @FXML
    void logoutBtnPressed(ActionEvent event) throws IOException {
        System.out.println("Logout pressed");
        sceneSwitch.resetSceneMap();
        sceneSwitch.activateScene(NavigationModel.LOGIN_ID, logoutBtn.getScene());
    }

    @FXML
    void notifsBtnClicked(ActionEvent event) {

    }

    public void initialize() {
        System.out.println("Init Mechanic Controller");
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(mainScreenMechanicStackPane, NavigationModel.MAIN_MECHANIC_ID);
    }

}
