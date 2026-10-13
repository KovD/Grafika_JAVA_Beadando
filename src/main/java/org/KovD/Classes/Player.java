package org.KovD.Classes;

public class Player extends entity {
    private boolean heavyIsDead = false;
    private Vector2 startPos;

    public Player(float x, float y, int textureID, float speed, String name) {
        super(x, y, textureID, name);
        startPos = new Vector2(x,y);
    }

    public void followTo(float x, float y) {
        getPosition().x = x;
        getPosition().y = y;
    }

    public void setDead(boolean dead) { this.heavyIsDead = dead; }
    public boolean isDead() { return this.heavyIsDead; }

    @Override
    public void reset(){
        setDead(false);
        position = startPos;
    }
}