package org.KovD.Classes;

import static org.lwjgl.opengl.GL11.*;

public class GameObject {
    protected boolean active = false;

    public void setActive(boolean active) {
        this.active = active;
    }

    public void update(float deltaTime) {
    }
}
