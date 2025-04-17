import com.raylib.java.raymath.Matrix;
import com.raylib.java.raymath.Raymath;

import java.util.Map;

public class ScaleMatrix implements SceneMatrix{
    private Matrix matrix;
    private final Expression xScaleExpression;
    private final Expression yScaleExpression;

    public ScaleMatrix(Expression translation) {
        this.matrix = Raymath.MatrixIdentity();
        this.xScaleExpression = translation;
        this.yScaleExpression = null;
    }

    public ScaleMatrix(Expression xTranslation, Expression yTranslation) {
        this.matrix = Raymath.MatrixIdentity();
        this.xScaleExpression = xTranslation;
        this.yScaleExpression = yTranslation;
    }

    @Override
    public void update(final Map<String,Variable> variableMap) {
        float xScale = xScaleExpression.evaluate(variableMap);

        matrix = Raymath.MatrixScale(
                xScale,
                yScaleExpression!=null ? yScaleExpression.evaluate(variableMap) : xScale,
                1
        );
    }

    @Override
    public Matrix getMatrix() {
        return matrix;
    }
}
