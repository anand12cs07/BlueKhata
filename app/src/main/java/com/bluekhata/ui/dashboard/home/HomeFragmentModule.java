package com.bluekhata.ui.dashboard.home;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeFragmentModule {


    @Provides
    HomeAdapter provideCategoryPagerAdapter(HomeFragment homeFragment){
        return new HomeAdapter(homeFragment.getActivity());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(HomeFragment fragment){
        return new LinearLayoutManager(fragment.getContext());
    }

    @Provides
    DividerItemDecoration provideDividerItemDecoration(HomeFragment fragment){
        return new DividerItemDecoration(fragment.getContext(),DividerItemDecoration.VERTICAL);
    }
}
