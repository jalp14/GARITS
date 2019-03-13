package TwentyThreeProductions.Model;

import TwentyThreeProductions.Controller.MainScreen.MainAdminController;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class NavigationModel {
    // URLs
    public static String LOGIN_URL = "src/TwentyThreeProductions/View/LoginScreen.fxml";
    public static String MAIN_ADMIN_URL = "src/TwentyThreeProductions/View/MainScreen/MainScreenAdmin.fxml";
    public static String MAIN_FFR_URL = "src/TwentyThreeProductions/View/MainScreen/MainScreenFFR.fxml";
    public static String MAIN_MECHANIC_URL = "src/TwentyThreeProductions/View/MainScreen/MainScreenMechanic.fxml";
    public static String USER_MANAGEMENT_URL = "src/TwentyThreeProductions/View/Users/UsersMain.fxml";
    public static String DB_MANAGEMENT_URL = "src/TwentyThreeProductions/View/Database/DbManagement.fxml";
    public static String DB_RESTORE_URL = "src/TwentyThreeProductions/View/Database/DbRestore.fxml";
    public static URL tmpURL;
    private static DBLogic dbController = DBLogic.getDBInstance();
    private static SceneSwitch sceneSwitch = SceneSwitch.getInstance ();

    // Scene IDs
    public static String LOGIN_ID = "Login";
    public static String MAIN_ADMIN_ID = "MainAdmin";
    public static String MAIN_FFR_ID = "MainFFR";
    public static String MAIN_MECHANIC_ID = "MainMechanic";
    public static String USER_MANAGEMENT_ID = "UserManagement";
    public static String DB_MANAGEMENT_ID = "DBManagement";
    public static String DB_RESTORE_ID = "DBRestore";


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
    // Add constraints for views you want to instantiate
    public static URL getURL(String name) throws IOException {
        if (name.equals(LOGIN_ID)) {
            return tmpURL = new File(LOGIN_URL).toURI().toURL();
        } else if (name.equals(MAIN_ADMIN_ID)) {
            return tmpURL = new File(MAIN_ADMIN_URL).toURI().toURL();
        } else  if (name.equals(MAIN_MECHANIC_ID)){
            return tmpURL = new File(MAIN_MECHANIC_URL).toURI().toURL();
        } else if ((name.equals(MAIN_FFR_ID))) {
            return tmpURL = new File(MAIN_FFR_URL).toURI().toURL();
        } else if ((name.equals(USER_MANAGEMENT_ID))) {
            return tmpURL = new File(USER_MANAGEMENT_URL).toURI().toURL();
        } else if ((name.equals(DB_MANAGEMENT_ID))) {
            return tmpURL = new File(DB_MANAGEMENT_URL).toURI().toURL();
        } else if ((name.equals(DB_RESTORE_ID))) {
            return tmpURL = new File(DB_RESTORE_URL).toURI().toURL();
        }
        else {
            System.out.println("User type not found");
            return null;
        }
    }

    public static void detectUserType(Scene scene) throws IOException {
        System.out.println("Detecting User");
        if (dbController.getUser_type().equals("ADMIN")) {
            type = user_type.ADMIN;
            sceneSwitch.activateScene(MAIN_ADMIN_ID, scene);
        } else if (dbController.getUser_type().equals("FRANCHISEE")) {
            type = user_type.FRANCHISEE;
            sceneSwitch.activateScene(MAIN_FFR_ID, scene);
        } else if (dbController.getUser_type().equals("RECEPTIONIST")) {
            type = user_type.RECEPTIONIST;
            sceneSwitch.activateScene(MAIN_FFR_ID, scene);
        } else if (dbController.getUser_type().equals("FOREPERSON")) {
            type = user_type.FOREPERSON;
            sceneSwitch.activateScene(MAIN_FFR_ID, scene);
        } else if (dbController.getUser_type().equals("MECHANIC")) {
            type = user_type.MECHANIC;
            sceneSwitch.activateScene(MAIN_MECHANIC_ID, scene);
        }
        System.out.println("Detected User : " + type);
    }

}