package study.pmoreira.project6.ui.book;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import study.pmoreira.project6.entity.Book;
import study.pmoreira.project6.utils.BookUtils;

class BookLoader extends AsyncTaskLoader<List<Book>> {

    private String mUrl;

    BookLoader(Context context, String url, String queryString) {
        super(context);
        try {
            mUrl = url + URLEncoder.encode(queryString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            mUrl = url + queryString;
        }
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        return BookUtils.fetchData(mUrl);
    }
}
