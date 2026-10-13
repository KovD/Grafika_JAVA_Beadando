package org.KovD.Classes;

public class Overlay extends entity {
    private static final String TEXTURE_PATH =
            "/textures/bg.png";
    private static int cachedTexture = -1;
    private final int layerTexture;

    private static int texture() {
        if (cachedTexture == -1) {
            cachedTexture = TextureLoader.load(TEXTURE_PATH);
        }
        return cachedTexture;
    }

    public Overlay(String name) {
        super(Renderer.getScreenWidth() / 2f, Renderer.getScreenHeight() / 2f, texture(), name);
        this.layerTexture = texture();
        this.active = true;
    }

    @Override
    public void update(float deltaTime) {
        setPosition(Renderer.getScreenWidth() / 2f, Renderer.getScreenHeight() / 2f);
    }

    @Override
    public void Draw() {
        if (!active) return;
        Renderer.draw(layerTexture,
                Renderer.getScreenWidth() / 2f,
                Renderer.getScreenHeight() / 2f,
                Renderer.getScreenWidth(),
                Renderer.getScreenHeight(),
                0f);
    }

    @Override
    public void reset() { }
}