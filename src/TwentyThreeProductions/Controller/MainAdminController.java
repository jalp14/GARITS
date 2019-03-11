package TwentyThreeProductions.Controller;

import TwentyThreeProductions.Model.DBLogic;
import TwentyThreeProductions.Model.SceneSwitch;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class MainAdminController {

    private SceneSwitch sceneSwitch;


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
    private StackPane stackPane;

    @FXML
    void backuprestoreBtnClicked(ActionEvent event) throws IOException {
        System.out.println("Backup/Restore pressed");
        sceneSwitch.activateScene("DBManagement", logoutBtn.getScene());
    }

    @FXML
    void logoutBtnPressed(ActionEvent event) {
        System.out.println("Logout pressed");
        sceneSwitch.switchScene("Login");
    }

    @FXML
    void notifsBtnClicked(ActionEvent event) {
        JFXDialog dialog;
        JFXDialogLayout dialogLayout;
        JFXButton okbtn;
        okbtn = new JFXButton("Ok");
        dialogLayout = new JFXDialogLayout();
        dialog = new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
        dialogLayout.setHeading(new Text("Hello"));
        dialogLayout.setBody(new Text("soakok asokfokdgo ogkod odkgo"));
        okbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });
        dialogLayout.setActions(okbtn);
        dialog.show();
    }

    @FXML
    void usersClicked(ActionEvent event) throws IOException {
        System.out.println("Users Clicked");
        sceneSwitch.activateScene("Users", logoutBtn.getScene());
    }


    public void initialize() {
        System.out.println("New Login Controller");
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(logoutBtn.getParent(),"MainAdmin");

    }

}