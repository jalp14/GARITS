package TwentyThreeProductions.Model.Notification;

import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class Notification {

    private JFXSnackbar notification;
    private JFXSnackbarLayout notificationLayout;

    public Notification(StackPane pane) {
        notification = new JFXSnackbar(pane);
        notificationLayout = new JFXSnackbarLayout("Message");
    }

    public void setNotificationMessage(String message) {
        notificationLayout.setToast(message);
    }

    public void showNotification() {
        notification.getPopupContainer().setTranslateY(-1);
        notification.enqueue(new JFXSnackbar.SnackbarEvent(notificationLayout));
    }

}
