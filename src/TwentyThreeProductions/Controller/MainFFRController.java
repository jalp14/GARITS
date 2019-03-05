package TwentyThreeProductions.Controller;

import TwentyThreeProductions.Model.SceneSwitch;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainFFRController {

    @FXML
    private JFXButton jobsBtn;

    @FXML
    private JFXButton customersBtn;

    @FXML
    private JFXButton notifsBtn;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text userTypeLbl;

    @FXML
    private JFXButton logoutBtn;

    @FXML
    private Label welcomeMessage;

    @FXML
    private JFXButton partsBtn;

    @FXML
    private JFXButton reportsBtn;

    @FXML
    void customersBtnClicked(ActionEvent event) {

    }

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

    @FXML
    void partsBtnClicked(ActionEvent event) {

    }

    @FXML
    void reportsBtnClicked(ActionEvent event) {

    }

    public void backToLogin() {
        Stage tmpStage = (Stage) logoutBtn.getScene().getWindow();
        SceneSwitch sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.switchScene("Login");
    }

    public void initialize() {
        System.out.println("Init FFR Controller");
    }

}
