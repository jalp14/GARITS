package TwentyThreeProductions.Controller.Jobs.EditMonitor;

import TwentyThreeProductions.Domain.*;
import TwentyThreeProductions.Model.*;
import TwentyThreeProductions.Model.Database.DAO.*;
import TwentyThreeProductions.Model.HelperClasses.PaymentHelper;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.HashMap;

public class EditMonitorJobController {

    private SceneSwitch sceneSwitch;

    private JobReference jobReference;

    private PaymentHelper paymentHelper;

    private HashMap<String, User> mechanicHashMap;

    private boolean cardPaymentRequired = false;

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
    private Label amountHeading;

    @FXML
    private JFXTextField amountField;

    @FXML
    private JFXCheckBox jobCompletedCheckbox;

    @FXML
    private JFXCheckBox jobPaidCheckbox;

    @FXML
    private ToggleGroup jobType;

    @FXML
    private JFXRadioButton motRadio;

    @FXML
    private JFXRadioButton repairsRadio;

    @FXML
    private JFXRadioButton annualServiceRadio;

    @FXML
    private ToggleGroup paymentType;

    @FXML
    private JFXRadioButton cashRadio;

    @FXML
    private JFXRadioButton cardRadio;


    // Once the button labelled 'MoT' is pressed, the other job type buttons are forcibly unpressed.
    @FXML
    void motRadioSelected(ActionEvent event) {
        annualServiceRadio.setSelected(false);
        repairsRadio.setSelected(false);
    }

    // Once the button labelled 'Annual Service' is pressed, the other job type buttons are forcibly unpressed.
    @FXML
    void annualServiceRadioSelected(ActionEvent event) {
        motRadio.setSelected(false);
        repairsRadio.setSelected(false);
    }

    // Once the button labelled 'Repairs' is pressed, the other job type buttons are forcibly unpressed.
    @FXML
    void repairsRadioSelected(ActionEvent event) {
        motRadio.setSelected(false);
        annualServiceRadio.setSelected(false);
    }

    // The system moves to the page for adding a part to the currently selected job.
    @FXML
    void addPartBtnClick(ActionEvent event) throws IOException {
        sceneSwitch.activateSceneAlways(NavigationModel.ADD_PART_TO_JOB_ID, backBtn.getScene());
    }

    // The system checks to see if the job currently has a car attached to it. If it does, then the system moves to the
    // page for adding a part to the currently selected job. Otherwise, the system sends an alert stating the a task
    // cannot be added to a parts-only job.
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

    // The system clears every adjustment to the job that could have been made, before refreshing it to the new appropriate
    // values and moving back to the previous page.
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

    // If, by clicking the box, it marks the box as selected, it sets the status to 'Completed'. Otherwise, it marks the
    // status as 'Pending'
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

    // If, by clicking the box, it marks the box as selected, it sets the payment status to 'True' and makes the fields for
    // inputting how the customer paid for the job visible. Otherwise, it marks the payment status as 'False' and makes those
    // previously mentioned fields invisible once again
    @FXML
    void jobPaidCheckboxClicked(ActionEvent event) {
        if(jobPaidCheckbox.isSelected()) {
            jobReference.getJob().setPaidFor("True");
            System.out.println("True");
            cashRadio.setVisible(true);
            cardRadio.setVisible(true);
            amountHeading.setVisible(true);
            amountField.setVisible(true);
        }
        else {
            jobReference.getJob().setPaidFor("False");
            System.out.println("False");
            cashRadio.setVisible(false);
            cardRadio.setVisible(false);
            amountHeading.setVisible(false);
            amountField.setVisible(false);
        }
    }

    // Once the button labelled 'Cash' is pressed, the other payment type button is forcibly unpressed and the system notes
    // that further payment details do not need to be added.
    @FXML
    void cashRadioSelected(ActionEvent event) {
        cardRadio.setSelected(false);
        cardPaymentRequired = false;
    }

    // Once the button labelled 'Card' is pressed, the other payment type button is forcibly unpressed and the system notes
    // that further payment details need to be added.
    @FXML
    void cardRadioSelected(ActionEvent event) {
        cashRadio.setSelected(false);
        cardPaymentRequired = true;
    }

    // The user sets the mechanic tied to the job to the currently selected mechanic in the drop box menu.
    @FXML
    void mechanicComboBoxClicked(ActionEvent event) throws IOException {
        try {
            User mechanic = mechanicHashMap.get(mechanicComboBox.getSelectionModel().getSelectedItem().getText());
            jobReference.getJob().setUsername(mechanic.getUsername());
        }
        catch(Exception e) {}
    }

    @FXML
    void saveBtnClicked(ActionEvent event) throws IOException {
        // The system gets the previously selected job as an object to store the new changes to, as well as retrieving
        // the current date of the system.
        Job job = jobReference.getJob();
        java.util.Date currentDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());

        // After this, if the job type is not a parts-only type. the system will set the job description to the description
        // of the job type button that was selected earlier. In the event that one was not selected, an alert will appear
        // stating as much.
        if(!(job.getDescription().equals("Spare parts ordered"))) {
            if (motRadio.isSelected()) {
                job.setDescription("MoT job");
            } else if (annualServiceRadio.isSelected()) {
                job.setDescription("Annual Service job");
            } else if (repairsRadio.isSelected()) {
                job.setDescription("Repairs job");
            }
            if (job.getDescription().isEmpty()) {
                SystemAlert systemAlert = new SystemAlert(editMonitorJobStackPane,
                        "Failure", "No job type selected");
            }
        }

        // After this, if the job has been marked as completed, it will make the date of the job's completion the current
        // system date. Otherwise, this value will be set to null. After this, the job is used to update the details for
        // the job within the system database.
        if(job.getStatus().equals("Completed")) {
            job.setDateCompleted(sqlDate);
        }
        else {
            job.setDateCompleted(null);
        }
        JobDAO jobDAO = new JobDAO();
        jobDAO.update(job);

        // After this is done, if the job has been marked as paid, the system makes sure that the static class for payment
        // details is updated with the amount paid and, in the event that the amount paid is empty, an alert stating as much
        // will be created.
        if(job.getPaidFor().equals("True")) {
            try {
                paymentHelper.setAmountPaid(Float.parseFloat(amountField.getText()));
                if(amountField.getText().isEmpty()) {
                    SystemAlert systemAlert = new SystemAlert(editMonitorJobStackPane,
                            "Failure", "No price given");
                }

                // If the payment field is not empty, a new object to store the payment is created as well as an object
                // to add the payment to the database. If the payment type is selected as type, the appropriate fields are
                // added to the object and then passed into the database. Otherwise, the system clears every adjustment to
                // the job that could have been made, before refreshing it to the new appropriate values and moving to the
                // page for setting up card details.
                else if (cashRadio.isSelected()) {
                    Payment payment = new Payment();
                    PaymentDAO paymentDAO = new PaymentDAO();
                    payment.setJobID(job.getJobID());
                    payment.setCustomerID(job.getCustomerID());
                    payment.setType("Cash");
                    payment.setDate(sqlDate);
                    payment.setAmount(paymentHelper.getAmountPaid());
                    paymentDAO.saveCash(payment);
                } else {
                    motRadio.setSelected(false);
                    annualServiceRadio.setSelected(false);
                    repairsRadio.setSelected(false);
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
                    sceneSwitch.activateSceneAlways(NavigationModel.ADD_NEW_CARD_DETAILS_ID, backBtn.getScene());
                }
            }

            // If the price is not in a number format, the system will produce an alert stating this and set the status of
            // the payment to false once again.
            catch(Exception e) {
                SystemAlert systemAlert = new SystemAlert(editMonitorJobStackPane,
                        "Failure", "Invalid price given");
                job.setPaidFor("False");
            }
        }

        // Once the payment has been sorted out, the system produces an alert stating that the job details have been updated
        // and after that the system clears every adjustment to the job that could have been made, then refreshing it to the
        // new appropriate values
        SystemAlert systemAlert = new SystemAlert(editMonitorJobStackPane,
                "Success", "Saved changes to job");
        motRadio.setSelected(false);
        annualServiceRadio.setSelected(false);
        repairsRadio.setSelected(false);
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

    // This function is called up when the page is first opened, and it adds the scene to the list of currently
    // active scenes as well as changing the labels for the username and type with the currently logged in user,
    // and then finally it initialises the hashmap for storing the mechanics as well as the static classes that will be
    // needed, and finally refreshes the currently stored details of the job.
    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(editMonitorJobStackPane, NavigationModel.EDIT_MONITOR_JOB_ID);
        usernameLbl.setText(DBLogic.getDBInstance().getUsername());
        usertypeLbl.setText(DBLogic.getDBInstance().getUser_type());
        jobReference = JobReference.getInstance();
        paymentHelper = PaymentHelper.getInstance();
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
            jobCompletedCheckbox.setDisable(true);
        }
        if(jobReference.getJob().getPaidFor().equals("True")) {
            jobPaidCheckbox.setSelected(true);
            jobPaidCheckbox.setSelected(true);
        }
        else {
            cashRadio.setVisible(false);
            cardRadio.setVisible(false);
            amountHeading.setVisible(false);
            amountField.setVisible(false);
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
        if(jobReference.getJob().getDescription().equals("Spare parts ordered")) {
            motRadio.setVisible(false);
            annualServiceRadio.setVisible(false);
            repairsRadio.setVisible(false);
        }
        else if(jobReference.getJob().getDescription().equals("MoT job")) {
            motRadio.setSelected(true);
            annualServiceRadio.setSelected(false);
            repairsRadio.setSelected(false);
        }
        else if(jobReference.getJob().getDescription().equals("Annual Service job")) {
            motRadio.setSelected(false);
            annualServiceRadio.setSelected(true);
            repairsRadio.setSelected(false);
        }
        else {
            motRadio.setSelected(false);
            annualServiceRadio.setSelected(false);
            repairsRadio.setSelected(true);
        }
    }
}
