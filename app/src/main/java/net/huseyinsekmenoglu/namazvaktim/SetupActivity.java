package net.huseyinsekmenoglu.namazvaktim;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import net.huseyinsekmenoglu.database.Database;
import net.huseyinsekmenoglu.database.Language;
import net.huseyinsekmenoglu.database.LanguageChild;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by huseyin.sekmenoglu on 8.2.2016.
 * http://www.androidhive.info/2013/07/android-expandable-list-view-tutorial/
 * http://emreardic.blogspot.com.tr/2014/08/diyanet-ezan-vakitleri-api.html
 */
public class SetupActivity extends AppCompatActivity {
    //expandable listview adapter
    private ExpandableListWithImageAdapter ExpAdapter;
    private ExpandableListAdapter listAdapter;
    private ExpandableListView expListView;
    //parent and child lists
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    //usefull vars
    private SharedPreferences prefs;
    private int lastExpandedPosition = -1;
    private String nameCountry, nameCity, nameTown;
    //may change in next versions
    private String[] SehirliUlkeler = {"TURKIYE", "ABD", "KANADA"};
    private String languages[] = {"Türkçe", "English"};
    private String languageCodes[] = {"TR", "EN"};
    private int images[] = {R.drawable.lang_tr, R.drawable.lang_en};
    private Boolean expWithImage = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        // SharedPreferences
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        // preparing list data
        prepareListLanguageData();
        expListView.expandGroup(0);
        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
                    expListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //if language clicked
                if (expWithImage) {
                    expWithImage = false;
                    setLocale(languageCodes[childPosition]);
                    //get country names
                    prepareListData("", "");
                    expListView.expandGroup(0);

                    // if country clicked
                } else if (listDataHeader.get(groupPosition).equals(getString(R.string.Country))) {
                    nameCountry = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
                    prepareListData(nameCountry, "");
                    expListView.collapseGroup(0);
                    expListView.expandGroup(1);

                    //if city clicked
                } else if (listDataHeader.get(groupPosition).equals(getString(R.string.City))) {
                    nameCity = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
                    if (nameCountry.equals(SehirliUlkeler[0]) || nameCountry.equals(SehirliUlkeler[1]) || nameCountry.equals(SehirliUlkeler[2])) {
                        prepareListData(nameCountry, nameCity);
                        expListView.collapseGroup(1);
                        expListView.expandGroup(2);
                    } else {
                        nameTown = nameCity;
                        nameCity = nameCountry;
                        expListView.collapseGroup(1);
                        SelectCity();
                    }

                    //if town clicked
                } else if (listDataHeader.get(groupPosition).equals(getString(R.string.Town))) {
                    expListView.collapseGroup(2);
                    nameTown = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
                    SelectCity();
                }
                return false;
            }
        });
    }

    /*end select city*/
    private void SelectCity() {
        //save to preferences
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(getString(R.string.Country), nameCountry);
        editor.putString(getString(R.string.City), nameCity);
        editor.putString(getString(R.string.Town), nameTown);
        editor.commit();
        //show message
        Toast.makeText(getApplicationContext(), nameCountry + ", " + nameTown, Toast.LENGTH_SHORT).show();
    }

    /*prepare language list for expandable listview*/
    private void prepareListLanguageData() {
        listDataHeader = new ArrayList<>();
        listDataHeader.add(getString(R.string.language));
        //diller tanımlanır
        ArrayList<Language> list = new ArrayList<>();
        ArrayList<LanguageChild> ch_list = new ArrayList<>();
        //dillerin adları ve resimleri diziye atılır
        for (int j = 0; images.length > j; j++) {
            LanguageChild ch = new LanguageChild();
            ch.setName(languages[j]);
            ch.setImage(images[j]);
            ch_list.add(ch);
        }
        //listview için son hazırlık
        Language lang = new Language();
        lang.setName(getString(R.string.language));
        lang.setItems(ch_list);
        list.add(lang);
        //listview
        ExpAdapter = new ExpandableListWithImageAdapter(this, list);
        expListView.setAdapter(ExpAdapter);
    }

    /*
     * Preparing the list data
     */
    private void prepareListData(String country, String city) {
        // Adding main groups
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        listDataHeader.add(getString(R.string.Country));
        listDataChild.put(listDataHeader.get(0), prepareListCountry());
        if (!country.equals("")) {//şehir istenmişse şehir grubunu ekle
            List<String> tmp;
            if (country.equals(SehirliUlkeler[0]) || country.equals(SehirliUlkeler[1]) || country.equals(SehirliUlkeler[2])) {
                tmp = prepareListCity(country);
            } else {
                tmp = prepareListTown(country);
            }
            listDataHeader.add(getString(R.string.City));
            listDataChild.put(listDataHeader.get(1), tmp);
        }
        if (!city.equals("")) {//ilçe istenmişse ilçe grubunu ekle
            listDataHeader.add(getString(R.string.Town));
            listDataChild.put(listDataHeader.get(1), prepareListCity(country));
            listDataChild.put(listDataHeader.get(2), prepareListTown(city));
        }
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        listAdapter.notifyDataSetChanged();
        // setting list adapter
        expListView.setAdapter(listAdapter);
    }

    private List<String> prepareListCountry() {
        // Adding child data
        Database db = new Database(getApplicationContext());
        return db.getContries();
    }

    private List<String> prepareListCity(String country) {
        if (country.equals("")) {
            return new ArrayList<>();
        } else {
            Database db = new Database(getApplicationContext());
            return db.getCities(country);
        }
    }

    private List<String> prepareListTown(String city) {
        if (city.equals("")) {
            return new ArrayList<>();
        } else {
            Database db = new Database(getApplicationContext());
            return db.getTown(city);
        }
    }

    //change language
    private void setLocale(String localeCode) {
        Locale locale = new Locale(localeCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        //save to preferences
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(getString(R.string.language), localeCode);
        editor.commit();
        //show message
        Toast.makeText(getApplicationContext(), localeCode, Toast.LENGTH_SHORT).show();
    }
}
