package net.huseyinsekmenoglu.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.huseyinsekmenoglu.database.Language;
import net.huseyinsekmenoglu.database.LanguageChild;
import net.huseyinsekmenoglu.namazvaktim.R;

import java.util.ArrayList;

/**
 * Created by huseyin.sekmenoglu on 9.2.2016.
 * http://www.tutorialsbuzz.com/2014/07/custom-expandable-listview-image-text.html
 */
public class ExpandableListWithImageAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<Language> lang;

    public ExpandableListWithImageAdapter(Context context, ArrayList<Language> lang) {
        this.context = context;
        this.lang = lang;
    }

    @Override
    public int getGroupCount() {
        return lang.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<LanguageChild> chList = lang.get(groupPosition).getItems();
        return chList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return lang.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<LanguageChild> chList = lang.get(groupPosition).getItems();
        return chList.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Language group = (Language) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.activity_setup_group, null);
        }
        //textbox bulunur ve içeriği doldurulur
        TextView tv = (TextView) convertView.findViewById(R.id.lblListHeader);
        tv.setText(group.getName());
        //return
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LanguageChild child = (LanguageChild) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_setup_item2, null);
        }
        //textbox bulunur ve içeriği doldurulur
        TextView tv = (TextView) convertView.findViewById(R.id.country_name);
        tv.setText(child.getName());
        //imagebox bulunur
        ImageView iv = (ImageView) convertView.findViewById(R.id.flag);
        iv.setImageResource(child.getImage());
        //return
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
