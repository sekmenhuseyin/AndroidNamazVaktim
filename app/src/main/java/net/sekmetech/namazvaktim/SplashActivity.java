package net.sekmetech.namazvaktim;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import net.sekmetech.helpers.Functions;

/**
 * Created by huseyin on 25.1.2016.
 * https://www.bignerdranch.com/blog/splash-screens-the-right-way/ *
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //check for setup finished status
        if (!prefs.getBoolean(getString(R.string.prefSetup), false)) {
            //show setup page
            Intent intent = new Intent(this, SetupActivity.class);
            startActivity(intent);
            finish();

        } else {// if not first launch show main page
            //notify: if not activated before and if need to show notify
            Functions fn = new Functions(this);
            fn.SetNotification();
            //show home page
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
