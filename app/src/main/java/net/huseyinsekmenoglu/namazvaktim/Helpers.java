package net.huseyinsekmenoglu.namazvaktim;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by huseyin.sekmenoglu on 17.2.2016.
 * general helper functions
 */
public class Helpers {
    private Context mContext;

    public Helpers(Context tmp) {
        mContext = tmp;
    }

    public static SharedPreferences getSharedPreferences(Context mContext, String str) {
        return mContext.getSharedPreferences(str, 0);
    }

    public boolean HaveNetworkConnection() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

}
