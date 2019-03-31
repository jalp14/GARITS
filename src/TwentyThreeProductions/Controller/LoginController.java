package TwentyThreeProductions.Controller;

import TwentyThreeProductions.Model.DBLogic;
import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import TwentyThreeProductions.Model.SystemAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.net.URL;
import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    private SceneSwitch sceneSwitch;
    private URL currentURL;
    private URL previousURL;


    private DBLogic dbController;

    @FXML
    private Pane mainPane;

    @FXML
    private JFXTextField usernameField;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private JFXButton loginButton;

    @FXML
    private StackPane loginStackPane;

    @FXML
    private JFXButton forgotPasswordButton;

    @FXML
    private JFXButton settingsBtn;

    @FXML
    void settingsBtnClicked(ActionEvent event) throws IOException {
        sceneSwitch.activateScene(NavigationModel.SETTINGS_ID, loginButton.getScene());
    }

    @FXML
    void forgotClicked(ActionEvent event) throws IOException  {
        System.out.println("Forgot clicked");

    }

    @FXML
    void loginClicked(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        System.out.println("Login Clicked");
        // Passing user inputs to the DB
        dbController.setLoginDetails(usernameField.getText(), passwordField.getText());
        // Check if login details match
        if (dbController.verifyUser() == true) {
            NavigationModel.detectUserType(loginButton.getScene());
            System.out.println("Setting up homescreen");
        } else {

            System.out.println("User not found, please try again");
            SystemAlert systemAlert = new SystemAlert(loginStackPane, "Login Error", "Wrong username or password typed");
        }

    }

    public void initialize() throws IOException {
        System.out.println("New Login Controller");
        System.out.println("System Command Generate DB Element");
        currentURL = new File(NavigationModel.LOGIN_URL).toURI().toURL();
        previousURL = null;
        dbController = DBLogic.getDBInstance();
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(loginStackPane, NavigationModel.LOGIN_ID);
        loginButton.setDefaultButton(true);
    }

}