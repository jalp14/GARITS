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

    // The system saves the report that is currently being viewed to an external PDF file.
    @FXML
    void generateReportBtnClicked(ActionEvent event) {
        try {
            JasperExportManager.exportReportToPdfFile(printReport, "src/TwentyThreeProductions/PDFs/ExportFile/NoVehiclesMonthlyReport.pdf");
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    // The system shows the report that has been generated previously
    public void showReport() {
        File file = new File("src/TwentyThreeProductions/PDFs/Template/NoVehiclesMonthlyReport.html");

        try {
            webView.getEngine().load(file.toURI().toURL().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
    public void setupReport() {
        // The system creates objects that will be used to search for items in the database, as well as a hashmap to store
        // requested values.
        JobDAO jobDAO = new JobDAO();
        CustomerDAO customerDAO = new CustomerDAO();
        parameters = new HashMap<>();
        try {
            noVehiclesReport = JasperCompileManager.compileReport("src/TwentyThreeProductions/PDFs/Template/NoVehiclesMonthlyReport.jrxml");
            for (Job j : jobDAO.getAll()) {
                // The job will not be added if it is a part-only job, and the system will instead skip to the next job.
                if (j.getRegistrationID().isEmpty()) {
                    continue;
                }
                // The system then gets the job ID of the job and the username for the job if it is the one that is currently
                // being searched or there were no restrictions in place. Otherwise, the system will skip that job and move
                // onto the next one.
                parameters.put("JOBID", j.getJobID());
                if (jobReference.getJob().getUsername().equals("ANY")) {
                    parameters.put("USERNAME", j.getUsername());
                } else if (j.getUsername().equals(jobReference.getJob().getUsername())) {
                    parameters.put("USERNAME", j.getUsername());
                } else {
                    continue;
                }

                // The system then gets the customer ID for the customer if they match the selected customer type or there was
                // no restriction selected. Otherwise, the system will move on to the next job.
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
                // The system then gets the car registration for the job and the job type if it matches the one selected
                // by the user or there was not a job type specified. Otherwise, it moves on to the next job.
                parameters.put("CARREGISTRATIONID", j.getRegistrationID());
                if (jobReference.getJob().getDescription().equals("ANY")) {
                    parameters.put("DESCRIPTION", j.getDescription());
                } else if (j.getDescription().equals(jobReference.getJob().getDescription())) {
                    parameters.put("DESCRIPTION", j.getDescription());
                } else {
                    continue;
                }

                // Finally, the system gets the statuses of the job before using this data to generate an accurate report
                // using the settings selected by the user.
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

    // The system gets the static classes that will be used on this page, and then attempts to set up the report as well
    // as display it.
    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        jobReference = JobReference.getInstance();
        customerReference = CustomerReference.getInstance();
        setupReport();
        showReport();
    }

}

