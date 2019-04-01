package TwentyThreeProductions.Controller.Jobs.EditMonitor;

import TwentyThreeProductions.Domain.Customer;
import TwentyThreeProductions.Domain.JobTask;
import TwentyThreeProductions.Domain.Task;
import TwentyThreeProductions.Model.Database.DAO.CustomerDAO;
import TwentyThreeProductions.Model.Database.DAO.JobTaskDAO;
import TwentyThreeProductions.Model.Database.DAO.TaskDAO;
import TwentyThreeProductions.Model.JobReference;
import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import TwentyThreeProductions.Model.SystemAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;

public class AddTaskToJobController {

    private SceneSwitch sceneSwitch;

    private JobReference jobReference;

    private HashMap<String, Task> taskHashMap;

    @FXML
    private StackPane addTaskToJobStackPane;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text usertypeLbl;

    @FXML
    private Label welcomeMessage;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton addTaskBtn;

    @FXML
    private Label jobDetailsLbl;

    @FXML
    private Label formLabel;

    @FXML
    private JFXTextField searchField;

    @FXML
    private JFXButton searchBtn;

    @FXML
    private JFXListView<Label> taskList;

    @FXML
    private JFXTextField alteredDurationField;

    @FXML
    private Label alteredDurationLabel;

    @FXML
    void addTaskBtnClicked(ActionEvent event) {
        if(taskList.getSelectionModel().isEmpty()) {
            SystemAlert systemAlert = new SystemAlert(addTaskToJobStackPane,
                    "Failure", "No task selected");
        }
        else {
            Task task = taskHashMap.get(taskList.getSelectionModel().getSelectedItem().getText());
            JobTask jobTask = new JobTask();
            JobTaskDAO jobTaskDAO = new JobTaskDAO();
            jobTask.setJobID(jobReference.getJob().getJobID());
            jobTask.setTaskID(task.getTaskID());
            try {
                if(alteredDurationField.getText().isEmpty()) {
                    jobTask.setAlteredDuration(task.getDefaultDuration());
                }
                else {
                    jobTask.setAlteredDuration(Time.valueOf(alteredDurationField.getText()));
                }
                boolean isTaskInJob = false;
                for(JobTask jt: jobTaskDAO.getAll()) {
                    if (jt.getTaskID() == jobTask.getTaskID() && jt.getJobID() == jobTask.getJobID()) {
                        isTaskInJob = true;
                    }
                }
                if(isTaskInJob) {
                    jobTaskDAO.update(jobTask);
                    SystemAlert systemAlert = new SystemAlert(addTaskToJobStackPane,
                            "Success", "Updated task duration");
                }
                else {
                    jobTaskDAO.save(jobTask);
                    SystemAlert systemAlert = new SystemAlert(addTaskToJobStackPane,
                            "Success", "Added task to job");
                }
                taskList.getSelectionModel().select(null);
                taskList.getItems().clear();
                taskHashMap.clear();
                refreshList();
            }
            catch(Exception e) {
                SystemAlert systemAlert = new SystemAlert(addTaskToJobStackPane,
                        "Failure", "Invalid duration given");
            }
        }
    }

    @FXML
    void backBtnClicked(ActionEvent event) throws IOException {
        taskList.getItems().clear();
        taskHashMap.clear();
        searchField.setText("");
        refreshList();
        sceneSwitch.activateSceneAlways(NavigationModel.EDIT_MONITOR_JOB_ID, backBtn.getScene());
    }

    @FXML
    void searchBtnClick(ActionEvent event) {
        String searchTerm = searchField.getText();
        taskList.getItems().clear();
        taskHashMap.clear();
        if(searchTerm.isEmpty()) {
            refreshList();
        }
        else {
            TaskDAO taskDAO = new TaskDAO();
            for(Task t: taskDAO.getAll()) {
                if(String.valueOf(t.getTaskID()).contains(searchTerm) || String.valueOf(t.getDefaultDuration()).contains(searchTerm) || t.getName().contains(searchTerm)) {
                    Label taskLabel = new Label("ID: " + t.getTaskID() + " / Name: " + t.getName() + " / Default Duration: " + t.getDefaultDuration());
                    taskHashMap.put(taskLabel.getText(), t);
                    taskList.getItems().add(taskLabel);
                }
            }
        }
    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(addTaskToJobStackPane, NavigationModel.ADD_TASK_TO_JOB_ID);
        jobReference = jobReference.getInstance();
        taskHashMap = new HashMap<>();
        refreshList();
    }

    public void refreshList() {
        TaskDAO taskDAO = new TaskDAO();
        for(Task t: taskDAO.getAll()) {
            Label taskLabel = new Label("ID: " + t.getTaskID() + " / Name: " + t.getName() + " / Default Duration: " + t.getDefaultDuration());
            taskHashMap.put(taskLabel.getText(), t);
            taskList.getItems().add(taskLabel);
        }
        Customer customer = new Customer();
        CustomerDAO customerDAO = new CustomerDAO();
        for(Customer c: customerDAO.getAll()) {
            if(jobReference.getJob().getCustomerID() == Integer.parseInt(c.getCustomerID())) {
                customer.setFirstName(c.getFirstName());
                customer.setLastName(c.getLastName());
                break;
            }
        }
        if(jobReference.getJob().getRegistrationID() == null) {
            jobDetailsLbl.setText("Date: " + jobReference.getJob().getDateBookedIn() + " / Name: " + customer.getFirstName() + " " + customer.getLastName() + " / Part-only job");
        }
        else {
            jobDetailsLbl.setText("Date: " + jobReference.getJob().getDateBookedIn() + " / Name: " + customer.getFirstName() + " " + customer.getLastName() + " / Car ID: " + jobReference.getJob().getRegistrationID());
        }
    }
}
