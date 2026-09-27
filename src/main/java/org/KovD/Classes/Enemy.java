package org.KovD.Classes;

public class Enemy extends GameObject {
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

    public void activateEnemy(float spawnX, float spawnY) {
        this.getPosition().x = spawnX;
        this.getPosition().y = spawnY;
        this.active = true;
    }

    public void deactivateEnemy() {
        this.active = false;
    }

    public void setTarget(GameObject target) {
        float dx = target.getPosition().x - this.getPosition().x;
        float dy = target.getPosition().y - this.getPosition().y;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        if (distance > 0) {
            this.dirX = dx / distance;
            this.dirY = dy / distance;
            this.hasTarget = true;
        }
    }

    @Override
    public void update(float deltaTime) {
        if (hasTarget && active) {
            this.getPosition().x += dirX * speed * deltaTime;
            this.getPosition().y += dirY * speed * deltaTime;
        }
    }
}
