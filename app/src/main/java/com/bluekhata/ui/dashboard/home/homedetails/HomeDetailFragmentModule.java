package com.bluekhata.ui.dashboard.home.homedetails;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeDetailFragmentModule {

    @Provides
    HomeDetailAdapter provideHomeDetailAdapter(HomeDetailFragment homeFragment){
        return new HomeDetailAdapter();
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(HomeDetailFragment fragment){
        return new LinearLayoutManager(fragment.getContext());
    }

    @Provides
    DividerItemDecoration provideDividerItemDecoration(HomeDetailFragment fragment){
        return new DividerItemDecoration(fragment.getContext(),DividerItemDecoration.VERTICAL);
    }
}
