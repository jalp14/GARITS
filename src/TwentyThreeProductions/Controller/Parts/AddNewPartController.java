package TwentyThreeProductions.Controller.Parts;

import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class AddNewPartController {

    private SceneSwitch sceneSwitch;

    @FXML
    private StackPane addNewPartStackPane;

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
    private Label formLabel;

    @FXML
    private Label formLabel1;

    @FXML
    private Label formLabel11;

    @FXML
    private Label formLabel12;

    @FXML
    private Label formLabel121;

    @FXML
    private Label formLabel1211;

    @FXML
    private JFXTextField partNameField;

    @FXML
    private JFXTextField manufacturerField;

    @FXML
    private JFXTextField vehicleTypeField;

    @FXML
    private JFXTextField yearsField;

    @FXML
    private Label formLabel1212;

    @FXML
    private JFXTextField priceWholeNumField;

    @FXML
    private Label formLabel12121;

    @FXML
    private JFXTextField priceDecimalField;

    @FXML
    private JFXTextField stockLevelField;

    @FXML
    void addPartBtnClicked(ActionEvent event) throws IOException {
        sceneSwitch.activateScene(NavigationModel.ADD_NEW_PART_ID, backBtn.getScene());
    }

    @FXML
    void backBtnClicked(ActionEvent event) {
        sceneSwitch.switchScene(NavigationModel.PARTS_MAIN_ID);
    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(addNewPartStackPane, NavigationModel.ADD_NEW_PART_ID);
    }

}

