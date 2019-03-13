package TwentyThreeProductions.Controller.MainScreen;

import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFFRController {

    private SceneSwitch sceneSwitch;

   @FXML
   private StackPane mainScreenFFRStackPane;

   @FXML
   private JFXButton jobsBtn;

   @FXML
   private JFXButton customersBtn;

   @FXML
   private JFXButton notifsBtn;

   @FXML
   private Text usernameLbl;

   @FXML
   private Text userTypeLbl;

   @FXML
   private JFXButton logoutBtn;

   @FXML
   private Label welcomeMessage;

   @FXML
   private JFXButton partsBtn;

   @FXML
   private JFXButton reportsBtn;

   @FXML
   void customersBtnClicked(ActionEvent event) throws IOException {
      sceneSwitch.activateScene(NavigationModel.CUSTOMER_MAIN_ID, logoutBtn.getScene());
   }

   @FXML
   void jobsBtnClicked(ActionEvent event) {

   }

   @FXML
   void logoutBtnPressed(ActionEvent event) throws IOException {
      System.out.println("Logout pressed");
      sceneSwitch.resetSceneMap();
      sceneSwitch.activateScene(NavigationModel.LOGIN_ID, logoutBtn.getScene());
   }

   @FXML
   void notifsBtnClicked(ActionEvent event) {

   }

   @FXML
   void partsBtnClicked(ActionEvent event) {

   }

   @FXML
   void reportsBtnClicked(ActionEvent event) {

   }

   public void initialize() {
       System.out.println("Init FFR Controller");
       sceneSwitch = SceneSwitch.getInstance();
       sceneSwitch.addScene(mainScreenFFRStackPane, NavigationModel.MAIN_FFR_ID);
   }

}
