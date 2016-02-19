package net.huseyinsekmenoglu.helpers;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import net.huseyinsekmenoglu.namazvaktim.ApiConnect;
import net.huseyinsekmenoglu.namazvaktim.MainActivity;
import net.huseyinsekmenoglu.namazvaktim.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by huseyin.sekmenoglu on 17.2.2016.
 * general helper functions
 */
public class Functions {
    private Activity mActivity;
    private Context mContext;
    private SharedPreferences prefs;

    //constructor
    public Functions(Context tmp) {
        mContext = tmp;
        prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public Functions(Activity tmp) {
        mActivity = tmp;
        mContext = tmp.getApplicationContext();
        prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    //get shared preferences
    public static SharedPreferences getPreferences(Context mContext, String str) {
        return mContext.getSharedPreferences(str, 0);
    }

    //find if has network connection
    public boolean HaveNetworkConnection() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    //update procedure
    public void UpdatevakitTable() {
        //get preferences
        String countryID = prefs.getString(mContext.getString(R.string.prefCountryID), mContext.getString(R.string.defaultUlkeID)),
                cityID = prefs.getString(mContext.getString(R.string.prefCityID), mContext.getString(R.string.defaultSehirID)),
                townID = prefs.getString(mContext.getString(R.string.prefTownID), mContext.getString(R.string.defaultIlceID)),
                updateLink;
        if (countryID.equals(cityID)) updateLink = countryID + "/" + townID;
        else updateLink = countryID + "/" + cityID + "/" + townID;
        new ApiConnect(mActivity).execute(updateLink, townID);
    }

    //bugün  ile girilen gün arasındaki fark
    public int getDayDifference(Date updateTime) {
        int diffInDays = 0;
        try {
            //01.01.2016 şeklinde bir tarih formatı yap
            SimpleDateFormat dfDate = new SimpleDateFormat(mContext.getString(R.string.dateFormat), Locale.ENGLISH);
            //gelen tarihi yeni formata çevir
            updateTime = dfDate.parse(dfDate.format(updateTime));
            //bugğnğ yeni formata çevir
            Date dayNow = dfDate.parse(dfDate.format((new Date()).getTime()));
            //farkı bul
            diffInDays = (int) ((dayNow.getTime() - updateTime.getTime()) / (1000 * 60 * 60 * 24));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return diffInDays;
    }

    //my advancednotification
    public void Notification() {
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(mContext, MainActivity.class);
        // The stack builder object will contain an artificial back stack for the started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        //notification
        NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mContext)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!")
                        .setOngoing(true)
                        .setAutoCancel(false)
                        .setContentIntent(resultPendingIntent);
        mNotificationManager.notify(1, mBuilder.build());
    }

}
