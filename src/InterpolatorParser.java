import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class InterpolatorParser extends AnimatorParserBaseVisitor<Interpolator> {

    final private Map<String, Function<Float, Float>> easingFunctions = new HashMap<>() {{
        //https://easings.net
        put("linear", x -> x);
        put("easeInSine", x -> (float) (1-Math.cos((Math.PI * x) / 2)));
        put("easeOutSine", x -> (float) Math.sin((Math.PI * x) / 2));
        put("easeInOutSine", x -> (float) (-(Math.cos(Math.PI * x) - 1) / 2));
        put("easeInQuad", x -> x * x);
        put("easeOutQuad", x -> 1 - (1 - x) * (1 - x));
        put("easeInOutQuad", x -> x < 0.5 ? 2 * x * x : (float) (1 - Math.pow(-2 * x + 2, 2) / 2));
        put("easeInCubic", x -> x * x * x);
        put("easeOutCubic", x -> 1 - (float) Math.pow(1 - x, 3));
        put("easeInOutCubic", x -> x < 0.5 ? 4 * x * x * x : (float) (1 - Math.pow(-2 * x + 2, 3) / 2));
        put("easeInQuart", x -> x * x * x * x);
        put("easeOutQuart", x -> 1 - (float) Math.pow(1 - x, 4));
        put("easeInOutQuart", x -> x < 0.5 ? 8 * x * x * x * x : (float) (1 - Math.pow(-2 * x + 2, 4) / 2));
        put("easeInQuint", x -> x * x * x * x * x);
        put("easeOutQuint", x -> (float) (1 - Math.pow(1 - x, 5)));
        put("easeInOutQuint", x -> x < 0.5 ? 16 * x * x * x * x * x : (float) (1 - Math.pow(-2 * x + 2, 5) / 2));
        put("easeInExpo", x -> x == 0 ? 0 : (float) Math.pow(2, 10 * x - 10));
        put("easeOutExpo", x -> x == 1 ? 1 : (float) (1 - Math.pow(2, -10 * x)));
        put("easeInOutExpo", x -> x == 0 ? 0 : (float) (x == 1 ? 1 : x < 0.5 ? Math.pow(2, 20 * x - 10) / 2 : (2 - Math.pow(2, -20 * x + 10)) / 2));
        put("easeInCirc", x -> (float) (1 - Math.sqrt(1 - Math.pow(x, 2))));
        put("easeOutCirc", x -> (float) Math.sqrt(1 - Math.pow(x - 1, 2)));
        put("easeInOutCirc", x -> (float) (x < 0.5 ? (1 - Math.sqrt(1 - Math.pow(2 * x, 2))) / 2 : (Math.sqrt(1 - Math.pow(-2 * x + 2, 2)) + 1) / 2));
        put("easeInBack", x -> (float) (2.70158 * x * x * x - 1.70158 * x * x));
        put("easeOutBack", x -> (float) (1 + 2.70158 * Math.pow(x - 1, 3) + 1.70158 * Math.pow(x - 1, 2)));
        put("easeInOutBack", x -> (float) (x < 0.5 ? (Math.pow(2 * x, 2) * ((1.70158 * 1.525 + 1) * 2 * x - 1.70158 * 1.525)) / 2 : (Math.pow(2 * x - 2, 2) * ((1.70158 * 1.525 + 1) * (x * 2 - 2) + 1.70158 * 1.525) + 2) / 2));
        put("easeInElastic", x -> x == 0 ? 0 : (float) (x == 1 ? 1 : -Math.pow(2, 10 * x - 10) * Math.sin((x * 10 - 10.75) * ((2 * Math.PI) / 3))));
        put("easeOutElastic", x -> x == 0 ? 0 : (float) (x == 1 ? 1 : Math.pow(2, -10 * x) * Math.sin((x * 10 - 0.75) * ((2 * Math.PI) / 3)) + 1));
        put("easeInOutElastic", x -> x == 0 ? 0 : (float) (x == 1 ? 1 : x < 0.5 ? -(Math.pow(2, 20 * x - 10) * Math.sin((20 * x - 11.125) * ((2 * Math.PI) / 4.5))) / 2 : (Math.pow(2, -20 * x + 10) * Math.sin((20 * x - 11.125) * ((2 * Math.PI) / 4.5))) / 2 + 1));
        Function<Float, Float> easeOutBounce = x -> {
            if (x < 1 / 2.75) {
                return (float) (7.5625 * x * x);
            } else if (x < 2 / 2.75) {
                return (float) (7.5625 * (x -= (float) (1.5 / 2.75)) * x + 0.75);
            } else if (x < 2.5 / 2.75) {
                return (float) (7.5625 * (x -= (float) (2.25 / 2.75)) * x + 0.9375);
            } else {
                return (float) (7.5625 * (x -= (float) (2.625 / 2.75)) * x + 0.984375);
            }
        };
        put("easeInBounce", x -> 1 - easeOutBounce.apply(1 - x));
        put("easeOutBounce", easeOutBounce);
        put("easeInOutBounce", x -> x < 0.5 ? (1 - easeOutBounce.apply(1 - 2 * x)) / 2 : (1 + easeOutBounce.apply(2 * x - 1)) / 2);
    }};

    @Override
    public Interpolator visitInterpolator(AnimatorParser.InterpolatorContext ctx) {

        if (ctx.name.getText().equals("constant")) {
            if (ctx.argumentList() == null || ctx.argumentList().args.size() != 1) {
                throw new IllegalStateException("Constant interpolator requires exactly 1 arguments, line " + ctx.getStart().getLine());
            }
            if (ctx.duration != null) {
                return new ConstantInterpolator(
                        Integer.parseInt(ctx.duration.getText()),
                        new Expression(ctx.argumentList().args.get(0))
                );
            }
            return new ConstantInterpolator(new Expression(ctx.argumentList().args.get(0)));
        }

        if (!easingFunctions.containsKey(ctx.name.getText())) {
            throw new IllegalStateException("Unrecognized interpolator: " + ctx.name.getText() + " at line " + ctx.start.getLine());
        }

        if (ctx.argumentList() == null || ctx.argumentList().args.size() != 2) {
            String name = ctx.name.getText();
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            throw new IllegalStateException(name + " interpolator requires exactly 2 arguments, line " + ctx.getStart().getLine());
        }

        if (ctx.duration == null) {
            throw new IllegalStateException("Non constant interpolator requires duration, line " + ctx.getStart().getLine());
        }

        return new UniversalInterpolator(
                Integer.parseInt(ctx.duration.getText()),
                new Expression(ctx.argumentList().args.get(0)),
                new Expression(ctx.argumentList().args.get(1)),
                easingFunctions.get(ctx.name.getText())
        );
    }
}