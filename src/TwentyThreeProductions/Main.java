package TwentyThreeProductions;

import TwentyThreeProductions.Controller.LoginController;
import TwentyThreeProductions.Model.SceneSwitch;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    static LoginController loginController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Welcome to GARITS");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("View/LoginScreen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root,800,600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
