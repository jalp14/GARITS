package TwentyThreeProductions.Model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

public class SceneSwitch {

    private Scene scene;
    private Parent parent;
    private Parent currentRoot;
    private HashMap<String, StackPane> sceneMap;
    private static SceneSwitch sceneSwitch = null;


    private SceneSwitch() {
        sceneMap = new HashMap<>();
    }

    public void resetSceneMap() {
        sceneMap.clear();
        System.out.println(sceneMap.size());
    }

    public static SceneSwitch getInstance() {
        if (sceneSwitch == null) {
            sceneSwitch = new SceneSwitch();
        }
        return sceneSwitch;
    }

    public void activateScene(String name, Scene scene) throws IOException {
        if (sceneMap.containsKey(name)) {
            switchScene(name);
        } else {
            this.scene = scene;
            currentRoot = FXMLLoader.load(NavigationModel.getURL(name));
            scene.setRoot(currentRoot);
        }
    }


    public Node getScene(String name) {
        return sceneMap.get(name);
    }

    public void switchScene(String name) {
        scene.setRoot(sceneMap.get(name));
    }


    public void addScene(StackPane pane, String name) {
        sceneMap.put(name, pane);
    }

    public boolean removeScene(String name) {
        if (sceneMap.remove(name) == null) {
            System.out.println("Screen didn't exist");
            return false;
        } else {
            return true;
        }
    }

}
