package org.KovD.Classes;

public class PlayerBuilder {
    private int textureID = TextureLoader.load("/textures/player.png");
    private float speed = 3f;

    public Player CreateMySexyPlayer(float startX, float startY) {
        Player player = new Player(startX, startY, textureID, speed, "Player");
        player.setActive(true);
        return player;
    }
}