package TwentyThreeProductions.Controller.Parts;

import TwentyThreeProductions.Domain.Part;
import TwentyThreeProductions.Model.*;
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

public class SearchUpdateStockController {

    private SceneSwitch sceneSwitch;

    private HashMap<String, Part> partHashMap;

    private PartReference partReference;

    @FXML
    private StackPane searchUpdateStockStackPane;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text usertypeLbl;

    @FXML
    private Label welcomeMessage;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton updateStockBtn;

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
        partList.getItems().clear();
        partHashMap.clear();
        refreshList();
        sceneSwitch.switchScene(NavigationModel.PARTS_MAIN_ID);
    }

    @FXML
    void updateStockBtnClicked(ActionEvent event) throws IOException {
        // The system checks to make sure that a part was selected to have its stock or threshold altered and,
        // if a part was not selected, the system produces an alert stating that a part was not selected and thus
        // no part could be altered.
        if(partList.getSelectionModel().isEmpty()) {
            SystemAlert systemAlert = new SystemAlert(searchUpdateStockStackPane,
                    "Failure", "No part selected");
        }

        // Otherwise, the system creates an object which holds the selected part, so that ic can be passed through to
        // the next screen for updating the stock of a part. Once this is done, the system moves onto the next screen.
        else {
            partReference.setPart(partHashMap.get(partList.getSelectionModel().getSelectedItem().getText()));
            partList.getSelectionModel().select(null);
            partList.getItems().clear();
            partHashMap.clear();
            refreshList();
            sceneSwitch.activateSceneAlways(NavigationModel.UPDATE_STOCK_ID, backBtn.getScene());
        }
    }

    @FXML
    void searchBtnClick(ActionEvent event) {
        // The system clears both the list and the hashmap and prepares the appropriate items in order to correctly
        // search for available parts.
        PartDAO partDAO = new PartDAO();
        String searchTerm = searchField.getText();
        partList.getSelectionModel().select(null);
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
                    Label partLabel = new Label("ID: " + p.getPartID() + " / Name: " + p.getName() + " / Stock: " + p.getStockLevel());
                    partHashMap.put(partLabel.getText(), p);
                    partList.getItems().add(partLabel);
                }
            }
        }
    }

    // This function is called up when the page is first opened, and it adds the scene to the list of currently
    // active scenes as well as changing the labels for the username and type with the currently logged in user,
    // and then finally it initialises the hashmap for storing the parts and refresh the list of currently available
    // parts as well as gets the instance of the static class for storing the part.
    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(searchUpdateStockStackPane, NavigationModel.SEARCH_UPDATE_STOCK_ID);
        usernameLbl.setText(DBLogic.getDBInstance().getUsername());
        usertypeLbl.setText(DBLogic.getDBInstance().getUser_type());
        partReference = PartReference.getInstance();
        partHashMap = new HashMap<>();
        refreshList();
    }

    // This function goes through every part in the system database, assigns them to a label to be put in the list
    // of available items as well as in the hashmap with an appropriate string reference.
    public void refreshList() {
        PartDAO partDAO = new PartDAO();
        for(Part p: partDAO.getAll()) {
            Label partLabel = new Label("ID: " + p.getPartID() + " / Name: " + p.getName() + " / Stock: " + p.getStockLevel());
            partHashMap.put(partLabel.getText(), p);
            partList.getItems().add(partLabel);
        }
    }
}

