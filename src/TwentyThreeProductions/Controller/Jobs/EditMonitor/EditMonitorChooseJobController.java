package TwentyThreeProductions.Controller.Jobs.EditMonitor;

import TwentyThreeProductions.Domain.Customer;
import TwentyThreeProductions.Domain.Job;
import TwentyThreeProductions.Model.*;
import TwentyThreeProductions.Model.Database.DAO.CustomerDAO;
import TwentyThreeProductions.Model.Database.DAO.JobDAO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.HashMap;

public class EditMonitorChooseJobController {

    private SceneSwitch sceneSwitch;

    private JobReference jobReference;

    private HashMap<String, Job> jobHashMap;

    @FXML
    private StackPane editMonitorChooseJobStackPane;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text usertypeLbl;

    @FXML
    private Label welcomeMessage;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton nextBtn;

    @FXML
    private Label formLabel;

    @FXML
    private JFXTextField searchField;

    @FXML
    private JFXButton searchBtn;

    @FXML
    private JFXListView<Label> jobList;

    // The system clears the currently selected job, the list of jobs, the hashmap for it and the search term before
    // refreshing the list and moving back to the previous page.
    @FXML
    void backBtnClicked(ActionEvent event) {
        jobList.getSelectionModel().select(null);
        jobList.getItems().clear();
        jobHashMap.clear();
        searchField.clear();
        refreshList();
        sceneSwitch.switchScene(NavigationModel.JOBS_MAIN_ID);
    }

    // If the job selection is empty, the system will produce an alert stating as much to the user. Otherwise, the system
    // stores the job in the static class before moving to the page for altering the job details.
    @FXML
    void nextBtnClicked(ActionEvent event) throws IOException {
        if(jobList.getSelectionModel().isEmpty()) {
            SystemAlert systemAlert = new SystemAlert(editMonitorChooseJobStackPane,
                    "Failure", "Job not selected");
        }
        else{
            jobReference.setJob(jobHashMap.get(jobList.getSelectionModel().getSelectedItem().getText()));
            sceneSwitch.activateSceneAlways(NavigationModel.EDIT_MONITOR_JOB_ID, backBtn.getScene());
        }
    }

    @FXML
    void searchBtnClick(ActionEvent event) {
        // The system clears both the list and the hashmap and prepares the appropriate items in order to correctly
        // search for available jobs.
        String searchTerm = searchField.getText();
        jobList.getItems().clear();
        jobHashMap.clear();

        // If the search term inputted by the user is empty, the system refreshes the list with every value available.
        if(searchTerm.isEmpty()) {
            refreshList();
        }

        // Otherwise, it only adds the jobs that contain the currently inputted value as either their ID, customer name,
        // or date booked in to the list of available parts.
        else {
            JobDAO jobDAO = new JobDAO();
            CustomerDAO customerDAO = new CustomerDAO();
            for(Job j: jobDAO.getAll()) {
                Customer customer = new Customer();
                for (Customer c : customerDAO.getAll()) {
                    if(Integer.parseInt(c.getCustomerID()) == (j.getCustomerID())) {
                        customer.setFirstName(c.getFirstName());
                        customer.setLastName(c.getLastName());
                        break;
                    }
                }
                if(String.valueOf(j.getDateBookedIn()).contains(searchTerm) || (customer.getFirstName() + " " + customer.getLastName()).contains(searchTerm) || j.getRegistrationID().contains(searchTerm)) {
                    Label jobLabel = new Label("Job ID: " + j.getJobID() +  " / Date: " + j.getDateBookedIn() + " / Name: " + customer.getFirstName() + " " + customer.getLastName() + " / Car ID: " + j.getRegistrationID());
                    jobHashMap.put(jobLabel.getText(), j);
                    jobList.getItems().add(jobLabel);
                }
            }
        }
    }

    // This function is called up when the page is first opened, and it adds the scene to the list of currently
    // active scenes as well as changing the labels for the username and type with the currently logged in user,
    // and then finally it initialises the hashmap for storing the jobs and refresh the list of currently available
    // jobs as well as gets the instance of the static class for storing the job.
    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(editMonitorChooseJobStackPane, NavigationModel.EDIT_MONITOR_CHOOSE_ID);
        usernameLbl.setText(DBLogic.getDBInstance().getUsername());
        usertypeLbl.setText(DBLogic.getDBInstance().getUser_type());
        jobReference = jobReference.getInstance();
        jobHashMap = new HashMap<>();
        refreshList();
    }

    // This function goes through every job within the system database as well as every customer, matching the customer
    // ID to the appropriate job so that it can retrieve the name of the customer to use in the label. Once this is done,
    // the system then determines whether the job has a car or is part only. After this, the system creates a label
    // for the job using the Job ID, the date the job was booked, the name of the customer that booked it, either the car ID
    // or, if it is a part-only job, a string specifying as much, as well as the status of the job. Once this is done, the
    // system adds this label and the job entry itself to the hashmap for jobs.
    public void refreshList() {
        JobDAO jobDAO = new JobDAO();
        CustomerDAO customerDAO = new CustomerDAO();
        for(Job j: jobDAO.getAll()) {
            Customer customer = new Customer();
            for(Customer c: customerDAO.getAll()) {
                if(Integer.parseInt(c.getCustomerID()) == (j.getCustomerID())) {
                    customer.setFirstName(c.getFirstName());
                    customer.setLastName(c.getLastName());
                    break;
                }
            }
            Label jobLabel;
            if(j.getRegistrationID() == null) {
                jobLabel = new Label("Job ID: " + j.getJobID() + " / Date: " + j.getDateBookedIn() +
                        " / Name: " + customer.getFirstName() + " " + customer.getLastName() +
                        " / Part-only job / Status: " + j.getStatus() + " / Is Paid: " + j.getPaidFor());
            }
            else {
                jobLabel = new Label("Job ID: " + j.getJobID() + " / Date: " + j.getDateBookedIn() +
                        " / Name: " + customer.getFirstName() + " " + customer.getLastName() + " / Car ID: " + j.getRegistrationID() + "" +
                        " / Status: " + j.getStatus() + " / Is Paid: " + j.getPaidFor());
            }
            jobHashMap.put(jobLabel.getText(), j);
            jobList.getItems().add(jobLabel);
        }
    }
}
