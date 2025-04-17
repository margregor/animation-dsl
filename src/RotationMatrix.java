import com.raylib.java.core.ray.Ray;
import com.raylib.java.raymath.Matrix;
import com.raylib.java.raymath.Raymath;

import java.util.Map;

public class RotationMatrix implements SceneMatrix {
    private Matrix matrix;
    private final Expression angle;
    private final Expression centerX;
    private final Expression centerY;

    public RotationMatrix(Expression angle) {
        this.angle = angle;
        centerX = null;
        centerY = null;
    }

    public RotationMatrix(Expression angle, Expression xCenter, Expression yCenter) {
        this.angle = angle;
        this.centerX = xCenter;
        this.centerY = yCenter;
    }

    @Override
    public void update(Map<String, Variable> variableMap) {
        Matrix translationMatrix = Raymath.MatrixTranslate(0, 0, 0);
        Matrix inverseTranslationMatrix = Raymath.MatrixTranslate(0, 0, 0);
        Matrix rotationMatrix;

        if (centerX != null && centerY != null) {
            float x = centerX.evaluate(variableMap);
            float y = centerY.evaluate(variableMap);
            translationMatrix = Raymath.MatrixTranslate(x, y, 0);
            inverseTranslationMatrix = Raymath.MatrixTranslate(-x, -y, 0);
        }

        rotationMatrix = Raymath.MatrixRotateZ(Raymath.DEG2RAD * angle.evaluate(variableMap));
        matrix = Raymath.MatrixMultiply(inverseTranslationMatrix, Raymath.MatrixMultiply(rotationMatrix, translationMatrix));
    }

    @Override
    public Matrix getMatrix() {
        return matrix;
    }
}
