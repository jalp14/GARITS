package TwentyThreeProductions.Model;

import TwentyThreeProductions.Controller.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.lang.invoke.SwitchPoint;
import java.net.URL;
import java.util.HashMap;

public class SceneSwitch {

    private Scene scene;
    private URL currentURL;
    private URL previousURL;
    private Parent currentRoot;
    private Parent previousRoot;


    public SceneSwitch(URL sceneFile, URL currentURL, Scene scene) {
        this.currentURL = sceneFile;
        this.previousURL = currentURL;
        this.scene = scene;
    }

    public void switchScene() throws IOException {
        currentRoot = FXMLLoader.load(currentURL);
        scene.setRoot(currentRoot);
    }

    public void previousScene() throws IOException {
        previousRoot = FXMLLoader.load(previousURL);
        scene.setRoot(currentRoot);
    }

}

