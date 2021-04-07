package com.bluekhata.ui.dashboard.home.homedetails;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bluekhata.ui.dashboard.history.HistoryFragment;
import com.bluekhata.utils.LinearLayoutManagerWrapper;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeDetailFragmentModule {

    @Provides
    HomeDetailAdapter provideHomeDetailAdapter(HomeDetailFragment homeFragment){
        return new HomeDetailAdapter();
    }

    @Provides
    LinearLayoutManagerWrapper provideLinearLayoutManager(HomeDetailFragment fragment){
        return new LinearLayoutManagerWrapper(fragment.getContext());
    }

    @Provides
    DividerItemDecoration provideDividerItemDecoration(HomeDetailFragment fragment){
        return new DividerItemDecoration(fragment.getContext(),DividerItemDecoration.VERTICAL);
    }
}
