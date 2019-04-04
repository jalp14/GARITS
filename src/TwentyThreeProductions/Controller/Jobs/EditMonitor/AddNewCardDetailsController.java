package TwentyThreeProductions.Controller.Jobs.EditMonitor;

import TwentyThreeProductions.Domain.*;
import TwentyThreeProductions.Model.*;
import TwentyThreeProductions.Model.Database.DAO.*;
import TwentyThreeProductions.Model.HelperClasses.PaymentHelper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.sql.Date;


public class AddNewCardDetailsController {

    private SceneSwitch sceneSwitch;

    private CustomerReference customerReference;

    private JobReference jobReference;

    private Payment payment;

    private PaymentHelper paymentHelper;

    @FXML
    private StackPane addNewCardDetailsStackPane;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text usertypeLbl;

    @FXML
    private Label welcomeMessage;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton addCardBtn;

    @FXML
    private Label customerNameLbl;

    @FXML
    private Label cardNumberHeading;

    @FXML
    private Label nameHeading;

    @FXML
    private Label expDateHeading;

    @FXML
    private Label cvvHeading;

    @FXML
    private JFXTextField cardNumberField;

    @FXML
    private JFXTextField nameField;

    @FXML
    private JFXTextField expDateField;

    @FXML
    private JFXTextField cvvField;

    @FXML
    void addCardBtnClicked(ActionEvent event) {
        Payment payment = new Payment();
        PaymentDAO paymentDAO = new PaymentDAO();
        if (cardNumberField.getText().isEmpty() || nameField.getText().isEmpty() ||
                expDateField.getText().isEmpty() || cvvField.getText().isEmpty()) {
            SystemAlert systemAlert = new SystemAlert(addNewCardDetailsStackPane,
                    "Failure", "Blank field(s)");
        } else {
            //try {
                Integer.parseInt(cardNumberField.getText());
                Integer.parseInt(cvvField.getText());
                if(cvvField.getText().length() != 3) {
                    SystemAlert systemAlert = new SystemAlert(addNewCardDetailsStackPane,
                            "Failure", "CVV must be 3 characters");
                }
                else {
                    payment.setJobID(jobReference.getJob().getJobID());
                    payment.setCustomerID(jobReference.getJob().getCustomerID());
                    payment.setType("Card");
                    java.util.Date currentDate = new java.util.Date();
                    java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
                    payment.setDate(sqlDate);
                    payment.setAmount(paymentHelper.getAmountPaid());
                    payment.setCardNumber(Integer.parseInt(cardNumberField.getText()));
                    payment.setCvv(Integer.parseInt(cvvField.getText()));
                    payment.setCardName(nameField.getText());
                    payment.setExpiryDate(expDateField.getText());
                    paymentDAO.save(payment);
                    SystemAlert systemAlert = new SystemAlert(addNewCardDetailsStackPane,
                            "Success", "Payment details added");
                }
            /*}
            catch(Exception e) {
              SystemAlert systemAlert = new SystemAlert(newJobNewVehicleStackPane,
                      "Failure", "Invalid entries for field(s)");
            }*/
        }
    }

    @FXML
    void backBtnClicked(ActionEvent event) {
        clearInputs();
        sceneSwitch.switchScene(NavigationModel.EDIT_MONITOR_JOB_ID);
    }

    public void initialize() {
        System.out.println("AddVehicletoCustomer controller init");
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(addNewCardDetailsStackPane, NavigationModel.ADD_NEW_CARD_DETAILS_ID);
        usernameLbl.setText(DBLogic.getDBInstance().getUsername());
        usertypeLbl.setText(DBLogic.getDBInstance().getUser_type());
        jobReference = JobReference.getInstance();
        paymentHelper = PaymentHelper.getInstance();
        Customer customer = new Customer();
        CustomerDAO customerDAO = new CustomerDAO();
        for(Customer c: customerDAO.getAll()) {
            if(jobReference.getJob().getCustomerID() == Integer.parseInt(c.getCustomerID())) {
                customer = c;
                break;
            }
        }
        customerNameLbl.setText("Name: " + customer.getFirstName() + " " + customer.getLastName());
        clearInputs();
    }

    public void clearInputs() {
        cardNumberField.clear();
        nameField.clear();
        expDateField.clear();
        cvvField.clear();
    }
}

