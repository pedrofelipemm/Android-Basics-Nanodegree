package study.pmoreira.project6.ui.book;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import study.pmoreira.project6.R;
import study.pmoreira.project6.entity.Book;
import study.pmoreira.project6.utils.NetworkUtils;

public class BookActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private static int loaderId = 0;

    private static final String QUERY_URL = "https://www.googleapis.com/books/v1/volumes?maxResults=15&q=";

    private EditText mSearchEditTextView;
    private ListView mBookListview;
    private BookAdapter mBookAdapter;
    private TextView mEmptyStateTextView;
    private ProgressBar mProgressbar;
    private View mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity);

        initViews();

        mBookAdapter = new BookAdapter(this, new ArrayList<Book>());

        mBookListview.setAdapter(mBookAdapter);
        mBookListview.setEmptyView(mEmptyStateTextView);

        if (loaderId > 0) {
            getLoaderManager().initLoader(loaderId, null, this);
        }
    }

    private void initViews() {
        mSearchEditTextView = (EditText) findViewById(R.id.search_edittext);
        mBookListview = (ListView) findViewById(R.id.book_list);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        mProgressbar = (ProgressBar) findViewById(R.id.progressbar);
        mContainer = findViewById(R.id.list_container);

        Button searchButton = (Button) findViewById(R.id.search_book);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                showProgressbar();
                if (NetworkUtils.isConnected(BookActivity.this)) {
                    getLoaderManager().initLoader(++loaderId, null, BookActivity.this);
                } else {
                    hideProgressbar();
                    if (mBookAdapter.getCount() > 0) {
                        Toast.makeText(BookActivity.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                    mEmptyStateTextView.setText(R.string.no_internet_connection);
                }
            }
        });
    }

    public void showProgressbar() {
        mProgressbar.setVisibility(View.VISIBLE);
        mContainer.setVisibility(View.GONE);
    }

    public void hideProgressbar() {
        mProgressbar.setVisibility(View.GONE);
        mContainer.setVisibility(View.VISIBLE);
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        return new BookLoader(this, QUERY_URL, mSearchEditTextView.getText().toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> data) {
        mEmptyStateTextView.setText(R.string.no_book);
        hideProgressbar();

        mBookAdapter.clear();
        if (data != null && !data.isEmpty()) {
            mBookAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mBookAdapter.clear();
    }
}
