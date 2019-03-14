package TwentyThreeProductions.Controller.Jobs.EditMonitor;

import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class EditMonitorJobController {

    private SceneSwitch sceneSwitch;

    @FXML
    private StackPane editMonitorJobStackPane;

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
    private Label tasksHeading;

    @FXML
    private JFXButton addTaskBtn;

    @FXML
    private JFXListView<?> taskList;

    @FXML
    private Label jobDetailsLbl;

    @FXML
    private Label partsHeading;

    @FXML
    private JFXButton addPartBtn;

    @FXML
    private JFXListView<?> partList;

    @FXML
    private Label mechanicHeading;

    @FXML
    private JFXComboBox<?> mechanicComboBox;

    @FXML
    private Label mechanicHeading1;

    @FXML
    private Label mechanicHeading2;

    @FXML
    private JFXCheckBox jobCompletedCheckbox;

    @FXML
    private JFXCheckBox jobPaidCheckbox;

    @FXML
    void addPartBtnClick(ActionEvent event) {

    }

    @FXML
    void addTaskBtnClick(ActionEvent event) {

    }

    @FXML
    void backBtnClicked(ActionEvent event) {

    }

    @FXML
    void jobCompletedCheckboxClicked(ActionEvent event) {

    }

    @FXML
    void jobPaidCheckboxClicked(ActionEvent event) {

    }

    @FXML
    void mechanicComboBoxClicked(ActionEvent event) {

    }

    @FXML
    void saveBtnClicked(ActionEvent event) {

    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(editMonitorJobStackPane, NavigationModel.EDIT_MONITOR_JOB_ID);
    }

}
