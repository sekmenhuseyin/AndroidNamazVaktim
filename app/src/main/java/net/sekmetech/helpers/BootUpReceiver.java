package net.sekmetech.helpers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import net.sekmetech.namazvaktim.R;

/**
 * Created by huseyin on 28.1.2016
 * runs on certain times
 */
public class BootUpReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        //if not installed yet... exit
        if (!prefs.getBoolean(context.getString(R.string.prefSetup), false)) return;
        Functions fn = new Functions(context);
        //actions
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            if (!prefs.getBoolean(context.getString(R.string.prefAutostart), true)) return;
            fn.SetServiceAlarm();//after boot complete
        } else {
            if (!prefs.getBoolean(context.getString(R.string.prefShowNotify), true)) return;
            fn.Notification();//for alarm manager
        }
    }
}