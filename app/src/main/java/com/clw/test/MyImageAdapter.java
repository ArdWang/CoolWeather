package com.clw.test;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by ${dongjunshuai} on 2017/6/13.
 */

public class MyImageAdapter extends PagerAdapter {

    private List<String> imageUrls;
    private AppCompatActivity activity;

    public MyImageAdapter(List<String> imageUrls, AppCompatActivity activity) {
        this.imageUrls = imageUrls;
        this.activity = activity;
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        String url = imageUrls.get(position);
        PhotoView photoView = new PhotoView(activity);
        Glide.with(activity)
                .load(url)
                .into(photoView);
        container.addView(photoView);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
        return photoView;
    }

    @Override
    public int getCount() {
        return imageUrls != null ? imageUrls.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
