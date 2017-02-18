package study.pmoreira.project2;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Team team1 = new Team();
    private Team team2 = new Team();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        blurBackground();
    }

    private void blurBackground() {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;

        ImageView background = (ImageView) findViewById(R.id.image_view_background);
        background.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.background, options));
    }

    public void updateGoals(View v) {
        switch (v.getId()) {
            case R.id.btn_minus_goals_team1:
                team1.decrementGoals();
                break;
            case R.id.btn_plus_goals_team1:
                team1.incrementGoals();
                break;
            case R.id.btn_minus_goals_team2:
                team2.decrementGoals();
                break;
            case R.id.btn_plus_goals_team2:
                team2.incrementGoals();
        }
        updateView();
    }

    public void updateFouls(View v) {
        switch (v.getId()) {
            case R.id.btn_minus_fouls_team1:
                team1.decrementFouls();
                break;
            case R.id.btn_plus_fouls_team1:
                team1.incrementFouls();
                break;
            case R.id.btn_minus_fouls_team2:
                team2.decrementFouls();
                break;
            case R.id.btn_plus_fouls_team2:
                team2.incrementFouls();
                break;
        }
        updateView();
    }

    public void resetScore(View v) {
        team1.resetScore();
        team2.resetScore();

        updateView();
    }

    private void updateView() {
        TextView goalsTeam1 = (TextView) findViewById(R.id.text_view_team1_goals);
        goalsTeam1.setText(String.valueOf(team1.getGoals()));

        TextView goalsTeam2 = (TextView) findViewById(R.id.text_view_team2_goals);
        goalsTeam2.setText(String.valueOf(team2.getGoals()));

        TextView foulsTeam1 = (TextView) findViewById(R.id.text_view_team1_fouls);
        foulsTeam1.setText(String.valueOf(team1.getFouls()));

        TextView foulsTeam2 = (TextView) findViewById(R.id.text_view_team2_fouls);
        foulsTeam2.setText(String.valueOf(team2.getFouls()));
    }
}
