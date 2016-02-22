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
 */
public class BootUpReceiver extends BroadcastReceiver {


    /*class AsyncTaskClass extends AsyncTask<Context, String, String> {
        AsyncTaskClass() {
        }

        protected void onPreExecute() {
        }

        protected String doInBackground(Context... strings) {
            new EzanAlarmManager().alarmlariKur(strings[0]);
            return null;
        }

        protected void onPostExecute(String s) {
        }
    }*/

    /*//tarih değiştiğini fark ettiğinde
    private final BroadcastReceiver m_timeChangedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            //if not installed yet... exit
            if (!prefs.getBoolean(context.getString(R.string.prefSetup), false)) return;
            //saat yada zaman dilimi değiştiğinde
            if (intent.getAction().equals(Intent.ACTION_TIME_CHANGED) || intent.getAction().equals(Intent.ACTION_TIMEZONE_CHANGED)) {
                //update if necessary
                //MainActivity mac = new MainActivity();
                //mac.UpdateVakit();
            }
        }
    };*/

    //telefon açıldığını farkettiğinde
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        //if not installed yet... exit
        if (!prefs.getBoolean(context.getString(R.string.prefSetup), false)) return;
        //after boot complete
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            //notify
            Functions fn = new Functions(context);
            fn.Notification();


            /*if (set.getBoolean("isongoing", true)) {
                context.startService(new Intent(context, WidgetService.class));
            }
            context.startService(new Intent(context, DummyWidgetUpdaterService.class));*/

            //update if necessary
            //mac.UpdateVakit();
        }
    }
}