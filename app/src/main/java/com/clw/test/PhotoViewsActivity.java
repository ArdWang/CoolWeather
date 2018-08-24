package com.clw.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.clw.R;
import com.clw.test.MyImageAdapter;
import com.clw.ui.adapter.like.PhotoViewPager;

import java.util.List;

public class PhotoViewsActivity extends AppCompatActivity{

    private List<String> result;

    private MyImageAdapter myImageAdapter;

    private PhotoViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoview);

        initView();
        initData();
    }

    private void initView(){
        viewPager = findViewById(R.id.mPhotoView);
    }

    private void initData() {
        result = getIntent().getStringArrayListExtra("imgUrls");
        myImageAdapter = new MyImageAdapter(result,this);
        viewPager.setAdapter(myImageAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                //currentPosition = position;
                //mTvImageCount.setText(currentPosition + 1 + "/" + Urls.size());
            }
        });


    }
}
