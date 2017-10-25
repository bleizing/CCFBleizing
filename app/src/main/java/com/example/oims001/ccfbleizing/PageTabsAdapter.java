package com.example.oims001.ccfbleizing;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PageTabsAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragList = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    public PageTabsAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        return fragList.get(position);
    }

    @Override
    public int getCount() {
        return fragList.size();
    }

    public void addFrag(Fragment fragment, String title) {
        fragList.add(fragment);
        titles.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
        //return null;
    }
}