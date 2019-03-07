package TwentyThreeProductions.Controller;

import TwentyThreeProductions.Model.SceneSwitch;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class UserManagementController {

    private Stage tmpStage;
    private SceneSwitch sceneSwitch;

    @FXML
    private JFXButton addUserBtn;

    @FXML
    private JFXButton notifsBtn;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text usertypeLbl;

    @FXML
    private Label welcomeMessage;

    @FXML
    private JFXButton editBtn;

    @FXML
    private JFXButton backBtn;

    @FXML
    void addUserBtnClicked(ActionEvent event) {

    }

    @FXML
    void backBtnClicked(ActionEvent event) throws IOException {
        sceneSwitch.switchScene("MainAdmin");
    }

    @FXML
    void editBtnClicked(ActionEvent event) {

    }

    @FXML
    void notifsBtnClicked(ActionEvent event) {

    }

    public void initialize() {
        System.out.println("New Users Controller");
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(addUserBtn.getParent(), "Users");
    }

}
