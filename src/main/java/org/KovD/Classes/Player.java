package org.KovD.Classes;

public class Player extends entity {
    private float speed;

    public Player(float x, float y, String texturePath, float speed) {
        super(x, y, texturePath);
        this.speed = speed;
    }

    public void followTo(float x, float y) {
        getPosition().x = x;
        getPosition().y = y;
    }
}