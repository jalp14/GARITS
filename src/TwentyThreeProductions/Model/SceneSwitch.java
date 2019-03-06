package TwentyThreeProductions.Model;

import TwentyThreeProductions.Controller.LoginController;
import com.sun.javafx.fxml.FXMLLoaderHelper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class SceneSwitch {

    private Scene scene;
    private Parent parent;
    private Parent currentRoot;
    private HashMap<String, Parent> sceneMap;
    private static SceneSwitch sceneSwitch = null;


    private SceneSwitch() {
        sceneMap = new HashMap<>();
    }

    public static SceneSwitch getInstance() {
        if (sceneSwitch == null) {
            sceneSwitch = new SceneSwitch();
        }
        return sceneSwitch;
    }

    public void activateScene(String name, Scene scene) throws IOException {
        this.scene = scene;
        currentRoot = FXMLLoader.load(NavigationModel.getURL(name));
        scene.setRoot(currentRoot);
    }

    public void addScene(Parent parent, String name) {
        this.parent = parent;
        sceneMap.put(name, parent);
    }

    public void switchScene(String name) {
        scene.setRoot(sceneMap.get(name));
    }

}
