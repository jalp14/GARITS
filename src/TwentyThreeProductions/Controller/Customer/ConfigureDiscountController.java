package TwentyThreeProductions.Controller.Customer;

import TwentyThreeProductions.Domain.Discount;
import TwentyThreeProductions.Model.DBLogic;
import TwentyThreeProductions.Model.HelperClasses.CustomerHelper;
import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import TwentyThreeProductions.Model.SystemNotification;
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

public class ConfigureDiscountController {
///////////////////////////////////// Configure discount for a new customer account \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    private SceneSwitch sceneSwitch;
    private Discount domainDiscount;
    private CustomerHelper helper;


    @FXML
    private StackPane ConfigureDiscountStackPane;

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
    private JFXTextField fixedDiscountField;


    @FXML
    private JFXTextField band1range1;

    @FXML
    private JFXTextField band1range2;

    @FXML
    private JFXTextField band1percentage;

    @FXML
    private JFXTextField band2range1;

    @FXML
    private JFXTextField band2range2;

    @FXML
    private JFXTextField band2percentage;

    @FXML
    private JFXTextField band3range1;

    @FXML
    private JFXTextField band3range2;

    @FXML
    private JFXTextField band3percentage;

    @FXML
    void backBtnClicked(ActionEvent event) {
        // Take the user back to the form
        sceneSwitch.switchScene(NavigationModel.ADD_NEW_CUSTOMER_ID);
    }

    @FXML
    void saveBtnClicked(ActionEvent event) { // Save the discount details and pass it to the helper class
        if (fixedDiscountRadioBtn.isSelected()) {
            // Check the type of discount and apply the necessary constraints
            domainDiscount.setValue(Double.parseDouble(fixedDiscountField.getText()));
            domainDiscount.setType(CustomerHelper.DISCOUNT_FIXED_NAME);
            domainDiscount.setBand(null);
            domainDiscount.setCustomerID(helper.getCurrentCustomerID());
            domainDiscount.setVatValue(0);
            domainDiscount.setPartValue(0);
        } else if (flexibleDiscountRadioBtn.isSelected()) {
            setupFlexiDiscount();
        } else if (variableDiscountRadioBtn.isSelected()) {
            domainDiscount.setValue(Double.parseDouble(variableDiscountField.getText()));
            domainDiscount.setBand(null);
            domainDiscount.setType(CustomerHelper.DISCOUNT_VARIABLE_NAME);
            domainDiscount.setCustomerID(helper.getCurrentCustomerID());
            domainDiscount.setVatValue(Double.parseDouble(vatDiscount.getText()));
            domainDiscount.setPartValue(Double.parseDouble(partsDiscount.getText()));
            domainDiscount.setPartValue(0);
        }
        // Pass the discount object to the helper class
        helper.setDiscount(domainDiscount,0);
        // Show an unobtrusive notification to show that discount has been set to the customer account
        SystemNotification notification = new SystemNotification(ConfigureDiscountStackPane);
        notification.setNotificationMessage("Configured Discount");
        notification.showNotification(NavigationModel.CONFIGURE_DISCOUNT_ID, DBLogic.getDBInstance().getUsername());

    }

    public void setupFlexiDiscount() { // if flexible discount option is selcted then this function is called
        // Set all the fields required for a flexible discount and set it to the domain discount
        domainDiscount.setValue(0);
        domainDiscount.setType(CustomerHelper.DISCOUNT_FLEXIBLE_NAME);
        domainDiscount.setCustomerID(helper.getCurrentCustomerID());
        domainDiscount.setVatValue(0);
        domainDiscount.setPartValue(0);
        // setup the bands for the flexible discount
        // Setup bands
        domainDiscount.setBand1range1(Double.parseDouble(band1range1.getText()));
        domainDiscount.setBand1range2(Double.parseDouble(band1range2.getText()));
        domainDiscount.setBand1percent(Double.parseDouble(band1percentage.getText()));
        domainDiscount.setBand2range1(Double.parseDouble(band2range1.getText()));
        domainDiscount.setBand2range2(Double.parseDouble(band2range2.getText()));
        domainDiscount.setBand2percent(Double.parseDouble(band2percentage.getText()));
        domainDiscount.setBand3range1(Double.parseDouble(band3range1.getText()));
        domainDiscount.setBand3range2(Double.parseDouble(band3range2.getText()));
        domainDiscount.setBand3percent(Double.parseDouble(band3percentage.getText()));
    }



    public void initialize() {
        // Initialise the form and apply checks and set constraints
        sceneSwitch = SceneSwitch.getInstance();
        usernameLbl.setText(DBLogic.getDBInstance().getUsername());
        usertypeLbl.setText(DBLogic.getDBInstance().getUser_type());
        domainDiscount = new Discount();
        helper = CustomerHelper.getInstance();
    }

}

