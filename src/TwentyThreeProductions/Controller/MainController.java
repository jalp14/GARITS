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
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class MainController {

    @FXML
    private Button backButton;

    @FXML
    void goBack(ActionEvent event) {

    }

    @FXML
    private Button logoutButton;

    @FXML
    void logoutPressed(ActionEvent event) throws IOException {
        System.out.println("Logout pressed");
        backToLogin();
    }

    public void initialize() {
        System.out.println("New Login Controller");
        if (NavigationModel.getType().toString().equals("ADMIN")) {
            System.out.println("Type : ADMIN");
         //   logoutButton.setVisible(false);
        }

    }

    public void backToLogin() throws IOException {
        Stage tmpStage = (Stage) logoutButton.getScene().getWindow();
        SceneSwitch sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.switchScene("Login");
    }

}
