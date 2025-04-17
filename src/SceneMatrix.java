import com.raylib.java.raymath.Matrix;

import java.util.Map;

public interface SceneMatrix {
    void update(final Map<String,Variable> variableMap);
    Matrix getMatrix();
}
