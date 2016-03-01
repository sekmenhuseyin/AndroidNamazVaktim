package net.sekmetech.namazvaktim;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import net.sekmetech.database.Database;
import net.sekmetech.helpers.Functions;

import java.io.IOException;

/**
 * Created by huseyin on 25.1.2016.
 * https://www.bignerdranch.com/blog/splash-screens-the-right-way/ *
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Database db = new Database(this);
        Functions fn = new Functions(this);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //check for setup finished status
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
            //notify with alarm manager: if not activated before and if need to show notify
            if (!fn.isAlarmActive() && prefs.getBoolean(getString(R.string.prefShowNotify), true))
                fn.SetServiceAlarm();
            //show home page
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
