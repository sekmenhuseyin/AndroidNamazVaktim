package net.huseyinsekmenoglu.namazvaktim;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import net.huseyinsekmenoglu.database.ApiConnect;
import net.huseyinsekmenoglu.home.tAyarlar;
import net.huseyinsekmenoglu.home.tImsakiye;
import net.huseyinsekmenoglu.home.tKible;
import net.huseyinsekmenoglu.home.tKitaplik;
import net.huseyinsekmenoglu.home.tOnemliGunler;
import net.huseyinsekmenoglu.home.tTakvim;
import net.huseyinsekmenoglu.home.tVakit;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        //update namaz vakit
        new ApiConnect(this).execute("13/11075");
        //new NotificationActivity();
        //Intent intent = new Intent(this, NotificationActivity.class);
        //startActivity(intent);
        //new Update().execute("13/11075");

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        // Return a PlaceholderFragment (defined as a static inner class below).
        public Fragment getItem(int position) {
            switch (position) {
                case 6:
                    return new tAyarlar();
                case 5:
                    return new tKitaplik();
                case 4:
                    return new tOnemliGunler();
                case 3:
                    return new tTakvim();
                case 2:
                    return new tImsakiye();
                case 1:
                    return new tKible();
                default:
                    return new tVakit();
            }
        }

        @Override
        public int getCount() {
            return 7;
        }//0-6, default:0

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 6:
                    return String.valueOf(R.string.tabAyarlar);
                case 5:
                    return String.valueOf(R.string.tabKitaplik);
                case 4:
                    return String.valueOf(R.string.tabOnemliGun);
                case 3:
                    return String.valueOf(R.string.tabTakvim);
                case 2:
                    return String.valueOf(R.string.tabImsakiye);
                case 1:
                    return String.valueOf(R.string.tabKible);
                default://0
                    return String.valueOf(R.string.tabVakit);
            }
        }
    }

}
