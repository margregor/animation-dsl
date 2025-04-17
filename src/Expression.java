import java.util.Map;

public class Expression {
    private final AnimatorParser.ExprContext ctx;

    public Expression(AnimatorParser.ExprContext ctx) {
        this.ctx = ctx;
    }

    public float evaluate(final Map<String,Variable> variableMap) {
        ExpressionParser visitor = new ExpressionParser(variableMap);
        return visitor.visit(ctx);
    }
}
