package org.KovD.Classes;

import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.stb.STBImage.*;
import org.lwjgl.BufferUtils;
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

        ByteBuffer img = stbi_load(path, w, h, c, 4);

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