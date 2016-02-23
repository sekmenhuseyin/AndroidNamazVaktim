package net.huseyinsekmenoglu.namazvaktim;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import net.huseyinsekmenoglu.helpers.Functions;

/**
 * Created by huseyin on 28.1.2016
 * runs on certain times
 * telefon açıldığını farkettiğinde
 */
public class BootUpReceiver extends BroadcastReceiver {
    Functions fn;

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        //if not installed yet... exit
        if (!prefs.getBoolean(context.getString(R.string.prefSetup), false)) return;
        //after boot complete show notification
        fn = new Functions(context);
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) fn.Notification();
    }
}