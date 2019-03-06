package TwentyThreeProductions.Controller;


import TwentyThreeProductions.Model.SceneSwitch;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainMechanicController {

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
    void logoutBtnPressed(ActionEvent event) {
        backToLogin();
    }

    @FXML
    void notifsBtnClicked(ActionEvent event) {

    }

    public void backToLogin() {
        SceneSwitch sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.switchScene("Login");
    }

    public void initialize() {
        System.out.println("Init Mechanic Controller");
    }

}
