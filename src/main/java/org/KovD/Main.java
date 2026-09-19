package org.KovD;
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
        float input = 0;
        while(!glfwWindowShouldClose(Window)) {
            if(glfwGetKey(Window, GLFW_KEY_ENTER) == GL_TRUE) {
                input += 0.01;
            };

            if(glfwGetKey(Window, GLFW_KEY_ESCAPE) == GL_TRUE) {
                glfwDestroyWindow(Window);
                break;
            };
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT);
            glBegin(GL_QUADS);
                glColor4f(1+input,0+input,0-input,0);
                glVertex2f(-0.5f, 0.5f + input);
                glColor4f(1-input,1,0+input,0+input);
                glVertex2f(0.5f, 0.5f);
                glColor4f(1-input,0,1,0+input);
                glVertex2f(0.5f-input, -0.5f-input);
                glColor4f(1+input,0-input,1,0+input);
                glVertex2f(-0.5f, -0.5f+input);
            glEnd();
            glfwSwapBuffers(Window);
        }
        glfwTerminate();
    }
}
