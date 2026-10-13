package org.KovD.Classes;

public class Score extends GameObject {
    private int score;
    private float calculator = 0f;

    public int getScore() {
        return score;
    }


    public void inc_score() {
        score++;
    }

    @Override
    public void update(float deltaTime) {
        calculator += deltaTime;

        if (calculator >= 1f) {
            calculator -= 1f;
            inc_score();
        }
    }

    @Override
    public void reset() {
        score = 0;
    }
}