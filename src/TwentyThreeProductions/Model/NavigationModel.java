package TwentyThreeProductions.Model;

import TwentyThreeProductions.Controller.MainAdminController;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class NavigationModel {

    public static String LOGIN_URL = "src/TwentyThreeProductions/View/LoginScreen.fxml";
    public static String MAIN_ADMIN_URL = "src/TwentyThreeProductions/View/MainScreenAdmin.fxml";
    public static String MAIN_FFR_URL = "src/TwentyThreeProductions/View/MainScreenFFR.fxml";
    public static String MAIN_MECHANIC_URL = "src/TwentyThreeProductions/View/MainScreenMechanic.fxml";
    public static URL tmpURL;
    private static DBLogic dbController = DBLogic.getDBInstance();
    private static SceneSwitch sceneSwitch = SceneSwitch.getInstance();

    public enum user_type {
        NONE,
        ADMIN,
        FRANCHISEE,
        RECEPTIONIST,
        FOREPERSON,
        MECHANIC
    }

    private static user_type type = user_type.NONE;

    public static user_type getType() {
        return type;
    }


    private NavigationModel() {
    }
    // Add URL for other Views
    public static URL getURL(String name) throws IOException {
        if (name.equals("Login")) {
            return tmpURL = new File(LOGIN_URL).toURI().toURL();
        } else if (name.equals("MainAdmin")) {
            return tmpURL = new File(MAIN_ADMIN_URL).toURI().toURL();
        } else  if (name.equals("MainMechanic")){
            return tmpURL = new File(MAIN_MECHANIC_URL).toURI().toURL();
        } else if ((name.equals("MainFFR"))) {
            return tmpURL = new File(MAIN_FFR_URL).toURI().toURL();
        } else {
            System.out.println("User type not found");
            return null;
        }
    }

    public static void detectUserType(Scene scene, Parent parent) throws IOException {
        System.out.println("Detecting User");
        if (dbController.getUser_type().equals("ADMIN")) {
            type = user_type.ADMIN;
            sceneSwitch.addScene(scene, parent, "MainAdmin");
            sceneSwitch.activateScene("MainAdmin");
        } else if (dbController.getUser_type().equals("FRANCHISEE")) {
            type = user_type.FRANCHISEE;
            sceneSwitch.addScene(scene, parent, "MainFFR");
            sceneSwitch.activateScene("MainFFR");
        } else if (dbController.getUser_type().equals("RECEPTIONIST")) {
            type = user_type.RECEPTIONIST;
            sceneSwitch.addScene(scene, parent, "MainFFR");
            sceneSwitch.activateScene("MainFFR");
        } else if (dbController.getUser_type().equals("FOREPERSON")) {
            type = user_type.FOREPERSON;
            sceneSwitch.addScene(scene, parent, "MainFFR");
            sceneSwitch.activateScene("MainFFR");
        } else if (dbController.getUser_type().equals("MECHANIC")) {
            type = user_type.MECHANIC;
            sceneSwitch.addScene(scene, parent, "MainMechanic");
            sceneSwitch.activateScene("MainMechanic");
        }
        System.out.println("Detected User : " + type);
    }

}