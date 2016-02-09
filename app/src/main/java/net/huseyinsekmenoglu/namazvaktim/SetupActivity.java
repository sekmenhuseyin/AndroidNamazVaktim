package net.huseyinsekmenoglu.namazvaktim;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import net.huseyinsekmenoglu.database.Database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by huseyin.sekmenoglu on 8.2.2016.
 * ülke, şehir, ilçe seçiminden sonra vakitleri güncelle
 * copied from: http://www.androidhive.info/2013/07/android-expandable-list-view-tutorial/
 */
public class SetupActivity extends AppCompatActivity {
    private ExpandableListAdapter listAdapter;
    private ExpandableListView expListView;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    private int lastExpandedPosition = -1;
    private String nameCountry, nameCity, nameTown;
    private String[] SehirliUlkeler = {"TURKIYE", "ABD", "KANADA"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        // preparing list data
        prepareListData("", "");
        expListView.expandGroup(0);
        expListView.collapseGroup(1);
        expListView.collapseGroup(2);
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
                //select case by group name
                if (listDataHeader.get(groupPosition).equals(getString(R.string.Country))) {
                    nameCountry = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
                    prepareListData(nameCountry, "");
                    expListView.collapseGroup(0);
                    expListView.expandGroup(1);

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

                } else if (listDataHeader.get(groupPosition).equals(getString(R.string.Town))) {
                    expListView.collapseGroup(2);
                    nameTown = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
                    SelectCity();
                }
                return false;
            }
        });
    }

    /*end selcet city*/
    private void SelectCity() {
        Toast.makeText(getApplicationContext(), nameCountry + ", " + nameTown, Toast.LENGTH_SHORT).show();
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
        if (!country.equals("")) {//şehir istenmişse şihir grubunu ekle
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
}
