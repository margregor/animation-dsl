import java.util.Map;

public class ConstantInterpolator implements Interpolator {
    private final Expression valueExpression;
    private final Integer interpolatorDuration;
    private final Float value;
    private int time;

    public ConstantInterpolator(Expression value) {
        valueExpression = value;
        interpolatorDuration = null;
        this.value = null;
        time = 0;
    }

    public ConstantInterpolator(float value) {
        valueExpression = null;
        interpolatorDuration = null;
        this.value = value;
        time = 0;
    }

    public ConstantInterpolator(int duration, Expression value) {
        valueExpression = value;
        interpolatorDuration = duration;
        this.value = null;
        time = 0;
    }

    @Override
    public boolean finite() {
        return interpolatorDuration != null;
    }

    @Override
    public float getNextValue(final Map<String, Variable> variableMap) {
        time++;
        if (valueExpression != null) return valueExpression.evaluate(variableMap);
        if (value != null) return value;
        throw new IllegalStateException("Broken constant");
    }

    @Override
    public boolean finished() {
        return interpolatorDuration != null && time >= interpolatorDuration;
    }
}
