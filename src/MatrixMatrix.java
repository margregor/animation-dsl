import com.raylib.java.raymath.Matrix;
import com.raylib.java.raymath.Raymath;

import java.util.Map;

public class MatrixMatrix implements SceneMatrix {
    private Matrix matrix;
    private final Expression a, b, c, d, e, f;

    public MatrixMatrix(Expression a, Expression b, Expression c, Expression d, Expression e, Expression f) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
    }


    @Override
    public void update(Map<String, Variable> variableMap) {
        matrix = new Matrix(
                a.evaluate(variableMap), c.evaluate(variableMap), 0, e.evaluate(variableMap),
                b.evaluate(variableMap), d.evaluate(variableMap), 0, f.evaluate(variableMap),
                0, 0, 1, 0,
                0, 0, 0, 1);
    }

    @Override
    public Matrix getMatrix() {
        return matrix;
    }
}
