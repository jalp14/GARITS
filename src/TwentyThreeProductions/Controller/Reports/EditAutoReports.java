package TwentyThreeProductions.Controller.Reports;

import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class EditAutoReports {

    private SceneSwitch sceneSwitch;

    @FXML
    private StackPane selectUserStackPane;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text usertypeLbl;

    @FXML
    private Label welcomeMessage;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton removeBtn;

    @FXML
    private JFXListView<?> autoReportsList;

    @FXML
    private JFXButton viewDetailsBtn;

    @FXML
    private JFXButton newAutoReportBtn;

    @FXML
    void backBtnClicked(ActionEvent event) {
        sceneSwitch.switchScene(NavigationModel.REPORTS_MAIN_ID);
    }

    @FXML
    void newAutoReportBtnClicked(ActionEvent event) {

    }

    @FXML
    void removeBtnClicked(ActionEvent event) {

    }

    @FXML
    void viewDetailsBtnClicked(ActionEvent event) {

    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(selectUserStackPane, NavigationModel.EDIT_AUTO_REPORTS_ID);
    }


}
