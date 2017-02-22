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

public class DetailActivity extends AppCompatActivity {

    public static final String COVER_ID = "COVER_ID";
    public static final String ARTIST_DESCRIPTION = "ARTIST_DESCRIPTION";

    @BindView(R.id.detail_artist_description_textview)
    TextView descriptionTextView;

    @BindView(R.id.detail_cover_imageview)
    ImageView coverImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        coverImageView.setImageDrawable(ContextCompat.getDrawable(this, getIntent().getIntExtra(COVER_ID, 0)));
        descriptionTextView.setText(getIntent().getStringExtra(ARTIST_DESCRIPTION));
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
