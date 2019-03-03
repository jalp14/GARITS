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
                NavigationModel.detectUserType();
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
        sceneSwitch.addScene(forgotPasswordButton.getScene(), forgotPasswordButton.getParent(), "MainAdmin");
        sceneSwitch.activateScene("MainAdmin");
    }






}