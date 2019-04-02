package TwentyThreeProductions.Controller.Customer;


import TwentyThreeProductions.Domain.Customer;
import TwentyThreeProductions.Domain.Discount;
import TwentyThreeProductions.Model.DBLogic;
import TwentyThreeProductions.Model.Database.DAO.DiscountDAO;
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

public class EditDiscountController {

    private SceneSwitch sceneSwitch;
    private DiscountDAO discountDAO;
    private CustomerHelper helper;
    private Discount domainDiscount;
    private boolean isNew = false;

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
    private JFXTextField vatDiscount;

    @FXML
    private JFXTextField partsDiscount;


    @FXML
    private JFXRadioButton flexibleDiscountRadioBtn;

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
        sceneSwitch.switchScene(NavigationModel.EDIT_CUSTOMER_ID);
    }

    @FXML
    void saveBtnClicked(ActionEvent event) {
        domainDiscount = new Discount();
        if (fixedDiscountRadioBtn.isSelected()) {
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
        if (isNew == true) {
            // 0 for New Discount
            helper.setDiscount(domainDiscount, 0);
            System.out.println("New Discount");
        } else {
            // 1 for existing Discount
            helper.setDiscount(domainDiscount, 1);
            System.out.println("Updating Discount");
        }
        SystemNotification notification = new SystemNotification(ConfigureDiscountStackPane);
        notification.setNotificationMessage("Discount edited successfully");
        notification.showNotification(NavigationModel.EDIT_DISCOUNT_ID, DBLogic.getDBInstance().getUsername());

    }


    public void setupFlexiDiscount() {
        domainDiscount.setValue(0);
        domainDiscount.setType(CustomerHelper.DISCOUNT_FLEXIBLE_NAME);
        domainDiscount.setCustomerID(helper.getCurrentCustomerID());
        domainDiscount.setVatValue(0);
        domainDiscount.setPartValue(0);

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
        sceneSwitch = SceneSwitch.getInstance();
        helper = CustomerHelper.getInstance();
        usernameLbl.setText(DBLogic.getDBInstance().getUsername());
        usertypeLbl.setText(DBLogic.getDBInstance().getUser_type());
        discountDAO = new DiscountDAO();
        if (CustomerHelper.getInstance().isCustomerCasual()) {
            setupDiscount();
            isNew = false;
        } else {
            isNew = true;
        }

    }


    public void setupDiscount() {
        discountDAO = new DiscountDAO();
        int currentCustomerID = CustomerHelper.getInstance().getCurrentCustomerID();
        System.out.println("CustomerID : " + currentCustomerID);
        Discount tmpDiscount = discountDAO.getDiscount(currentCustomerID);
            if (tmpDiscount.getType().equals(CustomerHelper.DISCOUNT_FIXED_NAME)) {
                fixedDiscountRadioBtn.setSelected(true);
                fixedDiscountField.setText(Double.toString(tmpDiscount.getValue()));
            } else if (tmpDiscount.getType().equals(CustomerHelper.DISCOUNT_FLEXIBLE_NAME)) {
                flexibleDiscountRadioBtn.setSelected(true);
                band1range1.setText(Double.toString(tmpDiscount.getBand1range1()));
                band1range2.setText(Double.toString(tmpDiscount.getBand1range2()));
                band1percentage.setText(Double.toString(tmpDiscount.getBand1percent()));
                band2range1.setText(Double.toString(tmpDiscount.getBand2range1()));
                band2range2.setText(Double.toString(tmpDiscount.getBand2range2()));
                band2percentage.setText(Double.toString(tmpDiscount.getBand2percent()));
                band3range1.setText(Double.toString(tmpDiscount.getBand3range1()));
                band3range2.setText(Double.toString(tmpDiscount.getBand3range2()));
                band3percentage.setText(Double.toString(tmpDiscount.getBand3percent()));
            } else if (tmpDiscount.getType().equals(CustomerHelper.DISCOUNT_VARIABLE_NAME)) {
                variableDiscountRadioBtn.setSelected(true);
                variableDiscountField.setText(Double.toString(tmpDiscount.getValue()));
                vatDiscount.setText(Double.toString(tmpDiscount.getVatValue()));
                partsDiscount.setText(Double.toString(tmpDiscount.getPartValue()));
            }
        }
}

