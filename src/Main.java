import com.raylib.java.Raylib;
import com.raylib.java.core.Color;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.apache.commons.cli.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Options options = new Options();

        options.addOption(Option.builder()
                .option("w")
                .longOpt("width")
                .hasArg()
                .desc("window width")
                .type(Number.class)
                .build()
        );

        options.addOption(Option.builder()
                .option("h")
                .longOpt("height")
                .hasArg()
                .desc("window height")
                .type(Number.class)
                .build()
        );

        options.addOption(Option.builder()
                .option("f")
                .longOpt("frames")
                .hasArg()
                .desc("target FPS")
                .type(Number.class)
                .build()
        );


        CommandLineParser cmdParser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = cmdParser.parse(options, args);

            if (cmd.getArgs().length == 0) {
                throw new ParseException("No input files provided");
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("Animator [file]", options);

            System.exit(1);
        }

        CharStream input = CharStreams.fromFileName(cmd.getArgs()[0]);

        // create a lexer that feeds off of input CharStream
        AnimatorLexer lexer = new AnimatorLexer(input);

        // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // create a parser that feeds off the tokens buffer
        AnimatorParser parser = new AnimatorParser(tokens);

        // start parsing at the transformList rule
        ParseTree tree = parser.scene();

        int width = cmd.hasOption("width") ? Integer.parseInt(cmd.getOptionValue("width")) : 800;
        int height = cmd.hasOption("height") ? Integer.parseInt(cmd.getOptionValue("height")) : 600;
        int targetFPS =cmd.hasOption("frames") ? Integer.parseInt(cmd.getOptionValue("frames")) : 60;

        // create a visitor to traverse the parse tree
        SceneParser visitor = new SceneParser(width, height);
        var result = visitor.visit(tree);

        Raylib rlj = new Raylib();
        rlj.core.InitWindow(width, height, "Scene");
        rlj.core.SetTargetFPS(targetFPS);

        boolean finished = false;

        while (!rlj.core.WindowShouldClose()){
            if (!finished)
                finished=(!result.update());
            rlj.core.BeginDrawing();
            rlj.core.ClearBackground(Color.WHITE);

            result.draw(rlj);

            rlj.text.DrawText(
                    result.getVariables().toString().replace(',', '\n'),
                    5,
                    5,
                    10,
                    Color.BLACK
            );
            if (finished)
            {
                int textWidth = rlj.text.MeasureText("Fin", 50);
                rlj.text.DrawText("Fin", width/2-textWidth/2 - 2, height/2-25 - 2, 50, Color.BLACK);
                rlj.text.DrawText("Fin", width/2-textWidth/2 + 2, height/2-25 - 2, 50, Color.BLACK);
                rlj.text.DrawText("Fin", width/2-textWidth/2 - 2, height/2-25 + 2, 50, Color.BLACK);
                rlj.text.DrawText("Fin", width/2-textWidth/2 + 2, height/2-25 + 2, 50, Color.BLACK);

                rlj.text.DrawText("Fin", width/2-textWidth/2, height/2-25, 50, Color.WHITE);
            }
            rlj.core.EndDrawing();
        }

        result.unloadTextures(rlj);
    }
}