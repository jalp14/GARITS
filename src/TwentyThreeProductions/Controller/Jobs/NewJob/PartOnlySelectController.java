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

    @FXML
    void backBtnClicked(ActionEvent event) {
        partList.getSelectionModel().select(null);
        partList.getItems().clear();
        partHashMap.clear();
        searchField.setText("");
        refreshList();
        sceneSwitch.switchScene(NavigationModel.NEW_JOB_CAR_MENU_ID);
    }

    @FXML
    void searchBtnClick(ActionEvent event) {
        String searchTerm = searchField.getText();
        partList.getItems().clear();
        partHashMap.clear();
        if(searchTerm.isEmpty()) {
            refreshList();
        }
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
        if(partList.getSelectionModel().isEmpty()) {
            SystemAlert systemAlert = new SystemAlert(partOnlySelectStackPane,
                    "Failure", "No part selected");
        }
        else {
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
                job.setCustomerID(Integer.parseInt(customer.getCustomerID()));
                job.setRegistrationID(null);
                java.util.Date currentDate = new java.util.Date();
                java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
                job.setDateBookedIn(sqlDate);
                job.setDescription("Spare parts ordered");
                job.setStatus("Pending");
                job.setPaidFor("False");
                try {
                    if (Integer.parseInt(stockUsedField.getText()) < 1 || part.getStockLevel() < Integer.parseInt(stockUsedField.getText())) {
                        SystemAlert systemAlert = new SystemAlert(partOnlySelectStackPane,
                                "Failure", "Stock out of bounds");
                    } else {
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
                            SystemNotification notification = new SystemNotification(partOnlySelectStackPane);
                            notification.setNotificationMessage("The number of parts has fallen below the threshold, " +
                                    "please order more as soon as possible");
                            notification.showNotification(NavigationModel.UPDATE_STOCK_ID, DBLogic.getDBInstance().getUsername());
                        }
                        partList.getSelectionModel().select(null);
                        partList.getItems().clear();
                        partHashMap.clear();
                        refreshList();
                    }
                } catch (Exception e) {
                    SystemAlert systemAlert = new SystemAlert(partOnlySelectStackPane,
                            "Failure", "Invalid stock given");
                }
            }
        }
    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(partOnlySelectStackPane, NavigationModel.PART_ONLY_SELECT_ID);
        usernameLbl.setText(DBLogic.getDBInstance().getUsername());
        usertypeLbl.setText(DBLogic.getDBInstance().getUser_type());
        customerReference = CustomerReference.getInstance();
        partHashMap = new HashMap<>();
        refreshList();
    }

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