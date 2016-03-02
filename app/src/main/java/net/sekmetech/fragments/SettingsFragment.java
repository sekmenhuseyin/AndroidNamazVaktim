package net.sekmetech.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import net.sekmetech.namazvaktim.R;

/**
 * Copyright (c) 2016. Tüm hakları saklıdır.
 * <p/>
 * Created by huseyin.sekmenoglu on 2.3.2016.
 * http://stackoverflow.com/questions/26564400/creating-a-preference-screen-with-support-v21-toolbar
 */
public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}