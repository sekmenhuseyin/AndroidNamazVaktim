package net.sekmetech.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import net.sekmetech.database.Database;
import net.sekmetech.database.Vakit;
import net.sekmetech.helpers.CustomListAdapter;
import net.sekmetech.namazvaktim.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MonthlyFragmant extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myInflatedView = inflater.inflate(R.layout.fragment_home_monthly, container, false);
        Context mContext = container.getContext();
        //commons
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        Database db = new Database(mContext);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dfDate = new SimpleDateFormat(getString(R.string.dateFormat), Locale.ENGLISH);
        //get data
        int town = Integer.parseInt(prefs.getString(getString(R.string.prefTownID), getString(R.string.defaultIlceID)));
        List<Vakit> vakitler = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            Vakit tablo = db.getVakit(town, dfDate.format(cal.getTime()));
            if (tablo.GetTarih().equals("")) break;
            vakitler.add(tablo);
            cal.add(Calendar.DATE, 1);
        }
        //get view
        ListView listMonthly = (ListView) myInflatedView.findViewById(R.id.listMonthly);
        //create adapter
        CustomListAdapter veriAdaptoru = new CustomListAdapter(this.getActivity(), vakitler);
        //connect data and adapter
        listMonthly.setAdapter(veriAdaptoru);
        //return
        return myInflatedView;
    }
}