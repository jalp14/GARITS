package TwentyThreeProductions.Controller;

import TwentyThreeProductions.Model.DBLogic;
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
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;

public class LoginController {

    private DBLogic sqlController;

    public enum user_type {
        NONE,
        ADMIN,
        FRANCHISEE,
        RECEPTIONIST,
        FOREPERSON,
        MECHANIC
    }

    private user_type type = user_type.NONE;

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
            if (sqlController.verifyUser(temp1, temp2) == true) {
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

    public void initialize() {
        System.out.println("New Login Controller");
        System.out.println("Init DB");
        sqlController = new DBLogic();

    }

    public void showMainScene() throws IOException {
        URL url = new File("src/TwentyThreeProductions/View/MainScreen.fxml").toURI().toURL();
        SceneSwitch sceneSwitch = new SceneSwitch(url,url, forgotPasswordButton.getScene());
        sceneSwitch.switchScene();
    }

    public void detectUserType() {
        System.out.println("Detecting User");
        if (sqlController.getUser_type().equals("ADMIN")) {
            type = user_type.ADMIN;
        } else if (sqlController.getUser_type().equals("FRANCHISEE")) {
            type = user_type.FRANCHISEE;
        } else if (sqlController.getUser_type().equals("RECEPTIONIST")) {
            type = user_type.RECEPTIONIST;
        } else if (sqlController.getUser_type().equals("FOREPERSON")) {
            type = user_type.FOREPERSON;
        } else if (sqlController.getUser_type().equals("MECHANIC")) {
            type = user_type.MECHANIC;
        }
        System.out.println("Detected User : " + type);
    }

}
