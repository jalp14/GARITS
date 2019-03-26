package TwentyThreeProductions.Controller.Jobs.EditMonitor;

import TwentyThreeProductions.Domain.Customer;
import TwentyThreeProductions.Domain.Part;
import TwentyThreeProductions.Domain.PartJob;
import TwentyThreeProductions.Model.Database.DAO.CustomerDAO;
import TwentyThreeProductions.Model.Database.DAO.PartJobDAO;
import TwentyThreeProductions.Model.Database.DAO.PartDAO;
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
        if(partList.getSelectionModel().isEmpty()) {
            SystemAlert systemAlert = new SystemAlert(addPartToJobStackPane,
                    "Failure", "No task selected");
        }
        else {
            Part part = partHashMap.get(partList.getSelectionModel().getSelectedItem().getText());
            PartJob partJob = new PartJob();
            PartJobDAO partJobDAO = new PartJobDAO();
            PartDAO partDAO = new PartDAO();
            partJob.setJobID(jobReference.getJob().getJobID());
            partJob.setPartID(part.getPartID());
            try {
                if (Integer.parseInt(stockUsedField.getText()) < 1 || Integer.parseInt(part.getStockLevel()) < Integer.parseInt(stockUsedField.getText())) {
                    SystemAlert systemAlert = new SystemAlert(addPartToJobStackPane,
                            "Failure", "Stock out of bounds");
                } else {
                    partJob.setStockUsed(stockUsedField.getText());
                    boolean isPartInJob = false;
                    int stockDifference = 0;
                    for (PartJob pj : partJobDAO.getAll()) {
                        if (pj.getPartID().equals(partJob.getPartID()) && pj.getJobID() == partJob.getJobID()) {
                            isPartInJob = true;
                            stockDifference = Integer.parseInt(pj.getStockUsed()) + Integer.parseInt(partJob.getStockUsed());
                        }
                    }
                    if (stockDifference > Integer.parseInt(part.getStockLevel())) {
                        SystemAlert systemAlert = new SystemAlert(addPartToJobStackPane,
                                "Failure", "Stock out of bounds");
                    }
                    else {
                        if (isPartInJob) {
                            partJobDAO.update(partJob);
                            part.setStockLevel(String.valueOf(Integer.parseInt(part.getStockLevel()) - stockDifference));
                            SystemAlert systemAlert = new SystemAlert(addPartToJobStackPane,
                                    "Success", "Stock used updated");
                        } else {
                            partJobDAO.save(partJob);
                            part.setStockLevel(String.valueOf(Integer.parseInt(part.getStockLevel()) - Integer.parseInt(stockUsedField.getText())));
                            SystemAlert systemAlert = new SystemAlert(addPartToJobStackPane,
                                    "Success", "Added part to job");
                        }
                        partDAO.update(part);
                        partList.getSelectionModel().select(null);
                        partList.getItems().clear();
                        partHashMap.clear();
                        refreshList();
                    }
                }
            }
            catch(Exception e) {
                SystemAlert systemAlert = new SystemAlert(addPartToJobStackPane,
                        "Failure", "Invalid stock given");
            }
        }
    }

    @FXML
    void backBtnClicked(ActionEvent event) throws IOException {
        partList.getItems().clear();
        partHashMap.clear();
        searchField.setText("");
        refreshList();
        sceneSwitch.activateSceneAlways(NavigationModel.EDIT_MONITOR_JOB_ID, backBtn.getScene());
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

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(addPartToJobStackPane, NavigationModel.ADD_PART_TO_JOB_ID);
        jobReference = jobReference.getInstance();
        partHashMap = new HashMap<>();
        refreshList();
    }

    public void refreshList() {
        PartDAO partDAO = new PartDAO();
        for (Part p : partDAO.getAll()) {
            if (Integer.parseInt(p.getStockLevel()) > 0) {
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

