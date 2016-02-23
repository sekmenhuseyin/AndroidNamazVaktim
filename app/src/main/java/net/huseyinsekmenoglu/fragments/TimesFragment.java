package net.huseyinsekmenoglu.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.huseyinsekmenoglu.database.Database;
import net.huseyinsekmenoglu.database.Vakit;
import net.huseyinsekmenoglu.helpers.Functions;
import net.huseyinsekmenoglu.namazvaktim.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class TimesFragment extends Fragment {
    private View myInflatedView;
    private Context mContext;
    private Functions fn;
    private TextView lblImsak, lblGunes, lblOgle, lblIkindi, lblAksam, lblYatsi, lblTown,
            txtImsak, txtGunes, txtOgle, txtIkindi, txtAksam, txtYatsi, txtRemainingTime;
    private ImageView imgImsak, imgGunes, imgOgle, imgIkindi, imgAksam, imgYatsi;
    private String today;
    private Long counterStartTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myInflatedView = inflater.inflate(R.layout.fragment_home_times, container, false);
        mContext = container.getContext();
        SimpleDateFormat dfDate = new SimpleDateFormat(getString(R.string.dateFormat), Locale.ENGLISH);
        today = dfDate.format((new Date()).getTime());//Returns 15/10/2012
        fn = new Functions(this.getActivity());
        //get textviews
        lblImsak = (TextView) myInflatedView.findViewById(R.id.lblImsak);
        lblGunes = (TextView) myInflatedView.findViewById(R.id.lblGunes);
        lblOgle = (TextView) myInflatedView.findViewById(R.id.lblOgle);
        lblIkindi = (TextView) myInflatedView.findViewById(R.id.lblIkindi);
        lblAksam = (TextView) myInflatedView.findViewById(R.id.lblAksam);
        lblYatsi = (TextView) myInflatedView.findViewById(R.id.lblYatsi);
        lblTown = (TextView) myInflatedView.findViewById(R.id.lblTown);
        txtImsak = (TextView) myInflatedView.findViewById(R.id.txtImsak);
        txtGunes = (TextView) myInflatedView.findViewById(R.id.txtGunes);
        txtOgle = (TextView) myInflatedView.findViewById(R.id.txtOgle);
        txtIkindi = (TextView) myInflatedView.findViewById(R.id.txtIkindi);
        txtAksam = (TextView) myInflatedView.findViewById(R.id.txtAksam);
        txtYatsi = (TextView) myInflatedView.findViewById(R.id.txtYatsi);
        txtRemainingTime = (TextView) myInflatedView.findViewById(R.id.txtRemainingTime);
        //imageviews
        imgImsak = (ImageView) myInflatedView.findViewById(R.id.imgImsak);
        imgGunes = (ImageView) myInflatedView.findViewById(R.id.imgGunes);
        imgOgle = (ImageView) myInflatedView.findViewById(R.id.imgOgle);
        imgIkindi = (ImageView) myInflatedView.findViewById(R.id.imgIkindi);
        imgAksam = (ImageView) myInflatedView.findViewById(R.id.imgAksam);
        imgYatsi = (ImageView) myInflatedView.findViewById(R.id.imgYatsi);
        //refresh and load vakits
        RefreshVakit();
        WriteVakits();
        CountDownTimer countDownTimer = new MyCountDownTimer(counterStartTime, 1000);
        countDownTimer.start();
        //show page
        return myInflatedView;
    }

    //refresh vakit
    private void RefreshVakit() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        Database db = new Database(mContext);
        //reset textviews and imageviews
        imgImsak.setImageResource(R.drawable.vakit_white);
        txtImsak.setTextColor(Color.BLACK);
        lblImsak.setTextColor(Color.BLACK);
        imgGunes.setImageResource(R.drawable.vakit_white);
        txtGunes.setTextColor(Color.BLACK);
        lblGunes.setTextColor(Color.BLACK);
        imgOgle.setImageResource(R.drawable.vakit_white);
        txtOgle.setTextColor(Color.BLACK);
        lblOgle.setTextColor(Color.BLACK);
        imgIkindi.setImageResource(R.drawable.vakit_white);
        txtIkindi.setTextColor(Color.BLACK);
        lblIkindi.setTextColor(Color.BLACK);
        imgAksam.setImageResource(R.drawable.vakit_white);
        txtAksam.setTextColor(Color.BLACK);
        lblAksam.setTextColor(Color.BLACK);
        imgYatsi.setImageResource(R.drawable.vakit_white);
        txtYatsi.setTextColor(Color.BLACK);
        lblYatsi.setTextColor(Color.BLACK);
        //variables
        int town = Integer.parseInt(prefs.getString(getString(R.string.prefTownID), ""));
        String townName = prefs.getString(getString(R.string.prefTown), "");
        Vakit tablo = db.getVakit(town, today);
        //if no vakit found update again
        if (tablo.GetId() == 0) fn.UpdatevakitTable();
        //write values of namaz vakits
        txtImsak.setText(tablo.GetImsak());
        txtGunes.setText(tablo.GetGunes());
        txtOgle.setText(tablo.GetOgle());
        txtIkindi.setText(tablo.GetIkindi());
        txtAksam.setText(tablo.GetAksam());
        txtYatsi.setText(tablo.GetYatsi());
        lblTown.setText(townName);
    }

    private void WriteVakits() {
        Date now = new Date(), imsak = now, gunes = now, ogle = now, ikindi = now, aksam = now, yatsi = now;
        //convert textviews to datetimes
        try {
            SimpleDateFormat df = new SimpleDateFormat(getString(R.string.timeFormat), Locale.ENGLISH);
            imsak = df.parse((today + " " + txtImsak.getText()));
            gunes = df.parse((today + " " + txtGunes.getText()));
            ogle = df.parse((today + " " + txtOgle.getText()));
            ikindi = df.parse((today + " " + txtIkindi.getText()));
            aksam = df.parse((today + " " + txtAksam.getText()));
            yatsi = df.parse((today + " " + txtYatsi.getText()));
        } catch (Exception ignored) {
        }
        //remaining time and active vakit
        String remaining = DateUtils.formatElapsedTime((now.getTime() - yatsi.getTime()) / 1000); // Remaining time to seconds
        if (!remaining.contains(getString(R.string.minus))) {//yatsi zamanı
            imgYatsi.setImageResource(R.drawable.vakit_red);
            lblYatsi.setTextColor(Color.WHITE);
            txtYatsi.setTextColor(Color.WHITE);
            remaining = DateUtils.formatElapsedTime((imsak.getTime() - now.getTime() + (1000 * 60 * 60 * 24)) / 1000);
            counterStartTime = imsak.getTime() - now.getTime() + (1000 * 60 * 60 * 24);
            txtRemainingTime.setText(remaining);
        } else {//aksam zamanı
            remaining = DateUtils.formatElapsedTime((now.getTime() - aksam.getTime()) / 1000);
            if (!remaining.contains(getString(R.string.minus))) {
                imgAksam.setImageResource(R.drawable.vakit_red);
                lblAksam.setTextColor(Color.WHITE);
                txtAksam.setTextColor(Color.WHITE);
                remaining = DateUtils.formatElapsedTime((yatsi.getTime() - now.getTime()) / 1000);
                counterStartTime = yatsi.getTime() - now.getTime();
                txtRemainingTime.setText(remaining);
            } else {//ikindi zamanı
                remaining = DateUtils.formatElapsedTime((now.getTime() - ikindi.getTime()) / 1000);
                if (!remaining.contains(getString(R.string.minus))) {
                    imgIkindi.setImageResource(R.drawable.vakit_red);
                    lblIkindi.setTextColor(Color.WHITE);
                    txtIkindi.setTextColor(Color.WHITE);
                    remaining = DateUtils.formatElapsedTime((aksam.getTime() - now.getTime()) / 1000);
                    counterStartTime = aksam.getTime() - now.getTime();
                    txtRemainingTime.setText(remaining);
                } else {//öğle zamanı
                    remaining = DateUtils.formatElapsedTime((now.getTime() - ogle.getTime()) / 1000);
                    if (!remaining.contains(getString(R.string.minus))) {
                        imgOgle.setImageResource(R.drawable.vakit_red);
                        lblOgle.setTextColor(Color.WHITE);
                        txtOgle.setTextColor(Color.WHITE);
                        remaining = DateUtils.formatElapsedTime((ikindi.getTime() - now.getTime()) / 1000);
                        counterStartTime = ikindi.getTime() - now.getTime();
                        txtRemainingTime.setText(remaining);
                    } else {//güneş zamanı
                        remaining = DateUtils.formatElapsedTime((now.getTime() - gunes.getTime()) / 1000);
                        if (!remaining.contains(getString(R.string.minus))) {
                            imgGunes.setImageResource(R.drawable.vakit_red);
                            lblGunes.setTextColor(Color.WHITE);
                            txtGunes.setTextColor(Color.WHITE);
                            remaining = DateUtils.formatElapsedTime((ogle.getTime() - now.getTime()) / 1000);
                            counterStartTime = ogle.getTime() - now.getTime();
                            txtRemainingTime.setText(remaining);
                        } else {//imsak zamanı
                            remaining = DateUtils.formatElapsedTime((now.getTime() - imsak.getTime()) / 1000);
                            if (!remaining.contains(getString(R.string.minus))) {
                                imgImsak.setImageResource(R.drawable.vakit_red);
                                lblImsak.setTextColor(Color.WHITE);
                                txtImsak.setTextColor(Color.WHITE);
                                remaining = DateUtils.formatElapsedTime((gunes.getTime() - now.getTime()) / 1000);
                                counterStartTime = gunes.getTime() - now.getTime();
                                txtRemainingTime.setText(remaining);
                            } else {//yatsı zamanı
                                imgYatsi.setImageResource(R.drawable.vakit_red);
                                lblYatsi.setTextColor(Color.WHITE);
                                txtYatsi.setTextColor(Color.WHITE);
                                remaining = DateUtils.formatElapsedTime((imsak.getTime() - now.getTime()) / 1000);
                                counterStartTime = imsak.getTime() - now.getTime();
                                txtRemainingTime.setText(remaining);
                            }
                        }
                    }
                }
            }
        }
    }

    //countdown timer
    public class MyCountDownTimer extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }


        @Override
        public void onTick(long millisUntilFinished) {
            txtRemainingTime.setText(DateUtils.formatElapsedTime(millisUntilFinished / 1000));
        }

        @Override
        public void onFinish() {
            //refresh aktif vakit and remaining time
            RefreshVakit();
            WriteVakits();
        }
    }
}
