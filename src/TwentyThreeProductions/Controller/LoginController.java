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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.net.URL;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;

public class LoginController {

    private SceneSwitch sceneSwitch;
    private URL currentURL;
    private URL previousURL;
    public enum user_type {
        NONE,
        ADMIN,
        FRANCHISEE,
        RECEPTIONIST,
        FOREPERSON,
        MECHANIC
    }

    private user_type type = user_type.NONE;
    
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
    void loginClicked(ActionEvent event) throws IOException {
        System.out.println("Login Clicked");
        String temp1, temp2;
        temp1 = usernameField.getText();
        temp2 = passwordField.getText();
        try {
            if (dbController.verifyUser(temp1, temp2) == true) {
                System.out.println("User verified");
                detectUserType();
                showMainScene();
            } else {
                System.out.println("Wrong credentials");
            }
        } catch (SQLException se){
            se.printStackTrace();
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
        }
    }

    public void initialize() throws IOException {
        System.out.println("New Login Controller");
        System.out.println("System Command Generate DB Element");
        currentURL = new File(NavigationModel.LOGIN_URL).toURI().toURL();
        previousURL = null;
        dbController = DBLogic.getDBInstance();
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(forgotPasswordButton.getScene(), forgotPasswordButton.getParent(), "Login");

    }

    public void showMainScene() throws IOException {
        URL url = new File(NavigationModel.MAIN_URL).toURI().toURL();
        sceneSwitch.addScene(forgotPasswordButton.getScene(), forgotPasswordButton.getParent(), "Main");
        sceneSwitch.activateScene("Main");
    }

    public void detectUserType() {
        System.out.println("Detecting User");
        if (dbController.getUser_type().equals("ADMIN")) {
            type = user_type.ADMIN;
        } else if (dbController.getUser_type().equals("FRANCHISEE")) {
            type = user_type.FRANCHISEE;
        } else if (dbController.getUser_type().equals("RECEPTIONIST")) {
            type = user_type.RECEPTIONIST;
        } else if (dbController.getUser_type().equals("FOREPERSON")) {
            type = user_type.FOREPERSON;
        } else if (dbController.getUser_type().equals("MECHANIC")) {
            type = user_type.MECHANIC;
        }
        System.out.println("Detected User : " + type);
    }



}
