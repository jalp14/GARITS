package TwentyThreeProductions.Controller.Reports;

import TwentyThreeProductions.Domain.Customer;
import TwentyThreeProductions.Domain.Report;
import TwentyThreeProductions.Model.Database.DAO.CustomerDAO;
import TwentyThreeProductions.Model.Database.DAO.ReportDAO;
import TwentyThreeProductions.Model.HelperClasses.ReportHelper;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
/////////////////////////////// Report Viewer \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
public class SelectReportToView {
    private SceneSwitch sceneSwitch;
    private ReportDAO reportDAO;
    private Report report;
    private ArrayList<Report> reports;
    private HashMap<String, Report> reportHashMap;

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
    private JFXListView<Label> reportsList;

    @FXML
    void ViewBtnClicked(ActionEvent event) throws IOException { // View selected report
        String selectedReport = reportsList.getSelectionModel().getSelectedItem().getText();
        ReportHelper.setViewReportLocation(reportHashMap.get(selectedReport).getFileLocation());
        sceneSwitch.activateScene(NavigationModel.VIEW_REPORT_ID, backBtn.getScene());
    }

    @FXML
    void backBtnClicked(ActionEvent event) {
        sceneSwitch.switchScene(NavigationModel.REPORTS_MAIN_ID);
    }

    @FXML
    void queryTyped(KeyEvent event) { // Show reports related to the query
        System.out.println("Query typed");
        reportDAO = new ReportDAO();
        String searchTerm = searchField.getText();
        reportsList.getItems().clear();
        reportHashMap.clear();
        // Check if search term is blank or not
        if (searchTerm.isEmpty()) {
            loadAllReports();
        } else {
            for (Report r : reportDAO.getAll()) {
                if (r.getFileLocation().contains(searchTerm)) {
                    Label tmpLabel = new Label (r.getReportID() + ":" + r.getFileLocation());
                    reportHashMap.put(tmpLabel.getText(), r);
                    reportsList.getItems().add(tmpLabel);
                }
            }
        }
    }

    public void initialize() { // Initialise the current form
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(SearchCustomerStackPane, NavigationModel.SELECT_REPORT_TO_VIEW_ID);
        reportHashMap = new HashMap<>();
        loadAllReports();
    }

    public void loadAllReports() { // Load all the available reports in the list
        reportDAO = new ReportDAO();
        reports = new ArrayList<>();
        reports = reportDAO.getAll();

        for (int i = 0; i < reports.size(); i++) {
            report = reports.get(i);
            Label tmpLabel = new Label(report.getReportID() + ":" + report.getFileLocation());
            reportsList.getItems().add(tmpLabel);
            reportHashMap.put(tmpLabel.getText(), report);
        }

    }


}

