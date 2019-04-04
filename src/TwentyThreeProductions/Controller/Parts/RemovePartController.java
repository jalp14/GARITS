package TwentyThreeProductions.Controller.Parts;

import TwentyThreeProductions.Domain.Part;
import TwentyThreeProductions.Model.DBLogic;
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

    // The system clears the currently selected part before refreshing the list and moving back to the previous
    // page.
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
        // The system clears both the list and the hashmap and prepares the appropriate items in order to correctly
        // search for available parts.
        PartDAO partDAO = new PartDAO();
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
            for(Part p: partDAO.getAll()) {
                if(p.getPartID().contains(searchTerm) || p.getName().contains(searchTerm) || String.valueOf(p.getStockLevel()).contains(searchTerm)) {
                    Label partLabel = new Label("ID: " + p.getPartID() + " / Name: " + p.getName());
                    partHashMap.put(partLabel.getText(), p);
                    partList.getItems().add(partLabel);
                }
            }
        }
    }

    // This function is called up when the page is first opened, and it adds the scene to the list of currently
    // active scenes as well as changing the labels for the username and type with the currently logged in user,
    // and then finally it initialises\ the hashmap for storing the parts and refresh the list of currently available
    // parts.
    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(removePartStackPane, NavigationModel.REMOVE_PART_ID);
        usernameLbl.setText(DBLogic.getDBInstance().getUsername());
        usertypeLbl.setText(DBLogic.getDBInstance().getUser_type());
        partHashMap = new HashMap<>();
        refreshList();
    }

    // This function goes through every part in the system database, assigns them to a label to be put in the list
    // of available items as well as in the hashmap with an appropriate string reference.
    public void refreshList() {
        PartDAO partDAO = new PartDAO();
        for(Part p: partDAO.getAll()) {
            Label partLabel = new Label("ID: " + p.getPartID() + " / Name: " + p.getName());
            partHashMap.put(partLabel.getText(), p);
            partList.getItems().add(partLabel);
        }
    }
}

