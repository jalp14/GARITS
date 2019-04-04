package TwentyThreeProductions.Controller.Parts;

import TwentyThreeProductions.Domain.Part;
import TwentyThreeProductions.Model.*;
import TwentyThreeProductions.Model.Database.DAO.PartDAO;
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
    private Label stockLevelLbl;

    @FXML
    private Label thresholdLevelLbl;

    @FXML
    private JFXButton reduceStockBtn;

    @FXML
    private JFXButton increaseStockBtn;

    @FXML
    private JFXButton reduceThresholdBtn;

    @FXML
    private JFXButton increaseThresholdBtn;

    @FXML
    private JFXTextField stockLevelField;

    @FXML
    private JFXTextField thresholdLevelField;

    // The system clears the stock level field and threshold level field before moving back to the previous page.
    @FXML
    void backBtnClicked(ActionEvent event) throws IOException {
        stockLevelField.clear();
        thresholdLevelField.clear();
        sceneSwitch.activateSceneAlways(NavigationModel.SEARCH_UPDATE_STOCK_ID, backBtn.getScene());
    }

    @FXML
    void saveBtnClicked(ActionEvent event) {
        try {
            // If either the current value of the system stock or the system threshold is set to a value below zero, the
            // system produces an alert stating that the stock cannot be updated because the stock/threshold must be a
            // value of zero or greater.
            if (Integer.parseInt(stockLevelField.getText()) < 0) {
                SystemAlert systemAlert = new SystemAlert(updateStockStackPane,
                        "Failure", "Value below zero");
            }

            // Otherwise, the system creates a new part object to pass the updated stock level to. Once this is done, the new
            // part object is passed through an operation to update the stock level within the system database, after which the
            // system produces an alert to state that the stock has been successfully updated and refreshes the fields with the
            // new stock/threshold levels.
            else {
                Part part = partReference.getPart();
                PartDAO partDAO = new PartDAO();
                part.setStockLevel(Integer.parseInt(stockLevelField.getText()));
                part.setThresholdLevel(Integer.parseInt(thresholdLevelField.getText()));
                partDAO.update(part);
                SystemAlert systemAlert = new SystemAlert(updateStockStackPane,
                        "Success", "Stock/Threshold updated");
                stockLevelField.setText(String.valueOf(part.getStockLevel()));
                thresholdLevelField.setText(String.valueOf(part.getThresholdLevel()));

                // However, if these changes cause the stock level to go below the threshold level, a notification warning the user
                // of this is sent to them immediately after.
                if(part.getStockLevel() <= part.getThresholdLevel()) {
                    SystemNotification notification = new SystemNotification(updateStockStackPane);
                    notification.setNotificationMessage("The number of parts has fallen below the threshold, " +
                            "please order more as soon as possible");
                    notification.showNotification(NavigationModel.UPDATE_STOCK_ID, DBLogic.getDBInstance().getUsername());
                }
            }
        }
        // If the values inputted are not integeers, the system will produce an alert stating that there are invalid characters
        // within the field.
        catch(Exception e) {
            SystemAlert systemAlert = new SystemAlert(updateStockStackPane,
                    "Failure", "Invalid character in field(s)");
        }
    }

    //The system checks to see if an integer has been inputted into the field. If it has, then the system decreases
    // the stock of the selected part by one. Otherwise, the system produces an alert stating that there is an
    // invalid character within the field.
    @FXML
    void reduceStockBtnClicked(ActionEvent event) throws IOException {
        try {
            stockLevelField.setText(String.valueOf(Integer.parseInt(stockLevelField.getText()) - 1));
        } catch(Exception e) {
            SystemAlert systemAlert = new SystemAlert(updateStockStackPane,
                    "Failure", "Invalid character in stock field");
        }
    }

    //The system checks to see if an integer has been inputted into the field. If it has, then the system increases
    // the stock of the selected part by one. Otherwise, the system produces an alert stating that there is an
    // invalid character within the field.
    @FXML
    void increaseStockBtnClicked(ActionEvent event) {
        try {
            stockLevelField.setText(String.valueOf(Integer.parseInt(stockLevelField.getText()) + 1));
        } catch(Exception e) {
            SystemAlert systemAlert = new SystemAlert(updateStockStackPane,
                    "Failure", "Invalid character in stock field");
        }
    }

    //The system checks to see if an integer has been inputted into the field. If it has, then the system decreases
    // the stock of the selected part by one. Otherwise, the system produces an alert stating that there is an
    // invalid character within the field.
    @FXML
    void reduceThresholdBtnClicked(ActionEvent event) throws IOException {
        try {
            thresholdLevelField.setText(String.valueOf(Integer.parseInt(thresholdLevelField.getText()) - 1));
        } catch(Exception e) {
            SystemAlert systemAlert = new SystemAlert(updateStockStackPane,
                    "Failure", "Invalid character in threshold field");
        }
    }

    //The system checks to see if an integer has been inputted into the field. If it has, then the system increases
    // the stock of the selected part by one. Otherwise, the system produces an alert stating that there is an
    // invalid character within the field.
    @FXML
    void increaseThresholdBtnClicked(ActionEvent event) {

        try {
            thresholdLevelField.setText(String.valueOf(Integer.parseInt(thresholdLevelField.getText()) + 1));
        } catch(Exception e) {
            SystemAlert systemAlert = new SystemAlert(updateStockStackPane,
                    "Failure", "Invalid character in threshold field");
        }
    }

    // This function is called up when the page is first opened, and it adds the scene to the list of currently
    // active scenes as well as changing the labels for the username and type with the currently logged in user, the
    // name of the part with the currently selected part and the stock/threshold levels with the currently stored values,
    // and then finally it initialises the static class which stored the previously selected part.
    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(updateStockStackPane, NavigationModel.UPDATE_STOCK_ID);
        usernameLbl.setText(DBLogic.getDBInstance().getUsername());
        usertypeLbl.setText(DBLogic.getDBInstance().getUser_type());
        partReference = PartReference.getInstance();
        stockLevelField.setText(String.valueOf(partReference.getPart().getStockLevel()));
        thresholdLevelField.setText(String.valueOf(partReference.getPart().getThresholdLevel()));
        partNameLbl.setText(partReference.getPart().getName());
    }

}


