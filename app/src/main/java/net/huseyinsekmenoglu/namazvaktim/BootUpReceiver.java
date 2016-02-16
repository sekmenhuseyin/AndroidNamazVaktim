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
            //if not installed yet... exit
            if (!prefs.getBoolean(context.getString(R.string.prefSetup), false)) return;
            //saat yada zaman dilimi değiştiğinde
            if (intent.getAction().equals(Intent.ACTION_TIME_CHANGED) || intent.getAction().equals(Intent.ACTION_TIMEZONE_CHANGED)) {
                int datetime = prefs.getInt(context.getString(R.string.prefUpdate), 0);
                Toast.makeText(context, context.getString(R.string.Updating), Toast.LENGTH_LONG).show();
            }
        }
    };

    //telefon açıldığını farkettiğinde
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        //if not installed yet... exit
        if (!prefs.getBoolean(context.getString(R.string.prefSetup), false)) return;
        //after boot complete
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Toast.makeText(context, context.getString(R.string.app_name), Toast.LENGTH_LONG).show();
            /* show notifactions*/
            /*Intent i = new Intent(context, StartupActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);*/
        }
    }
}