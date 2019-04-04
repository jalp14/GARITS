package TwentyThreeProductions.Controller.Jobs.NewJob;

import TwentyThreeProductions.Domain.*;
import TwentyThreeProductions.Model.*;
import TwentyThreeProductions.Model.Database.DAO.*;
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

public class PartOnlySelectController {

    private SceneSwitch sceneSwitch;

    private CustomerReference customerReference;

    private HashMap<String, Part> partHashMap;

    @FXML
    private StackPane partOnlySelectStackPane;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text usertypeLbl;

    @FXML
    private Label welcomeMessage;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton selectPartBtn;

    @FXML
    private Label formLabel;

    @FXML
    private JFXTextField searchField;

    @FXML
    private JFXButton searchBtn;

    @FXML
    private JFXListView<Label> partList;

    @FXML
    private JFXTextField stockUsedField;

    @FXML
    private Label stockUsedLabel;

    @FXML
    private Label customerNameLbl;

    // The system clears the list of parts and the hashmap associated with it before refreshing the list and returning
    // to the previous page
    @FXML
    void backBtnClicked(ActionEvent event) {
        partList.getSelectionModel().select(null);
        partList.getItems().clear();
        partHashMap.clear();
        searchField.clear();
        refreshList();
        sceneSwitch.switchScene(NavigationModel.NEW_JOB_CAR_MENU_ID);
    }

    @FXML
    void searchBtnClick(ActionEvent event) {
        // The system clears both the list and the hashmap and prepares the appropriate items in order to correctly
        // search for available parts.
        String searchTerm = searchField.getText();
        partList.getItems().clear();
        partHashMap.clear();

        // If the search term inputted by the user is empty, the system refreshes the list with every value available.
        if(searchTerm.isEmpty()) {
            refreshList();
        }

        // Otherwise, it only adds the parts that contain the currently inputted value as either their ID, stock level or
        // name to the list of available parts.
        else {
            PartDAO partDAO = new PartDAO();
            for(Part p: partDAO.getAll()) {
                if((p.getPartID().contains(searchTerm) || p.getName().contains(searchTerm) || ((String.valueOf(p.getStockLevel()).contains(searchTerm))
                && p.getStockLevel() > 0))) {
                    Label partLabel = new Label("ID: " + p.getPartID() + " / Name: " + p.getName() + " / Stock: " + p.getStockLevel());
                    partHashMap.put(partLabel.getText(), p);
                    partList.getItems().add(partLabel);
                }
            }
        }
    }

    @FXML
    void selectPartBtnClicked(ActionEvent event) throws IOException {
        // If there is not a part selected, the system will not continue and instead produce an alert stating that a
        // part must be selected to continue.
        if(partList.getSelectionModel().isEmpty()) {
            SystemAlert systemAlert = new SystemAlert(partOnlySelectStackPane,
                    "Failure", "No part selected");
        }
        else {
            // Otherwise, the system begins to create an object for the new job, the part which will have its stock adjusted,
            // and the junction table between parts and jobs. The system then generates a job ID for the job object.
            Part part = partHashMap.get(partList.getSelectionModel().getSelectedItem().getText());
            Customer customer = customerReference.getCustomer();
            PartJob partJob = new PartJob();
            Job job = new Job();
            PartDAO partDAO = new PartDAO();
            JobDAO jobDAO = new JobDAO();
            UserDAO userDAO = new UserDAO();
            PartJobDAO partJobDAO = new PartJobDAO();
            int jobID = 1;
            if(!(jobDAO.getAll().isEmpty())) {
                for (Job j : jobDAO.getAll()) {
                    jobID++;
                }
            }
            job.setJobID(jobID);

            // After that is completed, the system checks to see if there is a mechanic available for the job, either through
            // the currently logged in user label or through the system database. If there are no mechanics in the system, an
            // alert will pop up telling the user this. Otherwise, the mechanic's username is attached to the job object.
            boolean isMechanicTableEmpty = false;
            if (usertypeLbl.getText().equals("Mechanic") || usertypeLbl.getText().equals("Foreperson")) {
                job.setUsername(usernameLbl.getText().substring(8));
            } else if (userDAO.getMechanics().isEmpty()) {
                isMechanicTableEmpty = true;
            } else {
                for (User u : userDAO.getMechanics()) {
                    job.setUsername(u.getUsername());
                    break;
                }
            }
            if (isMechanicTableEmpty) {
                SystemAlert systemAlert = new SystemAlert(partOnlySelectStackPane,
                        "Failure", "No mechanic in system database");
            } else {
                // After this, the customer ID is set to the customer that was selected earlier, the registration ID
                // for the vehicle is set to null, the date booked is set to the current date, the job description is set
                // to specify that it is a parts-only job and the statuses are set to the appropriate values.
                job.setCustomerID(Integer.parseInt(customer.getCustomerID()));
                job.setRegistrationID(null);
                java.util.Date currentDate = new java.util.Date();
                java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
                job.setDateBookedIn(sqlDate);
                job.setDescription("Spare parts ordered");
                job.setStatus("Pending");
                job.setPaidFor("False");
                job.setChecked("False");
                try {
                    // The system checks to see if the stock specified by the user is not equal to a value below 1 or
                    // above the current stock so that it can actually be used by the system. If it cannot, the system will produce
                    // an alert stating this.
                    if (Integer.parseInt(stockUsedField.getText()) < 1 || part.getStockLevel() < Integer.parseInt(stockUsedField.getText())) {
                        SystemAlert systemAlert = new SystemAlert(partOnlySelectStackPane,
                                "Failure", "Stock out of bounds");
                    }

                    // The system adds the job the system database, and then reduces the stock of the selected part by the
                    // inputted value before updating it within the system database. After this, the junction table for the
                    // parts and jobs has the IDs for both parts and jobs set, as well as the stock used to the value that was
                    // inputted. After this is done, the entry is saved in the junction table and a system alert is generated.
                    else {
                        jobDAO.save(job);
                        part.setStockLevel(part.getStockLevel() - Integer.parseInt(stockUsedField.getText()));
                        partDAO.update(part);
                        partJob.setJobID(jobID);
                        partJob.setPartID(part.getPartID());
                        partJob.setStockUsed(stockUsedField.getText());
                        partJobDAO.save(partJob);
                        SystemAlert systemAlert = new SystemAlert(partOnlySelectStackPane,
                                "Success", "Added part-only job");
                        if(part.getStockLevel() <= part.getThresholdLevel()) {
                            // If the stock of the part falls below the threshold as a result of this action, a notification
                            // will be generated for the user alerting them of this.
                            SystemNotification notification = new SystemNotification(partOnlySelectStackPane);
                            notification.setNotificationMessage("The number of parts has fallen below the threshold, " +
                                    "please order more as soon as possible");
                            notification.showNotification(NavigationModel.UPDATE_STOCK_ID, DBLogic.getDBInstance().getUsername());
                        }

                        // Finally, the system clears the list of parts and the hashmap associated with it before refreshing the list and returning
                        // to the previous page
                        partList.getSelectionModel().select(null);
                        partList.getItems().clear();
                        partHashMap.clear();
                        refreshList();
                    }
                } catch (Exception e) {
                    // If the stock inputted is not an integer, an alert will be generating specifying that the value is
                    // invalid.
                    SystemAlert systemAlert = new SystemAlert(partOnlySelectStackPane,
                            "Failure", "Invalid stock given");
                }
            }
        }
    }

    // This function is called when the page is first loaded, and it adds the current scene to the list of active scenes
    // on the system, as well as generates the labels for both the username and usertype of the user currently logged into
    // the system. After this, the system initialises the static class for returning the job details as well as the customer
    // details and the hashmap of the parts.
    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(partOnlySelectStackPane, NavigationModel.PART_ONLY_SELECT_ID);
        usernameLbl.setText(DBLogic.getDBInstance().getUsername());
        usertypeLbl.setText(DBLogic.getDBInstance().getUser_type());
        customerReference = CustomerReference.getInstance();
        partHashMap = new HashMap<>();
        refreshList();
    }

    // This function first generates the appropriate classes for querying the database, before generating a list of all
    // parts currently within the system database. After this, a label is generated for both the name of the customer
    // who is currently assigned to the job being worked on as well as details on the job itself.
    public void refreshList() {
        PartDAO partDAO = new PartDAO();
        for(Part p: partDAO.getAll()) {
            if (p.getStockLevel() > 0) {
                Label partLabel = new Label("ID: " + p.getPartID() + " / Name: " + p.getName() + " / Stock: " + p.getStockLevel());
                partHashMap.put(partLabel.getText(), p);
                partList.getItems().add(partLabel);
            }
        }
        customerNameLbl.setText("Name: " + customerReference.getCustomer().getFirstName() + " " + customerReference.getCustomer().getLastName());
        stockUsedField.setText("1");

    }
}