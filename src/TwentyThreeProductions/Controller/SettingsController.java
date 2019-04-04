package TwentyThreeProductions.Controller;


import TwentyThreeProductions.Domain.Backup;
import TwentyThreeProductions.Model.DBLogic;
import TwentyThreeProductions.Model.Database.DAO.BackupDAO;
import TwentyThreeProductions.Model.Database.DBServer;
import TwentyThreeProductions.Model.HelperClasses.ReportHelper;
import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SettingsController {

    private DBLogic dbController;
    private String selectedDBName;
    private ArrayList<Backup> backups;
    private Backup backup;
    private HashMap<String, Backup> backupHashMap;

    private SceneSwitch sceneSwitch;

    @FXML
    private StackPane settingsStackPane;

    @FXML
    private Pane mainPane;

    @FXML
    private JFXButton restoreBtn;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXComboBox<Label> restoreList;

    @FXML
    private JFXComboBox<Label> themeList;

    @FXML
    private JFXButton changeThemeBtn;

    @FXML
    void backBtnClicked(ActionEvent event) {
        sceneSwitch.switchScene(NavigationModel.LOGIN_ID);
    }

    @FXML
    void changeThemeBtnClicked(ActionEvent event) {

    }

    @FXML
    void restoreBtnClicked(ActionEvent event) {
        System.out.println("Restore button clicked");
        try {
            Backup backup = backupHashMap.get(restoreList.getSelectionModel().getSelectedItem().getText());
            Process proc = Runtime.getRuntime().exec(new String[]{"./restore.sh", backup.getFileLocation()});
            System.out.println("Restarting the sql server");
            DBServer.getInstance().restartServer();
            System.out.println("Server restarted, please login again");
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        dbController = DBLogic.getDBInstance();
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(settingsStackPane, NavigationModel.SETTINGS_ID);
        backups = new ArrayList<>();
        backupHashMap = new HashMap<>();
        loadBackups();
    }

    public void loadBackups() {
        BackupDAO backupDAO = new BackupDAO();
        backups = backupDAO.getAll();
        for (int i = 0; i < backups.size(); i++) {
            backup = backups.get(i);
            Label label = new Label(backup.getFileLocation());
            backupHashMap.put(label.getText(), backup);
            restoreList.getItems().add(label);
        }
    }

}

