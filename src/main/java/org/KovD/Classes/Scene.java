package org.KovD.Classes;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    private List<GameObject> gameObjects;

    public Scene() {
        this.gameObjects = new ArrayList<>();
    }

    public void addObject(GameObject obj) {
        this.gameObjects.add(obj);
    }

    private void updateAll(float deltaTime) {
        for (GameObject obj : gameObjects) {
            obj.update(deltaTime);
        }
    }

    private void renderAll() {
        for (GameObject obj : gameObjects) {
            obj.Draw();
        }
    }

    public void sceneLoop(float deltaTime) {
        updateAll(deltaTime);
        renderAll();
    }
}