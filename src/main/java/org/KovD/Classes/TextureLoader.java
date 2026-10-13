package org.KovD.Classes;

import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.stb.STBImage.*;
import org.lwjgl.BufferUtils;
import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;
import java.nio.ByteBuffer;

public class TextureLoader {
    private static final java.util.Map<String, Integer> cache = new java.util.HashMap<>();
    private static final java.util.Map<Integer, int[]> sizes = new java.util.HashMap<>();

    public static int load(String path) {
        Integer cached = cache.get(path);
        if (cached != null) return cached;
        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        IntBuffer c = BufferUtils.createIntBuffer(1);

        stbi_set_flip_vertically_on_load(false);

        ByteBuffer fileData;
        try (InputStream in = TextureLoader.class.getResourceAsStream(path)) {
            if (in == null) throw new IllegalStateException("Nem található a textúra a classpath-on: " + path);
            java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
            byte[] chunk = new byte[8192];
            int n;
            while ((n = in.read(chunk)) != -1) {
                out.write(chunk, 0, n);
            }
            byte[] bytes = out.toByteArray();
            fileData = BufferUtils.createByteBuffer(bytes.length);
            fileData.put(bytes).flip();
        } catch (IOException e) {
            throw new IllegalStateException("Hiba a textúra olvasásakor: " + path, e);
        }

        ByteBuffer img = stbi_load_from_memory(fileData, w, h, c, 4);
        if (img == null) {
            throw new IllegalStateException("Nem sikerült dekódolni: " + path + " (" + stbi_failure_reason() + ")");
        }

        int id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, w.get(0), h.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, img);

        stbi_image_free(img);
        cache.put(path, id);
        sizes.put(id, new int[]{ w.get(0), h.get(0) });
        return id;
    }


    public static int getWidth(int textureId)  { return sizes.get(textureId)[0]; }
    public static int getHeight(int textureId) { return sizes.get(textureId)[1]; }
}