package net.huseyinsekmenoglu.namazvaktim;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

/**
 * Created by huseyin on 28.1.2016
 */
public class BootUpReceiver extends BroadcastReceiver {
    //tarih değiştiğini fark ettiğinde
    private final BroadcastReceiver m_timeChangedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            if (!prefs.getBoolean(String.valueOf((R.string.prefSetup)), false)) {
                return;//if not installed yet... exit
            }

            final String action = intent.getAction();
            if (action.equals(Intent.ACTION_TIME_CHANGED) || action.equals(Intent.ACTION_TIMEZONE_CHANGED)) {
                int datetime = prefs.getInt(String.valueOf(R.string.prefUpdate), 0);
                Toast.makeText(context, String.valueOf(R.string.Updating), Toast.LENGTH_LONG).show();
            }
        }
    };

    //telefon açıldığını farkettiğinde
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Toast.makeText(context, String.valueOf(R.string.app_name), Toast.LENGTH_LONG).show();

            /* or do something different */
            /* show notifactions*/
            /*Intent i = new Intent(context, StartupActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);*/
        }
    }
}