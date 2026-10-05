package org.KovD.Classes;

public class entity extends GameObject {
    private Vector2 position;
    private int textureId;
    private float angle = 0;
    private float scale = 1f;
    private final int texW, texH;

    public entity(float x, float y, String TexturePath) {
        this.position = new Vector2(x, y);
        this.textureId = TextureLoader.load(TexturePath);
        this.texW = TextureLoader.getWidth(textureId);
        this.texH = TextureLoader.getHeight(textureId);
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
}
