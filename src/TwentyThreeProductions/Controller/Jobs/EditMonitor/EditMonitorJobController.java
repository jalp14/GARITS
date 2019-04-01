package TwentyThreeProductions.Controller.Jobs.EditMonitor;

import TwentyThreeProductions.Domain.*;
import TwentyThreeProductions.Model.Database.DAO.*;
import TwentyThreeProductions.Model.JobReference;
import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import TwentyThreeProductions.Model.SystemAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.HashMap;

public class EditMonitorJobController {

    private SceneSwitch sceneSwitch;

    private JobReference jobReference;

    private HashMap<String, User> mechanicHashMap;

    @FXML
    private StackPane editMonitorJobStackPane;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text usertypeLbl;

    @FXML
    private Label welcomeMessage;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private Label tasksHeading;

    @FXML
    private JFXButton addTaskBtn;

    @FXML
    private JFXListView<Label> taskList;

    @FXML
    private Label jobDetailsLbl;

    @FXML
    private Label partsHeading;

    @FXML
    private JFXButton addPartBtn;

    @FXML
    private JFXListView<Label> partList;

    @FXML
    private Label mechanicHeading;

    @FXML
    private JFXComboBox<Label> mechanicComboBox;

    @FXML
    private Label mechanicHeading1;

    @FXML
    private Label mechanicHeading2;

    @FXML
    private JFXCheckBox jobCompletedCheckbox;

    @FXML
    private JFXCheckBox jobPaidCheckbox;

    @FXML
    void addPartBtnClick(ActionEvent event) throws IOException {
        sceneSwitch.activateSceneAlways(NavigationModel.ADD_PART_TO_JOB_ID, backBtn.getScene());
    }

    @FXML
    void addTaskBtnClick(ActionEvent event) throws IOException {
        if(jobDetailsLbl.getText().contains("/ Part-only job")) {
            SystemAlert systemAlert = new SystemAlert(editMonitorJobStackPane,
                    "Failure", "Cannot add tasks to a part-only job");
        }
        else {
            sceneSwitch.activateSceneAlways(NavigationModel.ADD_TASK_TO_JOB_ID, backBtn.getScene());
        }
    }

    @FXML
    void backBtnClicked(ActionEvent event) {
        jobCompletedCheckbox.setSelected(false);
        jobPaidCheckbox.setSelected(false);
        taskList.getSelectionModel().select(null);
        taskList.getItems().clear();
        partList.getSelectionModel().select(null);
        partList.getItems().clear();
        mechanicComboBox.getSelectionModel().select(null);
        mechanicComboBox.getItems().clear();
        mechanicHashMap.clear();
        refreshList();
        sceneSwitch.switchScene(NavigationModel.EDIT_MONITOR_CHOOSE_ID);
    }

    @FXML
    void jobCompletedCheckboxClicked(ActionEvent event) {
        if(jobCompletedCheckbox.isSelected()) {
            jobReference.getJob().setStatus("Completed");
            System.out.println("True");
        }
        else {
            jobReference.getJob().setStatus("Pending");
            System.out.println("False");
        }
    }

    @FXML
    void jobPaidCheckboxClicked(ActionEvent event) {
        if(jobPaidCheckbox.isSelected()) {
            jobReference.getJob().setPaidFor("True");
            System.out.println("True");
        }
        else {
            jobReference.getJob().setPaidFor("False");
            System.out.println("False");
        }
    }

    @FXML
    void mechanicComboBoxClicked(ActionEvent event) throws IOException {
        try {
            User mechanic = mechanicHashMap.get(mechanicComboBox.getSelectionModel().getSelectedItem().getText());
            jobReference.getJob().setUsername(mechanic.getUsername());
        }
        catch(Exception e) {}
    }

    @FXML
    void saveBtnClicked(ActionEvent event) {
        Job job = jobReference.getJob();
        JobDAO jobDAO = new JobDAO();
        jobDAO.update(job);
        SystemAlert systemAlert = new SystemAlert(editMonitorJobStackPane,
                "Success", "Saved changes to job");
        jobCompletedCheckbox.setSelected(false);
        jobPaidCheckbox.setSelected(false);
        taskList.getSelectionModel().select(null);
        taskList.getItems().clear();
        partList.getSelectionModel().select(null);
        partList.getItems().clear();
        mechanicComboBox.getSelectionModel().select(null);
        mechanicComboBox.getItems().clear();
        mechanicHashMap.clear();
        refreshList();
    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(editMonitorJobStackPane, NavigationModel.EDIT_MONITOR_JOB_ID);
        jobReference = JobReference.getInstance();
        mechanicHashMap = new HashMap<>();
        refreshList();
    }

    public void refreshList() {
        UserDAO userDAO = new UserDAO();
        JobTaskDAO jobTaskDAO = new JobTaskDAO();
        PartJobDAO partJobDAO = new PartJobDAO();
        TaskDAO taskDAO = new TaskDAO();
        PartDAO partDAO = new PartDAO();
        for(User u: userDAO.getMechanics()) {
            Label mechanicLabel = new Label("Username: " + u.getUsername());
            mechanicHashMap.put(mechanicLabel.getText(), u);
            mechanicComboBox.getItems().add(mechanicLabel);
            if(jobReference.getJob().getUsername().equals(u.getUsername())) {
                mechanicComboBox.getSelectionModel().select(mechanicLabel);
                break;
            }
        }
        for(JobTask jt: jobTaskDAO.getAll()) {
            if (jt.getJobID() == jobReference.getJob().getJobID()) {
                for (Task t : taskDAO.getAll()) {
                    if (jt.getTaskID() == t.getTaskID()) {
                        Label jobTaskLabel = new Label("Task: " + t.getName() + " / Duration: " + jt.getAlteredDuration());
                        taskList.getItems().add(jobTaskLabel);
                    }
                }
            }
        }
        for(PartJob pj: partJobDAO.getAll()) {
            if (pj.getJobID() == jobReference.getJob().getJobID()) {
                for(Part p: partDAO.getAll()) {
                    if(pj.getPartID() == p.getPartID()) {
                        Label jobPartLabel = new Label("Part: " + p.getName() + " / Amount: " + pj.getStockUsed());
                        partList.getItems().add(jobPartLabel);
                    }
                }
            }
        }
        if(jobReference.getJob().getStatus().equals("Completed")) {
            jobCompletedCheckbox.setSelected(true);
        }
        if(jobReference.getJob().getPaidFor().equals("True")) {
            jobPaidCheckbox.setSelected(true);
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
