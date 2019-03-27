package TwentyThreeProductions.Controller.Reports;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class ReportsMain {

    @FXML
    private StackPane partsMainStackPane;

    @FXML
    private JFXButton newReportBtn;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text usertypeLbl;

    @FXML
    private Label welcomeMessage;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton existingReportBtn;

    @FXML
    private JFXButton editAutoReportsBtn;

    @FXML
    void backBtnClicked(ActionEvent event) {

    }

    @FXML
    void editAutoReportsBtnClicked(ActionEvent event) {

    }

    @FXML
    void existingReportBtnClicked(ActionEvent event) {

    }

    @FXML
    void newReportBtnClicked(ActionEvent event) {

    }

    public void initialize() {
        System.out.println("Reports Main Screen");
    }


}
