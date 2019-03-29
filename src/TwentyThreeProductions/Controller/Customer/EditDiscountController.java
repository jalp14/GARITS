package TwentyThreeProductions.Controller.Customer;


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
    private JFXComboBox<Label> bandCombi;

    @FXML
    private JFXTextField fixedDiscountField;

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
        helper.setDiscount(domainDiscount);
        SystemNotification notification = new SystemNotification(ConfigureDiscountStackPane);
        notification.setNotificationMessage("Discount edited successfully");
        notification.showNotification(NavigationModel.EDIT_DISCOUNT_ID, DBLogic.getDBInstance().getUsername());

    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        helper = CustomerHelper.getInstance();
        setupBands();
        setupDiscount();
    }

    public void setupBands() {
        bandCombi.getItems().add(new Label("Band 1"));
        bandCombi.getItems().add(new Label("Band 2"));
        bandCombi.getItems().add(new Label("Band 3"));
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
            bandCombi.getSelectionModel().select(helper.getBandNo(tmpDiscount.getBand()));
        } else if (tmpDiscount.getType().equals(CustomerHelper.DISCOUNT_VARIABLE_NAME)) {
            variableDiscountRadioBtn.setSelected(true);
            variableDiscountField.setText(Double.toString(tmpDiscount.getValue()));
            vatDiscount.setText(Double.toString(tmpDiscount.getVatValue()));
            partsDiscount.setText(Double.toString(tmpDiscount.getPartValue()));
        }
    }

}

