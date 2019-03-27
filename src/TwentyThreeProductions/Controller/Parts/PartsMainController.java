package TwentyThreeProductions.Controller.Parts;

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

    @FXML
    void addNewPartBtnClicked(ActionEvent event) throws IOException {
        sceneSwitch.activateScene(NavigationModel.ADD_NEW_PART_ID, backBtn.getScene());
    }

    @FXML
    void backBtnClicked(ActionEvent event) {
        sceneSwitch.switchScene(NavigationModel.MAIN_FFR_ID);
    }

    @FXML
    void removePartBtnClicked(ActionEvent event) throws IOException {
        sceneSwitch.activateSceneAlways(NavigationModel.REMOVE_PART_ID, backBtn.getScene());
    }

    @FXML
    void updatePartStockBtnClicked(ActionEvent event) throws IOException {
        sceneSwitch.activateSceneAlways(NavigationModel.SEARCH_UPDATE_STOCK_ID, backBtn.getScene());
    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(partsMainStackPane, NavigationModel.PARTS_MAIN_ID);
    }

}
