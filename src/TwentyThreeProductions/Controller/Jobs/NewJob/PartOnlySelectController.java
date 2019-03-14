package TwentyThreeProductions.Controller.Jobs.NewJob;

import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class PartOnlySelectController {

    private SceneSwitch sceneSwitch;

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
    private JFXListView<?> partList;

    @FXML
    void backBtnClicked(ActionEvent event) {

    }

    @FXML
    void searchBtnClick(ActionEvent event) {

    }

    @FXML
    void selectPartBtnClicked(ActionEvent event) {

    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(partOnlySelectStackPane, NavigationModel.PART_ONLY_SELECT_ID);
    }

}
