package com.martiandeveloper.wordpool.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.martiandeveloper.wordpool.R;
import com.martiandeveloper.wordpool.tools.SplashAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    // UI Components
    // ViewPager
    @BindView(R.id.activity_splash_mainVP)
    ViewPager activity_splash_mainVP;
    // LinearLayout
    @BindView(R.id.activity_splash_dotLL)
    LinearLayout activity_splash_dotLL;
    // MaterialButton
    @BindView(R.id.activity_splash_nextBTN)
    MaterialButton activity_splash_nextBTN;
    @BindView(R.id.activity_splash_previousBTN)
    MaterialButton activity_splash_previousBTN;

    // String
    @BindString(R.string.done)
    String done;
    @BindString(R.string.next)
    String next;

    // Variables
    // List
    private List<Integer> splash_images;
    private List<String> splash_titles, splash_descriptions;
    private List<MaterialTextView> dotList;
    // Integer
    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        initialFunctions();
    }

    private void initialFunctions() {
        declareVariables();
        fillTHeImageList();
        setAdapter();
        addDotIndicator(0);
        setListeners();
        hidePreviousButton();
    }

    private void declareVariables() {
        splash_images = new ArrayList<>();
        splash_titles = Arrays.asList(getResources().getStringArray(R.array.titles));
        splash_descriptions = Arrays.asList(getResources().getStringArray(R.array.descriptions));
        dotList = new ArrayList<>();
    }

    private void fillTHeImageList() {
        splash_images.add(R.drawable.box);
        splash_images.add(R.drawable.diagram);
        splash_images.add(R.drawable.google);
    }

    private void setAdapter() {
        PagerAdapter pagerAdapter = new SplashAdapter(this, splash_images, splash_titles, splash_descriptions);
        activity_splash_mainVP.setAdapter(pagerAdapter);
        activity_splash_mainVP.setPageTransformer(true, (view, position) -> {

            int pageWidth = view.getWidth();

            if (position < -1) {
                view.setAlpha(1);
            } else if (position <= 1) {
                int pageWidthDividedBy10 = pageWidth / 10;
                int pageWidthDividedBy3 = pageWidth / 3;

                MaterialTextView layout_splash_descriptionTV = view.findViewById(R.id.layout_splash_descriptionTV);
                layout_splash_descriptionTV.setTranslationX(-position * pageWidthDividedBy10);

                LinearLayout layout_splash_mainLL = view.findViewById(R.id.layout_splash_mainLL);
                layout_splash_mainLL.setTranslationX(-position * pageWidthDividedBy3);
            } else {
                view.setAlpha(1);
            }
        });
    }

    private void addDotIndicator(int position) {
        activity_splash_dotLL.removeAllViews();

        for (int i = 0; i < splash_images.size(); i++) {
            dotList.add(new MaterialTextView(this));
            dotList.get(i).setText(Html.fromHtml("&#8226;"));
            dotList.get(i).setTextColor(getResources().getColor(R.color.colorThree));
            dotList.get(i).setTextSize(32);

            activity_splash_dotLL.addView(dotList.get(i));
        }

        if (dotList.size() > 0) {
            dotList.get(position).setTextColor(getResources().getColor(R.color.colorTwo));
        }
    }

    private void setListeners() {
        activity_splash_mainVP.addOnPageChangeListener(this);
    }

    private void hidePreviousButton() {
        activity_splash_previousBTN.setClickable(false);
        activity_splash_previousBTN.setVisibility(View.INVISIBLE);
    }

    private void showPreviousButton() {
        activity_splash_previousBTN.setClickable(true);
        activity_splash_previousBTN.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        addDotIndicator(position);
        page = position;

        if (page == 0) {
            hidePreviousButton();
        } else {
            showPreviousButton();
        }

        if (page == 2) {
            activity_splash_nextBTN.setText(done);
        } else {
            activity_splash_nextBTN.setText(next);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @OnClick(R.id.activity_splash_nextBTN)
    public void activity_main_nextBTNClicked() {
        if (page != 2) {
            activity_splash_mainVP.setCurrentItem(page + 1);
        } else {
            saveToSharedPreferences();
            go();
        }
    }

    private void saveToSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MainActivity.KEY, "yes");
        editor.apply();
    }

    private void go() {
        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        SplashActivity.this.finish();
    }

    @OnClick(R.id.activity_splash_previousBTN)
    public void activity_main_previousBTNClicked() {
        activity_splash_mainVP.setCurrentItem(page - 1);
    }
}
