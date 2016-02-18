package net.huseyinsekmenoglu.namazvaktim;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

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
                //update if necessary
                MainActivity mac = new MainActivity();
                mac.UpdateVakit();
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
            //update if necessary
            MainActivity mac = new MainActivity();
            mac.UpdateVakit();
        }
    }
}