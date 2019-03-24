package TwentyThreeProductions.Controller.Jobs.NewJob;

import TwentyThreeProductions.Domain.Customer;
import TwentyThreeProductions.Domain.Job;
import TwentyThreeProductions.Domain.JobPart;
import TwentyThreeProductions.Domain.Part;
import TwentyThreeProductions.Model.CustomerReference;
import TwentyThreeProductions.Model.Database.DAO.JobDAO;
import TwentyThreeProductions.Model.Database.DAO.JobPartDAO;
import TwentyThreeProductions.Model.Database.DAO.PartDAO;
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
import java.util.Date;
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
                if((p.getPartID().contains(searchTerm) || p.getName().contains(searchTerm) || p.getStockLevel().contains(searchTerm))
                && Integer.parseInt(p.getStockLevel()) > 0) {
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
            JobPart jobPart = new JobPart();
            Job job = new Job();
            PartDAO partDAO = new PartDAO();
            JobDAO jobDAO = new JobDAO();
            JobPartDAO jobPartDAO = new JobPartDAO();
            int jobID = 1;
            for(Job j: jobDAO.getAll()) {
                jobID++;
            }
            job.setJobID(jobID);
            job.setUsername(usernameLbl.getText().substring(7));
            job.setCustomerID(Integer.parseInt(customer.getCustomerID()));
            job.setRegistrationID(null);
            java.util.Date currentDate = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
            job.setDateBookedIn(sqlDate);
            job.setDescription("Spare part ordered");
            job.setSparePartsUsed(part.getPartID());
            job.setStatus("Pending");
            job.setPaidFor("False");
            jobDAO.save(job);
            part.setStockLevel(String.valueOf(Integer.parseInt(part.getStockLevel()) - 1));
            partDAO.update(part);
            jobPart.setJobID(jobID);
            jobPart.setPartID(part.getPartID());
            jobPart.setStockUsed("1");
            //jobPartDAO.save(jobPart);
            SystemAlert systemAlert = new SystemAlert(partOnlySelectStackPane,
                    "Success", "Added part-only job");
            partList.getSelectionModel().select(null);
            partList.getItems().clear();
            partHashMap.clear();
            refreshList();
        }
    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(partOnlySelectStackPane, NavigationModel.PART_ONLY_SELECT_ID);
        customerReference = CustomerReference.getInstance();
        partHashMap = new HashMap<>();
        refreshList();
    }

    public void refreshList() {
        PartDAO partDAO = new PartDAO();
        for(Part p: partDAO.getAll()) {
            if (Integer.parseInt(p.getStockLevel()) > 0) {
                Label partLabel = new Label("ID: " + p.getPartID() + " / Name: " + p.getName() + " / Stock: " + p.getStockLevel());
                partHashMap.put(partLabel.getText(), p);
                partList.getItems().add(partLabel);
            }
        }
    }
}