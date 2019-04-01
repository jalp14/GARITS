package TwentyThreeProductions.Controller.Reports;

import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;

public class ViewReport {

    private SceneSwitch sceneSwitch;

    @FXML
    private StackPane partsMainStackPane;

    @FXML
    private JFXButton generateReportBtn;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text usertypeLbl;

    @FXML
    private Label welcomeMessage;

    @FXML
    private JFXButton backBtn;

    @FXML
    private WebView pdfView;

    @FXML
    void backBtnClicked(ActionEvent event) {
        sceneSwitch.switchScene(NavigationModel.SELECT_REPORT_TO_VIEW_ID);
    }

    @FXML
    void generateReportBtnClicked(ActionEvent event) {

    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();;
    }


}

