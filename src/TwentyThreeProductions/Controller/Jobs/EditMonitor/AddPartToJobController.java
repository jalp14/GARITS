package TwentyThreeProductions.Controller.Jobs.EditMonitor;

import TwentyThreeProductions.Domain.Customer;
import TwentyThreeProductions.Domain.Part;
import TwentyThreeProductions.Domain.PartJob;
import TwentyThreeProductions.Model.*;
import TwentyThreeProductions.Model.Database.DAO.CustomerDAO;
import TwentyThreeProductions.Model.Database.DAO.PartJobDAO;
import TwentyThreeProductions.Model.Database.DAO.PartDAO;
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

public class AddPartToJobController {

    private SceneSwitch sceneSwitch;

    private JobReference jobReference;

    private HashMap<String, Part> partHashMap;

    @FXML
    private StackPane addPartToJobStackPane;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text usertypeLbl;

    @FXML
    private Label welcomeMessage;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton addPartBtn;

    @FXML
    private Label jobDetailsLbl;

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
    void addPartBtnClicked(ActionEvent event) {
        // If there is not a part selected, the system will not continue and instead produce an alert stating that a
        // part must be selected to continue.
        if(partList.getSelectionModel().isEmpty()) {
            SystemAlert systemAlert = new SystemAlert(addPartToJobStackPane,
                    "Failure", "No part selected");
        }
        // Otherwise, the system begins to create an object for the part, which will have its stock adjusted, and the
        // junction table between parts and jobs. The system adds both IDs to the junction object before attempting to
        // get the stock level inputted.
        else {
            Part part = partHashMap.get(partList.getSelectionModel().getSelectedItem().getText());
            PartJob partJob = new PartJob();
            PartJobDAO partJobDAO = new PartJobDAO();
            PartDAO partDAO = new PartDAO();
            partJob.setJobID(jobReference.getJob().getJobID());
            partJob.setPartID(part.getPartID());
            try {
                // The system checks to see if the stock specified by the user is not equal to a value below 1 or
                // above the current stock so that it can actually be used by the system. If it cannot, the system will produce
                // an alert stating this.
                if (Integer.parseInt(stockUsedField.getText()) < 1 || part.getStockLevel() < Integer.parseInt(stockUsedField.getText())) {
                    SystemAlert systemAlert = new SystemAlert(addPartToJobStackPane,
                            "Failure", "Stock out of bounds");
                }
                // Otherwise, the system sets the stock to the inputted value and begins to check if the part is currently tied to
                // the job. If it is, a boolean value will be marked as true and, instead of adding a new part, simply adjusts the
                // stock taken for the current entry for the junction table as well as the current stock of the part, unless the
                // stock adjustment would cause the part stock to go below zero, in which case the system produces another alert
                // stating this.
                else {
                    partJob.setStockUsed(stockUsedField.getText());
                    boolean isPartInJob = false;
                    int stockDifference = 0;
                    for (PartJob pj : partJobDAO.getAll()) {
                        if (pj.getPartID().equals(partJob.getPartID()) && pj.getJobID() == partJob.getJobID()) {
                            isPartInJob = true;
                            stockDifference = Integer.parseInt(pj.getStockUsed()) + Integer.parseInt(partJob.getStockUsed());
                        }
                    }
                    if (stockDifference > part.getStockLevel()) {
                        SystemAlert systemAlert = new SystemAlert(addPartToJobStackPane,
                                "Failure", "Stock out of bounds");
                    }
                    else {
                        if (isPartInJob) {
                            partJobDAO.update(partJob);
                            part.setStockLevel(part.getStockLevel() - stockDifference);
                            SystemAlert systemAlert = new SystemAlert(addPartToJobStackPane,
                                    "Success", "Stock used updated");
                        }
                        // If the part is not currently tied to the job, it creates a new entry within the junction table for
                        // parts and jobs and then adjusts the stock accordingly, before producing an alert stating that the
                        // part was successfully added.
                        else {
                            partJobDAO.save(partJob);
                            part.setStockLevel(part.getStockLevel() - Integer.parseInt(stockUsedField.getText()));
                            SystemAlert systemAlert = new SystemAlert(addPartToJobStackPane,
                                    "Success", "Added part to job");
                        }
                        partDAO.update(part);

                        // If this action causes the stock level to go below the stock threshold, a notification alerting the
                        // user of this will be generated.
                        if(part.getStockLevel() <= part.getThresholdLevel()) {
                            SystemNotification notification = new SystemNotification(addPartToJobStackPane);
                            notification.setNotificationMessage("The number of parts has fallen below the threshold, " +
                                    "please order more as soon as possible");
                            notification.showNotification(NavigationModel.UPDATE_STOCK_ID, DBLogic.getDBInstance().getUsername());
                        }

                        //After this is all done, the list of parts is cleared alongside the hashmap, and the list is refreshed.
                        partList.getSelectionModel().select(null);
                        partList.getItems().clear();
                        partHashMap.clear();
                        refreshList();
                    }
                }
            }
            // If the value for stock is not an integer value, the system will produce an alert stating this.
            catch(Exception e) {
                SystemAlert systemAlert = new SystemAlert(addPartToJobStackPane,
                        "Failure", "Invalid stock given");
            }
        }
    }

    // The system clears the list of parts and the hashmap associated with it before refreshing the list and returning
    // to the previous page.
    @FXML
    void backBtnClicked(ActionEvent event) throws IOException {
        partList.getItems().clear();
        partHashMap.clear();
        searchField.clear();
        refreshList();
        sceneSwitch.activateSceneAlways(NavigationModel.EDIT_MONITOR_JOB_ID, backBtn.getScene());
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
                if((p.getPartID().contains(searchTerm) || p.getName().contains(searchTerm) || String.valueOf(p.getStockLevel()).contains(searchTerm))
                        && p.getStockLevel() > 0) {
                    Label partLabel = new Label("ID: " + p.getPartID() + " / Name: " + p.getName() + " / Stock: " + p.getStockLevel());
                    partHashMap.put(partLabel.getText(), p);
                    partList.getItems().add(partLabel);
                }
            }
        }
    }

    // This function is called when the page is first loaded, and it adds the current scene to the list of active scenes
    // on the system, as well as generates the labels for both the username and usertype of the user currently logged into
    // the system. After this, the system initialises the static class for returning the job details and the hashmap of the
    // parts.
    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(addPartToJobStackPane, NavigationModel.ADD_PART_TO_JOB_ID);
        usernameLbl.setText(DBLogic.getDBInstance().getUsername());
        usertypeLbl.setText(DBLogic.getDBInstance().getUser_type());
        jobReference = jobReference.getInstance();
        partHashMap = new HashMap<>();
        refreshList();
    }

    // This function first generates the appropriate classes for querying the database, before generating a list of all
    // parts currently within the system database. After this, a label is generated for both the name of the customer
    // who is currently assigned to the job being worked on as well as details on the job itself.
    public void refreshList() {
        PartDAO partDAO = new PartDAO();
        for (Part p : partDAO.getAll()) {
            if (p.getStockLevel() > 0) {
                Label partLabel = new Label("ID: " + p.getPartID() + " / Name: " + p.getName() + " / Stock: " + p.getStockLevel());
                partHashMap.put(partLabel.getText(), p);
                partList.getItems().add(partLabel);
            }
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
        stockUsedField.setText("1");
    }
}

