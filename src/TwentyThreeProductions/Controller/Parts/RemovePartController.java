package TwentyThreeProductions.Controller.Parts;

import TwentyThreeProductions.Domain.Part;
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
import java.util.HashMap;

public class RemovePartController {

    private SceneSwitch sceneSwitch;

    private HashMap<String, Part> partHashMap;

    @FXML
    private StackPane removePartStackPane;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text usertypeLbl;

    @FXML
    private Label welcomeMessage;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton removePartBtn;

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
        refreshList();
        sceneSwitch.switchScene(NavigationModel.PARTS_MAIN_ID);
    }

    @FXML
    void removePartBtnClicked(ActionEvent event) {
        // The system checks to make sure that a part was selected to be removed and, if a part was not selected, the
        // system produces an alert stating that a part was not selected and thus no part could be removed.
        if(partList.getSelectionModel().isEmpty()) {
            SystemAlert systemAlert = new SystemAlert(removePartStackPane,
                    "Failure", "No part selected");
        }

        // Otherwise, the system creates an object which holds the selected part, so that ic can be passed through to
        // an operation to remove a part from the system database. Once this is done, the system produces an alert stating
        // that a part was successfully removed from the system database and refreshes the list view.
        else {
            Part part = partHashMap.get(partList.getSelectionModel().getSelectedItem().getText());
            PartDAO partDAO = new PartDAO();
            partDAO.delete(part);
            SystemAlert systemAlert = new SystemAlert(removePartStackPane,
                    "Success", "Removed part");
            partList.getSelectionModel().select(null);
            partList.getItems().clear();
            partHashMap.clear();
            refreshList();
        }

    }

    @FXML
    void searchBtnClick(ActionEvent event) {
        PartDAO partDAO = new PartDAO();
        String searchTerm = searchField.getText();
        partList.getItems().clear();
        partHashMap.clear();
        if(searchTerm.isEmpty()) {
            refreshList();
        }
        else {
            for(Part p: partDAO.getAll()) {
                if(p.getPartID().contains(searchTerm) || p.getName().contains(searchTerm) || p.getStockLevel().contains(searchTerm)) {
                    Label partLabel = new Label("ID: " + p.getPartID() + " / Name: " + p.getName());
                    partHashMap.put(partLabel.getText(), p);
                    partList.getItems().add(partLabel);
                }
            }
        }
    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(removePartStackPane, NavigationModel.REMOVE_PART_ID);
        partHashMap = new HashMap<>();
        refreshList();
    }

    public void refreshList() {
        PartDAO partDAO = new PartDAO();
        for(Part p: partDAO.getAll()) {
            Label partLabel = new Label("ID: " + p.getPartID() + " / Name: " + p.getName());
            partHashMap.put(partLabel.getText(), p);
            partList.getItems().add(partLabel);
        }
    }
}

