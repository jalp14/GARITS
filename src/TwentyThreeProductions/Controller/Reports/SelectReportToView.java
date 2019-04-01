package TwentyThreeProductions.Controller.Reports;

import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class SelectReportToView {
    private SceneSwitch sceneSwitch;

    @FXML
    private StackPane SearchCustomerStackPane;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text usertypeLbl;

    @FXML
    private Label welcomeMessage;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton viewBtn;

    @FXML
    private Label formLabel;

    @FXML
    private JFXTextField searchField;

    @FXML
    private JFXListView<?> reportsList;

    @FXML
    void ViewBtnClicked(ActionEvent event) {

    }

    @FXML
    void backBtnClicked(ActionEvent event) {
        sceneSwitch.switchScene(NavigationModel.REPORTS_MAIN_ID);
    }

    @FXML
    void queryTyped(KeyEvent event) {

    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(SearchCustomerStackPane, NavigationModel.SELECT_REPORT_TO_VIEW_ID);;
    }


}

