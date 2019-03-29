package TwentyThreeProductions.Controller;


import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class SettingsController {

    private SceneSwitch sceneSwitch;

    @FXML
    private StackPane loginStackPane;

    @FXML
    private Pane mainPane;

    @FXML
    private JFXButton restoreBtn;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXComboBox<Label> restoreList;

    @FXML
    private JFXComboBox<Label> themeList;

    @FXML
    private JFXButton changeThemeBtn;

    @FXML
    void backBtnClicked(ActionEvent event) {
        sceneSwitch.switchScene(NavigationModel.LOGIN_ID);
    }

    @FXML
    void changeThemeBtnClicked(ActionEvent event) {

    }

    @FXML
    void restoreBtnClicked(ActionEvent event) {

    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
    }

}

