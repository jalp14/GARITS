package TwentyThreeProductions.Controller.Parts;

import TwentyThreeProductions.Domain.Part;
import TwentyThreeProductions.Model.Database.DAO.PartDAO;
import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.PartReference;
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
        // The system checks to make sure that a part was selected to be removed and, if a part was not selected, the
        // system produces an alert stating that a part was not selected and thus no part could be removed.
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
            PartDAO partDAO = new PartDAO();
            String searchTerm = searchField.getText();
            partList.getSelectionModel().select(null);
            partList.getItems().clear();
            partHashMap.clear();
            if(searchTerm.isEmpty()) {
                refreshList();
            }
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

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(searchUpdateStockStackPane, NavigationModel.SEARCH_UPDATE_STOCK_ID);
        partReference = PartReference.getInstance();
        partHashMap = new HashMap<>();
        refreshList();
    }


    public void refreshList() {
        PartDAO partDAO = new PartDAO();
        for(Part p: partDAO.getAll()) {
            Label partLabel = new Label("ID: " + p.getPartID() + " / Name: " + p.getName() + " / Stock: " + p.getStockLevel());
            partHashMap.put(partLabel.getText(), p);
            partList.getItems().add(partLabel);
        }
    }
}

