package com.bluekhata.ui.addcategory;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by aman on 15-08-2018.
 */

public class AddCategoryPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<Fragment> fragmentList;

    public AddCategoryPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentList = new ArrayList<>();
    }

    public void addFragment(Fragment fragment) {
        fragmentList.add(fragment);
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        if (position < fragmentList.size()) {
            return fragmentList.get(position);
        }
        return null;
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
