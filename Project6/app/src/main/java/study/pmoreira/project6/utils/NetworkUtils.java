package study.pmoreira.project6.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static android.content.Context.CONNECTIVITY_SERVICE;

public final class NetworkUtils {

    private NetworkUtils() {
    }

    public static boolean isConnected(Context context) {
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }
}
