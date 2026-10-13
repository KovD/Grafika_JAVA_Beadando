package org.KovD.Classes;

public class Enemy extends entity {
    private int health;
    private float speed;
    private float basespeed;
    private float dirX = 0;
    private float dirY = 0;
    private boolean hasTarget = false;
    private static final float MARGIN = 15f;
    private Score score;
    private entity target;

    public Enemy(float x, float y, int textureID, int health, float speed, String name,Score score) {
        super(x, y, textureID, name);
        this.health = health;
        this.basespeed = speed;
        this.speed = basespeed;
        this.score = score;
    }

    public void activateEnemy(float spawnX, float spawnY, entity target) {
        calc_speed();
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
        speed = basespeed;
    }

    public void setTarget(entity target) {
        this.target = target;
        float dx = target.getPosition().x - this.getPosition().x;
        float dy = target.getPosition().y - this.getPosition().y;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        if (distance > 0) {
            this.dirX = dx / distance;
            this.dirY = dy / distance;
            this.hasTarget = true;
        }
    }

    public void calc_speed() {
        if (this.score != null) {
            int maxExtraSpeed = this.score.getScore() / 2;
            this.speed = this.speed + (float)(Math.random() * maxExtraSpeed);
        }
    }

    @Override
    public void update(float deltaTime) {
        if (hasTarget && active) {
            isTheHeavyDead();
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

    public void isTheHeavyDead(){
        if (this.getEntityhitbox().intersects(target.getEntityhitbox())) {
            if (target instanceof Player) {
                ((Player) target).setDead(true);
            }
        }
    }

    @Override
    public void reset(){
        deactivateEnemy();
    }
}
