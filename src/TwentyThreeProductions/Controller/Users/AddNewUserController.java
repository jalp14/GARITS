package TwentyThreeProductions.Controller.Users;


import TwentyThreeProductions.Model.DBLogic;
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
    private JFXTextField lastNameField;

    @FXML
    private JFXTextField usernameField;

    @FXML
    private JFXTextField passwordField;

    @FXML
    private JFXComboBox<Label> roleCombi;

    @FXML
    void addUserBtnClicked(ActionEvent event) {
       // performChecks();
        if (dbLogic.addUser(firstNameField.getText(), lastNameField.getText(), usernameField.getText(), passwordField.getText(), roleCombi.getSelectionModel().getSelectedItem().getText())) {
             systemAlert = new SystemAlert(addNewUserStackPane, "Success!", "Created new user. Please restart the program to use the new account");
        } else {
            systemAlert = new SystemAlert(addNewUserStackPane, "Failed", "Please try again");
        }
    }

    public void performChecks() {
        if (usernameField.getText().isBlank()) {
             systemAlert = new SystemAlert(addNewUserStackPane, "Username error", "No username typed in");
        }
    }

    @FXML
    void backBtnClicked(ActionEvent event) {
        sceneSwitch.switchScene(NavigationModel.USER_MANAGEMENT_ID);
    }

    public void setupRole() {
        roleCombi.getItems().add(new Label("ADMIN"));
        roleCombi.getItems().add(new Label("FRANCHISEE"));
        roleCombi.getItems().add(new Label("RECEPTIONIST"));
        roleCombi.getItems().add(new Label("FOREPERSON"));
        roleCombi.getItems().add(new Label("MECHANIC"));
    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(addNewUserStackPane, NavigationModel.ADD_NEW_USER_ID);
        dbLogic = DBLogic.getDBInstance();
        setupRole();
    }

}
