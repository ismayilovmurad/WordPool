package com.martiandeveloper.wordpool.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.textview.MaterialTextView;
import com.martiandeveloper.wordpool.R;
import com.martiandeveloper.wordpool.model.NavigationMenu;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ExpandableListAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {

    private final Context context;
    private final List<NavigationMenu> menuHeaderList;
    private final HashMap<NavigationMenu, List<NavigationMenu>> menuChildList;

    public ExpandableListAdapter(Context context, List<NavigationMenu> menuHeaderList, HashMap<NavigationMenu, List<NavigationMenu>> menuChildList) {
        this.context = context;
        this.menuHeaderList = menuHeaderList;
        this.menuChildList = menuChildList;
    }

    @Override
    public int getGroupCount() {
        return menuHeaderList.size();
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        if (menuChildList.get(menuHeaderList.get(groupPosition)) != null) {
            return Objects.requireNonNull(menuChildList.get(menuHeaderList.get(groupPosition))).size();
        } else {
            return 0;
        }
    }

    @Override
    public NavigationMenu getGroup(int groupPosition) {
        return menuHeaderList.get(groupPosition);
    }

    @Override
    public NavigationMenu getChild(int groupPosition, int childPosition) {
        return Objects.requireNonNull(menuChildList.get(menuHeaderList.get(groupPosition))).get(childPosition);
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
        return false;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String headerText = getGroup(groupPosition).getName();
        int image = getGroup(groupPosition).getImage();

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            convertView = layoutInflater.inflate(R.layout.list_item_header, null);
        }

        MaterialTextView list_item_header_mainTV = convertView.findViewById(R.id.list_item_header_mainTV);
        list_item_header_mainTV.setText(headerText);

        ImageView list_item_header_mainIV = convertView.findViewById(R.id.list_item_header_mainIV);
        list_item_header_mainIV.setImageResource(image);

        return convertView;
    }

    @SuppressWarnings("unused")
    @SuppressLint("InflateParams")
    @Override
    public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        String childText = getChild(groupPosition, childPosition).getName();

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            convertView = layoutInflater.inflate(R.layout.list_item_child, null);
        }

        MaterialTextView list_item_child_mainTV = convertView.findViewById(R.id.list_item_child_mainTV);
        list_item_child_mainTV.setText(childText);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
