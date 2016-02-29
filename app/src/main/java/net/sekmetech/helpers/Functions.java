package net.sekmetech.helpers;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.format.DateUtils;
import android.widget.RemoteViews;

import net.sekmetech.database.Database;
import net.sekmetech.database.DatabaseUpdate;
import net.sekmetech.database.Vakit;
import net.sekmetech.database.VakitAsDate;
import net.sekmetech.namazvaktim.MainActivity;
import net.sekmetech.namazvaktim.R;
import net.sekmetech.namazvaktim.SettingsActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by huseyin.sekmenoglu on 17.2.2016.
 * general helper functions
 */
public class Functions {
    private Database db;
    private Activity mActivity;
    private Context mContext;
    private SharedPreferences prefs;
    private AlarmManager service;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    private RemoteViews mContentView;
    private String today;
    private int notifyID = 1;
    private Date now = new Date(), imsak = now, gunes = now, ogle = now, ikindi = now, aksam = now, yatsi = now;


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
        //set alarm manager
        service = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        //locales
        String defaulLocale = Locale.getDefault().getLanguage();
        String savedLocale = prefs.getString(mContext.getString(R.string.prefLang), "");
        if (!defaulLocale.equals(savedLocale)) {
            Locale locale = new Locale(mContext.getString(R.string.defaultLocale));
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            mContext.getResources().updateConfiguration(config, mContext.getResources().getDisplayMetrics());
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
        if (!HaveNetworkConnection()) return;
        //get preferences
        String countryID = prefs.getString(mContext.getString(R.string.pref1CountryID), mContext.getString(R.string.defaultUlkeID)),
                cityID = prefs.getString(mContext.getString(R.string.pref1CityID), mContext.getString(R.string.defaultSehirID)),
                townID = prefs.getString(mContext.getString(R.string.pref1TownID), mContext.getString(R.string.defaultIlceID)),
                updateLink;
        if (countryID.equals(cityID)) updateLink = countryID + "/" + townID;
        else updateLink = countryID + "/" + cityID + "/" + townID;
        new DatabaseUpdate(mActivity).execute(updateLink, townID);
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

    //aktif olan vakti bul
    public int getActiveVakit(VakitAsDate tablo) {
        int result;
        Date now = new Date(),
                imsak = tablo.GetImsak(),
                gunes = tablo.GetGunes(),
                ogle = tablo.GetOgle(),
                ikindi = tablo.GetIkindi(),
                aksam = tablo.GetAksam(),
                yatsi = tablo.GetYatsi();
        String remaining = DateUtils.formatElapsedTime((now.getTime() - yatsi.getTime()) / 1000);
        if (!remaining.contains(mContext.getString(R.string.minus)))
            result = R.string.vakit_yatsi;
        else {
            remaining = DateUtils.formatElapsedTime((now.getTime() - aksam.getTime()) / 1000);
            if (!remaining.contains(mContext.getString(R.string.minus)))
                result = R.string.vakit_aksam;
            else {
                remaining = DateUtils.formatElapsedTime((now.getTime() - ikindi.getTime()) / 1000);
                if (!remaining.contains(mContext.getString(R.string.minus)))
                    result = R.string.vakit_ikindi;
                else {
                    remaining = DateUtils.formatElapsedTime((now.getTime() - ogle.getTime()) / 1000);
                    if (!remaining.contains(mContext.getString(R.string.minus)))
                        result = R.string.vakit_ogle;
                    else {
                        remaining = DateUtils.formatElapsedTime((now.getTime() - gunes.getTime()) / 1000);
                        if (!remaining.contains(mContext.getString(R.string.minus)))
                            result = R.string.vakit_gunes;
                        else {
                            remaining = DateUtils.formatElapsedTime((now.getTime() - imsak.getTime()) / 1000);
                            if (!remaining.contains(mContext.getString(R.string.minus)))
                                result = R.string.vakit_imsak;
                            else result = R.string.vakit_yatsi;
                        }
                    }
                }
            }
        }
        return result;
    }

    //my advancednotification
    //http://stackoverflow.com/questions/23222063/android-custom-notification-layout-with-remoteviews
    //http://stackoverflow.com/questions/32901922/android-notification-with-custom-xml-layout-not-showing
    public void Notification() {
        int town = Integer.parseInt(prefs.getString(mContext.getString(R.string.pref1TownID), mContext.getString(R.string.defaultIlceID)));
        String townName = prefs.getString(mContext.getString(R.string.pref1Town), mContext.getString(R.string.Istanbul));
        Vakit tablo = db.getVakit(town, today);
        if (tablo.GetId() == 0) return;//eğer kayıt bulunamadıysa işlemi sonlandır
        String tblImsak = tablo.GetImsak(),
                tblGunes = tablo.GetGunes(),
                tblOgle = tablo.GetOgle(),
                tblIkindi = tablo.GetIkindi(),
                tblAksam = tablo.GetAksam(),
                tblYatsi = tablo.GetYatsi();
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(mContext, MainActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT |
                Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NO_ANIMATION);
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
        mContentView = new RemoteViews(mContext.getPackageName(), R.layout.notification);
        mContentView.setTextViewText(R.id.notifyCity, townName);
        mContentView.setTextViewText(R.id.notifyImsak, tblImsak);
        mContentView.setTextViewText(R.id.notifyGunes, tblGunes);
        mContentView.setTextViewText(R.id.notifyOgle, tblOgle);
        mContentView.setTextViewText(R.id.notifyIkindi, tblIkindi);
        mContentView.setTextViewText(R.id.notifyAksam, tblAksam);
        mContentView.setTextViewText(R.id.notifyYatsi, tblYatsi);
        mContentView.setTextViewText(R.id.notifyCity, townName);
        //reset texts
        mContentView.setTextViewText(R.id.notifyLblTimeLeft, mContext.getString(R.string.vakit_left));
        mContentView.setTextViewText(R.id.notifyLblImsak, mContext.getString(R.string.vakit_imsak));
        mContentView.setTextViewText(R.id.notifyLblGunes, mContext.getString(R.string.vakit_gunes));
        mContentView.setTextViewText(R.id.notifyLblOgle, mContext.getString(R.string.vakit_ogle));
        mContentView.setTextViewText(R.id.notifyLblIkindi, mContext.getString(R.string.vakit_ikindi));
        mContentView.setTextViewText(R.id.notifyLblAksam, mContext.getString(R.string.vakit_aksam));
        mContentView.setTextViewText(R.id.notifyLblYatsi, mContext.getString(R.string.vakit_yatsi));
        //reset colors
        mContentView.setTextColor(R.id.notifyLblYatsi, Color.BLACK);
        mContentView.setTextColor(R.id.notifyYatsi, Color.BLACK);
        mContentView.setTextColor(R.id.notifyLblAksam, Color.BLACK);
        mContentView.setTextColor(R.id.notifyAksam, Color.BLACK);
        mContentView.setTextColor(R.id.notifyLblIkindi, Color.BLACK);
        mContentView.setTextColor(R.id.notifyIkindi, Color.BLACK);
        mContentView.setTextColor(R.id.notifyLblOgle, Color.BLACK);
        mContentView.setTextColor(R.id.notifyOgle, Color.BLACK);
        mContentView.setTextColor(R.id.notifyLblGunes, Color.BLACK);
        mContentView.setTextColor(R.id.notifyGunes, Color.BLACK);
        mContentView.setTextColor(R.id.notifyLblImsak, Color.BLACK);
        mContentView.setTextColor(R.id.notifyImsak, Color.BLACK);
        mContentView.setInt(R.id.notifyLblYatsi, "setBackgroundColor", 0);
        mContentView.setInt(R.id.notifyYatsi, "setBackgroundColor", 0);
        mContentView.setInt(R.id.notifyLblAksam, "setBackgroundColor", 0);
        mContentView.setInt(R.id.notifyAksam, "setBackgroundColor", 0);
        mContentView.setInt(R.id.notifyLblIkindi, "setBackgroundColor", 0);
        mContentView.setInt(R.id.notifyIkindi, "setBackgroundColor", 0);
        mContentView.setInt(R.id.notifyLblOgle, "setBackgroundColor", 0);
        mContentView.setInt(R.id.notifyOgle, "setBackgroundColor", 0);
        mContentView.setInt(R.id.notifyLblGunes, "setBackgroundColor", 0);
        mContentView.setInt(R.id.notifyGunes, "setBackgroundColor", 0);
        mContentView.setInt(R.id.notifyLblImsak, "setBackgroundColor", 0);
        mContentView.setInt(R.id.notifyImsak, "setBackgroundColor", 0);
        //convert textviews to datetimes
        try {
            SimpleDateFormat df = new SimpleDateFormat(mContext.getString(R.string.timeFormat), Locale.ENGLISH);
            imsak = df.parse((today + " " + tblImsak));
            gunes = df.parse((today + " " + tblGunes));
            ogle = df.parse((today + " " + tblOgle));
            ikindi = df.parse((today + " " + tblIkindi));
            aksam = df.parse((today + " " + tblAksam));
            yatsi = df.parse((today + " " + tblYatsi));
        } catch (Exception ignored) {
        }
        String remaining = DateUtils.formatElapsedTime((now.getTime() - yatsi.getTime()) / 1000); // Remaining time to seconds
        if (!remaining.contains(mContext.getString(R.string.minus))) {//yatsi zamanı
            mContentView.setTextColor(R.id.notifyLblYatsi, Color.WHITE);
            mContentView.setTextColor(R.id.notifyYatsi, Color.WHITE);
            mContentView.setInt(R.id.notifyLblYatsi, "setBackgroundColor", Color.RED);
            mContentView.setInt(R.id.notifyYatsi, "setBackgroundColor", Color.RED);
            remaining = DateUtils.formatElapsedTime((imsak.getTime() - now.getTime() + (1000 * 60 * 60 * 24)) / (1000 * 60));

        } else {//aksam zamanı
            remaining = DateUtils.formatElapsedTime((now.getTime() - aksam.getTime()) / 1000);
            if (!remaining.contains(mContext.getString(R.string.minus))) {
                mContentView.setTextColor(R.id.notifyLblAksam, Color.WHITE);
                mContentView.setTextColor(R.id.notifyAksam, Color.WHITE);
                mContentView.setInt(R.id.notifyLblAksam, "setBackgroundColor", Color.RED);
                mContentView.setInt(R.id.notifyAksam, "setBackgroundColor", Color.RED);
                remaining = DateUtils.formatElapsedTime((yatsi.getTime() - now.getTime()) / (1000 * 60));

            } else {//ikindi zamanı
                remaining = DateUtils.formatElapsedTime((now.getTime() - ikindi.getTime()) / 1000);
                if (!remaining.contains(mContext.getString(R.string.minus))) {
                    mContentView.setTextColor(R.id.notifyLblIkindi, Color.WHITE);
                    mContentView.setTextColor(R.id.notifyIkindi, Color.WHITE);
                    mContentView.setInt(R.id.notifyLblIkindi, "setBackgroundColor", Color.RED);
                    mContentView.setInt(R.id.notifyIkindi, "setBackgroundColor", Color.RED);
                    remaining = DateUtils.formatElapsedTime((aksam.getTime() - now.getTime()) / (1000 * 60));

                } else {//öğle zamanı
                    remaining = DateUtils.formatElapsedTime((now.getTime() - ogle.getTime()) / 1000);
                    if (!remaining.contains(mContext.getString(R.string.minus))) {
                        mContentView.setTextColor(R.id.notifyLblOgle, Color.WHITE);
                        mContentView.setTextColor(R.id.notifyOgle, Color.WHITE);
                        mContentView.setInt(R.id.notifyLblOgle, "setBackgroundColor", Color.RED);
                        mContentView.setInt(R.id.notifyOgle, "setBackgroundColor", Color.RED);
                        remaining = DateUtils.formatElapsedTime((ikindi.getTime() - now.getTime()) / (1000 * 60));

                    } else {//güneş zamanı
                        remaining = DateUtils.formatElapsedTime((now.getTime() - gunes.getTime()) / 1000);
                        if (!remaining.contains(mContext.getString(R.string.minus))) {
                            mContentView.setTextColor(R.id.notifyLblGunes, Color.WHITE);
                            mContentView.setTextColor(R.id.notifyGunes, Color.WHITE);
                            mContentView.setInt(R.id.notifyLblGunes, "setBackgroundColor", Color.RED);
                            mContentView.setInt(R.id.notifyGunes, "setBackgroundColor", Color.RED);
                            remaining = DateUtils.formatElapsedTime((ogle.getTime() - now.getTime()) / (1000 * 60));

                        } else {//imsak zamanı
                            remaining = DateUtils.formatElapsedTime((now.getTime() - imsak.getTime()) / 1000);
                            if (!remaining.contains(mContext.getString(R.string.minus))) {
                                mContentView.setTextColor(R.id.notifyLblImsak, Color.WHITE);
                                mContentView.setTextColor(R.id.notifyImsak, Color.WHITE);
                                mContentView.setInt(R.id.notifyLblImsak, "setBackgroundColor", Color.RED);
                                mContentView.setInt(R.id.notifyImsak, "setBackgroundColor", Color.RED);
                                remaining = DateUtils.formatElapsedTime((gunes.getTime() - now.getTime()) / (1000 * 60));
                            } else {//yatsı zamanı
                                mContentView.setTextColor(R.id.notifyLblYatsi, Color.WHITE);
                                mContentView.setTextColor(R.id.notifyYatsi, Color.WHITE);
                                mContentView.setInt(R.id.notifyLblYatsi, "setBackgroundColor", Color.RED);
                                mContentView.setInt(R.id.notifyYatsi, "setBackgroundColor", Color.RED);
                                remaining = DateUtils.formatElapsedTime((imsak.getTime() - now.getTime()) / (1000 * 60));
                            }
                        }
                    }
                }
            }
        }
        //write remaining time
        mContentView.setTextViewText(R.id.notifyTimeLeft, remaining);
        //settings activity
        Intent settingsIntent = new Intent(mContext, SettingsActivity.class);
        PendingIntent settingsPendingIntent = PendingIntent.getActivity(mContext, 0, settingsIntent, 0);
        mContentView.setOnClickPendingIntent(R.id.notifyBtnSettings, settingsPendingIntent);
        //notification
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.drawable.transparent)
                .setOngoing(true)
                .setAutoCancel(false)
                .setVisibility(1)
                .setContent(mContentView)
                .setContentIntent(resultPendingIntent);
        mNotificationManager.notify(notifyID, mBuilder.build());
    }

    //just update notification
    public void updateNotification() {
        String remaining = DateUtils.formatElapsedTime((now.getTime() - yatsi.getTime()) / 1000); // Remaining time to seconds
        if (!remaining.contains(mContext.getString(R.string.minus))) {//yatsi zamanı
            remaining = DateUtils.formatElapsedTime((imsak.getTime() - now.getTime() + (1000 * 60 * 60 * 24)) / (1000 * 60));

        } else {//aksam zamanı
            remaining = DateUtils.formatElapsedTime((now.getTime() - aksam.getTime()) / 1000);
            if (!remaining.contains(mContext.getString(R.string.minus))) {
                remaining = DateUtils.formatElapsedTime((yatsi.getTime() - now.getTime()) / (1000 * 60));

            } else {//ikindi zamanı
                remaining = DateUtils.formatElapsedTime((now.getTime() - ikindi.getTime()) / 1000);
                if (!remaining.contains(mContext.getString(R.string.minus))) {
                    remaining = DateUtils.formatElapsedTime((aksam.getTime() - now.getTime()) / (1000 * 60));

                } else {//öğle zamanı
                    remaining = DateUtils.formatElapsedTime((now.getTime() - ogle.getTime()) / 1000);
                    if (!remaining.contains(mContext.getString(R.string.minus))) {
                        remaining = DateUtils.formatElapsedTime((ikindi.getTime() - now.getTime()) / (1000 * 60));

                    } else {//güneş zamanı
                        remaining = DateUtils.formatElapsedTime((now.getTime() - gunes.getTime()) / 1000);
                        if (!remaining.contains(mContext.getString(R.string.minus))) {
                            remaining = DateUtils.formatElapsedTime((ogle.getTime() - now.getTime()) / (1000 * 60));

                        } else {//imsak zamanı
                            remaining = DateUtils.formatElapsedTime((now.getTime() - imsak.getTime()) / 1000);
                            if (!remaining.contains(mContext.getString(R.string.minus))) {
                                remaining = DateUtils.formatElapsedTime((gunes.getTime() - now.getTime()) / (1000 * 60));
                            } else {//yatsı zamanı
                                remaining = DateUtils.formatElapsedTime((imsak.getTime() - now.getTime()) / (1000 * 60));
                            }
                        }
                    }
                }
            }
        }
        mContentView.setTextViewText(R.id.notifyTimeLeft, remaining);
        mNotificationManager.notify(notifyID, mBuilder.build());
    }

    //set alarm for notify service
    public void SetServiceAlarm() {
        //set alarm
        Intent notifyIntent = new Intent(mContext, BootUpReceiver.class);
        PendingIntent pending = PendingIntent.getBroadcast(mContext, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        // InexactRepeating allows Android to optimize the energy consumption
        service.setInexactRepeating(AlarmManager.RTC, Calendar.getInstance().getTimeInMillis(), 1000 * 60, pending);
    }

    public boolean isNotificationActive() {
        Intent notificationIntent = new Intent(mContext, MainActivity.class);
        PendingIntent test = PendingIntent.getActivity(mContext, notifyID, notificationIntent, PendingIntent.FLAG_NO_CREATE);
        return test != null;
    }

    public boolean isAlarmActive() {
        return (PendingIntent.getBroadcast(mContext, 0,
                new Intent(mContext, BootUpReceiver.class),
                PendingIntent.FLAG_NO_CREATE) != null);
    }
}
