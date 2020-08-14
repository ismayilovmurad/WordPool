package com.martiandeveloper.wordpool.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.martiandeveloper.wordpool.R;
import com.martiandeveloper.wordpool.model.NavigationMenu;
import com.martiandeveloper.wordpool.tools.AnimatedExpandableListView;
import com.martiandeveloper.wordpool.tools.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    // UI Components
    // MaterialToolbar
    @BindView(R.id.activity_home_mainTB)
    MaterialToolbar activity_home_mainTB;
    // DrawerLayout
    @BindView(R.id.activity_home_mainDL)
    DrawerLayout activity_home_mainDL;
    // ExpandableListView
    @BindView(R.id.activity_home_mainELV)
    AnimatedExpandableListView activity_home_mainELV;
    // NavigationView
    @BindView(R.id.activity_home_mainNV)
    NavigationView activity_home_mainNV;

    // String
    @BindString(R.string.press_back_again)
    String press_back_again;

    // Variables
    // ActionBarDrawerToggle
    private ActionBarDrawerToggle actionBarDrawerToggle;
    // List
    private List<NavigationMenu> headerList;
    private HashMap<NavigationMenu, List<NavigationMenu>> childList;
    // Boolean
    private boolean doubleBackToExitPressedOnce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initialFunctions();
    }

    private void initialFunctions() {
        setToolbar();
        declareVariables();
        setDrawer();
        setNavigationData();
        setExpandableList();
    }

    private void setToolbar() {
        setSupportActionBar(activity_home_mainTB);
    }

    private void declareVariables() {
        // ActionBarDrawerToggle
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, activity_home_mainDL, R.string.open, R.string.close);
        // List
        headerList = new ArrayList<>();
        childList = new HashMap<>();
        // Boolean
        doubleBackToExitPressedOnce = false;
    }

    private void setDrawer() {
        activity_home_mainDL.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                activity_home_mainDL.openDrawer(GravityCompat.START);
                return true;
            case R.id.menu_collection:
                showNewCollectionDialog();
                return true;
            case R.id.menu_word:
                showAddWordDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (activity_home_mainDL.isDrawerOpen(GravityCompat.START)) {
            activity_home_mainDL.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
            } else {
                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, press_back_again, Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
            }
        }
    }

    private void setNavigationData() {

        NavigationMenu collectionsMenu = new NavigationMenu("My Collections", R.drawable.ic_collections, true);
        headerList.add(collectionsMenu);
        List<NavigationMenu> childModelList = new ArrayList<>();
        NavigationMenu childModel = new NavigationMenu("English", 0, false);
        childModelList.add(childModel);

        childModel = new NavigationMenu("Spanish", 0, false);
        childModelList.add(childModel);

        if (collectionsMenu.isHasChildren()) {
            childList.put(collectionsMenu, childModelList);
        }

        NavigationMenu syncMenu = new NavigationMenu("Sync", R.drawable.ic_sync, false);
        headerList.add(syncMenu);
        childList.put(syncMenu, null);

        NavigationMenu findMenu = new NavigationMenu("Find", R.drawable.ic_find, false);
        headerList.add(findMenu);
        childList.put(findMenu, null);

        NavigationMenu settingsMenu = new NavigationMenu("Settings", R.drawable.ic_settings, false);
        headerList.add(settingsMenu);
        childList.put(settingsMenu, null);


        NavigationMenu aboutMenu = new NavigationMenu("About", R.drawable.ic_about, false);
        headerList.add(aboutMenu);
        childList.put(aboutMenu, null);
    }

    @SuppressWarnings("SameReturnValue")
    private void setExpandableList() {

        ExpandableListAdapter expandableListAdapter = new ExpandableListAdapter(this, headerList, childList);
        activity_home_mainELV.setAdapter(expandableListAdapter);

        activity_home_mainELV.setOnGroupClickListener((parent, v, groupPosition, id) -> {

            if (groupPosition != 0) {
                final Handler handler = new Handler();
                handler.postDelayed(() -> activity_home_mainDL.closeDrawer(GravityCompat.START), 200);
            }

            if (activity_home_mainELV.isGroupExpanded(groupPosition)) {
                activity_home_mainELV.collapseGroupWithAnimation(groupPosition);
            } else {
                activity_home_mainELV.expandGroupWithAnimation(groupPosition);
            }

            return true;
        });

        activity_home_mainELV.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {

            final Handler handler = new Handler();
            handler.postDelayed(() -> activity_home_mainDL.closeDrawer(GravityCompat.START), 200);

            return false;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    private void showNewCollectionDialog() {
        final AlertDialog dialog_new_collection = new AlertDialog.Builder(this).create();
        @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.dialog_new_collection, null);

        TextInputEditText dialog_new_collection_collectionNameET = view.findViewById(R.id.dialog_new_collection_collectionNameET);
        MaterialButton dialog_new_collection_createBTN = view.findViewById(R.id.dialog_new_collection_createBTN);

        showKeyboard(dialog_new_collection_collectionNameET);

        dialog_new_collection.setView(view);
        dialog_new_collection.setCanceledOnTouchOutside(false);
        dialog_new_collection.show();
    }

    private void showAddWordDialog() {
        final AlertDialog dialog_add_word = new AlertDialog.Builder(this).create();
        @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.dialog_add_word, null);

        TextInputEditText dialog_add_word_wordET = view.findViewById(R.id.dialog_add_word_wordET);
        TextInputEditText dialog_add_word_definitionET = view.findViewById(R.id.dialog_add_word_definitionET);
        MaterialButton dialog_add_word_addBTN = view.findViewById(R.id.dialog_add_word_addBTN);

        showKeyboard(dialog_add_word_wordET);

        dialog_add_word.setView(view);
        dialog_add_word.setCanceledOnTouchOutside(false);
        dialog_add_word.show();
    }

    private void showKeyboard(TextInputEditText textInputEditText) {
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            textInputEditText.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }, 200);
    }
}
