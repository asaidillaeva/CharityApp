package com.example.charityapp;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.charityapp.ui.help.HelpFragment;
import com.example.charityapp.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.List;


public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new HelpFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
    }

}