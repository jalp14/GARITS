package TwentyThreeProductions.Controller.Reports.NewReport;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class NewStockLevel {

    @FXML
    private StackPane partsMainStackPane;

    @FXML
    private JFXButton generateReportBtn;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text usertypeLbl;

    @FXML
    private Label welcomeMessage;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXTextField reportNameField;

    @FXML
    private Label fNameHeading;

    @FXML
    private Label periodHeading;

    @FXML
    private JFXDatePicker beginDatePicker;

    @FXML
    private JFXDatePicker endDatePicker;

    @FXML
    void backBtnClicked(ActionEvent event) {

    }

    @FXML
    void generateReportBtnClicked(ActionEvent event) {

    }

    public void initialize() {

    }

}

