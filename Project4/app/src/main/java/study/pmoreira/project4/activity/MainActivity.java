package study.pmoreira.project4.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import study.pmoreira.project4.R;
import study.pmoreira.project4.adapter.LibraryAdapter;
import study.pmoreira.project4.adapter.SimpleDividerItemDecoration;
import study.pmoreira.project4.utils.TracksUtils;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.track_list_recyclerview)
    RecyclerView trackListRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        trackListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        trackListRecyclerView.setAdapter(new LibraryAdapter(this, TracksUtils.fetchTracks(this)));
        trackListRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
    }

}
