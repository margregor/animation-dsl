import java.util.Map;
import java.util.function.Function;

public class UniversalInterpolator implements Interpolator {
    private final Expression startExpression;
    private final Expression endExpression;
    private final int interpolatorDuration;
    private final Function<Float, Float> easingFunction;
    private int time;

    public UniversalInterpolator(int duration, Expression start, Expression end, Function<Float, Float> easingFunction) {
        interpolatorDuration = duration;
        startExpression = start;
        endExpression = end;
        this.easingFunction = easingFunction;
        time = 0;
    }

    @Override
    public float getNextValue(final Map<String, Variable> variableMap) {
        final float start = startExpression.evaluate(variableMap);
        final float end = endExpression.evaluate(variableMap);
        return start + easingFunction.apply((float) time++ / interpolatorDuration) * (end - start);
    }

    @Override
    public boolean finished() {
        return time >= interpolatorDuration;
    }
}
