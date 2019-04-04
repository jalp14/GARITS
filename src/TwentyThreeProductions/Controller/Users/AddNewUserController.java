package TwentyThreeProductions.Controller.Users;


import TwentyThreeProductions.Domain.Mechanic;
import TwentyThreeProductions.Domain.User;
import TwentyThreeProductions.Model.DBLogic;
import TwentyThreeProductions.Model.Database.DAO.MechanicDAO;
import TwentyThreeProductions.Model.Database.DAO.UserDAO;
import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import TwentyThreeProductions.Model.SystemAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class AddNewUserController {
/////////////////////////// Add new User \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    private SceneSwitch sceneSwitch;
    private DBLogic dbLogic;
    private SystemAlert systemAlert;

    @FXML
    private StackPane addNewUserStackPane;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text usertypeLbl;

    @FXML
    private Label welcomeMessage;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton addUserBtn;

    @FXML
    private Label fNameHeading;

    @FXML
    private JFXTextField firstNameField;

    @FXML
    private Label lNameHeading;

    @FXML
    private Label usernameHeading;

    @FXML
    private Label passwordHeading;

    @FXML
    private Label roleHeading;

    @FXML
    private JFXTextField hourlyRateField;

    @FXML
    private JFXTextField lastNameField;

    @FXML
    private JFXTextField usernameField;

    @FXML
    private JFXTextField passwordField;

    @FXML
    private JFXComboBox<Label> roleCombi;

    @FXML
    void addUserBtnClicked(ActionEvent event) { // Save all the field details to the user object
        // Pass User object to the DAO class to add it to the database
        Mechanic mechanic = new Mechanic();
        MechanicDAO mechanicDAO;
       User user = new User();
       user.setUsername(usernameField.getText());
       user.setPassword(passwordField.getText());
       user.setFirstName(firstNameField.getText());
       user.setLastName(lastNameField.getText());
       user.setUserRole(roleCombi.getSelectionModel().getSelectedItem().getText());
       UserDAO userDAO = new UserDAO();
       userDAO.save(user);
        // if the user is mechanic then also save the hourly rate
       if (!(hourlyRateField.isDisabled())) {
           mechanicDAO = new MechanicDAO();
           mechanic.setUsername(usernameField.getText());
           mechanic.setHourlyRate(Integer.parseInt(hourlyRateField.getText()));
           mechanicDAO.save(mechanic);
       }

       SystemAlert systemAlert = new SystemAlert(addNewUserStackPane, "Success", "Added user");

    }

    @FXML
    void backBtnClicked(ActionEvent event) {
        sceneSwitch.switchScene(NavigationModel.USER_MANAGEMENT_ID);
    }

    @FXML
    void roleSelected(ActionEvent event) { // Enable/Disable the hourly rate depending on the type of the user
        if ((roleCombi.getSelectionModel().getSelectedItem().getText().equals("MECHANIC"))) {
            hourlyRateField.setDisable(false);
        } else if ((roleCombi.getSelectionModel().getSelectedItem().getText().equals("FOREPERSON"))) {
            hourlyRateField.setDisable(false);
        } else {
            hourlyRateField.setDisable(true);
        }
    }

    public void setupRole() { // Add all the roles to the combo box
        roleCombi.getItems().add(new Label("ADMIN"));
        roleCombi.getItems().add(new Label("FRANCHISEE"));
        roleCombi.getItems().add(new Label("RECEPTIONIST"));
        roleCombi.getItems().add(new Label("FOREPERSON"));
        roleCombi.getItems().add(new Label("MECHANIC"));
    }

    public void initialize() { // Initialise the current form
        sceneSwitch = SceneSwitch.getInstance();
        usernameLbl.setText(DBLogic.getDBInstance().getUsername());
        usertypeLbl.setText(DBLogic.getDBInstance().getUser_type());
        dbLogic = DBLogic.getDBInstance();
        setupRole();
    }

}
