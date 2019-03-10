package TwentyThreeProductions.Controller;

import TwentyThreeProductions.Model.DBLogic;
import TwentyThreeProductions.Model.SceneSwitch;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

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
    private JFXListView<Label> dbList;

    @FXML
    void restoreBtnClicked(ActionEvent event) {
        System.out.println("Restore button clicked");
        try {
            selectedDBName = dbList.getSelectionModel().getSelectedItem().getText();
            System.out.println(selectedDBName);
            Process proc = Runtime.getRuntime().exec(new String[]{"./restore.sh", selectedDBName});

            // Wait for 5 seconds
            Thread.sleep(5000);

            System.out.println("Restarting the sql server");
            dbController.restartServer();

            System.out.println("Server restarted, please login again");
            sceneSwitch.switchScene("Login");

        } catch (IOException io) {
            io.printStackTrace();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    @FXML
    void backBtnClicked(ActionEvent event) {
        sceneSwitch.switchScene("DBManagement");
    }

    public void initialize() throws IOException {
        System.out.println("New DB Controller");
        System.out.println("System Command Generate DB Element");
        dbController = DBLogic.getDBInstance();
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(backBtn.getParent(), "DBRestore");
        addToList();
    }

    public void addToList() {
        File folder = new File("src/TwentyThreeProductions/GARITSpayload/DBBackups");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("File " + listOfFiles[i].getName());
                dbList.getItems().add(new Label(listOfFiles[i].getName()));
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
    }

}
