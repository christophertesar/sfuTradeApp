package com.example.myfirstapp;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;


import java.util.ArrayList;
import java.util.List;

/*
     * This file is downloaded from the following location:
     *     https://github.com/mitchtabian/ForSale/blob/bf047ab98c75b36baedc950f56c104aa0aaa95b1/app/src/main/java/codingwithmitch/com/forsale/util/SectionsPagerAdapter.java
     * The source code has not been modified.
     * The file has the following copyright from the original author:
     *     N/A
     */

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = "SectionsPagerAdapter";

    private final List<Fragment> mFragmentList = new ArrayList<>();

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment){
        mFragmentList.add(fragment);
    }
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
