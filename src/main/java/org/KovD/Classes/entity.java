package org.KovD.Classes;

public class entity extends GameObject {
    protected Vector2 position;
    private int textureId;
    private float angle = 0;
    private float scale = 1f;
    private final int texW, texH;
    private hitbox entityhitbox;

    public entity(float x, float y, int texture, String name) {
        this.position = new Vector2(x, y);
        this.name = name;
        id += 1;
        this.textureId = texture;
        this.texW = TextureLoader.getWidth(textureId);
        this.texH = TextureLoader.getHeight(textureId);
        this.entityhitbox = new hitbox(this, texW / 2f, texW / 2f, texH / 2f);
    }

    public void setPosition(float x, float y) {
        this.position.x = x;
        this.position.y = y;
    }

    public void setScale(float scale) { this.scale = scale; }

    public void Draw() {
        if (!active) return;
        Renderer.draw(textureId, position.x, position.y, texW * scale, texH * scale, angle);
    }

    public Vector2 getPosition() {
        return this.position;
    }
    public hitbox getEntityhitbox() { return this.entityhitbox; }
}
