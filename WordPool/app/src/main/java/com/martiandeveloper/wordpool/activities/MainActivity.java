package com.martiandeveloper.wordpool.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.martiandeveloper.wordpool.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    // UI Components
    @BindView(R.id.activity_main_mainIV)
    ImageView activity_main_mainIV;

    // Variables
    // Final
    public static final String SHARED_PREFERENCES = "Splash";
    public static final String KEY = "splash";
    // Boolean
    private String isSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initialFunctions();
    }

    private void initialFunctions() {
        loadImage();
        getSplashData();
        check();
    }

    private void loadImage() {
        Glide.with(this)
                .load(R.drawable.wordpool_logo)
                .priority(Priority.IMMEDIATE)
                .into(activity_main_mainIV);
    }

    private void getSplashData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        isSplash = sharedPreferences.getString(KEY, "no");
    }

    private void check() {
        if (isSplash.equals("no")) {
            go(SplashActivity.class);
        } else {
            go(HomeActivity.class);
        }
    }

    private void go(Class activity) {
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, activity);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            MainActivity.this.finish();
        }, 2000);
    }
}
