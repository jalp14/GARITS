package TwentyThreeProductions;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        // Init main method
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
