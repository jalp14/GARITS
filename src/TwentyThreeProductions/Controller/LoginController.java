package TwentyThreeProductions.Controller;

import TwentyThreeProductions.Model.DBLogic;
import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

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
    private JFXButton forgotPasswordButton;

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
        }

    }


    public void initialize() throws IOException {
        System.out.println("New Login Controller");
        System.out.println("System Command Generate DB Element");
        currentURL = new File(NavigationModel.LOGIN_URL).toURI().toURL();
        previousURL = null;
        dbController = DBLogic.getDBInstance();
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(forgotPasswordButton.getParent(), "Login");
        loginButton.setDefaultButton(true);
    }

}