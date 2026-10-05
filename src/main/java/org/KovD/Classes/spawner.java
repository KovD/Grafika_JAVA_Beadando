package org.KovD.Classes;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import java.util.ArrayList;
import java.util.List;

public class spawner extends GameObject {
    private List<Enemy> enemyPool;

    public boolean canSpawn = true;
    public int spawnAtTheTime = 1;
    public float spawnInterval = 1.5f;
    private float timer = 0.0f;
    private entity PlayerTraget;
    private int y_start = -10;
    private Score score;
    public float minInterval = 0.3f;
    public float intervalDecay = 0.02f;
    public float spawn_number_inc = 0.01f;

    public spawner(Scene scene, int poolSize, entity Target, Score score) {
        this.enemyPool = new ArrayList<>();
        this.PlayerTraget = Target;
        this.score = score;
        String path = "C:\\Java_projekt\\Beadando_Milef\\src\\main\\resources\\textures\\enemy.png";

        for (int i = 0; i < poolSize; i++) {
            Enemy enemy = new Enemy(0, 0, path, 10, 160f, "Enemy" + i, score);
            enemy.deactivateEnemy();
            enemyPool.add(enemy);
            scene.addObject(enemy);
        }
    }

    public void update(float deltaTime) {
        if (!active) return;
        if (!canSpawn) return;

        timer += deltaTime;

        float interval = Math.max(minInterval, spawnInterval - score.getScore() * intervalDecay);
        spawnAtTheTime = 1 + (int)(score.getScore() * spawn_number_inc);
        if (timer >= interval) {
            spawnEnemies(spawnAtTheTime);
            timer = 0.0f;
        }
    }

    private void spawnEnemies(int count) {
        float screenW = Renderer.getScreenWidth();

        int spawned = 0;
        for (Enemy enemy : enemyPool) {
            if (!enemy.isActive()) {
                float randomX = (float) (Math.random() * screenW);
                enemy.activateEnemy(randomX, y_start, this.PlayerTraget);
                spawned++;
                if (spawned >= count) break;
            }
        }
    }
}
