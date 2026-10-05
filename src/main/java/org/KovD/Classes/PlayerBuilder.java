package org.KovD.Classes;

public class PlayerBuilder {
    private String textPath = "C:\\Java_projekt\\Beadando_Milef\\src\\main\\resources\\textures\\player.png";
    private float speed = 3f;

    public Player CreateMySexyPlayer(float startX, float startY) {
        Player player = new Player(startX, startY, textPath, speed, "Player");
        player.setActive(true);
        return player;
    }
}