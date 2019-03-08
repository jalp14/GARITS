package TwentyThreeProductions.Controller;

import TwentyThreeProductions.Model.DBLogic;
import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DbManagementController {

    private DBLogic dbController;
    private SceneSwitch sceneSwitch;

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
    private JFXButton restoreBackupBtn;

    @FXML
    void addUserBtnClicked(ActionEvent event) {
        try {
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            Process proc = Runtime.getRuntime().exec(new String[]{"./test.sh", timeStamp});
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    @FXML
    void backBtnClicked(ActionEvent event) {
        sceneSwitch.switchScene("MainAdmin");
    }

    public void initialize() throws IOException {
        System.out.println("New DB Controller");
        System.out.println("System Command Generate DB Element");
        dbController = DBLogic.getDBInstance();
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(newBackupBtn.getParent(), "DBManagement");
    }

}
