package org.KovD.Classes;

public class Player extends entity {
    private boolean heavyIsDead = false;

    public Player(float x, float y, String texturePath, float speed, String name) {
        super(x, y, texturePath, name);
    }

    public void followTo(float x, float y) {
        getPosition().x = x;
        getPosition().y = y;
    }

    public void setDead(boolean dead) { this.heavyIsDead = true; }
    public boolean isDead() { return this.heavyIsDead; }
}