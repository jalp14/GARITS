package TwentyThreeProductions.Controller.Reports.NewReport;


import TwentyThreeProductions.Domain.Customer;
import TwentyThreeProductions.Domain.Job;
import TwentyThreeProductions.Model.*;
import TwentyThreeProductions.Model.Database.DBConnectivity;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import net.sf.jasperreports.engine.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.Date;

public class NewNoVehiclesMonthlySettings {

    private SceneSwitch sceneSwitch;
    private Connection connection;
    private JasperReport noVehiclesReport;
    private DBConnectivity dbConnectivity;
    private JasperPrint printReport;
    private JobReference jobReference;
    private CustomerReference customerReference;

    @FXML
    private StackPane newNoVehiclesMonthlySettingsStackPane;

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
    private JFXTextField usernameField;

    @FXML
    private JFXRadioButton jobTypeRadioAll;

    @FXML
    private Label fNameHeading;

    @FXML
    private Label jobTypeHeading;

    @FXML
    private Label custTypeHeading;

    @FXML
    private JFXRadioButton jobTypeRadioMoT;

    @FXML
    private JFXRadioButton jobTypeRadioService;

    @FXML
    private JFXRadioButton jobTypeRadioRepair;

    @FXML
    private JFXRadioButton custTypeRadioAll;

    @FXML
    private JFXRadioButton custTypeRadioCasual;

    @FXML
    private JFXRadioButton custTypeRadioAccount;

    @FXML
    private Label periodHeading;

    @FXML
    private JFXDatePicker datePicker;

    // Once this button is clicked, all other buttons of the same type are forcibly unclicked
    @FXML
    void custTypeRadioAllSelected(ActionEvent event) {
        custTypeRadioAccount.setSelected(false);
        custTypeRadioCasual.setSelected(false);
    }

    // Once this button is clicked, all other buttons of the same type are forcibly unclicked
    @FXML
    void custTypeRadioAccountSelected(ActionEvent event) {
        custTypeRadioAll.setSelected(false);
        custTypeRadioCasual.setSelected(false);
    }

    // Once this button is clicked, all other buttons of the same type are forcibly unclicked
    @FXML
    void custTypeRadioCasualSelected(ActionEvent event) {
        custTypeRadioAll.setSelected(false);
        custTypeRadioAccount.setSelected(false);
    }

    // Once this button is clicked, all other buttons of the same type are forcibly unclicked
    @FXML
    void jobTypeRadioAllSelected(ActionEvent event) {
        jobTypeRadioMoT.setSelected(false);
        jobTypeRadioRepair.setSelected(false);
        jobTypeRadioService.setSelected(false);
    }

    // Once this button is clicked, all other buttons of the same type are forcibly unclicked
    @FXML
    void jobTypeRadioMoTSelected(ActionEvent event) {
        jobTypeRadioAll.setSelected(false);
        jobTypeRadioRepair.setSelected(false);
        jobTypeRadioService.setSelected(false);
    }

    // Once this button is clicked, all other buttons of the same type are forcibly unclicked
    @FXML
    void jobTypeRadioRepairSelected(ActionEvent event) {
        jobTypeRadioAll.setSelected(false);
        jobTypeRadioMoT.setSelected(false);
        jobTypeRadioService.setSelected(false);
    }

    // Once this button is clicked, all other buttons of the same type are forcibly unclicked
    @FXML
    void jobTypeRadioServiceSelected(ActionEvent event) {
        jobTypeRadioAll.setSelected(false);
        jobTypeRadioMoT.setSelected(false);
        jobTypeRadioRepair.setSelected(false);
    }

    // The system returns to the main page for generating a new report.
    @FXML
    void backBtnClicked(ActionEvent event) {
        sceneSwitch.switchScene(NavigationModel.NEW_REPORT_MENU_ID);
    }

    @FXML
    void generateReportBtnClicked(ActionEvent event) throws IOException {
        // The system creates new objects for both customer and job to pass through to the report as search terms.
        Job job = new Job();
        Customer customer = new Customer();

        // Depending on which job type was selected, the job type of the new job object is set to the appropriate value.
        // On top of that, the customer type is also set to the appropriate value for the selected customer type. If either
        // of these fields have been left as blank, however, an alert stating as much is shown.
        if(jobTypeRadioAll.isSelected()) {
            job.setDescription("ANY");
        }
        else if(jobTypeRadioMoT.isSelected()) {
            job.setDescription("MoT job");
        }
        else if(jobTypeRadioRepair.isSelected()) {
            job.setDescription("Repairs job");
        }
        else if(jobTypeRadioService.isSelected()) {
            job.setDescription("Annual Service job");
        }
        if(custTypeRadioAll.isSelected()) {
            customer.setCustomerType("ANY");
        }
        else if(custTypeRadioAccount.isSelected()) {
            customer.setCustomerType("ACCOUNT");
        }
        else if(custTypeRadioCasual.isSelected()) {
            customer.setCustomerType("CASUAL");
        }
        if(job.getDescription().isEmpty() || customer.getCustomerType().isEmpty()) {
            SystemAlert systemAlert = new SystemAlert(newNoVehiclesMonthlySettingsStackPane,
                    "Failure", "Either job type or customer type not selected");
        }
        // After this, the username and date for the job object are set to the values that have been inputted by the user.
        // In the event that these fields are empty, the system searches through all usernames and the jobs from the current
        // month.
        else {
            if(usernameField.getText().isEmpty()) {
                job.setUsername("ANY");
            }
            else {
                job.setUsername(usernameField.getText());
            }
            if(datePicker.getValue() == null) {
                java.util.Date currentDate = new java.util.Date();
                java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
                job.setDateBookedIn(sqlDate);
            }
            else {
                job.setDateBookedIn(Date.valueOf(datePicker.getValue()));
            }

            // After this is done, the static classes are set to the appropriate values and the page for actually generating
            // the reports is moved to.
            jobReference.setJob(job);
            customerReference.setCustomer(customer);
            sceneSwitch.activateSceneAlways(NavigationModel.NEW_NO_VEHICLES_MONTHLY_ID, backBtn.getScene());
        }
    }

    // This function is called when the page is opened, and simply adds the scene to the list of active scenes and
    // initialises the static classes that will be used on this page.
    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(newNoVehiclesMonthlySettingsStackPane, NavigationModel.NEW_NO_VEHICLES_MONTHLY_SETTINGS_ID);
        jobReference = JobReference.getInstance();
        customerReference = CustomerReference.getInstance();
    }

    // This function simply sets all of the values that have data that can be inputted back to their default values.
    public void clearInputs() {
        usernameField.clear();
        custTypeRadioAccount.setSelected(false);
        custTypeRadioAll.setSelected(false);
        custTypeRadioCasual.setSelected(false);
        jobTypeRadioAll.setSelected(false);
        jobTypeRadioMoT.setSelected(false);
        jobTypeRadioRepair.setSelected(false);
        jobTypeRadioService.setSelected(false);
        datePicker.setValue(null);
    }

}
