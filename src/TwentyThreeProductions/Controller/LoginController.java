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

public class LoginController {

    private DBLogic sqlController;

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
        blackMagic();
    }

    @FXML
    void loginClicked(ActionEvent event) {
        sqlController = new DBLogic();
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
        System.out.println("New Login Controller");

    }

    public void blackMagic() throws IOException {
        URL url = new File("src/TwentyThreeProductions/View/Garits.fxml").toURI().toURL();
        SceneSwitch sceneSwitch = new SceneSwitch(url,url, forgotPasswordButton.getScene());
        sceneSwitch.switchScene();
    }

}
