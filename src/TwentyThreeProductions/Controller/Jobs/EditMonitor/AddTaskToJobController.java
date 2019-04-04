package TwentyThreeProductions.Controller.Jobs.EditMonitor;

import TwentyThreeProductions.Domain.Customer;
import TwentyThreeProductions.Domain.JobTask;
import TwentyThreeProductions.Domain.Task;
import TwentyThreeProductions.Model.*;
import TwentyThreeProductions.Model.Database.DAO.CustomerDAO;
import TwentyThreeProductions.Model.Database.DAO.JobTaskDAO;
import TwentyThreeProductions.Model.Database.DAO.TaskDAO;
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
        // If there is not a task selected, the system will not continue and instead produce an alert stating that a
        // task must be selected to continue.
        if(taskList.getSelectionModel().isEmpty()) {
            SystemAlert systemAlert = new SystemAlert(addTaskToJobStackPane,
                    "Failure", "No task selected");
        }

        // Otherwise, the system begins to create an object for the task, which can potentially have its duration
        // adjusted, and the junction table between jobs and tasks. The system adds both IDs to the junction object
        // before attempting to determine whether the duration needs to be adjusted.
        else {
            Task task = taskHashMap.get(taskList.getSelectionModel().getSelectedItem().getText());
            JobTask jobTask = new JobTask();
            JobTaskDAO jobTaskDAO = new JobTaskDAO();
            jobTask.setJobID(jobReference.getJob().getJobID());
            jobTask.setTaskID(task.getTaskID());
            try {

                // If the altered duration field is left as blank, the system will simply use the default duration as the
                // tasks duration.
                if(alteredDurationField.getText().isEmpty()) {
                    jobTask.setAlteredDuration(task.getDefaultDuration());
                }

                // Otherwise, the system will set the task duration to the time that was inputted by the user.
                else {
                    jobTask.setAlteredDuration(Time.valueOf(alteredDurationField.getText()));
                }

                // After this, the system checks if the task is currently tied to the job. If it is, a boolean value will
                // be marked as true and, instead of adding a new task, simply adjusts the duration for the current entry
                // for the junction table. If the task is not currently tied to the job, it instead adds a new entry
                // in the junction table for that task.
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

                // After the task has been added or adjusted, the list of tasks within the system and the hashmap for them
                // is cleared and refreshed.
                taskList.getSelectionModel().select(null);
                taskList.getItems().clear();
                taskHashMap.clear();
                refreshList();
            }

            // If the time inputted does not follow the specified format, the system produces an alert stating this.
            catch(Exception e) {
                SystemAlert systemAlert = new SystemAlert(addTaskToJobStackPane,
                        "Failure", "Invalid duration given");
            }
        }
    }

    // The system clears the list of tasks and the hashmap associated with it before refreshing the list and returning
    // to the previous page.
    @FXML
    void backBtnClicked(ActionEvent event) throws IOException {
        taskList.getItems().clear();
        taskHashMap.clear();
        searchField.clear();
        refreshList();
        sceneSwitch.activateSceneAlways(NavigationModel.EDIT_MONITOR_JOB_ID, backBtn.getScene());
    }

    @FXML
    void searchBtnClick(ActionEvent event) {
        // The system clears both the list and the hashmap and prepares the appropriate items in order to correctly
        // search for available tasks.
        String searchTerm = searchField.getText();
        taskList.getItems().clear();
        taskHashMap.clear();

        // If the search term inputted by the user is empty, the system refreshes the list with every value available.
        if(searchTerm.isEmpty()) {
            refreshList();
        }

        // Otherwise, it only adds the tasks that contain the currently inputted value as either their ID, default duration
        // or name to the list of available tasks.
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

    // This function is called when the page is first loaded, and it adds the current scene to the list of active scenes
    // on the system, as well as generates the labels for both the username and usertype of the user currently logged into
    // the system. After this, the system initalises the static class for returning the job details and the hashmap of the
    // tasks.
    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(addTaskToJobStackPane, NavigationModel.ADD_TASK_TO_JOB_ID);
        usernameLbl.setText(DBLogic.getDBInstance().getUsername());
        usertypeLbl.setText(DBLogic.getDBInstance().getUser_type());
        jobReference = jobReference.getInstance();
        taskHashMap = new HashMap<>();
        refreshList();
    }

    // This function first generates the appropriate classes for querying the database, before generating a list of all
    // tasks currently within the system database. After this, a label is generated for both the name of the customer
    // who is currently assigned to the job being worked on as well as details on the job itself.
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
