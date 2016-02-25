package net.sekmetech.namazvaktim;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import net.sekmetech.fragments.CalendarFragment;
import net.sekmetech.fragments.CompassFragmant;
import net.sekmetech.fragments.HolyDaysFragmant;
import net.sekmetech.fragments.LibraryFragmant;
import net.sekmetech.fragments.MonthlyFragmant;
import net.sekmetech.fragments.TimesFragment;
import net.sekmetech.helpers.Functions;

import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    /* The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private boolean doubleBackToExitPressedOnce = false;
    private SharedPreferences prefs;
    private Functions fn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        fn = new Functions(this);
        //locales
        String defaulLocale = Locale.getDefault().getLanguage();
        String savedLocale = prefs.getString(getString(R.string.prefLang), "");
        if (!defaulLocale.equals(savedLocale)) {
            Locale locale = new Locale(getString(R.string.defaultLocale));
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }
        //find days passed after last update
        int diffInDays = fn.getDayDifference(new Date(prefs.getLong(getString(R.string.prefUpdate), 0)));
        //update namaz vakit
        if (diffInDays > 21) fn.UpdatevakitTable();
        //layout
        setContentView(R.layout.activity_main);
        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // the adapter that will return a fragment for each the sections of the activity.
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);//this will host the section contents.
        mViewPager.setAdapter(mSectionsPagerAdapter);
        //tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.lblListHeader) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Intent intent = getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // {@link FragmentPagerAdapter} returns a fragment corresponding to one of the tabs
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        // Return a PlaceholderFragment (defined as a static inner class below).
        public Fragment getItem(int position) {
            switch (position) {
                case 5:
                    return new LibraryFragmant();
                case 4:
                    return new HolyDaysFragmant();
                case 3:
                    return new CalendarFragment();
                case 2:
                    return new MonthlyFragmant();
                case 1:
                    return new CompassFragmant();
                default:
                    return new TimesFragment();
            }
        }

        @Override
        public int getCount() {
            return 6;
        }//0-5, default:0

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 5:
                    return getString(R.string.tabKitaplik);
                case 4:
                    return getString(R.string.tabOnemliGun);
                case 3:
                    return getString(R.string.tabTakvim);
                case 2:
                    return getString(R.string.tabImsakiye);
                case 1:
                    return getString(R.string.tabKible);
                default://0
                    return getString(R.string.tabVakit);
            }
        }
    }

}
