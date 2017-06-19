package ay3524.com.flags.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Ashish on 19-06-2017.
 */

public class Constants {
    public static final String BASE_URI = "http://www.androidbegin.com/tutorial/";

    public static final String FLAG_URL_INTENT_STRING = "FLAG_URL";
    public static final String COUNTRY_INTENT_STRING = "COUNTRY";
    public static final String RANK_INTENT_STRING = "RANK";
    public static final String POPULATION_INTENT_STRING = "POPULATION";

    public static boolean isConnected(Context context){
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}