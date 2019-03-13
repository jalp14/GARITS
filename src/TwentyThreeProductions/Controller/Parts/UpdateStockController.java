package TwentyThreeProductions.Controller.Parts;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class UpdateStockController {

    @FXML
    private StackPane updateStockStackPane;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text usertypeLbl;

    @FXML
    private Label welcomeMessage;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private Label partNameLbl;

    @FXML
    private JFXButton reduceStockBtn;

    @FXML
    private JFXButton increaseStockBtn;

    @FXML
    private JFXTextField stockLevelField;

    @FXML
    void backBtnClicked(ActionEvent event) {

    }

    @FXML
    void removePartBtnClicked(ActionEvent event) {

    }

    public void initialize() {

    }

}


