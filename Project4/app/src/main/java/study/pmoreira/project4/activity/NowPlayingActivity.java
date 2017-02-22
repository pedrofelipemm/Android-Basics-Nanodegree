package study.pmoreira.project4.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import study.pmoreira.project4.R;

public class NowPlayingActivity extends AppCompatActivity {

    public static final String COVER_ID = "COVER_ID";
    public static final String MUSIC_LENGTH = "MUSIC_LENGTH";

    @BindView(R.id.now_playing_cover_imageview)
    ImageView coverImageView;

    @BindView(R.id.music_length_textview)
    TextView musicLengthTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        coverImageView.setImageDrawable(ContextCompat.getDrawable(this, getIntent().getIntExtra(COVER_ID, 0)));
        musicLengthTextView.setText(formatMusicLength(getIntent().getIntExtra(MUSIC_LENGTH, 0)));
    }

    private String formatMusicLength(int musicLength) {
        int min = musicLength / 60;
        int sec = musicLength % 60;

        return sec > 9 ? String.format("%d:%d", min, sec) : String.format("%d:0%d", min, sec);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
