package TwentyThreeProductions.Controller.Database;

import TwentyThreeProductions.Model.DBLogic;
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

public class DbRestoreController {

    private DBLogic dbController;
    private SceneSwitch sceneSwitch;
    private String selectedDBName;

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
            selectedDBName = backupList.getSelectionModel().getSelectedItem().getText();
            System.out.println(selectedDBName);
            Process proc = Runtime.getRuntime().exec(new String[]{"./restore.sh", selectedDBName});

            // Wait for 5 seconds
            Thread.sleep(5000);

            System.out.println("Restarting the sql server");
            dbController.restartServer();

            System.out.println("Server restarted, please login again");
          //  sceneSwitch.switchScene("Login", backBtn.getScene());

        } catch (IOException io) {
            io.printStackTrace();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }


    public void initialize() throws IOException {
        System.out.println("New DB Controller");
        System.out.println("System Command Generate DB Element");
        dbController = DBLogic.getDBInstance();
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(restoreDBStackPane, NavigationModel.DB_RESTORE_ID);
        addToList();
    }

    public void addToList() {
        File folder = new File("src/TwentyThreeProductions/GARITSpayload/DBBackups");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("File " + listOfFiles[i].getName());
                backupList.getItems().add(new Label(listOfFiles[i].getName()));
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
    }
}
