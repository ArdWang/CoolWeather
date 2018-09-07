package com.clw.test;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.List;

/**
 * Created by Dangelo on 2016/9/24.
 */
public class NewsoAdapter extends FragmentPagerAdapter {

    private List<String> titles;
    private List<Fragment> newsFragmentList;

    public NewsoAdapter(FragmentManager fm, List<String> titles, List<Fragment> newsFragmentList) {
        super(fm);
        this.titles = titles;
        this.newsFragmentList = newsFragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return newsFragmentList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getCount() {
        return newsFragmentList.size();
    }
}
