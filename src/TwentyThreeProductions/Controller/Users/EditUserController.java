package TwentyThreeProductions.Controller.Users;

import TwentyThreeProductions.Domain.User;
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

    private SceneSwitch sceneSwitch;
    private UserDAO userDAO;
    private ArrayList<User> currentUsers;
    private User currentUser;


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
    void deleteBtnClicked(ActionEvent event) {
        System.out.println("Delete button clicked");
        userDAO = new UserDAO();
        userDAO.delete(currentUser);
        SystemAlert systemAlert = new SystemAlert(editUserStackPane, "Deleted Successfully", "Please logout to apply changes");

    }

    @FXML
    void currentUserCombiSelected(ActionEvent event) {
        getSelectedUser();
        firstNameField.setText(currentUser.getFirstName());
        lastNameField.setText(currentUser.getLastName());
        usernameField.setText(currentUser.getUsername());
        passwordField.setText(currentUser.getPassword());
        roleCombi.getSelectionModel().select(getRoleNo(currentUser.getUserRole()));
    }

    @FXML
    void saveBtnClicked(ActionEvent event) {
        currentUser.setFirstName(firstNameField.getText());
        currentUser.setLastName(lastNameField.getText());
        currentUser.setUserRole(roleCombi.getSelectionModel().getSelectedItem().getText());
        currentUser.setUsername(usernameField.getText());
        currentUser.setPassword(passwordField.getText());
        userDAO = new UserDAO();
        userDAO.update(currentUser);
        SystemAlert systemAlert = new SystemAlert(editUserStackPane, "Edited Successfully", "Please logout to apply changes");

    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(editUserStackPane, NavigationModel.EDIT_USER_ID);
        injectAvailableUsers();
        setupRole();
    }

    public void setupRole() {
        roleCombi.getItems().add(new Label("ADMIN"));
        roleCombi.getItems().add(new Label("FRANCHISEE"));
        roleCombi.getItems().add(new Label("RECEPTIONIST"));
        roleCombi.getItems().add(new Label("FOREPERSON"));
        roleCombi.getItems().add(new Label("MECHANIC"));
    }


    public int getRoleNo(String userRole) {
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

    public void injectAvailableUsers() {

            userDAO = new UserDAO();
            currentUsers = new ArrayList<>();
            currentUsers = userDAO.getAll();
            for (int i = 0; i < currentUsers.size(); i++) {
                User tmpUser = currentUsers.get(i);
                currentUserCombi.getItems().add(new Label(tmpUser.getUsername()));
            }

    }

    public void getSelectedUser() {
        for (int i = 0; i < currentUsers.size(); i++) {
            if (currentUsers.get(i).getUsername().equals(currentUserCombi.getSelectionModel().getSelectedItem().getText())) {
                currentUser = currentUsers.get(i);
            }
        }
    }

}
