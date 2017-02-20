package study.pmoreira.project3;

public class ScoreNode implements Comparable {

    private String name;
    private int score;

    public ScoreNode(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public void updateScore(int score) {
        this.score += score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(Object o) {
        return ((Integer) ((ScoreNode) o).getScore()).compareTo(score);
    }
}