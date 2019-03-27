package TwentyThreeProductions.Model.Database.Interfaces;

import TwentyThreeProductions.Domain.Notification;
import TwentyThreeProductions.Domain.User;

import java.util.ArrayList;

public interface INotification {
    ArrayList<Notification> getNotification(String user);
    void save(Notification notification);
    void delete(Notification notification);

}
