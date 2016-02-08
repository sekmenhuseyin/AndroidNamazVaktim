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


    private final BroadcastReceiver m_timeChangedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            if (prefs.getBoolean("firstLaunch", true)) {
                return;
            }//if not installed yet... exit

            final String action = intent.getAction();
            if (action.equals(Intent.ACTION_TIME_CHANGED) || action.equals(Intent.ACTION_TIMEZONE_CHANGED)) {
                int datetime = prefs.getInt("date", 0);
                Toast.makeText(context, Integer.toString(datetime), Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Toast.makeText(context, "Service Started", Toast.LENGTH_LONG).show();

            /* or do something different */
            /* show notifactions*/
            /*Intent i = new Intent(context, StartupActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);*/
        }
    }
}