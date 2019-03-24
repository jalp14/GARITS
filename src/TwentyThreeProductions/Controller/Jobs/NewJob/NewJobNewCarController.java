package TwentyThreeProductions.Controller.Jobs.NewJob;

import TwentyThreeProductions.Model.CustomerReference;
import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;


public class NewJobNewCarController {

    private SceneSwitch sceneSwitch;

    private CustomerReference customerReference;

    @FXML
    private StackPane newJobNewCarStackPane;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text usertypeLbl;

    @FXML
    private Label welcomeMessage;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton addPartBtn;

    @FXML
    private Label customerNameLbl;

    @FXML
    private Label regHeading;

    @FXML
    private JFXTextField registrationField;

    @FXML
    private Label makeHeading;

    @FXML
    private Label modelHeading;

    @FXML
    private Label serialHeading;

    @FXML
    private Label chassisNoHeading;

    @FXML
    private Label colourHeading;

    @FXML
    private JFXTextField makeField;

    @FXML
    private JFXTextField modelField;

    @FXML
    private JFXTextField serialField;

    @FXML
    private JFXTextField chassisNoField;

    @FXML
    private JFXTextField colourField;

    @FXML
    void addPartBtnClicked(ActionEvent event) {

    }

    @FXML
    void backBtnClicked(ActionEvent event) {
        sceneSwitch.switchScene(NavigationModel.NEW_JOB_CAR_MENU_ID);
    }

    public void initialize() {
        System.out.println("AddCartoCustomer controller init");
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(newJobNewCarStackPane, NavigationModel.NEW_JOB_NEW_CAR_ID);
        customerReference = customerReference.getInstance();
    }


}

