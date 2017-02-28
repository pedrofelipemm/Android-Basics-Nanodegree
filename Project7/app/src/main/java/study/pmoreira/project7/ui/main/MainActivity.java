package study.pmoreira.project7.ui.main;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import study.pmoreira.project7.R;
import study.pmoreira.project7.entity.News;
import study.pmoreira.project7.ui.settings.SettingsActivity;
import study.pmoreira.project7.utils.NetworkUtils;
import study.pmoreira.project7.utils.NewsUtils;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private static final int LOADER_ID = 0;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.progressbar)
    ProgressBar mProgressBar;

    @BindView(R.id.message_textview)
    TextView mMessageTextView;

    @BindView(R.id.message_view)
    View mMessageView;

    @BindView(R.id.container)
    SwipeRefreshLayout mContainer;

    @BindView(R.id.try_again_button)
    Button tryAgainButton;

    private NewsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAdapter = new NewsAdapter(this, new ArrayList<News>());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        getLoaderManager().initLoader(LOADER_ID, getPreferenceBundle(), this);

        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);

        mContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLoaderManager().restartLoader(LOADER_ID, getPreferenceBundle(), MainActivity.this);
                mContainer.setRefreshing(false);
            }
        });

        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressbar();
                getLoaderManager().restartLoader(LOADER_ID,getPreferenceBundle(), MainActivity.this);
            }
        });
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        showProgressbar();
        return new NewsLoader(MainActivity.this, args);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        mAdapter.clear();
        hideProgressbar();
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        } else if (!NetworkUtils.isConnected(MainActivity.this)) {
            showMessage(getString(R.string.no_connection));
        } else {
            showMessage(getString(R.string.no_data_available));
        }
    }

    private void showMessage(String msg) {
        mMessageView.setVisibility(View.VISIBLE);
        mMessageTextView.setText(msg);
        mContainer.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mAdapter.clear();
    }

    private void showProgressbar() {
        mProgressBar.setVisibility(View.VISIBLE);
        if (mAdapter.getItemCount() < 1) {
            mContainer.setVisibility(View.GONE);
        }
        mMessageView.setVisibility(View.GONE);
    }

    private void hideProgressbar() {
        mProgressBar.setVisibility(View.GONE);
        mContainer.setVisibility(View.VISIBLE);
        mMessageView.setVisibility(View.GONE);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        mAdapter.clear();

        Bundle bundle = getPreferenceBundle();
        switch (key) {
            case NewsUtils.PARAM_PAGE_SIZE:
                bundle.putString(key, sharedPreferences.getString(key, NewsUtils.DETAULT_PAGE_SIZE));
                break;
            case NewsUtils.PARAM_ORDER_BY:
                bundle.putString(key, sharedPreferences.getString(key, NewsUtils.DETAULT_ORDER_BY));
                break;
            case NewsUtils.PARAM_SECTION:
                bundle.putString(key, sharedPreferences.getString(key, NewsUtils.DETAULT_SECTION));
                break;
        }

        getLoaderManager().restartLoader(LOADER_ID, bundle, this);
    }

    public Bundle getPreferenceBundle() {
        SharedPreferences defaultSharedPreference = getDefaultSharedPreference();

        Bundle bundle = new Bundle();

        String orderBy = defaultSharedPreference.getString(NewsUtils.PARAM_ORDER_BY, NewsUtils.DETAULT_ORDER_BY);
        bundle.putString(NewsUtils.PARAM_ORDER_BY, orderBy);

        String section = defaultSharedPreference.getString(NewsUtils.PARAM_SECTION, NewsUtils.DETAULT_SECTION);
        bundle.putString(NewsUtils.PARAM_SECTION, section);

        String pageSize = defaultSharedPreference.getString(NewsUtils.PARAM_PAGE_SIZE, NewsUtils.DETAULT_PAGE_SIZE);
        bundle.putString(NewsUtils.PARAM_PAGE_SIZE, pageSize);

        return bundle;
    }

    private SharedPreferences getDefaultSharedPreference() {
        return getSharedPreferences(this.getPackageName() + "_preferences", MODE_PRIVATE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
