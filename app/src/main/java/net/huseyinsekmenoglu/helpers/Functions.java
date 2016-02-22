package net.huseyinsekmenoglu.helpers;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import net.huseyinsekmenoglu.database.Database;
import net.huseyinsekmenoglu.database.Vakit;
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
    Database db;
    String today;
    private Activity mActivity;
    private Context mContext;
    private SharedPreferences prefs;

    //constructor
    public Functions(Context tmp) {
        contextor(tmp);
    }

    public Functions(Activity tmp) {
        mActivity = tmp;
        contextor(tmp.getApplicationContext());
    }

    private void contextor(Context tmp) {
        mContext = tmp;
        prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        db = new Database(mContext);
        SimpleDateFormat dfDate = new SimpleDateFormat(mContext.getString(R.string.dateFormat), Locale.ENGLISH);
        today = dfDate.format((new Date()).getTime());//Returns 15/10/2012
        //locales
        String defaulLocale = Locale.getDefault().getLanguage();
        String savedLocale = prefs.getString(mContext.getString(R.string.prefLang), "");
        if (!defaulLocale.equals(savedLocale) && mActivity != null) {
            Locale locale = new Locale(mContext.getString(R.string.defaultLocale));
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            mActivity.getBaseContext().getResources().updateConfiguration(config, mActivity.getBaseContext().getResources().getDisplayMetrics());
        }
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
            //bugünü yeni formata çevir
            Date dayNow = dfDate.parse(dfDate.format((new Date()).getTime()));
            //farkı bul
            diffInDays = (int) ((dayNow.getTime() - updateTime.getTime()) / (1000 * 60 * 60 * 24));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return diffInDays;
    }

    //my advancednotification
    //http://stackoverflow.com/questions/23222063/android-custom-notification-layout-with-remoteviews
    //http://stackoverflow.com/questions/32901922/android-notification-with-custom-xml-layout-not-showing
    public void Notification() {
        int town = Integer.parseInt(prefs.getString(mContext.getString(R.string.prefTownID), mContext.getString(R.string.defaultIlceID)));
        String townName = prefs.getString(mContext.getString(R.string.prefTown), mContext.getString(R.string.Istanbul));
        Vakit tablo = db.getVakit(town, today);
        if (tablo.GetId() == 0) return;//eğer kayıt bulunamadıysa işlemi sonlandır
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(mContext, MainActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        resultIntent.setAction(Intent.ACTION_MAIN);
        // The stack builder object will contain an artificial back stack for the started Activity.
        // This ensures that navigating backward from the Activity leads out of your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        //remoteview
        RemoteViews mContentView = new RemoteViews(mContext.getPackageName(), R.layout.notification);
        mContentView.setTextViewText(R.id.notifyCity, townName);
        mContentView.setTextViewText(R.id.notifyImsak, tablo.GetImsak());
        mContentView.setTextViewText(R.id.notifyGunes, tablo.GetGunes());
        mContentView.setTextViewText(R.id.notifyOgle, tablo.GetOgle());
        mContentView.setTextViewText(R.id.notifyIkindi, tablo.GetIkindi());
        mContentView.setTextViewText(R.id.notifyAksam, tablo.GetAksam());
        mContentView.setTextViewText(R.id.notifyYatsi, tablo.GetYatsi());
        mContentView.setTextViewText(R.id.notifyCity, townName);
        //notification
        NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.drawable.transparent)
                .setOngoing(true)
                .setAutoCancel(false)
                .setVisibility(1)
                .setContent(mContentView);
        mNotificationManager.notify(1, mBuilder.build());

    }

}
