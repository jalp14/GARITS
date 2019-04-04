package TwentyThreeProductions.Controller.Database;

import TwentyThreeProductions.Model.DBLogic;
import TwentyThreeProductions.Model.HelperClasses.SettingsHelper;
import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import TwentyThreeProductions.Model.SystemAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.sun.scenario.Settings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class AutomaticBackupSettingsController {
///////////// Settings for automatically backing up the system on a given date \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    private SceneSwitch sceneSwitch;
    private SettingsHelper helper;


    @FXML
    private StackPane autoBackupSettingsStackPane;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text usertypeLbl;

    @FXML
    private Label welcomeMessage;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private Label lastBackupLbl;

    @FXML
    private JFXDatePicker backupDate;

    @FXML
    private Label nextBackupLbl;

    @FXML
    void backBtnClicked(ActionEvent event) {
        // Take user back to the previous form
        sceneSwitch.switchScene(NavigationModel.DB_MANAGEMENT_ID);
    }

    @FXML
    void saveBtnClicked(ActionEvent event) {
        // Save current settings
        System.out.println(helper.checkDate());
    }

    @FXML
    void dateChanged(ActionEvent event) {
        // If date is changed append the changes to the local file
        if (backupDate.getValue() == null) {
            SystemAlert alert = new SystemAlert(autoBackupSettingsStackPane, "Error", "Please select a date");
        } else { // Set the next backup date
            helper.setBackupDate(backupDate.getValue().toString());
            System.out.println(backupDate.getValue().toString());
            helper.writeDate();
            nextBackupLbl.setText(helper.setupNextMonth());
        }
    }

    public void initialize() {
        // Initialise the current form and set all the necessary constraints
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(autoBackupSettingsStackPane, NavigationModel.AUTOMATIC_BACKUP_SETTINGS_ID);
        usernameLbl.setText(DBLogic.getDBInstance().getUsername());
        usertypeLbl.setText(DBLogic.getDBInstance().getUser_type());
        helper = SettingsHelper.getInstance();
    }

}

