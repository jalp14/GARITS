package TwentyThreeProductions.Controller.Database;

import TwentyThreeProductions.Model.DBLogic;
import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class DbManagementController {

    private SceneSwitch sceneSwitch;
    private DBLogic dbController;

    @FXML
    private JFXButton newBackupBtn;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text usertypeLbl;

    @FXML
    private Label welcomeMessage;

    @FXML
    private JFXButton backBtn;

    @FXML
    private StackPane dbMngtStackPane;

    @FXML
    private JFXButton restoreBackupBtn;

    @FXML
    void backBtnClicked(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(NavigationModel.MAIN_ADMIN_ID);
    }

    @FXML
    void newBackupBtnClicked(ActionEvent event) {
        try {
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            Process proc = Runtime.getRuntime().exec(new String[]{"./backup.sh", timeStamp});
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    @FXML
    void restoreBackupBtnClicked(ActionEvent event) throws IOException {
        System.out.println("Backup/Restore pressed");
        sceneSwitch.activateScene(NavigationModel.DB_RESTORE_ID, backBtn.getScene());
    }


    public void initialize() throws IOException {
        System.out.println("New DB Controller");
        System.out.println("System Command Generate DB Element");
        dbController = DBLogic.getDBInstance();
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(dbMngtStackPane, NavigationModel.DB_MANAGEMENT_ID);
    }

}


