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

    @FXML
    void backBtnClicked(ActionEvent event) {
        jobList.getSelectionModel().select(null);
        jobList.getItems().clear();
        jobHashMap.clear();
        searchField.setText("");
        refreshList();
        sceneSwitch.switchScene(NavigationModel.JOBS_MAIN_ID);
    }

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
        String searchTerm = searchField.getText();
        jobList.getItems().clear();
        jobHashMap.clear();
        if(searchTerm.isEmpty()) {
            refreshList();
        }
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
    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(editMonitorChooseJobStackPane, NavigationModel.EDIT_MONITOR_CHOOSE_ID);
        usernameLbl.setText(DBLogic.getDBInstance().getUsername());
        usertypeLbl.setText(DBLogic.getDBInstance().getUser_type());
        jobReference = jobReference.getInstance();
        jobHashMap = new HashMap<>();
        refreshList();
    }

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
