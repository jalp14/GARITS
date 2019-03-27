package TwentyThreeProductions.Controller.MainScreen;

import TwentyThreeProductions.Model.DBLogic;
import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.Notification.Notification;
import TwentyThreeProductions.Model.SceneSwitch;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class MainAdminController {

    private SceneSwitch sceneSwitch;

    @FXML
    private StackPane mainScreenAdminStackPane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXButton usersBtn;

    @FXML
    private JFXButton backuprestoreBtn;

    @FXML
    private JFXButton notifsBtn;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text userTypeLbl;

    @FXML
    private JFXButton logoutBtn;

    @FXML
    private Label welcomeMessage;


    @FXML
    void backuprestoreBtnClicked(ActionEvent event) throws IOException {
        System.out.println("Backup/Restore pressed");
        sceneSwitch.activateScene(NavigationModel.DB_MANAGEMENT_ID, logoutBtn.getScene());
    }

    @FXML
    void logoutBtnPressed(ActionEvent event) throws IOException {
        System.out.println("Logout pressed");
        sceneSwitch.resetSceneMap();
        sceneSwitch.activateScene(NavigationModel.LOGIN_ID, logoutBtn.getScene());
    }

    @FXML
    void notifsBtnClicked(ActionEvent event) {
        System.out.println("Notifications Btn Clicked");
        Notification notification = new Notification(mainScreenAdminStackPane);
        notification.setNotificationMessage("This is a test notification");
        notification.showNotification();
    }


    @FXML
    void usersBtnClicked(ActionEvent event) throws IOException {
        System.out.println("Users Clicked");
        sceneSwitch.activateScene(NavigationModel.USER_MANAGEMENT_ID, logoutBtn.getScene());
    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(mainScreenAdminStackPane,NavigationModel.MAIN_ADMIN_ID);
        setLblConstraints();
    }


    public void setLblConstraints() {
        welcomeMessage.setText("Welcome " + DBLogic.getDBInstance().getUsername());
        usernameLbl.setText(DBLogic.getDBInstance().getUsername());
        userTypeLbl.setText(DBLogic.getDBInstance().getUserType());
    }
}