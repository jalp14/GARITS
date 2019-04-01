package TwentyThreeProductions.Controller.Users;

import TwentyThreeProductions.Model.DBLogic;
import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class UserManagementController {

    private Stage tmpStage;
    private SceneSwitch sceneSwitch;

    @FXML
    private JFXButton addNewUserBtn;

    @FXML
    private JFXButton editUserBtn;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text usertypeLbl;

    @FXML
    private StackPane usersMainStackPane;

    @FXML
    private Label welcomeMessage;

    @FXML
    private JFXButton backBtn;

    @FXML
    void addNewUserBtnClicked(ActionEvent event) throws IOException {
        System.out.println("Add user btn clicked");
        sceneSwitch.activateScene(NavigationModel.ADD_NEW_USER_ID, backBtn.getScene());
    }

    @FXML
    void backBtnClicked(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(NavigationModel.MAIN_ADMIN_ID);
    }

    @FXML
    void editUserBtnClicked(ActionEvent event) throws IOException {
        sceneSwitch.activateScene(NavigationModel.EDIT_USER_ID, backBtn.getScene());
    }

    @FXML
    void notifsBtnClicked(ActionEvent event) {

    }

    public void initialize() {
        System.out.println("New Users Controller");
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(usersMainStackPane, NavigationModel.USER_MANAGEMENT_ID);
        usernameLbl.setText(DBLogic.getDBInstance().getUsername());
        usertypeLbl.setText(DBLogic.getDBInstance().getUser_type());
    }

}
