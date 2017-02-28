package study.pmoreira.project7.ui.main;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;

import java.util.List;

import study.pmoreira.project7.entity.News;
import study.pmoreira.project7.utils.NewsUtils;

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private Bundle mArgs;

    public NewsLoader(Context context, Bundle args) {
        super(context);
        mArgs = args;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        return NewsUtils.fetchData( mArgs);
    }
}
