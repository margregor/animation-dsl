import java.util.Map;

public class ExpressionParser extends AnimatorParserBaseVisitor<Float> {
    private final Map<String,Variable> variableMap;

    public ExpressionParser(Map<String,Variable> variableMap) {
        this.variableMap = variableMap;
    }

    @Override
    public Float visitLiteral(AnimatorParser.LiteralContext ctx) {
        return Float.parseFloat(ctx.getText());
    }

    @Override
    public Float visitVarRead(AnimatorParser.VarReadContext ctx) {
        return variableMap.get(ctx.ID().getText()).getValue();
    }

    @Override
    public Float visitBinOp(AnimatorParser.BinOpContext ctx) {
        switch (ctx.op.getType()) {
            case AnimatorParser.POW -> {
                return (float) Math.pow(this.visit(ctx.left), this.visit(ctx.right));
            }
            case AnimatorParser.MUL -> {
                return this.visit(ctx.left) * this.visit(ctx.right);
            }
            case AnimatorParser.DIV -> {
                return this.visit(ctx.left) / this.visit(ctx.right);
            }
            case AnimatorParser.ADD -> {
                return this.visit(ctx.left) + this.visit(ctx.right);
            }
            case AnimatorParser.SUB -> {
                return this.visit(ctx.left) - this.visit(ctx.right);
            }
            default -> throw new IllegalStateException("Unsupported operator: <" + ctx.op.getText() + "> at line " + ctx.start.getLine());
        }
    }

    @Override
    public Float visitParenExpr(AnimatorParser.ParenExprContext ctx) {
        return this.visit(ctx.expr());
    }

    @Override
    public Float visitUnOp(AnimatorParser.UnOpContext ctx) {
        if (ctx.op.getType() == AnimatorParser.SUB) {
            return -this.visit(ctx.right);
        }
        throw new IllegalStateException("Unsupported operator: <" + ctx.op.getText() + "> at line " + ctx.start.getText());
    }
}