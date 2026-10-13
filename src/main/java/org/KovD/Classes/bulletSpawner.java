package org.KovD.Classes;

import java.util.ArrayList;
import java.util.List;

public class bulletSpawner extends GameObject {
    private final List<bullet> bulletPool = new ArrayList<>();
    private final entity player;
    private final int textureID;

    public float fireInterval = 0.15f;
    private float cooldown = 0f;

    public bulletSpawner(Scene scene, int poolSize, entity player, List<Enemy> enemies) {
        this.player = player;
        this.textureID = TextureLoader.load("/textures/bullit.png");

        for (int i = 0; i < poolSize; i++) {
            bullet b = new bullet(0, 0, textureID, 600f, "Bullet" + i, enemies);
            b.deactivateBullet();
            bulletPool.add(b);
            scene.addObject(b);
        }
    }

    public void shoot() {
        if (cooldown > 0f) return;

        for (bullet b : bulletPool) {
            if (!b.isActive()) {
                b.activateBullet(player.getPosition().x, player.getPosition().y);
                cooldown = fireInterval;
                return;
            }
        }
    }

    @Override
    public void update(float deltaTime) {
        if (cooldown > 0f) cooldown -= deltaTime;
    }

    @Override
    public void reset() {
        cooldown = 0f;
    }
}