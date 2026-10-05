package org.KovD.Classes;
import static org.lwjgl.opengl.GL33.*;

public class Renderer {
    private static int program, vao, vbo, ebo;
    private static int uPosition, uAngle, uSize, uScreen;
    private static int screenW, screenH;

private static final String VERT =
"#version 330 core\n" +
        "layout(location = 0) in vec2 aPos;\n" +
        "layout(location = 1) in vec2 aUV;\n" +
        "uniform vec2  uPosition;\n" +
        "uniform vec2  uSize;\n" +
        "uniform float uAngle;\n" +
        "uniform vec2  uScreen;\n" +
        "out vec2 vUV;\n" +
        "void main() {\n" +
        "    float c = cos(uAngle), s = sin(uAngle);\n" +
        "    vec2 p = aPos * uSize;\n" +
        "    p = vec2(c * p.x - s * p.y, s * p.x + c * p.y) + uPosition;\n" +
        "    vec2 ndc = (p / uScreen) * 2.0 - 1.0;\n" +
        "    gl_Position = vec4(ndc.x, -ndc.y, 0.0, 1.0);\n" +
        "    vUV = aUV;\n" +
        "}\n";

    private static final String FRAG =
            "#version 330 core\n" +
                    "in vec2 vUV;\n" +
                    "uniform sampler2D uTex;\n" +
                    "out vec4 FragColor;\n" +
                    "void main() {\n" +
                    "    FragColor = texture(uTex, vUV);\n" +
                    "}\n";

    public static void init(int screenW, int screenH) {
        program = createProgram(VERT, FRAG);
        uPosition = glGetUniformLocation(program, "uPosition");
        uAngle    = glGetUniformLocation(program, "uAngle");
        uSize     = glGetUniformLocation(program, "uSize");
        uScreen   = glGetUniformLocation(program, "uScreen");
        glUseProgram(program);
        glUniform1i(glGetUniformLocation(program, "uTex"), 0);
        setScreenSize(screenW, screenH);

        float[] vertices = {
                -0.5f,  0.5f, 0f, 1f,
                0.5f,  0.5f, 1f, 1f,
                0.5f, -0.5f, 1f, 0f,
                -0.5f, -0.5f, 0f, 0f
        };
        int[] indices = { 0, 1, 2, 2, 3, 0 };

        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        int stride = 4 * Float.BYTES;
        glVertexAttribPointer(0, 2, GL_FLOAT, false, stride, 0L);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, stride, 2L * Float.BYTES);
        glEnableVertexAttribArray(1);

        glBindVertexArray(0);
    }

    public static void setScreenSize(int w, int h) {
        screenW = w;
        screenH = h;
        glUseProgram(program);
        glUniform2f(uScreen, w, h);
    }

    public static int getScreenWidth()  { return screenW; }
    public static int getScreenHeight() { return screenH; }

    public static void draw(int textureId, float x, float y, float w, float h, float angleDeg) {
        glUseProgram(program);
        glUniform2f(uPosition, x, y);
        glUniform2f(uSize, w, h);
        glUniform1f(uAngle, (float) Math.toRadians(angleDeg));

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textureId);

        glBindVertexArray(vao);
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0L);
        glBindVertexArray(0);
    }

    public static void cleanup() {
        glDeleteProgram(program);
        glDeleteBuffers(vbo);
        glDeleteBuffers(ebo);
        glDeleteVertexArrays(vao);
    }

    private static int createProgram(String vs, String fs) {
        int v = compile(GL_VERTEX_SHADER, vs);
        int f = compile(GL_FRAGMENT_SHADER, fs);
        int p = glCreateProgram();
        glAttachShader(p, v);
        glAttachShader(p, f);
        glLinkProgram(p);
        if (glGetProgrami(p, GL_LINK_STATUS) == GL_FALSE)
            throw new RuntimeException("Link hiba: " + glGetProgramInfoLog(p));
        glDeleteShader(v);
        glDeleteShader(f);
        return p;
    }

    private static int compile(int type, String src) {
        int s = glCreateShader(type);
        glShaderSource(s, src);
        glCompileShader(s);
        if (glGetShaderi(s, GL_COMPILE_STATUS) == GL_FALSE)
            throw new RuntimeException("Shader hiba: " + glGetShaderInfoLog(s));
        return s;
    }
}
