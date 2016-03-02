package net.sekmetech.namazvaktim;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import net.sekmetech.fragments.SettingsFragment;

/*
 * Copyright (c) 2016. Tüm hakları saklıdır.
 * http://stackoverflow.com/questions/26564400/creating-a-preference-screen-with-support-v21-toolbar
 */
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getFragmentManager().beginTransaction().replace(R.id.content_frame, new SettingsFragment()).commit();

    }
}
