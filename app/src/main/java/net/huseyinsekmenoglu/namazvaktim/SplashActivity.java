package net.huseyinsekmenoglu.namazvaktim;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;

import net.huseyinsekmenoglu.database.Database;

import java.io.IOException;

/**
 * Created by huseyin on 25.1.2016.
 * https://www.bignerdranch.com/blog/splash-screens-the-right-way/ *
 */
public class SplashActivity extends AppCompatActivity {
    private Database db = new Database(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        if (!prefs.getBoolean(getString(R.string.prefSetup), false)) {
            try {//create database
                db.createDataBase();
            } catch (IOException ioe) {
                throw new Error(getString(R.string.errorSetup));
            }
            //show setup page
            Intent intent = new Intent(this, SetupActivity.class);
            startActivity(intent);
            finish();

        } else {// if not first launch show main page


            // Creates an explicit intent for an Activity in your app
            Intent resultIntent = new Intent(this, MainActivity.class);

            // The stack builder object will contain an artificial back stack for the started Activity.
            // This ensures that navigating backward from the Activity leads out of
            // your application to the Home screen.
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            // Adds the back stack for the Intent (but not the Intent itself)
            stackBuilder.addParentStack(MainActivity.class);
            // Adds the Intent that starts the Activity to the top of the stack
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            //notification
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("My notification")
                            .setContentText("Hello World!")
                            .setOngoing(true)
                            .setAutoCancel(false)
                            .setContentIntent(resultPendingIntent);
            mNotificationManager.notify(1, mBuilder.build());

            //show home page
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
