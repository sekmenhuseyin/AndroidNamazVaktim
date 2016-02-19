package net.huseyinsekmenoglu.namazvaktim;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import net.huseyinsekmenoglu.database.Database;
import net.huseyinsekmenoglu.helpers.Functions;

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
            //notify
            Functions notify = new Functions(this);
            notify.Notification();
            //show home page
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
