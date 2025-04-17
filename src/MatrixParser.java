
public class MatrixParser extends AnimatorParserBaseVisitor<SceneMatrix> {

    @Override
    public SceneMatrix visitTransform(AnimatorParser.TransformContext ctx) {
        //https://www.w3.org/TR/SVGTiny12/coords.html#TransformList
        switch (ctx.name.getText())
        {
            case "translate" -> {
                if (ctx.argumentList() == null)
                {
                    throw new IllegalStateException("Translate transform requires exactly 1 or 2 arguments, line " + ctx.start.getLine());
                }
                switch (ctx.argumentList().args.size())
                {
                    case 1 -> {
                        return new TranslationMatrix(
                                new Expression(ctx.argumentList().args.get(0))
                        );
                    }
                    case 2 -> {
                        return new TranslationMatrix(
                                new Expression(ctx.argumentList().args.get(0)),
                                new Expression(ctx.argumentList().args.get(1))
                        );
                    }
                    default -> throw new IllegalStateException("Translate transform requires exactly 1 or 2 arguments, line " + ctx.start.getLine());
                }
            }
            case "scale" -> {
                if (ctx.argumentList() == null)
                {
                    throw new IllegalStateException("Scale transform requires exactly 1 or 2 arguments, line " + ctx.start.getLine());
                }
                switch (ctx.argumentList().args.size())
                {
                    case 1 -> {
                        return new ScaleMatrix(
                                new Expression(ctx.argumentList().args.get(0))
                        );
                    }
                    case 2 -> {
                        return new ScaleMatrix(
                                new Expression(ctx.argumentList().args.get(0)),
                                new Expression(ctx.argumentList().args.get(1))
                        );
                    }
                    default -> throw new IllegalStateException("Scale transform requires exactly 1 or 2 arguments, line " + ctx.start.getLine());
                }
            }
            case "rotate" -> {
                if (ctx.argumentList() == null)
                {
                    throw new IllegalStateException("Rotate transform requires exactly 1 or 3 arguments, line " + ctx.start.getLine());
                }
                switch (ctx.argumentList().args.size())
                {
                    case 1 -> {
                        return new RotationMatrix(
                                new Expression(ctx.argumentList().args.get(0))
                        );
                    }
                    case 3 -> {
                        return new RotationMatrix(
                                new Expression(ctx.argumentList().args.get(0)),
                                new Expression(ctx.argumentList().args.get(1)),
                                new Expression(ctx.argumentList().args.get(2))
                        );
                    }
                    default -> throw new IllegalStateException("Rotate transform requires exactly 1 or 3 arguments, line " + ctx.start.getLine());
                }
            }
            case "skewX" -> {
                if (ctx.argumentList() == null)
                {
                    throw new IllegalStateException("SkewX transform requires exactly 1 argument, line " + ctx.start.getLine());
                }
                if (ctx.argumentList().args.size() == 1) {
                    return new SkewXMatrix(
                            new Expression(ctx.argumentList().args.get(0))
                    );
                }
                throw new IllegalStateException("SkewX transform requires exactly 1 argument, line " + ctx.start.getLine());
            }
            case "skewY" -> {
                if (ctx.argumentList() == null)
                {
                    throw new IllegalStateException("SkewY transform requires exactly 1 argument, line " + ctx.start.getLine());
                }
                if (ctx.argumentList().args.size() == 1) {
                    return new SkewYMatrix(
                            new Expression(ctx.argumentList().args.get(0))
                    );
                }
                throw new IllegalStateException("SkewY transform requires exactly 1 argument, line " + ctx.start.getLine());
            }
            case "matrix" -> {
                if (ctx.argumentList() == null)
                {
                    throw new IllegalStateException("Matrix transform requires exactly 6 arguments, line " + ctx.start.getLine());
                }
                if (ctx.argumentList().args.size() == 6) {
                    return new MatrixMatrix(
                            new Expression(ctx.argumentList().args.get(0)),
                            new Expression(ctx.argumentList().args.get(1)),
                            new Expression(ctx.argumentList().args.get(2)),
                            new Expression(ctx.argumentList().args.get(3)),
                            new Expression(ctx.argumentList().args.get(4)),
                            new Expression(ctx.argumentList().args.get(5))
                    );
                }
                throw new IllegalStateException("Matrix transform requires exactly 6 arguments, line " + ctx.start.getLine());
            }
            default -> throw new IllegalStateException("Unrecognized transform: " + ctx.name.getText() + " at line " + ctx.start.getLine());
        }
    }
}