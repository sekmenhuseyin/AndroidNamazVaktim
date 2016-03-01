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
 */

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    private Functions fn;
    private SharedPreferences prefs;
    private Switch swAutoStart, swNotify, swIcon;
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
        //back button
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //floating action
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //get buttons and switches
        swAutoStart = (Switch) findViewById(R.id.swAutoStart);
        swNotify = (Switch) findViewById(R.id.swShowNotify);
        swIcon = (Switch) findViewById(R.id.swTransparentNotify);
        btnCity = (Button) findViewById(R.id.btnChangeCity);
        btnLang = (Button) findViewById(R.id.btnChangeLang);
        //get values
        swAutoStart.setChecked(prefs.getBoolean(getString(R.string.prefAutostart), true));
        swNotify.setChecked(prefs.getBoolean(getString(R.string.prefShowNotify), true));
        swIcon.setChecked(prefs.getBoolean(getString(R.string.prefIcon), false));
        // Register the onClick listener with the implementation above
        swAutoStart.setOnClickListener(this);
        swNotify.setOnClickListener(this);
        swIcon.setOnClickListener(this);
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
                break;

            case R.id.swTransparentNotify:
                editor.putBoolean(getString(R.string.prefIcon), swIcon.isChecked());
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
        Toast.makeText(this, "Ayarlar Kaydedildi", Toast.LENGTH_SHORT);
    }
}
