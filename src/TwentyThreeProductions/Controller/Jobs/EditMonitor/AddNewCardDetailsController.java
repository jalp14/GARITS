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
        // If any of the fields that are supposed to be inputted by the user are left as empty, the system will refuse
        // to process the fields, instead producing an alert stating that at least one of the fields is empty.
        if (cardNumberField.getText().isEmpty() || nameField.getText().isEmpty() ||
                expDateField.getText().isEmpty() || cvvField.getText().isEmpty()) {
            SystemAlert systemAlert = new SystemAlert(addNewCardDetailsStackPane,
                    "Failure", "Blank field(s)");
        } else {
            // Once the system verifies that all of the fields have been filled in, the system will next check that the
            // fields for the card number and CVV are integers, as is required for those values. If they are not, the
            // system produces an alert stating that at least one of the fields has an invalid value and does not continue.
            try {
                Integer.parseInt(cardNumberField.getText());
                Integer.parseInt(cvvField.getText());

                // The system then checks to make sure that the CVV is in fact 3 characters as is required, and if it is
                // more or less, the system produces an alert stating the CVV must be 3 characters, not continuing with the
                // function
                if(cvvField.getText().length() != 3) {
                    SystemAlert systemAlert = new SystemAlert(addNewCardDetailsStackPane,
                            "Failure", "CVV must be 3 characters");
                }

                // Once all the checks have been made, the system adds the details from the fields into the appropriate
                // fields for the payment object, including automatically getting the date that the payment was made on.
                // It also uses the amount value specified on the previous page to input a correct amount paid. Once this
                // is done, the system runs the function to store the payment as a new entry within the system database,
                // before producing an alert stating that the operation was successful.
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
            }
            catch(Exception e) {
              SystemAlert systemAlert = new SystemAlert(addNewCardDetailsStackPane,
                      "Failure", "Invalid entries for field(s)");
            }
        }
    }

    // This function simply runs the function to clear every input and returns to the previous page, with the now-updated
    // job.
    @FXML
    void backBtnClicked(ActionEvent event) {
        clearInputs();
        sceneSwitch.switchScene(NavigationModel.EDIT_MONITOR_JOB_ID);
    }

    // This function is called upon when the page is first loaded. It first adds the correct scenes to the list of
    // currently activated scenes, after which it sets the values for the username, usertype and customer name to the
    // appropriate values, using various static classes where necessary. Finally, the system initialises the other static
    // classes that will be used by the system.
    public void initialize() {
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

    // When this function is called upon, every field on the current page is cleared, ready for the values to be
    // re-inputted.
    public void clearInputs() {
        cardNumberField.clear();
        nameField.clear();
        expDateField.clear();
        cvvField.clear();
    }
}

