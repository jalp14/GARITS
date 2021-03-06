package TwentyThreeProductions.Controller.Reports.NewReport;

import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class NewReportMenu {
/////////////////////// Generate new report \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
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
    void aveTimePriceBtnClicked(ActionEvent event) throws IOException {
        sceneSwitch.activateScene(NavigationModel.NEW_AVE_TIME_PRICE_ID, backBtn.getScene());
    }

    @FXML
    void backBtnClicked(ActionEvent event) {
        sceneSwitch.switchScene(NavigationModel.REPORTS_MAIN_ID);
    }

    @FXML
    void noVehiclesMonthlyBtnClicked(ActionEvent event) throws IOException {
        sceneSwitch.activateScene(NavigationModel.NEW_NO_VEHICLES_MONTHLY_SETTINGS_ID, backBtn.getScene());
    }

    @FXML
    void stockLevelBtnClicked(ActionEvent event) throws IOException {
        sceneSwitch.activateScene(NavigationModel.NEW_STOCK_LEVEL_ID, backBtn.getScene());
    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(partsMainStackPane, NavigationModel.NEW_REPORT_MENU_ID);

    }


}
