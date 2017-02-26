package study.pmoreira.quakereport.ui.earthquake;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import study.pmoreira.quakereport.R;
import study.pmoreira.quakereport.utils.QueryUtils;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG = EarthquakeActivity.class.getName();

    @BindView(R.id.list)
    ListView earthquakeListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        ButterKnife.bind(this);

        earthquakeListView.setAdapter(new EarthquakeAdapter(this, QueryUtils.extractEarthquakes()));
    }
}
