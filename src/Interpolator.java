import java.util.Map;

public interface Interpolator {
    float getNextValue(final Map<String, Variable> variableMap);

    default boolean finite() {
        return true;
    }

    boolean finished();
}
