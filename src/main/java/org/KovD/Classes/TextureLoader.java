package org.KovD.Classes;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.*;
import org.lwjgl.BufferUtils;
import java.nio.IntBuffer;
import java.nio.ByteBuffer;

public class TextureLoader {

    public static int load(String path) {
        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        IntBuffer c = BufferUtils.createIntBuffer(1);

        stbi_set_flip_vertically_on_load(true);

        ByteBuffer img = stbi_load(path, w, h, c, 4);

        if (img == null) {
            throw new RuntimeException("Nem sikerült betölteni a képet: " + stbi_failure_reason());
        }

        int id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, w.get(0), h.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, img);

        stbi_image_free(img);

        return id;
    }
}