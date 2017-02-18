package study.pmoreira.project2;

public class Team {

    private int goals;
    private int fouls;

    public void incrementGoals() {
        goals++;
    }

    public void decrementGoals() {
        if (goals > 0) {
            goals--;
        }
    }

    public void incrementFouls() {
        fouls++;
    }

    public void decrementFouls() {
        if (fouls > 0) {
            fouls--;
        }
    }

    public int getGoals() {
        return goals;
    }

    public int getFouls() {
        return fouls;
    }

    public void resetScore() {
        goals = 0;
        fouls = 0;
    }
}
