package TwentyThreeProductions.Controller.Jobs.NewJob;

import TwentyThreeProductions.Domain.Discount;
import TwentyThreeProductions.Model.*;
import TwentyThreeProductions.Model.HelperClasses.CustomerHelper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class NewCustomerConfigureDiscountController {

    private SceneSwitch sceneSwitch;
    private Discount domainDiscount;
    private CustomerHelper helper;

    @FXML
    private StackPane newCustomerConfigureDiscountStackPane;

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
    private Label customerNameLbl;

    @FXML
    private JFXRadioButton fixedDiscountRadioBtn;

    @FXML
    private ToggleGroup discount;

    @FXML
    private JFXRadioButton variableDiscountRadioBtn;

    @FXML
    private Label discountTypeHeading;

    @FXML
    private JFXRadioButton flexibleDiscountRadioBtn;

    @FXML
    private JFXTextField vatDiscount;

    @FXML
    private JFXTextField partsDiscount;

    @FXML
    private JFXTextField variableDiscountField;

    @FXML
    private JFXComboBox<Label> bandCombi;

    @FXML
    private JFXTextField fixedDiscountField;


    // This function takes the user back to the page for creating the customer.
    @FXML
    void backBtnClicked(ActionEvent event) {
        sceneSwitch.switchScene(NavigationModel.NEW_JOB_NEW_CUSTOMER_ID);
    }


    // This function checks the type of discount that was selected and applies the necessary values to the object for it.
    // After this is done, it is applied to the static classed to be used for the new customer. After this, a notification
    // alerting the user of this is generated.
    @FXML
    void saveBtnClicked(ActionEvent event) {
        if (fixedDiscountRadioBtn.isSelected()) {
            domainDiscount.setValue(Double.parseDouble(fixedDiscountField.getText()));
            domainDiscount.setType(CustomerHelper.DISCOUNT_FIXED_NAME);
            domainDiscount.setBand(null);
            domainDiscount.setCustomerID(helper.getCurrentCustomerID());
            domainDiscount.setVatValue(0);
            domainDiscount.setPartValue(0);
        } else if (flexibleDiscountRadioBtn.isSelected()) {
            domainDiscount.setValue(helper.getRate(bandCombi.getSelectionModel().getSelectedIndex()));
            domainDiscount.setBand(bandCombi.getSelectionModel().getSelectedItem().getText());
            domainDiscount.setType(CustomerHelper.DISCOUNT_FLEXIBLE_NAME);
            domainDiscount.setCustomerID(helper.getCurrentCustomerID());
            domainDiscount.setVatValue(0);
            domainDiscount.setPartValue(0);
        } else if (variableDiscountRadioBtn.isSelected()) {
            domainDiscount.setValue(Double.parseDouble(variableDiscountField.getText()));
            domainDiscount.setBand(null);
            domainDiscount.setType(CustomerHelper.DISCOUNT_VARIABLE_NAME);
            domainDiscount.setCustomerID(helper.getCurrentCustomerID());
            domainDiscount.setVatValue(Double.parseDouble(vatDiscount.getText()));
            domainDiscount.setPartValue(Double.parseDouble(partsDiscount.getText()));
            domainDiscount.setPartValue(0);
        }
        helper.setDiscount(domainDiscount,0);
        SystemNotification notification = new SystemNotification(newCustomerConfigureDiscountStackPane);
        notification.setNotificationMessage("Configured Discount");
        notification.showNotification(NavigationModel.NEW_CUSTOMER_CONFIGURE_DISCOUNT_ID, DBLogic.getDBInstance().getUsername());

    }

    // This function is called up when the page is first opened, and it adds the scene to the list of currently
    // active scenes as well as changing the labels for the username and type with the currently logged in user,
    // and then finally it initialises the instance of the static class for storing the discount as well as an object to
    // store the discount details. This function also sets up the bands that will be used for the discount.
    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(newCustomerConfigureDiscountStackPane, NavigationModel.NEW_CUSTOMER_CONFIGURE_DISCOUNT_ID);
        usernameLbl.setText(DBLogic.getDBInstance().getUsername());
        usertypeLbl.setText(DBLogic.getDBInstance().getUser_type());
        setupBands();
        domainDiscount = new Discount();
        helper = CustomerHelper.getInstance();
    }

    // Sets 3 bands for use within the flexible discount.
    public void setupBands() {
        bandCombi.getItems().add(new Label("Band 1"));
        bandCombi.getItems().add(new Label("Band 2"));
        bandCombi.getItems().add(new Label("Band 3"));
    }

}

