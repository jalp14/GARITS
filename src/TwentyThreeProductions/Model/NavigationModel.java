package TwentyThreeProductions.Model;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class NavigationModel {

    public static String LOGIN_URL = "src/TwentyThreeProductions/View/LoginScreen.fxml";
    public static String MAIN_URL = "src/TwentyThreeProductions/View/MainScreen.fxml";
    public static URL tmpURL;

    public NavigationModel() {}

    public static URL getURL(String name) throws IOException {
        if (name.equals("Login")) {
            return tmpURL = new File(LOGIN_URL).toURI().toURL();
        } else if (name.equals("Main")) {
            return tmpURL = new File(MAIN_URL).toURI().toURL();
        } else {
            return tmpURL;
        }
    }



}
