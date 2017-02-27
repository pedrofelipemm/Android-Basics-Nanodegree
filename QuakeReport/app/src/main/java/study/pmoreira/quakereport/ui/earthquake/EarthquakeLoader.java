package study.pmoreira.quakereport.ui.earthquake;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

import study.pmoreira.quakereport.entity.Earthquake;
import study.pmoreira.quakereport.utils.EarthquakeUtils;

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    private static final String LOG = EarthquakeLoader.class.getName();

    private String mUrl;

    public EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        return EarthquakeUtils.fetchEarthquakeData(mUrl);
    }
}
