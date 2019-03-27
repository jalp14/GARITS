package TwentyThreeProductions.Model;

import TwentyThreeProductions.Domain.Notification;
import TwentyThreeProductions.Model.Database.DAO.NotificationDAO;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class SystemNotification {

    private JFXSnackbar notification;
    private JFXSnackbarLayout notificationLayout;
    private Notification domainNotification;
    private NotificationDAO notificationDAO;

    public SystemNotification(StackPane pane) {
        notification = new JFXSnackbar(pane);
        notificationLayout = new JFXSnackbarLayout("Message");
        domainNotification = new Notification();
        notificationDAO = new NotificationDAO();
    }

    public void setNotificationMessage(String message) {
        notificationLayout.setToast(message);
        domainNotification.setMessage(message);
    }

    public void showNotification(String currentView, String currentUser) {
        notification.getPopupContainer().setTranslateY(-1);
        notification.enqueue(new JFXSnackbar.SnackbarEvent(notificationLayout));
        domainNotification.setView(currentView);
        domainNotification.setUser(currentUser);
        domainNotification.setPeriod(LocalDateTime.now());
        saveNotification();
    }

    private void saveNotification() {
        notificationDAO.save(domainNotification);
    }


}
