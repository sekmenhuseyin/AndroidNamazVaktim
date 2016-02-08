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
 * copied from: http://www.androidhive.info/2013/07/android-expandable-list-view-tutorial/
 */
public class SetupActivity extends AppCompatActivity {
    private ExpandableListAdapter listAdapter;
    private ExpandableListView expListView;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    private int lastExpandedPosition = -1;
    private String nameCountry, nameCity, nameTown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        // preparing list data
        prepareListData();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        // setting list adapter
        expListView.setAdapter(listAdapter);
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
                if (listDataHeader.get(groupPosition) == getString(R.string.Country)) {
                    nameCountry = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
                    prepareListCity(nameCountry);
                    expListView.collapseGroup(0);
                    expListView.expandGroup(1);

                } else if (listDataHeader.get(groupPosition) == getString(R.string.City)) {
                    nameCity = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
                    prepareListTown(nameCity);
                    expListView.collapseGroup(1);
                    expListView.expandGroup(2);

                } else if (listDataHeader.get(groupPosition) == getString(R.string.Town)) {
                    expListView.collapseGroup(2);
                    nameTown = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
                    Toast.makeText(getApplicationContext(), nameCountry + ", " + nameCity + ", " + nameTown, Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        // Adding main groups
        listDataHeader = new ArrayList<>();
        listDataHeader.add(getString(R.string.Country));
        listDataHeader.add(getString(R.string.City));
        listDataHeader.add(getString(R.string.Town));
        // Adding child data
        Database db = new Database(getApplicationContext());
        List<String> liste = db.getContries();
        List<String> tmp = new ArrayList<>();
//add to first group items
        listDataChild = new HashMap<>();
        listDataChild.put(listDataHeader.get(0), liste); // Header, Child data
        listDataChild.put(listDataHeader.get(1), tmp);
        listDataChild.put(listDataHeader.get(2), tmp);
    }

    private void prepareListCity(String ulke) {
        // Adding child data
        Database db = new Database(getApplicationContext());
        List<String> liste = db.getCities(ulke);
        List<String> tmp = new ArrayList<>();
        //add to first group items
        listDataChild = new HashMap<>();
        listDataChild.put(listDataHeader.get(1), liste); // Header, Child data
        listDataChild.put(listDataHeader.get(2), tmp);
    }

    private void prepareListTown(String city) {
        // Adding child data
        Database db = new Database(getApplicationContext());
        List<String> liste = db.getTown(city);
        //add to first group items
        listDataChild = new HashMap<>();
        listDataChild.put(listDataHeader.get(2), liste); // Header, Child data
    }
}
