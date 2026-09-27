package org.KovD;
import org.KovD.Classes.*;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World");
        if (!glfwInit()) {
            throw new IllegalStateException("No init in my inits");
        }

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        long Window = glfwCreateWindow(640, 480, "Hello World", 0, 0);

        if (Window == 0) {
            throw new IllegalStateException("No window :(");
        }

        GLFWVidMode Video_mode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(Window, (Video_mode.width() - 640) / 2, (Video_mode.height() - 480) / 2);

        glfwShowWindow(Window);

        glfwMakeContextCurrent(Window);
        GL.createCapabilities();
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_TEXTURE_2D);


        glColor3f(1f, 1f, 1f);
        float input = 0;

        Scene scene = new Scene();
        String texPath = "C:\\Java_projekt\\Beadando_Milef\\src\\main\\resources\\textures\\enemy.png";

        GameObject centerObj = new GameObject(0.2f, 0f, texPath);

        Enemy enemy1 = new Enemy(-1.0f, 1.0f, texPath, 100, 0.5f);
        Enemy enemy2 = new Enemy(1.0f, 1.0f, texPath, 100, 0.5f);

        enemy1.setTarget(centerObj);
        enemy2.setTarget(centerObj);

        scene.addObject(centerObj);
        scene.addObject(enemy1);
        scene.addObject(enemy2);

        double lastTime = glfwGetTime();

        glfwSetFramebufferSizeCallback(Window, (long window, int width, int height) -> {
            glViewport(0, 0, width, height);

            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            float ratio = (float) width / (float) height;

            glOrtho(-ratio, ratio, -1f, 1f, -1f, 1f);

            glMatrixMode(GL_MODELVIEW);
        });

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(-(640f/480f), (640f/480f), -1f, 1f, -1f, 1f);
        glMatrixMode(GL_MODELVIEW);

        while(!glfwWindowShouldClose(Window)) {

            double currentTime = glfwGetTime();
            float deltaTime = (float) (currentTime - lastTime);
            lastTime = currentTime;

            if(glfwGetKey(Window, GLFW_KEY_ENTER) == GL_TRUE) {
                input += 0.01;
            }

            if(glfwGetKey(Window, GLFW_KEY_ESCAPE) == GL_TRUE) {
                glfwDestroyWindow(Window);
                break;
            }

            glClear(GL_COLOR_BUFFER_BIT);
            glfwPollEvents();

            scene.sceneLoop(deltaTime);

            glfwSwapBuffers(Window);
        }
        glfwTerminate();
    }
}
