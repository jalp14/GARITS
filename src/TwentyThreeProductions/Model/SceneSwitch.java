package TwentyThreeProductions.Model;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.HashMap;

public class SceneSwitch {

    private HashMap<String, Pane> sceneMap = new HashMap<>();
    private Scene main;

    public SceneSwitch(Scene main) {
        this.main = main;
    }

    protected void addScene(String name, Pane pane) {
        sceneMap.put(name,pane);
    }

    protected void removeScene(String name) {
        sceneMap.remove(name);
    }

    protected void activateScene(String name) {
        main.setRoot(sceneMap.get(name));
    }
}

