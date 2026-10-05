package org.KovD.Classes;

public class hitbox {
    private entity parent;
    private float radius;
    private float offsetX;
    private float offsetY;

    public hitbox(entity parent, float radius, float offsetX, float offsetY) {
        this.parent = parent;
        this.radius = radius;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public float getCenterX() {
        return parent.getPosition().x + offsetX;
    }

    public float getCenterY() {
        return parent.getPosition().y + offsetY;
    }

    public float getRadius() {
        return radius;
    }

    public boolean intersects(hitbox other) {
        float dx = this.getCenterX() - other.getCenterX();
        float dy = this.getCenterY() - other.getCenterY();
        float distanceSquared = (dx * dx) + (dy * dy);
        float radiusSum = this.getRadius() + other.getRadius();

        return distanceSquared <= (radiusSum * radiusSum);
    }
}
