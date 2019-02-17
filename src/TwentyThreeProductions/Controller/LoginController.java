package TwentyThreeProductions.Controller;


import TwentyThreeProductions.Model.DBLogic;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;

import java.sql.SQLException;

public class LoginController {

    private DBLogic sqlController;

    @FXML
    private JFXTextField usernameField;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private JFXButton loginButton;

    @FXML
    private JFXButton forgotPasswordButton;

    @FXML
    void forgotClicked(ActionEvent event) {
        System.out.println("Forgot clicked");
    }

    @FXML
    void loginClicked(ActionEvent event) {
        System.out.println("Login Clicked");
        String temp1, temp2;
        temp1 = usernameField.getText();
        temp2 = passwordField.getText();
        try {
            if (sqlController.readFromTable(temp1, temp2) == true) {
                System.out.println("User verified");
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
        sqlController = new DBLogic();
    }

}
