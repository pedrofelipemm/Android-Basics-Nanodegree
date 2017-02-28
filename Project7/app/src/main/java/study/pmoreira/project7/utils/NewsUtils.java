package study.pmoreira.project7.utils;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.internal.Utils;
import study.pmoreira.project7.BuildConfig;
import study.pmoreira.project7.entity.News;

import static study.pmoreira.project7.utils.NetworkUtils.createUrl;
import static study.pmoreira.project7.utils.NetworkUtils.makeHttpGetRequest;

public final class NewsUtils {

    private static final String TAG = Utils.class.getSimpleName();

    private static final String ENDPOINT_SEARCH = "http://content.guardianapis.com/search";

    public static final String DETAULT_SECTION = "technology";
    public static final String DETAULT_ORDER_BY = "newest";
    public static final String DETAULT_PAGE_SIZE = "20";

    private static final String PARAM_API_KEY = "api-key";
    public static final String PARAM_ORDER_BY = "order-by";
    public static final String PARAM_SECTION = "section";
    public static final String PARAM_PAGE_SIZE = "page-size";

    public static List<News> fetchData(Bundle args) {

        String section = args.getString(NewsUtils.PARAM_SECTION);
        String orderBy = args.getString(NewsUtils.PARAM_ORDER_BY);
        String pageSize = args.getString(NewsUtils.PARAM_PAGE_SIZE);

        String requestUrl = Uri.parse(ENDPOINT_SEARCH)
                .buildUpon()
                .appendQueryParameter(PARAM_API_KEY, BuildConfig.THE_GUARDIAN_API_KEY)
                .appendQueryParameter(PARAM_SECTION, section != null ? section : DETAULT_SECTION)
                .appendQueryParameter(PARAM_ORDER_BY, orderBy != null ? orderBy : DETAULT_ORDER_BY)
                .appendQueryParameter(PARAM_PAGE_SIZE, pageSize != null ? pageSize : DETAULT_PAGE_SIZE)
                .toString();

        Log.d(TAG, "fetchData: " + requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpGetRequest(createUrl(requestUrl));
        } catch (IOException e) {
            Log.e(TAG, "Error closing input stream", e);
        }

        return extractEntities(jsonResponse);
    }

    private static List<News> extractEntities(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }

        List<News> news = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray results = jsonObject.getJSONObject("response").getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                news.add(new News(
                        result.getString("webTitle"),
                        result.getString("sectionId"),
                        parseDate(result.getString("webPublicationDate")),
                        result.getString("webUrl")));
            }
        } catch (JSONException e) {
            Log.e(TAG, "Problem parsing the JSON results", e);
        }

        return news;
    }

    private static long parseDate(String stringDate) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", new Locale("PT", "BR")).parse(stringDate.replaceAll
                    ("Z$", "+0000")).getTime();
        } catch (ParseException e) {
            return 0;
        }
    }
}
