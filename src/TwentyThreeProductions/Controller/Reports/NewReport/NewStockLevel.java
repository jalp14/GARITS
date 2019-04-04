package TwentyThreeProductions.Controller.Reports.NewReport;

import TwentyThreeProductions.Domain.Report;
import TwentyThreeProductions.Model.DBLogic;
import TwentyThreeProductions.Model.Database.DAO.ReportDAO;
import TwentyThreeProductions.Model.Database.DBConnectivity;
import TwentyThreeProductions.Model.HelperClasses.ReportHelper;
import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import TwentyThreeProductions.Model.SystemNotification;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class NewStockLevel {

    private SceneSwitch sceneSwitch;
    private Connection connection;
    private JasperReport stockReport;
    private DBConnectivity dbConnectivity;
    private JasperPrint printReport;
    private ReportDAO reportDAO;
    private String fileName;
    private String fileLocation;

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
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
            String timeStamp = sdf.format(new Date());
            fileName = "StockReport" + timeStamp + ".pdf";
            reportName.setText(fileName);
            fileLocation = "src/TwentyThreeProductions/PDFs/ExportFile/";
            String fileLocation = "src/TwentyThreeProductions/PDFs/ExportFile/";
            String htmlName = "firstreminder" + timeStamp + ".html";

            JasperExportManager.exportReportToHtmlFile(printReport, fileLocation + htmlName);
            JasperExportManager.exportReportToPdfFile(printReport, fileLocation + fileName);
            saveReportToDB(htmlName);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void saveReportToDB(String htmlName) {
        reportDAO = new ReportDAO();
        Report report = new Report();
        report.setReportType(ReportHelper.ReportType.STOCK_LEVEL.toString());
        report.setUsername(DBLogic.getDBInstance().getUsername());
        report.setFileLocation(fileName);
        report.setHtmlLocation(htmlName);
        reportDAO.save(report);
        SystemNotification notification = new SystemNotification(partsMainStackPane);
        notification.setNotificationMessage("Stock Report saved as :" + fileName);
        notification.showNotification(NavigationModel.NEW_STOCK_LEVEL_ID, DBLogic.getDBInstance().getUsername());
    }


    public void showReport() {
        File file = new File("src/TwentyThreeProductions/PDFs/Template/Stock.html");

        try {
            webView.getEngine().load(file.toURI().toURL().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public void setupReport() {
        try {
            stockReport = JasperCompileManager.compileReport("src/TwentyThreeProductions/PDFs/Template/Stock_Level.jrxml");

            dbConnectivity = new DBConnectivity();
            connection = dbConnectivity.connection(connection);


            // PSA : printReport here means view the report
            printReport = JasperFillManager.fillReport(stockReport, null, connection);


            JasperExportManager.exportReportToHtmlFile(printReport, "src/TwentyThreeProductions/PDFs/Template/Stock.html");
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

