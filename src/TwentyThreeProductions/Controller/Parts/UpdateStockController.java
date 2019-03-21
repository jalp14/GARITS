package TwentyThreeProductions.Controller.Parts;

import TwentyThreeProductions.Domain.Part;
import TwentyThreeProductions.Model.Database.DAO.PartDAO;
import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.PartReference;
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

import java.io.IOException;

public class UpdateStockController {

    private SceneSwitch sceneSwitch;

    private PartReference partReference;

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
        sceneSwitch.switchScene(NavigationModel.SEARCH_UPDATE_STOCK_ID);
    }

    @FXML
    void saveBtnClicked(ActionEvent event) {
        try {
            // If the current value of the system stock is set to a value below zero, the system produces an alert stating
            // that the stock cannot be updated because the stock must be a value of zero or greater.
            if (Integer.parseInt(stockLevelField.getText()) < 0) {
                SystemAlert systemAlert = new SystemAlert(updateStockStackPane,
                        "Failure", "Stock below zero");
            }

            // Otherwise, the system creates a new part object to pass the updated stock level to. Once this is done, the new
            // part object is passed through an operation to update the stock level within the system database, after which the
            // system produces an alert to state that the stock has been successfully updated.
            else {
                Part part = partReference.getPart();
                PartDAO partDAO = new PartDAO();
                System.out.println(partReference.getPart().getPartID());
                Integer.parseInt(stockLevelField.getText());
                part.setStockLevel(stockLevelField.getText());
                partDAO.update(part);
                SystemAlert systemAlert = new SystemAlert(updateStockStackPane,
                        "Success", "Stock updated");
            }
        }
        catch(Exception e) {
            SystemAlert systemAlert = new SystemAlert(updateStockStackPane,
                    "Failure", "Invalid character in field");
        }
    }

    @FXML
    void reduceStockBtnClicked(ActionEvent event) throws IOException {

        //The system checks to see if an integer has been inputted into the field. If it has, then the system decreases
        // the stock of the selected part by one. Otherwise, the system produces an alert stating that there is an
        // invalid character within the field.
        try {
            stockLevelField.setText(String.valueOf(Integer.parseInt(stockLevelField.getText()) - 1));
        }
        catch(Exception e) {
            SystemAlert systemAlert = new SystemAlert(updateStockStackPane,
                    "Failure", "Invalid character in field");
        }
    }

    @FXML
    void increaseStockBtnClicked(ActionEvent event) {

        //The system checks to see if an integer has been inputted into the field. If it has, then the system increases
        // the stock of the selected part by one. Otherwise, the system produces an alert stating that there is an
        // invalid character within the field.
        try {
            stockLevelField.setText(String.valueOf(Integer.parseInt(stockLevelField.getText()) + 1));
        }
        catch(Exception e) {
            SystemAlert systemAlert = new SystemAlert(updateStockStackPane,
                    "Failure", "Invalid character in field");
        }
    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(updateStockStackPane, NavigationModel.UPDATE_STOCK_ID);
        partReference = PartReference.getInstance();
        //stockLevelField.setText(partReference.getPart().getStockLevel());
    }

}


