package net.sekmetech.helpers;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.sekmetech.database.Vakit;
import net.sekmetech.namazvaktim.R;

import java.util.List;

/**
 * Copyright (c) 2016. Tüm hakları saklıdır.
 * Created by huseyin.sekmenoglu on 25.2.2016.
 * <p/>
 * https://gelecegiyazanlar.turkcell.com.tr/konu/android/egitim/android-301/listview-ozellestirme
 */
public class CustomListAdapter extends BaseAdapter {
    private Activity mActivity;
    private List<Vakit> vakitler;

    public CustomListAdapter(Activity tActivity, List<Vakit> tVakit) {
        mActivity = tActivity;
        vakitler = tVakit;
    }

    @Override
    public int getCount() {
        return vakitler.size();
    }

    @Override
    public Object getItem(int position) {
        return vakitler.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get view
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fragment_home_monthly_item, null);
        }
        //vakitler
        Vakit tmp = vakitler.get(position);
        //textviews
        ((TextView) convertView.findViewById(R.id.monthlyTarih)).setText(tmp.GetTarih());
        ((TextView) convertView.findViewById(R.id.monthlyImsak)).setText(tmp.GetImsak());
        ((TextView) convertView.findViewById(R.id.monthlyGunes)).setText(tmp.GetGunes());
        ((TextView) convertView.findViewById(R.id.monthlyOgle)).setText(tmp.GetOgle());
        ((TextView) convertView.findViewById(R.id.monthlyIkindi)).setText(tmp.GetIkindi());
        ((TextView) convertView.findViewById(R.id.monthlyAksam)).setText(tmp.GetAksam());
        ((TextView) convertView.findViewById(R.id.monthlyYatsi)).setText(tmp.GetYatsi());
        //change today
        //if (position==0) (convertView.findViewById(R.id.monthlyTarih)).setBackgroundColor(Color.BLACK);
        //return
        return convertView;
    }
}
