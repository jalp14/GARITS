package TwentyThreeProductions.Controller.Reports.NewReport;

import TwentyThreeProductions.Model.Database.DBConnectivity;
import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import net.sf.jasperreports.engine.*;

import java.io.File;
import java.net.MalformedURLException;
import java.sql.Connection;

public class NewStockLevel {

    private SceneSwitch sceneSwitch;
    private Connection connection;

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
    private WebView webView;

    @FXML
    private Label reportName;

    @FXML
    void backBtnClicked(ActionEvent event) {
        sceneSwitch.switchScene(NavigationModel.NEW_REPORT_MENU_ID);
    }

    @FXML
    void generateReportBtnClicked(ActionEvent event) {

    }

    public void showReport() {
        File file = new File("test.html");

        try {
            webView.getEngine().load(file.toURI().toURL().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public void setupReport() {
        try {
            JasperReport report = JasperCompileManager.compileReport("src/TwentyThreeProductions/StockLevelReport.jrxml");
            JRDataSource source = new JREmptyDataSource();

            DBConnectivity dbConnectivity = new DBConnectivity();

            connection = dbConnectivity.connection(connection);

            JasperPrint print = JasperFillManager.fillReport(report, null, connection);

            JasperExportManager.exportReportToHtmlFile(print, "test.html");
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        setupReport();
        showReport();
    }

}

