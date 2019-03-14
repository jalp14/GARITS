package TwentyThreeProductions.Controller.Jobs.EditMonitor;

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

public class EditMonitorChooseJobController {

    private SceneSwitch sceneSwitch;

    @FXML
    private StackPane editMonitorChooseJobStackPane;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text usertypeLbl;

    @FXML
    private Label welcomeMessage;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton nextBtn;

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
        sceneSwitch.switchScene(NavigationModel.JOBS_MAIN_ID);
    }

    @FXML
    void nextBtnClicked(ActionEvent event) {

    }

    @FXML
    void searchBtnClick(ActionEvent event) {

    }

    public void initialze() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(editMonitorChooseJobStackPane, NavigationModel.EDIT_MONITOR_CHOOSE_ID);
    }

}
