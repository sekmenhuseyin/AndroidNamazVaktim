package net.sekmetech.namazvaktim;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import net.sekmetech.helpers.Functions;

/*
 * Copyright (c) 2016. Tüm hakları saklıdır.
 *
 * Created by huseyin.sekmenoglu on 18.2.2016.
 * ayarlar sayfası
 */
public class SettingsActivity extends AppCompatActivity {
    private Functions fn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //functions
        fn = new Functions(this);
        //floatingaction button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fn.UpdatevakitTable();
            }
        });
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
