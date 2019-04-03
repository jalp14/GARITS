package TwentyThreeProductions.Controller.Reports.NewReport;

import TwentyThreeProductions.Domain.Customer;
import TwentyThreeProductions.Domain.Job;
import TwentyThreeProductions.Model.CustomerReference;
import TwentyThreeProductions.Model.Database.DAO.CustomerDAO;
import TwentyThreeProductions.Model.Database.DAO.JobDAO;
import TwentyThreeProductions.Model.Database.DBConnectivity;
import TwentyThreeProductions.Model.JobReference;
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

import java.io.File;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.util.HashMap;

public class NewNoVehiclesMonthly {

    private SceneSwitch sceneSwitch;
    private Connection connection;
    private JasperReport noVehiclesReport;
    private DBConnectivity dbConnectivity;
    private JasperPrint printReport;
    private JobReference jobReference;
    private CustomerReference customerReference;
    private HashMap<String, Object> parameters;

    @FXML
    private StackPane newNoVehiclesMonthlyStackPane;

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
            JasperExportManager.exportReportToPdfFile(printReport, "src/TwentyThreeProductions/PDFs/ExportFile/NoVehiclesMonthlyReport.pdf");
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void showReport() {
        File file = new File("src/TwentyThreeProductions/PDFs/Template/NoVehiclesMonthlyReport.html");

        try {
            webView.getEngine().load(file.toURI().toURL().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public void setupReport() {
        JobDAO jobDAO = new JobDAO();
        CustomerDAO customerDAO = new CustomerDAO();
        parameters = new HashMap<>();
        try {
            noVehiclesReport = JasperCompileManager.compileReport("src/TwentyThreeProductions/PDFs/Template/NoVehiclesMonthlyReport.jrxml");
            for (Job j : jobDAO.getAll()) {
                if (j.getRegistrationID().isEmpty()) {
                    continue;
                }
                parameters.put("JOBID", j.getJobID());
                if (jobReference.getJob().getUsername().equals("ANY")) {
                    parameters.put("USERNAME", j.getUsername());
                } else if (j.getUsername().equals(jobReference.getJob().getUsername())) {
                    parameters.put("USERNAME", j.getUsername());
                } else {
                    continue;
                }
                for (Customer c : customerDAO.getAll()) {
                    if (customerReference.getCustomer().getCustomerType().equals("ANY")) {
                        parameters.put("CUSTOMERID", j.getCustomerID());
                        break;
                    } else if (c.getCustomerType().equals(customerReference.getCustomer().getCustomerType())
                            && c.getCustomerID().equals(j.getCustomerID())) {
                        parameters.put("CUSTOMERID", j.getCustomerID());
                        break;
                    }
                }
                parameters.put("CARREGISTRATIONID", j.getRegistrationID());
                if (jobReference.getJob().getDescription().equals("ANY")) {
                    parameters.put("DESCRIPTION", j.getDescription());
                } else if (j.getDescription().equals(jobReference.getJob().getDescription())) {
                    parameters.put("DESCRIPTION", j.getDescription());
                } else {
                    continue;
                }
                parameters.put("STATUS", j.getStatus());
                parameters.put("PAIDFOR", j.getPaidFor());
                printReport = JasperFillManager.fillReport(noVehiclesReport, parameters, new JREmptyDataSource());
            }
            JasperExportManager.exportReportToHtmlFile(printReport, "src/TwentyThreeProductions/PDFs/Template/NoVehiclesMonthlyReport.html");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        jobReference = JobReference.getInstance();
        customerReference = CustomerReference.getInstance();
        setupReport();
        showReport();
    }

}

