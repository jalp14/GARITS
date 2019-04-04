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
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class EditUserController {
///////////////////////////////// Edit user details \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    private SceneSwitch sceneSwitch;
    private UserDAO userDAO;
    private ArrayList<User> currentUsers;
    private User currentUser;
    private MechanicDAO mechanicDAO;
    private ArrayList<Mechanic> mechanics;
    private Mechanic mechanic;

    @FXML
    private StackPane editUserStackPane;

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
    private Label fNameHeading;

    @FXML
    private JFXButton deleteBtn;

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
    private JFXTextField lastNameField;

    @FXML
    private JFXTextField usernameField;

    @FXML
    private JFXTextField passwordField;

    @FXML
    private Label selectUserLabel;

    @FXML
    private JFXTextField hourlyRateField;


    @FXML
    private JFXComboBox<Label> roleCombi;

    @FXML
    private JFXComboBox<Label> currentUserCombi;

    @FXML
    private Label usernameTitle;

    @FXML
    void backBtnClicked(ActionEvent event) {
        sceneSwitch.switchScene(NavigationModel.USER_MANAGEMENT_ID);
    }

    @FXML
    void deleteBtnClicked(ActionEvent event) { // Delete the selected User
        System.out.println("Delete button clicked");
        if (!(hourlyRateField.isDisabled())) {
            mechanicDAO = new MechanicDAO();
            mechanicDAO.delete(mechanic);
        }
        userDAO = new UserDAO();
        userDAO.delete(currentUser);
        SystemAlert systemAlert = new SystemAlert(editUserStackPane, "Deleted Successfully", "Please logout to apply changes");
    }

    @FXML
    void roleSelected(ActionEvent event) { // Enable/Disable the hourly rate depending on the type of the user
        if ((roleCombi.getSelectionModel().getSelectedItem().getText().equals("MECHANIC"))) {
            hourlyRateField.setDisable(false);
            deleteBtn.setDisable(true);
            deleteBtn.setDisableVisualFocus(true);
        } else if ((roleCombi.getSelectionModel().getSelectedItem().getText().equals("FOREPERSON"))) {
            hourlyRateField.setDisable(false);
            deleteBtn.setDisable(true);
            deleteBtn.setDisableVisualFocus(true);
        } else {
            hourlyRateField.setDisable(true);
        }
    }

    @FXML
    void currentUserCombiSelected(ActionEvent event) { // Show User details based on the user selected from the combo box
        getSelectedUser();
        getMechanics();
        firstNameField.setText(currentUser.getFirstName());
        lastNameField.setText(currentUser.getLastName());
        usernameField.setText(currentUser.getUsername());
        passwordField.setText(currentUser.getPassword());
        if (!(mechanic == null)) {
            hourlyRateField.setText(Integer.toString(mechanic.getHourlyRate()));
        }

        roleCombi.getSelectionModel().select(getRoleNo(currentUser.getUserRole()));
    }

    @FXML
    void saveBtnClicked(ActionEvent event) { // Save changes to the database
        currentUser.setFirstName(firstNameField.getText());
        currentUser.setLastName(lastNameField.getText());
        currentUser.setUserRole(roleCombi.getSelectionModel().getSelectedItem().getText());
        currentUser.setUsername(usernameField.getText());
        currentUser.setPassword(passwordField.getText());
        userDAO = new UserDAO();
        userDAO.update(currentUser);
        if (!(hourlyRateField.isDisabled())) {
            mechanicDAO = new MechanicDAO();
            mechanic.setHourlyRate(Integer.parseInt(hourlyRateField.getText()));
            mechanic.setUsername(usernameField.getText());
            mechanicDAO.update(mechanic);
        }
        SystemAlert systemAlert = new SystemAlert(editUserStackPane, "Edited Successfully", "Please logout to apply changes");

    }

    public void initialize() { // Initialise the current form
        sceneSwitch = SceneSwitch.getInstance();
        usernameLbl.setText(DBLogic.getDBInstance().getUsername());
        usertypeLbl.setText(DBLogic.getDBInstance().getUser_type());
        mechanics = new ArrayList<>();
        injectAvailableUsers();
        setupRole();
    }

    public void setupRole() { // Add all the roles to the combo box
        roleCombi.getItems().add(new Label("ADMIN"));
        roleCombi.getItems().add(new Label("FRANCHISEE"));
        roleCombi.getItems().add(new Label("RECEPTIONIST"));
        roleCombi.getItems().add(new Label("FOREPERSON"));
        roleCombi.getItems().add(new Label("MECHANIC"));
    }


    public int getRoleNo(String userRole) { // Get different types of roles
        if (userRole.equals("ADMIN")) {
            return 0;
        } else if (userRole.equals("FRANCHISEE")) {
            return 1;
        } else if (userRole.equals("RECEPTIONIST")) {
            return 2;
        } else if (userRole.equals("FOREPERSON")) {
            return 3;
        } else if (userRole.equals("MECHANIC")) {
            return 4;
        }
        return -1;
    }

    public void injectAvailableUsers() { // Add all the available user to the combo box
        MechanicDAO mechanicDAO = new MechanicDAO();
            userDAO = new UserDAO();
            mechanics = mechanicDAO.getAll();
            currentUsers = new ArrayList<>();
            currentUsers = userDAO.getAll();
        for (User tmpUser : currentUsers) {
            currentUserCombi.getItems().add(new Label(tmpUser.getUsername()));
        }

    }

    public void getMechanics() { // Get details of selected mechanic if the user is a mechanic
        for (Mechanic mechanic1 : mechanics) {
            if (mechanic1.getUsername().equals(currentUserCombi.getSelectionModel().getSelectedItem().getText())) {
                mechanic = mechanic1;
            }
        }
    }

    public void getSelectedUser() { // Get details of selected user
        for (User currentUser1 : currentUsers) {
            if (currentUser1.getUsername().equals(currentUserCombi.getSelectionModel().getSelectedItem().getText())) {
                currentUser = currentUser1;
            }
        }

    }

}
