import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Variable {
    private final List<Interpolator> interpolators = new ArrayList<Interpolator>();
    private int currentInterpolatorIndex = 0;
    private float currentValue = 0;

    public float getValue() {
        return currentValue;
    }

    public Boolean updateValue(final Map<String, Variable> variableMap) {
        if (interpolators.isEmpty() || interpolators.size() <= currentInterpolatorIndex)
            return false;

        currentValue = interpolators.get(currentInterpolatorIndex).getNextValue(variableMap);
        if (!interpolators.get(currentInterpolatorIndex).finite())
            return null;
        if (interpolators.get(currentInterpolatorIndex).finished())
            currentInterpolatorIndex++;


        return true;
    }

    public void addInterpolator(Interpolator interpolator) {
        interpolators.add(interpolator);
    }
}
