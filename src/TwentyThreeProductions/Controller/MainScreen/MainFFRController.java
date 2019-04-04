package TwentyThreeProductions.Controller.MainScreen;

import TwentyThreeProductions.Domain.Job;
import TwentyThreeProductions.Model.DBLogic;
import TwentyThreeProductions.Model.Database.DAO.JobDAO;
import TwentyThreeProductions.Model.HelperClasses.SettingsHelper;
import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import TwentyThreeProductions.Model.SystemNotification;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;

public class MainFFRController {

    private SceneSwitch sceneSwitch;

    private SettingsHelper settingsHelper;

   @FXML
   private StackPane mainScreenFFRStackPane;

   @FXML
   private JFXButton jobsBtn;

   @FXML
   private JFXButton customersBtn;

   @FXML
   private JFXButton bellBtn;

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
   void bellBtnClicked(ActionEvent event) throws IOException {
      System.out.println("Bell Btn Clicked");
      sceneSwitch.activateScene(NavigationModel.NOTIFICATIONS_MAIN_ID, logoutBtn.getScene());
   }

   @FXML
   void customersBtnClicked(ActionEvent event) throws IOException {
      sceneSwitch.activateScene(NavigationModel.CUSTOMER_MAIN_ID, logoutBtn.getScene());
   }

   @FXML
   void jobsBtnClicked(ActionEvent event) throws IOException {
      sceneSwitch.activateScene(NavigationModel.JOBS_MAIN_ID, logoutBtn.getScene());
   }

   @FXML
   void logoutBtnPressed(ActionEvent event) throws IOException {
      System.out.println("Logout pressed");
      sceneSwitch.resetSceneMap();
      sceneSwitch.activateScene(NavigationModel.LOGIN_ID, logoutBtn.getScene());
   }


   @FXML
   void partsBtnClicked(ActionEvent event) throws IOException {
      sceneSwitch.activateScene(NavigationModel.PARTS_MAIN_ID, logoutBtn.getScene());
   }

   @FXML
   void reportsBtnClicked(ActionEvent event) throws IOException {
      sceneSwitch.activateScene(NavigationModel.REPORTS_MAIN_ID, partsBtn.getScene());
   }

   public void initialize() {
       System.out.println("Init FFR Controller");
       sceneSwitch = SceneSwitch.getInstance();
       sceneSwitch.addScene(mainScreenFFRStackPane, NavigationModel.MAIN_FFR_ID);
       setLblConstraints();
       if(userTypeLbl.getText().equals("FRANCHISEE")) {
          checkLatePayments();
       }
   }


   public void setLblConstraints() {
      welcomeMessage.setText("Welcome " + DBLogic.getDBInstance().getUsername());
      usernameLbl.setText(DBLogic.getDBInstance().getUsername());
      userTypeLbl.setText(DBLogic.getDBInstance().getUser_type());
   }

   public void checkLatePayments() {
      java.util.Date currentDate = new java.util.Date();
      java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
      JobDAO jobDAO = new JobDAO();
      for(Job j: jobDAO.getAll()) {
         Date latePaymentDate = j.getDateCompleted();
         latePaymentDate.setMonth(latePaymentDate.getMonth() + 1);
         if(sqlDate.after(latePaymentDate) && j.getPaidFor().equals("False") && j.getChecked().equals("False")) {
            SystemNotification notification = new SystemNotification(mainScreenFFRStackPane);
            notification.setNotificationMessage("There is a completed job that has not been paid for " +
                    "in one month.");
            notification.showNotification(NavigationModel.JOBS_MAIN_ID, DBLogic.getDBInstance().getUsername());
            j.setChecked("True");
            jobDAO.setChecked(j);
         }
      }
   }
}
