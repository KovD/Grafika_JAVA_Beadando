package org.KovD.Classes;

import imgui.ImGui;
import imgui.flag.ImGuiWindowFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import imgui.flag.ImGuiConfigFlags;

public class score_renderer {

    private ImGuiImplGl3 image_gui_GL3;
    private ImGuiImplGlfw image_gui_GLFW;

    public void init(long Window){
        ImGui.createContext();
        ImGui.getIO().addConfigFlags(ImGuiConfigFlags.NoMouseCursorChange);
        image_gui_GLFW = new ImGuiImplGlfw();
        image_gui_GL3 = new ImGuiImplGl3();
        image_gui_GLFW.init(Window, true);
        image_gui_GL3.init("#version 130");
    }

    public void render_score(Score score){
        image_gui_GL3.newFrame();
        image_gui_GLFW.newFrame();
        ImGui.newFrame();

        ImGui.setNextWindowPos(10, 10);
        ImGui.begin("Score",
                ImGuiWindowFlags.NoDecoration
                        | ImGuiWindowFlags.NoBackground
                        | ImGuiWindowFlags.NoMove
                        | ImGuiWindowFlags.NoInputs
                        | ImGuiWindowFlags.NoSavedSettings
                        | ImGuiWindowFlags.AlwaysAutoResize);
        ImGui.setWindowFontScale(2f);
        ImGui.text("Score: " + score.getScore());
        ImGui.end();

        ImGui.render();
        image_gui_GL3.renderDrawData(ImGui.getDrawData());
    }

    public void shutdown(){
        ImGui.destroyContext();
        image_gui_GL3.shutdown();
        image_gui_GLFW.shutdown();
    }
}


