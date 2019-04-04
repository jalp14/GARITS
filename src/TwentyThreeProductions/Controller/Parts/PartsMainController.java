package TwentyThreeProductions.Controller.Parts;

import TwentyThreeProductions.Model.DBLogic;
import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class PartsMainController {

    private SceneSwitch sceneSwitch;

    @FXML
    private StackPane partsMainStackPane;

    @FXML
    private JFXButton addNewPartBtn;

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
    private JFXButton updatePartStockBtn;

    // The system moves to the page for adding a new part.
    @FXML
    void addNewPartBtnClicked(ActionEvent event) throws IOException {
        sceneSwitch.activateScene(NavigationModel.ADD_NEW_PART_ID, backBtn.getScene());
    }

    // The system simply moves back to the main screen for the user.
    @FXML
    void backBtnClicked(ActionEvent event) {
        sceneSwitch.switchScene(NavigationModel.MAIN_FFR_ID);
    }

    // The system moves to the page for removing an existing part.
    @FXML
    void removePartBtnClicked(ActionEvent event) throws IOException {
        sceneSwitch.activateSceneAlways(NavigationModel.REMOVE_PART_ID, backBtn.getScene());
    }

    // The system moves to the page for adjusting the stock or threshold of an existing part.
    @FXML
    void updatePartStockBtnClicked(ActionEvent event) throws IOException {
        sceneSwitch.activateSceneAlways(NavigationModel.SEARCH_UPDATE_STOCK_ID, backBtn.getScene());
    }

    // This function is called when the page is opened, and simply adds the scene to the list of active scenes and
    // sets the labels for the username and usertype to the appropriate values from within the system database.
    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(partsMainStackPane, NavigationModel.PARTS_MAIN_ID);
        usernameLbl.setText(DBLogic.getDBInstance().getUsername());
        usertypeLbl.setText(DBLogic.getDBInstance().getUser_type());
    }

}
