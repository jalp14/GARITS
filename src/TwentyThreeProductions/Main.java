package TwentyThreeProductions;

import TwentyThreeProductions.Model.Database.DBServer;
import TwentyThreeProductions.Model.SceneSwitch;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.h2.tools.Server;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;

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
        primaryStage.setScene(scene);
        primaryStage.show();
        DBServer dbServer = DBServer.getInstance();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
