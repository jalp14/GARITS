package TwentyThreeProductions.Controller.Reports;

import TwentyThreeProductions.Model.Database.DAO.ReportDAO;
import TwentyThreeProductions.Model.HelperClasses.ReportHelper;
import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;

import java.net.MalformedURLException;

public class ViewReport {

    private SceneSwitch sceneSwitch;

    @FXML
    private StackPane partsMainStackPane;

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

    private ReportDAO reportDAO;

    @FXML
    void backBtnClicked(ActionEvent event) {
        sceneSwitch.switchScene(NavigationModel.SELECT_REPORT_TO_VIEW_ID);
    }


    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        showReport();
        System.out.println("Showing Report");
    }

    public void showReport() {
        String location = ReportHelper.getViewReportLocation();
        reportDAO = new ReportDAO();
       // String htmlLocation = reportDAO.get
        pdfView.getEngine().load("src/TwentyThreeProductions/PDFs/Template" +location);

    }


}

