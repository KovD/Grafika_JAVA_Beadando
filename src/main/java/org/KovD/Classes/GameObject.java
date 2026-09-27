package org.KovD.Classes;

import static org.lwjgl.opengl.GL11.*;

public class GameObject {
    private Vector2 position;
    private int textureId;
    private float angle = 0;
    protected boolean active = false;

    public GameObject(float x, float y, String TexturePath) {
        this.position = new Vector2(x, y);
        this.textureId = TextureLoader.load(TexturePath);
    }

    public Vector2 getPosition() {
        return this.position;
    }

    public void setPosition(float x, float y){
        this.position.x = x;
        this.position.y = y;
    }


    public void Draw() {
        if (!active) return;
        glBindTexture(GL_TEXTURE_2D, textureId);

        glPushMatrix();
        glTranslatef(position.x, position.y, 0);
        glRotatef(angle, 0, 0, 1);
        glScalef(0.2f, 0.2f, 1.0f);

        glBegin(GL_QUADS);
            glTexCoord2f(0, 1); glVertex2f(-0.5f,  0.5f);
            glTexCoord2f(1, 1); glVertex2f( 0.5f,  0.5f);
            glTexCoord2f(1, 0); glVertex2f( 0.5f, -0.5f);
            glTexCoord2f(0, 0); glVertex2f(-0.5f, -0.5f);
        glEnd();

        glPopMatrix();
    }

    public void update(float deltaTime) {
    }
}
