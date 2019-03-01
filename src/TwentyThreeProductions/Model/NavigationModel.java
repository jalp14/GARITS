package TwentyThreeProductions.Model;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class NavigationModel {

    public static String LOGIN_URL = "src/TwentyThreeProductions/View/LoginScreen.fxml";
    public static String MAIN_URL = "src/TwentyThreeProductions/View/MainScreen.fxml";
    public static URL tmpURL;
    private static DBLogic dbController = DBLogic.getDBInstance();
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


    public NavigationModel() {
    }

    public static URL getURL(String name) throws IOException {
        if (name.equals("Login")) {
            return tmpURL = new File(LOGIN_URL).toURI().toURL();
        } else if (name.equals("Main")) {
            return tmpURL = new File(MAIN_URL).toURI().toURL();
        } else {
            return tmpURL;
        }
    }

    public static void detectUserType() {
        System.out.println("Detecting User");
        if (dbController.getUser_type().equals("ADMIN")) {
            type = user_type.ADMIN;
        } else if (dbController.getUser_type().equals("FRANCHISEE")) {
            type = user_type.FRANCHISEE;
        } else if (dbController.getUser_type().equals("RECEPTIONIST")) {
            type = user_type.RECEPTIONIST;
        } else if (dbController.getUser_type().equals("FOREPERSON")) {
            type = user_type.FOREPERSON;
        } else if (dbController.getUser_type().equals("MECHANIC")) {
            type = user_type.MECHANIC;
        }
        System.out.println("Detected User : " + type);
    }


}
