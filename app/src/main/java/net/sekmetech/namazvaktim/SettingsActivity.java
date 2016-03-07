package net.sekmetech.namazvaktim;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import net.sekmetech.helpers.Functions;

import java.util.Date;

/*
 * Copyright (c) 2016. Tüm hakları saklıdır.
 *
 * Created by huseyin.sekmenoglu on 18.2.2016.
 * ayarlar sayfası
 */
public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    private Functions fn;
    private SharedPreferences prefs;
    private Switch swAutoStart, swNotify, swVibrate, swSound, swTransparentIcon;
    private Button btnCity, btnLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        fn = new Functions(this);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
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
        swTransparentIcon = (Switch) findViewById(R.id.swTransparentIcon);
        swSound = (Switch) findViewById(R.id.swSound);
        swVibrate = (Switch) findViewById(R.id.swVibrate);
        btnCity = (Button) findViewById(R.id.btnChangeCity);
        btnLang = (Button) findViewById(R.id.btnChangeLang);
        //get values
        swAutoStart.setChecked(prefs.getBoolean(getString(R.string.prefAutostart), true));
        swNotify.setChecked(prefs.getBoolean(getString(R.string.prefShowNotify), true));
        swTransparentIcon.setChecked(prefs.getBoolean(getString(R.string.prefIcon), true));
        swSound.setChecked(prefs.getBoolean(getString(R.string.prefAlarmSound), true));
        swVibrate.setChecked(prefs.getBoolean(getString(R.string.prefAlarmVibrate), true));
        // Register the onClick listener with the implementation above
        swAutoStart.setOnClickListener(this);
        swNotify.setOnClickListener(this);
        swTransparentIcon.setOnClickListener(this);
        swSound.setOnClickListener(this);
        swVibrate.setOnClickListener(this);
        btnCity.setOnClickListener(this);
        btnLang.setOnClickListener(this);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor = prefs.edit();
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

            case R.id.swTransparentIcon:
                editor.putBoolean(getString(R.string.prefIcon), swTransparentIcon.isChecked());
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
                //editor.putBoolean(getString(R.string.prefAutostart), true);
                break;

            case R.id.btnChangeLang:
                //editor.putBoolean(getString(R.string.prefAutostart), true);
                break;

            default:
                break;
        }
        editor.apply();
        Toast.makeText(this, getString(R.string.sett_Saved), Toast.LENGTH_SHORT).show();
    }

}
