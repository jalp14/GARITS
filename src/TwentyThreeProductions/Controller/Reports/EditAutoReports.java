package TwentyThreeProductions.Controller.Reports;

import TwentyThreeProductions.Model.SceneSwitch;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class EditAutoReports {
    private SceneSwitch sceneSwitch;

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
    private JFXComboBox<?> reportTypeCombi;

    @FXML
    private JFXRadioButton weeklyRadioBtn;

    @FXML
    private JFXRadioButton monthlyRadioBtn;

    @FXML
    void backBtnClicked(ActionEvent event) {

    }

    @FXML
    void saveBtnClicked(ActionEvent event) {
        
    }

    @FXML
    void viewDetailsBtnClicked(ActionEvent event) {

    }

    public void initialize() {

    }

}
