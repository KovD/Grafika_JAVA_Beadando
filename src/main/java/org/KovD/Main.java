package org.KovD;

import org.KovD.Classes.*;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Main {

    public static void main(String[] args) {

        if (!glfwInit()) {
            throw new IllegalStateException("No init in my inits");
        }

        WindowInitializer windowManager = new WindowInitializer();

        long Window = windowManager.CreateWindow();
        if (Window == 0) {
            throw new IllegalStateException("No window :(");
        }

        GLFWVidMode Video_mode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(Window, (Video_mode.width() - windowManager.getWindowWidth()) / 2, (Video_mode.height() - windowManager.getWindowHeight()) / 2);

        windowManager.initalizeWindow(Window);

        GL.createCapabilities();
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        int[] fbW = new int[1];
        int[] fbH = new int[1];
        glfwGetFramebufferSize(Window, fbW, fbH);
        glViewport(0, 0, fbW[0], fbH[0]);
        Renderer.init(fbW[0], fbH[0]);

        Scene scene = new Scene();
        String texPath = "C:\\Java_projekt\\Beadando_Milef\\src\\main\\resources\\textures\\enemy.png";
        String playerTextPath = "C:\\Java_projekt\\Beadando_Milef\\src\\main\\resources\\textures\\player.png";

        Player player = new Player(fbW[0] / 2f, fbH[0] / 2f, playerTextPath, 3f);
        player.setActive(true);

        spawner entitySpawner = new spawner(scene, 20, Window, player);
        entitySpawner.setActive(true);

        scene.addObject(player);
        scene.addObject(entitySpawner);

        glfwSetFramebufferSizeCallback(Window, (long window, int width, int height) -> {
            glViewport(0, 0, width, height);
            Renderer.setScreenSize(width, height);
        });

        double[] mx = new double[1];
        double[] my = new double[1];
        int[] w = new int[1];
        int[] h = new int[1];

        double lastTime = glfwGetTime();

        while (!glfwWindowShouldClose(Window)) {
            double currentTime = glfwGetTime();
            float deltaTime = (float) (currentTime - lastTime);
            lastTime = currentTime;

            if (glfwGetKey(Window, GLFW_KEY_ESCAPE) == GLFW_PRESS) {
                glfwSetWindowShouldClose(Window, true);
                break;
            }

            glfwPollEvents();

            glfwGetCursorPos(Window, mx, my);
            glfwGetWindowSize(Window, w, h);
            glfwGetFramebufferSize(Window, fbW, fbH);
            if (w[0] > 0 && h[0] > 0) {
                float worldX = (float) (mx[0] * fbW[0] / w[0]);
                float worldY = (float) (my[0] * fbH[0] / h[0]);
                player.followTo(worldX, worldY);
            }

            glClear(GL_COLOR_BUFFER_BIT);
            scene.sceneLoop(deltaTime);

            int err = glGetError();
            if (err != 0) System.out.println("GL hiba: 0x" + Integer.toHexString(err));

            glfwSwapBuffers(Window);
        }

        Renderer.cleanup();
        glfwDestroyWindow(Window);
        glfwTerminate();
    }
}