package TwentyThreeProductions.Controller.Jobs.EditMonitor;

import TwentyThreeProductions.Domain.JobPart;
import TwentyThreeProductions.Domain.Part;
import TwentyThreeProductions.Model.Database.DAO.JobPartDAO;
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
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

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
    void addPartBtnClicked(ActionEvent event) {
        if(partList.getSelectionModel().isEmpty()) {
            SystemAlert systemAlert = new SystemAlert(addPartToJobStackPane,
                    "Failure", "No task selected");
        }
        else {
            Part part = partHashMap.get(partList.getSelectionModel().getSelectedItem().getText());
            JobPart jobPart = new JobPart();
            JobPartDAO jobPartDAO = new JobPartDAO();
            jobPart.setJobID(jobReference.getJob().getJobID());
            jobPart.setPartID(part.getPartID());
            jobPart.setStockUsed("1");
            jobPartDAO.save(jobPart);
            SystemAlert systemAlert = new SystemAlert(addPartToJobStackPane,
                    "Success", "Added task to job");
            partList.getSelectionModel().select(null);
            partList.getItems().clear();
            partHashMap.clear();
            refreshList();
        }
    }

    @FXML
    void backBtnClicked(ActionEvent event) {
        partList.getItems().clear();
        partHashMap.clear();
        searchField.setText("");
        refreshList();
        sceneSwitch.switchScene(NavigationModel.EDIT_MONITOR_JOB_ID);
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
    }
}

