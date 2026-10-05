package org.KovD;

import org.KovD.Classes.*;
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

        windowManager.initalizeWindow(Window);

        GL.createCapabilities();
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        int[] fbW = new int[1];
        int[] fbH = new int[1];
        glfwGetFramebufferSize(Window, fbW, fbH);
        glViewport(0, 0, fbW[0], fbH[0]);

        Renderer.init(fbW[0], fbH[0]);
        score_renderer scoreRend = new score_renderer();
        scoreRend.init(Window);
        Scene scene = new Scene();
        PlayerBuilder playerBuilder = new PlayerBuilder();
        Player player = playerBuilder.CreateMySexyPlayer(fbW[0] / 2f, fbH[0] / 2f);
        Score score = new Score();
        spawner entitySpawner = new spawner(scene, 10000, player, score);
        entitySpawner.setActive(true);
        scene.addObject(score);
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
            if(player.isDead()){
                glfwSetWindowShouldClose(Window, true);
                break;
            }
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

            scoreRend.render_score(score);
            int err = glGetError();
            if (err != 0) System.out.println("GL hiba: 0x" + Integer.toHexString(err));

            glfwSwapBuffers(Window);
        }

        Renderer.cleanup();
        glfwDestroyWindow(Window);
        glfwTerminate();
    }
}