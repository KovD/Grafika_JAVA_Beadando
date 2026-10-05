package org.KovD.Classes;

public class Enemy extends entity {
    private int health;
    private float speed;
    private Vector2 target;
    private float dirX = 0;
    private float dirY = 0;
    private boolean hasTarget = false;

    public Enemy(float x, float y, String texturePath, int health, float speed) {
        super(x, y, texturePath);
        this.health = health;
        this.speed = speed;
    }

    public void activateEnemy(float spawnX, float spawnY, entity target) {
        this.getPosition().x = spawnX;
        this.getPosition().y = spawnY;
        setTarget(target);
        this.active = true;
    }

    public boolean isActive() {
        return this.active;
    }

    public void deactivateEnemy() {
        this.active = false;
    }

    public void setTarget(entity target) {
        float dx = target.getPosition().x - this.getPosition().x;
        float dy = target.getPosition().y - this.getPosition().y;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        if (distance > 0) {
            this.dirX = dx / distance;
            this.dirY = dy / distance;
            this.hasTarget = true;
        }
    }

    private static final float MARGIN = 100f;

    @Override
    public void update(float deltaTime) {
        if (hasTarget && active) {
            this.getPosition().x += dirX * speed * deltaTime;
            this.getPosition().y += dirY * speed * deltaTime;
            float x = this.getPosition().x;
            float y = this.getPosition().y;

            if (x < -MARGIN || x > Renderer.getScreenWidth() + MARGIN
                    || y < -MARGIN || y > Renderer.getScreenHeight() + MARGIN) {
                deactivateEnemy();
            }
        }
    }
}
