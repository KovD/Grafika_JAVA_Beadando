package org.KovD;
import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.GLFW.*;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello World");
        if (!glfwInit()) {
            throw new IllegalStateException("No init in my inits");
        }

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        long Window = glfwCreateWindow(640, 480, "Sex", 0, 0);

        if (Window == 0) {
            throw new IllegalStateException("No window :(");
        }

        GLFWVidMode Video_mode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(Window, (Video_mode.width() - 640) / 2, (Video_mode.height() - 480) / 2);

        glfwShowWindow(Window);
        while(!glfwWindowShouldClose(Window)) {
            glfwPollEvents();
        }
        glfwTerminate();
    }
}
