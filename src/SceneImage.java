import com.raylib.java.Raylib;
import com.raylib.java.core.Color;
import com.raylib.java.core.rCore;
import com.raylib.java.raymath.Raymath;
import com.raylib.java.rlgl.RLGL;
import com.raylib.java.textures.Image;
import com.raylib.java.textures.Texture2D;
import com.raylib.java.utils.Tracelog;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SceneImage {
    private final String path;
    private Texture2D texture = null;
    private final List<SceneMatrix> transforms;
    private Integer start = null;
    private Integer end = null;
    private Integer current = 0;

    public SceneImage(String path) {
        this.path = path;
        transforms = new ArrayList<>();
    }

    public SceneImage(String path, int start) {
        this.path = path;
        transforms = new ArrayList<>();
        this.start = start;
    }

    public SceneImage(String path, int start, int end) {
        this.path = path;
        transforms = new ArrayList<>();
        this.start = start;
        this.end = end;
    }

    public void addTransform(SceneMatrix transform) {
        transforms.add(transform);
    }

    public void update(final Map<String,Variable> variableMap) {
        current++;
        for (SceneMatrix transform : transforms)
            transform.update(variableMap);
    }

    //Why do I have to do all this just to load a non-resource image?
    private void loadTexture(Raylib rlj) {
        try (InputStream inputStream = new FileInputStream(path)) {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] input = new byte[4096];

            int read;
            while (-1 != (read = inputStream.read(input))) {
                buffer.write(input, 0, read);
            }
            byte[] fileData = buffer.toByteArray();

            String fileType = rCore.GetFileExtension(path).toLowerCase();
            Image image = rlj.textures.LoadImageFromMemory(fileType, fileData, 0);
            if (image.getData().length > 0) {
                Tracelog.Tracelog(3, "IMAGE: [" + path + "] Data loaded successfully (" + image.width + "x" + image.height + ")");
            } else {
                Tracelog.Tracelog(4, "IMAGE: [" + path + "] Failed to load data");
            }

            texture = rlj.textures.LoadTextureFromImage(image);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Raylib rlj) {

        if (start != null && current < start) return;
        if (end != null && current >= end) return;

        if (texture == null) {
            loadTexture(rlj);
        }
        RLGL.rlPushMatrix();
        for (int i = transforms.size()-1; i >= 0; i--) {
            RLGL.rlMultMatrixf(Raymath.MatrixToFloat(transforms.get(i).getMatrix()));
        }
        rlj.textures.DrawTexture(texture, -texture.width/2, -texture.height/2, Color.WHITE);
        RLGL.rlPopMatrix();
    }

    public void unload(Raylib rlj) {
        if (texture != null)
            rlj.textures.UnloadTexture(texture);
        texture = null;
    }
}
