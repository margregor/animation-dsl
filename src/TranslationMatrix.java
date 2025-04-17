import com.raylib.java.raymath.Matrix;
import com.raylib.java.raymath.Raymath;

import java.util.Map;

public class TranslationMatrix implements SceneMatrix{
    private Matrix matrix;
    private final Expression xTranslationExpression;
    private final Expression yTranslationExpression;

    public TranslationMatrix(Expression translation) {
        this.matrix = Raymath.MatrixIdentity();
        this.xTranslationExpression = translation;
        this.yTranslationExpression = null;
    }

    public TranslationMatrix(Expression xTranslation, Expression yTranslation) {
        this.matrix = Raymath.MatrixIdentity();
        this.xTranslationExpression = xTranslation;
        this.yTranslationExpression = yTranslation;
    }

    @Override
    public void update(final Map<String,Variable> variableMap) {
        matrix = Raymath.MatrixTranslate(
                xTranslationExpression.evaluate(variableMap),
                yTranslationExpression!=null ? yTranslationExpression.evaluate(variableMap) : 0,
                0
        );
    }

    @Override
    public Matrix getMatrix() {
        return matrix;
    }
}
