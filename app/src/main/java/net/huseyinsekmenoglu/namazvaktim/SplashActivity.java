package net.huseyinsekmenoglu.namazvaktim;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import net.huseyinsekmenoglu.database.Database;

import java.io.IOException;
import java.util.Date;

/**
 * Created by huseyin on 25.1.2016.
 * https://www.bignerdranch.com/blog/splash-screens-the-right-way/ *
 */
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        if (!prefs.getBoolean("isSetupFinished", false)) {
            try {//create database
                Database myDbHelper = new Database(this);
                myDbHelper.createDataBase();
            } catch (IOException ioe) {
                throw new Error("Kurulum hatası");
            }
            //show setup page
            Intent intent = new Intent(this, SetupActivity.class);
            startActivity(intent);
            finish();

        } else {// if not first launch check for updatetime
            int datetime = prefs.getInt("date", 0);
            int now = (int) new Date().getTime();
            if (now != datetime) {
                Toast.makeText(this, Integer.toString(now) + " &  " + Integer.toString(datetime), Toast.LENGTH_LONG).show();
                //save settings
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("date", now);
                editor.commit();
            }
            //show main page
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
