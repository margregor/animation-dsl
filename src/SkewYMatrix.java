import com.raylib.java.raymath.Matrix;
import com.raylib.java.raymath.Raymath;

import java.util.Map;

public class SkewYMatrix implements SceneMatrix {
    private Matrix matrix;
    private final Expression angle;

    public SkewYMatrix(Expression angle) {
        this.angle = angle;
    }


    @Override
    public void update(Map<String, Variable> variableMap) {
        matrix = new Matrix(
                1, 0, 0, 0,
                (float) Math.tan(Raymath.DEG2RAD * angle.evaluate(variableMap)), 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1);
    }

    @Override
    public Matrix getMatrix() {
        return matrix;
    }
}
