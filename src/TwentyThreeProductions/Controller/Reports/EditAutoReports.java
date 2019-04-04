package TwentyThreeProductions.Controller.Reports;

import TwentyThreeProductions.Domain.ReportSettings;
import TwentyThreeProductions.Model.Database.DAO.ReportSettingsDAO;
import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.sql.Date;
import java.time.LocalDate;

public class EditAutoReports {

    private SceneSwitch sceneSwitch;
    private ReportSettings reportSettings;
    private ReportSettingsDAO reportSettingsDAO;

    @FXML
    private StackPane selectUserStackPane;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text usertypeLbl;

    @FXML
    private Label welcomeMessage;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton viewDetailsBtn;

    @FXML
    private JFXComboBox<Label> reportTypeCombi;

    @FXML
    private JFXRadioButton weeklyRadioBtn;

    @FXML
    private JFXRadioButton monthlyRadioBtn;

    @FXML
    private JFXDatePicker nextDate;

    @FXML
    private ToggleGroup freq;

    @FXML
    void backBtnClicked(ActionEvent event) {
        sceneSwitch.switchScene(NavigationModel.REPORTS_MAIN_ID);
    }

    @FXML
    void saveBtnClicked(ActionEvent event) {
        reportSettingsDAO = new ReportSettingsDAO();
        reportSettings = new ReportSettings();

        if (weeklyRadioBtn.isSelected()) {
            reportSettings.setFrequency("WEEKLY");
        } else {
            reportSettings.setFrequency("MONTHLY");
        }

        LocalDate tmpDate = nextDate.getValue();
        reportSettings.setNextDate(Date.valueOf(tmpDate));

        reportSettingsDAO.updateDate(reportSettings);

    }

    @FXML
    void viewDetailsBtnClicked(ActionEvent event) {
        reportSettingsDAO = new ReportSettingsDAO();
        reportSettings = reportSettingsDAO.getStockSettings();
        if (reportSettings.getFrequency().equals("WEEKLY")) {
            weeklyRadioBtn.setSelected(true);
        } else {
            monthlyRadioBtn.setSelected(true);
        }
    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        addTypes();
    }

    public void addTypes() {
        reportTypeCombi.getItems().add(new Label("Stock"));
    }



}
