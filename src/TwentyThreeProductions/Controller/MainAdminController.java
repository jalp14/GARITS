package TwentyThreeProductions.Controller;


import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class MainAdminController {

    private SceneSwitch sceneSwitch;

    @FXML
    private JFXButton usersBtn;

    @FXML
    private JFXButton backuprestoreBtn;

    @FXML
    private JFXButton notifsBtn;

    @FXML
    private JFXButton logoutBtn;

    @FXML
    private Label welcomeMessage;


    @FXML
    void backuprestoreBtnClicked(ActionEvent event) {

    }

    @FXML
    void logoutBtnPressed(ActionEvent event) throws IOException {
        System.out.println("Logout pressed");
        sceneSwitch.switchScene("Login");
    }

    @FXML
    void notifsBtnClicked(ActionEvent event) {

    }


    @FXML
    void usersClicked(ActionEvent event) throws IOException {
        System.out.println("Users Clicked");
        sceneSwitch.activateScene("Users", logoutBtn.getScene());

    }

    public void initialize() {
        System.out.println("New Login Controller");
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(logoutBtn.getParent(),"MainAdmin");
    }

}