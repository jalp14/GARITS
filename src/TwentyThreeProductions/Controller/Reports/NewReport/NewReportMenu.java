package TwentyThreeProductions.Controller.Reports.NewReport;

import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class NewReportMenu {

    private SceneSwitch sceneSwitch;

    @FXML
    private StackPane partsMainStackPane;

    @FXML
    private JFXButton noVehiclesMonthlyBtn;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text usertypeLbl;

    @FXML
    private Label welcomeMessage;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton aveTimePriceBtn;

    @FXML
    private JFXButton stockLevelBtn;

    @FXML
    void aveTimePriceBtnClicked(ActionEvent event) {

    }

    @FXML
    void backBtnClicked(ActionEvent event) {
        sceneSwitch.switchScene(NavigationModel.REPORTS_MAIN_ID);
    }

    @FXML
    void noVehiclesMonthlyBtnClicked(ActionEvent event) {

    }

    @FXML
    void stockLevelBtnClicked(ActionEvent event) {

    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
    }


}
