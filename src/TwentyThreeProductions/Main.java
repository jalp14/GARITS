package TwentyThreeProductions;

import TwentyThreeProductions.Domain.Backup;
import TwentyThreeProductions.Model.Database.DAO.BackupDAO;
import TwentyThreeProductions.Model.Database.DBServer;
import TwentyThreeProductions.Model.HelperClasses.SettingsHelper;
import TwentyThreeProductions.Model.SystemNotification;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Init main method
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Welcome to GARITS");
          URL url = new File("src/TwentyThreeProductions/View/LoginScreen.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root,1200,750);
        scene.getStylesheets().add("TwentyThreeProductions/CSS/test.css");
        primaryStage.setScene(scene);
        primaryStage.show();
        DBServer dbServer = DBServer.getInstance();
        checkForAutomaticBackup();
    }

    public void checkForAutomaticBackup() {
        SettingsHelper helper = SettingsHelper.getInstance();
        Backup backup;
        if (helper.checkDate()) {
            backup = new Backup();
            BackupDAO backupDAO = new BackupDAO();
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
                String timeStamp = sdf.format(new Date());
                backup.setDate(timeStamp);
                backup.setFileLocation(timeStamp + ".zip");
                System.out.println("Backup Date : " + backup.getDate());
                Process proc = Runtime.getRuntime().exec(new String[]{"./backup.sh", timeStamp});
                backupDAO.save(backup);;
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
