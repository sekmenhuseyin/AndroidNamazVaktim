package net.sekmetech.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import net.sekmetech.namazvaktim.R;

public class MonthlyFragmant extends Fragment {
    private View myInflatedView;
    private Context mContext;
    private ListView listMonthly;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myInflatedView = inflater.inflate(R.layout.fragment_home_monthly, container, false);
        mContext = container.getContext();
        //get view
        listMonthly = (ListView) myInflatedView.findViewById(R.id.listMonthly);

        return myInflatedView;
    }
}