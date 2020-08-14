package com.martiandeveloper.wordpool.tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.google.android.material.textview.MaterialTextView;
import com.martiandeveloper.wordpool.R;

import java.util.List;

public class SplashAdapter extends PagerAdapter {

    private final Context context;

    private final List<Integer> splash_images;
    private final List<String> splash_titles, splash_descriptions;

    public SplashAdapter(Context context, List<Integer> splash_images, List<String> splash_titles, List<String> splash_descriptions) {
        this.context = context;
        this.splash_images = splash_images;
        this.splash_titles = splash_titles;
        this.splash_descriptions = splash_descriptions;
    }

    @Override
    public int getCount() {
        return splash_titles.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        View view = layoutInflater.inflate(R.layout.layout_splash, container, false);

        ImageView layout_splash_mainIV = view.findViewById(R.id.layout_splash_mainIV);
        MaterialTextView layout_splash_titleTV = view.findViewById(R.id.layout_splash_titleTV);
        MaterialTextView layout_splash_descriptionTV = view.findViewById(R.id.layout_splash_descriptionTV);

        Glide.with(context)
                .load(splash_images.get(position))
                .priority(Priority.IMMEDIATE)
                .into(layout_splash_mainIV);

        layout_splash_titleTV.setText(splash_titles.get(position));
        layout_splash_descriptionTV.setText(splash_descriptions.get(position));

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
