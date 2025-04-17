import com.raylib.java.Raylib;

import java.util.*;

public class Scene {
    Map<String, Variable> variableMap;
    List<SceneImage> imageList;

    public Scene() {
        variableMap = new HashMap<>();
        imageList = new ArrayList<>();
    }

    public void addImage(SceneImage image) {
        imageList.add(image);
    }

    public void addVariable(String name, Variable var) {
        variableMap.put(name, var);
    }

    public boolean update() {
        List<Boolean> done = new ArrayList<>();
        variableMap.values().forEach(variable -> done.add(variable.updateValue(variableMap)));

        imageList.forEach(image -> image.update(variableMap));

        boolean result = true;
        boolean hasFinite = false;
        for (Boolean b : done) {
            if (b == null)
            {
                continue;
            }
            hasFinite = true;
            if (!b)
            {
                result = false;
                break;
            }
        }
        return result && hasFinite;
    }

    public Map<String, Float> getVariables() {
        var ret = new HashMap<String, Float>();
        variableMap.forEach((k, v) -> ret.put(k, v.getValue()));
        return ret;
    }

    public Float getVariable(String name) {
        return variableMap.get(name).getValue();
    }

    public void draw(Raylib rlj) {
        for (SceneImage image : imageList) {
            image.draw(rlj);
        }
    }

    public void unloadTextures(Raylib rlj) {
        for (SceneImage image : imageList) {
            image.unload(rlj);
        }
    }
}
