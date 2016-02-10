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

        } else {// if not first launch check for updatetime
            int datetime = prefs.getInt(getString(R.string.prefUpdate), 0);
            int now = (int) new Date().getTime();
            if (now != datetime) {
                Toast.makeText(this, Integer.toString(now) + " &  " + Integer.toString(datetime), Toast.LENGTH_LONG).show();
                //save settings
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt(getString(R.string.prefUpdate), now);
                editor.commit();
            }
            //updatevakit
            String nameCountry = prefs.getString(getString(R.string.prefCountry), "");
            String nameTown = prefs.getString(getString(R.string.prefTown), "");
            db.UpdateNamazVakit(nameCountry, nameTown, getString(R.string.Updating));
            //show main page
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
