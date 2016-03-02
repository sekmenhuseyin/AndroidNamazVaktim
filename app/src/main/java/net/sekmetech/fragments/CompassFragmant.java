package net.sekmetech.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import net.sekmetech.database.Database;
import net.sekmetech.database.Vakit;
import net.sekmetech.namazvaktim.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CompassFragmant extends Fragment implements SensorEventListener {
    float[] inR = new float[16];
    float[] I = new float[16];
    float[] gravity = new float[3];
    float[] geomag = new float[3];
    float[] orientVals = new float[3];
    double azimuth = 0;
    View myInflatedView;
    Context mContext;
    // record the compass picture angle turned
    private float currentDegree = 0f;
    private float KibleDegree = 0f;
    // define the display assembly compass picture
    private ImageView image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myInflatedView = inflater.inflate(R.layout.fragment_home_compass, container, false);
        mContext = container.getContext();
        image = (ImageView) myInflatedView.findViewById(R.id.imgKible);
        RefreshVakit();
        // initialize your android device sensor capabilities
        SensorManager mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        // Register this class as a listener for the accelerometer sensor
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        // ...and the orientation sensor
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_NORMAL);
        return myInflatedView;
    }

    //refresh vakit
    public void RefreshVakit() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        Database db = new Database(mContext);
        //get textviews
        TextView txtKible = (TextView) myInflatedView.findViewById(R.id.txtKible);
        TextView txtAngle = (TextView) myInflatedView.findViewById(R.id.txtAngle);
        //variables
        int town = prefs.getInt(getString(R.string.pref1TownID), Integer.parseInt(getString(R.string.defaultIlceID)));
        float sapma = Float.parseFloat(prefs.getString(getString(R.string.sapma), getString(R.string.sifir)));
        KibleDegree = Float.parseFloat(prefs.getString(getString(R.string.pref1Angle), getString(R.string.sifir))) - sapma;
        //tarih
        SimpleDateFormat dfDate = new SimpleDateFormat(getString(R.string.dateFormat), Locale.ENGLISH);
        String today = dfDate.format((new Date()).getTime());//Returns 15/10/2012
        Vakit tablo = db.getVakit(town, today);
        //write values of namaz vakits
        txtKible.setText(tablo.GetKible());
        txtAngle.setText(String.format("%s%s", KibleDegree, getString(R.string.derece)));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // If the sensor data is unreliable return
        if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE)
            return;
        // Gets the value of the sensor that has been changed
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                gravity = event.values.clone();
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                geomag = event.values.clone();
                break;
        }
        // If gravity and geomag have values then find rotation matrix
        if (gravity != null && geomag != null) {
            // checks that the rotation matrix is found
            if (SensorManager.getRotationMatrix(inR, I, gravity, geomag)) {
                SensorManager.getOrientation(inR, orientVals);
                azimuth = Math.toDegrees(orientVals[0]);
                //Log.d("onSensorChanged", String.valueOf(azimuth)+", "+String.valueOf(roll)+", "+String.valueOf(pitch));
                float degree = Math.round(azimuth) - KibleDegree;
                // create a rotation animation (reverse turn degree degrees)
                RotateAnimation ra = new RotateAnimation(
                        currentDegree,
                        -degree,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF,
                        0.5f);
                // how long the animation will take place
                ra.setDuration(210);
                // set the animation after the end of the reservation status
                ra.setFillAfter(true);
                // Start the animation
                image.startAnimation(ra);
                currentDegree = -degree;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}