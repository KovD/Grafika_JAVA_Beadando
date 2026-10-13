package org.KovD.Classes;

import java.util.List;

public class bullet extends entity {
    private static final float MARGIN = 15f;

    private final float speed;
    private final List<Enemy> enemies;

    public bullet(float x, float y, int textureID, float speed, String name, List<Enemy> enemies) {
        super(x, y, textureID, name);
        this.speed = speed;
        this.enemies = enemies;
    }

    public void activateBullet(float spawnX, float spawnY) {
        setPosition(spawnX, spawnY);
        this.active = true;
    }

    public boolean isActive() {
        return this.active;
    }

    public void deactivateBullet() {
        this.active = false;
    }

    @Override
    public void update(float deltaTime) {
        if (!active) return;
        getPosition().y -= speed * deltaTime;

        hitEnemy();

        if (getPosition().y < -MARGIN) {
            deactivateBullet();
        }
    }

    public void hitEnemy() {
        for (Enemy enemy : enemies) {
            if (enemy.isActive() && getEntityhitbox().intersects(enemy.getEntityhitbox())) {
                enemy.deactivateEnemy();
                deactivateBullet();
                return;
            }
        }
    }

    @Override
    public void reset() {
        deactivateBullet();
    }
}