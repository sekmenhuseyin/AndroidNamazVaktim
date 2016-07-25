package net.sekmetech.namazvaktim;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import net.sekmetech.helpers.Functions;

import java.util.Date;
import java.util.Locale;

/*
 * Copyright (c) 2016. Tüm hakları saklıdır.
 *
 * Created by huseyin.sekmenoglu on 18.2.2016.
 * ayarlar sayfası
 */
public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    private Functions fn;
    private SharedPreferences prefs;
    private Switch swAutoStart, swNotify, swVibrate, swSound, swShowIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        fn = new Functions(this);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //locales
        String savedLocale = prefs.getString(getString(R.string.prefLang), "");
        Locale locale = new Locale(savedLocale);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //refresh button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //back button
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //get buttons and switches
        swAutoStart = (Switch) findViewById(R.id.swAutoStart);
        swNotify = (Switch) findViewById(R.id.swShowNotify);
        swShowIcon = (Switch) findViewById(R.id.swShowIcon);
        swSound = (Switch) findViewById(R.id.swSound);
        swVibrate = (Switch) findViewById(R.id.swVibrate);
        Button btnCity = (Button) findViewById(R.id.btnChangeCity);
        Button btnLang = (Button) findViewById(R.id.btnChangeLang);
        //version
        try {
            PackageInfo pinfo;
            pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            int versionNumber = pinfo.versionCode;
            String versionName = pinfo.versionName;
            TextView txtVersion = (TextView) findViewById(R.id.txtVersion);
            txtVersion.setText(String.format(getString(R.string.sd), versionName, versionNumber));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        //get values
        swAutoStart.setChecked(prefs.getBoolean(getString(R.string.prefAutostart), true));
        swNotify.setChecked(prefs.getBoolean(getString(R.string.prefShowNotify), true));
        swShowIcon.setChecked(prefs.getBoolean(getString(R.string.prefIcon), true));
        swSound.setChecked(prefs.getBoolean(getString(R.string.prefAlarmSound), true));
        swVibrate.setChecked(prefs.getBoolean(getString(R.string.prefAlarmVibrate), true));
        // Register the onClick listener with the implementation above
        swAutoStart.setOnClickListener(this);
        swNotify.setOnClickListener(this);
        swShowIcon.setOnClickListener(this);
        swSound.setOnClickListener(this);
        swVibrate.setOnClickListener(this);
        btnCity.setOnClickListener(this);
        btnLang.setOnClickListener(this);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor = prefs.edit();
        Intent intent;
        boolean settChanged = true;
        switch (v.getId() /*to get clicked view id**/) {
            case R.id.fab:
                fn.UpdatevakitTable();
                editor.putLong(getString(R.string.prefUpdate), new Date().getTime());
                break;

            case R.id.swAutoStart:
                editor.putBoolean(getString(R.string.prefAutostart), swAutoStart.isChecked());
                break;

            case R.id.swShowNotify:
                editor.putBoolean(getString(R.string.prefShowNotify), swNotify.isChecked());
                if (!swNotify.isChecked()) {
                    fn.CancelAlarm();
                    fn.CancelNotify();
                } else {
                    fn.SetNotification();
                }
                break;

            case R.id.swShowIcon:
                editor.putBoolean(getString(R.string.prefIcon), swShowIcon.isChecked());
                //var olan bilgilendirmeyi iptal edip tekrar oluşturur
                fn.CancelNotify();
                fn.Notification();
                break;

            case R.id.swSound:
                editor.putBoolean(getString(R.string.prefAlarmSound), swSound.isChecked());
                break;

            case R.id.swVibrate:
                editor.putBoolean(getString(R.string.prefAlarmVibrate), swVibrate.isChecked());
                break;

            case R.id.btnChangeCity:
                settChanged = false;
                intent = new Intent(getBaseContext(), SetupActivity.class);
                intent.putExtra(getString(R.string.tabAyarlar), getString(R.string.prefTown));
                startActivity(intent);
                break;

            case R.id.btnChangeLang:
                settChanged = false;
                intent = new Intent(getBaseContext(), SetupActivity.class);
                intent.putExtra(getString(R.string.tabAyarlar), getString(R.string.prefLang));
                startActivity(intent);
                break;

            default:
                break;
        }
        editor.apply();
        if (settChanged)
            Toast.makeText(this, getString(R.string.sett_Saved), Toast.LENGTH_SHORT).show();
    }

}
