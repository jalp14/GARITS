package TwentyThreeProductions.Controller.Parts;

import TwentyThreeProductions.Domain.Part;
import TwentyThreeProductions.Model.Database.DAO.PartDAO;
import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import TwentyThreeProductions.Model.SystemAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class UpdateStockController {

    private SceneSwitch sceneSwitch;

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
        sceneSwitch.switchScene(NavigationModel.PARTS_MAIN_ID);
    }

    @FXML
    void saveBtnClicked(ActionEvent event) {

        // If the current value of the system stock is set to a value below zero, the system produces an alert stating
        // that the stock cannot be updated because the stock must be a value of zero or greater.
        if(Integer.parseInt(stockLevelField.getText()) < 0) {
            SystemAlert systemAlert = new SystemAlert(updateStockStackPane,
                    "Failure", "Stock below zero");
        }

        // Otherwise, the system creates a new part object to pass the updated stock level to. Once this is done, the new
        // part object is passed through an operation to update the stock level within the system database, after which the
        // system produces an alert to state that the stock has been successfully updated.
        else {
            Part part = new Part();
            PartDAO partDAO = new PartDAO();
            partDAO.update(part);
            SystemAlert systemAlert = new SystemAlert(updateStockStackPane,
                    "Success", "Stock updated");
        }
    }

    @FXML
    void reduceStockBtnClicked(ActionEvent event) {
        // The system decreases the stock of the selected part by one.
        stockLevelField.setText(String.valueOf(Integer.parseInt(stockLevelField.getText()) - 1));
    }

    @FXML
    void increaseStockBtnClicked(ActionEvent event) {
        // The system increases the stock of the selected part by one.
        stockLevelField.setText(String.valueOf(Integer.parseInt(stockLevelField.getText()) + 1));
    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(updateStockStackPane, NavigationModel.UPDATE_STOCK_ID);
    }

}


