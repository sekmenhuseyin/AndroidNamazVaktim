package net.sekmetech.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.sekmetech.database.Database;
import net.sekmetech.database.Vakit;
import net.sekmetech.namazvaktim.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CompassFragmant extends Fragment {
    View myInflatedView;
    Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myInflatedView = inflater.inflate(R.layout.fragment_home_compass, container, false);
        mContext = container.getContext();
        RefreshVakit();
        return myInflatedView;
    }

    //refresh vakit
    public void RefreshVakit() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        Database db = new Database(mContext);
        //get textviews
        TextView txtKible = (TextView) myInflatedView.findViewById(R.id.txtKible);
        //variables
        int town = Integer.parseInt(prefs.getString(getString(R.string.prefTownID), ""));
        SimpleDateFormat dfDate = new SimpleDateFormat(getString(R.string.dateFormat), Locale.ENGLISH);
        String today = dfDate.format((new Date()).getTime());//Returns 15/10/2012
        Vakit tablo = db.getVakit(town, today);
        //write values of namaz vakits
        txtKible.setText(tablo.GetKible());
    }
}