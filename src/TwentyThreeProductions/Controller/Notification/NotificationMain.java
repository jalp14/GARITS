package TwentyThreeProductions.Controller.Notification;


import TwentyThreeProductions.Domain.Notification;
import TwentyThreeProductions.Model.DBLogic;
import TwentyThreeProductions.Model.Database.DAO.NotificationDAO;
import TwentyThreeProductions.Model.Database.DBHelper;
import TwentyThreeProductions.Model.Database.DBServer;
import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class NotificationMain {

    private SceneSwitch sceneSwitch;
    private NotificationDAO notificationDAO;
    private ArrayList<Notification> notifications;
    private HashMap<Label, Notification> notificationMap;


    @FXML
    private StackPane notificationStackPane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXButton backBtn;

    @FXML
    private Label notificationsHeading;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text userTypeLbl;

    @FXML
    private JFXButton refreshBtn;

    @FXML
    private JFXListView<Label> notificationsList;

    @FXML
    private JFXButton deleteBtn;

    @FXML
    private JFXButton goToAreaBtn;

    @FXML
    private Label timeLbl;

    @FXML
    private Label dateLbl;

    @FXML
    private Label areaLbl;

    @FXML
    void deleteBtnClicked(ActionEvent event) {
        int i = notificationsList.getSelectionModel().getSelectedIndex();
        Notification tmpNotification = notificationMap.get(notificationsList.getSelectionModel().getSelectedItem());
        notificationDAO = new NotificationDAO();
        notificationDAO.delete(tmpNotification);
        notificationsList.getItems().remove(i);
    }

    @FXML
    void goToAreaBtnClicked(ActionEvent event) throws IOException {
        sceneSwitch.activateScene(areaLbl.getText(), backBtn.getScene());
    }

    @FXML
    void notifSelected(MouseEvent event) {
        if (!(notificationsList.getSelectionModel().getSelectedItem() == null)) {
            if (!(notificationsList.getSelectionModel().getSelectedItem().getText().equals("No Notifications"))) {
                Notification tmpNotification = notificationMap.get(notificationsList.getSelectionModel().getSelectedItem());
                LocalDateTime ldt = tmpNotification.getPeriod();
                dateLbl.setText(ldt.getDayOfMonth() + "-" + ldt.getMonth() + "-" + ldt.getYear());
                timeLbl.setText(ldt.getHour() + ":" + ldt.getMinute() + ":" + ldt.getSecond());
                areaLbl.setText(tmpNotification.getView());
            }
        }
    }


    @FXML
    void refreshBtnClicked(ActionEvent event) {
        setupList();
    }

    @FXML
    void backBtnPressed(ActionEvent event) {
        if (DBLogic.getDBInstance().getUser_type().equals("ADMIN")) {
            sceneSwitch.switchScene(NavigationModel.MAIN_ADMIN_ID);
        } else if (DBLogic.getDBInstance().getUser_type().equals("FRANCHISEE")) {
            sceneSwitch.switchScene(NavigationModel.MAIN_FFR_ID);
        } else if (DBLogic.getDBInstance().getUser_type().equals("FOREPERSON")) {
            sceneSwitch.switchScene(NavigationModel.MAIN_FFR_ID);
        } else if (DBLogic.getDBInstance().getUser_type().equals("RECEPTIONIST")) {
            sceneSwitch.switchScene(NavigationModel.MAIN_FFR_ID);
        } else if (DBLogic.getDBInstance().getUser_type().equals("MECHANIC")) {
            sceneSwitch.switchScene(NavigationModel.MAIN_MECHANIC_ID);
        }
    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        notificationDAO = new NotificationDAO();
        usernameLbl.setText(DBLogic.getDBInstance().getUsername());
        userTypeLbl.setText(DBLogic.getDBInstance().getUser_type());
        notificationMap = new HashMap<>();
        setupList();
    }

    public void setupList() {
        if (!(notifications == null)) {
            notifications.clear();
        }
        notificationsList.getItems().clear();
        notifications = notificationDAO.getNotification(DBLogic.getDBInstance().getUsername());
        if (notifications.size() == 0) {
            notificationsList.getItems().add(new Label("No Notifications"));
        } else {
            for (int i = 0; i < notifications.size(); i++) {
                Notification notification = notifications.get(i);
                Label label = new Label(notification.getMessage());
                notificationsList.getItems().add(label);
                notificationMap.put(label, notification);
            }
            notificationsList.setExpanded(true);
            notificationsList.depthProperty().setValue(1);
            notificationsList.setVerticalGap(10.0);
        }
    }

}

