package TwentyThreeProductions.Controller.Reports;

import TwentyThreeProductions.Model.Database.DBConnectivity;
import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.AbstractXmlDataSource;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;

public class ReportsMain {

    private SceneSwitch sceneSwitch;



    @FXML
    private StackPane partsMainStackPane;

    @FXML
    private JFXButton newReportBtn;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text usertypeLbl;

    @FXML
    private Label welcomeMessage;

    @FXML
    private WebView webView;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton existingReportBtn;

    @FXML
    private JFXButton editAutoReportsBtn;

    @FXML
    void backBtnClicked(ActionEvent event) {
        sceneSwitch.switchScene(NavigationModel.MAIN_FFR_ID);
    }

    @FXML
    void editAutoReportsBtnClicked(ActionEvent event) throws IOException {
        sceneSwitch.activateScene(NavigationModel.EDIT_AUTO_REPORTS_ID, backBtn.getScene());
    }

    @FXML
    void existingReportBtnClicked(ActionEvent event) throws IOException {
        sceneSwitch.activateScene(NavigationModel.SELECT_REPORT_TO_VIEW_ID, backBtn.getScene());
    }

    @FXML
    void newReportBtnClicked(ActionEvent event) throws IOException {
        sceneSwitch.activateScene(NavigationModel.NEW_REPORT_MENU_ID, backBtn.getScene());
    }

    public void initialize() {
        System.out.println("Reports Main Screen");
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(partsMainStackPane, NavigationModel.REPORTS_MAIN_ID);

    }




}
