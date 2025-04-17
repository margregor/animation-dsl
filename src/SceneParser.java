public class SceneParser extends AnimatorParserBaseVisitor<Scene> {
    private final Scene scene;

    public SceneParser(int width, int height) {
        super();
        scene = new Scene();
        Variable widthVariable = new Variable();
        widthVariable.addInterpolator(new ConstantInterpolator(width));
        Variable heightVariable = new Variable();
        heightVariable.addInterpolator(new ConstantInterpolator(height));

        scene.addVariable("windowWidth", widthVariable);
        scene.addVariable("windowHeight", heightVariable);
    }

    @Override
    public Scene visitScene(AnimatorParser.SceneContext ctx) {
        super.visitScene(ctx);
        return scene;
    }

    @Override
    public Scene visitVariableAssignement(AnimatorParser.VariableAssignementContext ctx) {
        Variable newVar = new Variable();

        InterpolatorParser visitor = new InterpolatorParser();

        ctx.interpolatorList().interpolator().forEach(
                interpolator -> newVar.addInterpolator(visitor.visit(interpolator))
        );

        scene.addVariable(ctx.ID().getText(), newVar);

        return scene;
    }

    @Override
    public Scene visitAnimation(AnimatorParser.AnimationContext ctx) {
        String path = ctx.imagePath().path.getText();
        SceneImage newImage;
        if (ctx.lifetime() == null) {
            newImage = new SceneImage(path);
        } else if (ctx.lifetime().start != null && ctx.lifetime().end == null) {
            newImage = new SceneImage(path, Integer.parseInt(ctx.lifetime().start.getText()));
        } else if (ctx.lifetime().start != null && ctx.lifetime().end != null) {
            newImage = new SceneImage(
                    path,
                    Integer.parseInt(ctx.lifetime().start.getText()),
                    Integer.parseInt(ctx.lifetime().end.getText())
            );
        }
        else {
            throw new IllegalStateException("Unreachable");
        }

        MatrixParser visitor = new MatrixParser();
        ctx.transformList().transform().forEach(
                transformContext -> newImage.addTransform(visitor.visit(transformContext))
        );

        scene.addImage(newImage);

        return scene;
    }
}