package TwentyThreeProductions.Controller.Database;

import TwentyThreeProductions.Domain.Backup;
import TwentyThreeProductions.Model.DBLogic;
import TwentyThreeProductions.Model.Database.DAO.BackupDAO;
import TwentyThreeProductions.Model.Database.DBServer;
import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import javax.naming.Name;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class DbRestoreController {

    private DBLogic dbController;
    private SceneSwitch sceneSwitch;
    private String selectedDBName;
    private ArrayList<Backup> backups;
    private Backup backup;
    private HashMap<String, Backup> backupHashMap;

    @FXML
    private StackPane restoreDBStackPane;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text usertypeLbl;

    @FXML
    private Label welcomeMessage;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton restoreBtn;

    @FXML
    private JFXListView<Label> backupList;

    @FXML
    void backBtnClicked(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(NavigationModel.DB_MANAGEMENT_ID);
    }

    @FXML
    void restoreBtnClicked(ActionEvent event) {
        System.out.println("Restore button clicked");
        try {
            Backup backup = backupHashMap.get(backupList.getSelectionModel().getSelectedItem().getText());
            Process proc = Runtime.getRuntime().exec(new String[]{"./restore.sh", backup.getFileLocation()});
            System.out.println("Restarting the sql server");
            DBServer.getInstance().restartServer();
            System.out.println("Server restarted, please login again");
        } catch (IOException io) {
            io.printStackTrace();
        }
    }


    public void initialize() throws IOException {
        System.out.println("New DB Controller");
        System.out.println("System Command Generate DB Element");
        dbController = DBLogic.getDBInstance();
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(restoreDBStackPane, NavigationModel.DB_RESTORE_ID);
        usernameLbl.setText(DBLogic.getDBInstance().getUsername());
        usertypeLbl.setText(DBLogic.getDBInstance().getUser_type());
        backups = new ArrayList<>();
        backupHashMap = new HashMap<>();
       // addToList();
        loadBackups();
    }

    public void loadBackups() {
        BackupDAO backupDAO = new BackupDAO();
        backups = backupDAO.getAll();
        for (int i = 0; i < backups.size(); i++) {
            backup = backups.get(i);
            Label label = new Label(backup.getFileLocation());
            backupHashMap.put(label.getText(), backup);
            backupList.getItems().add(label);
        }
    }
}
