package com.bluekhata.ui.dashboard.history.filter;

import androidx.recyclerview.widget.DividerItemDecoration;

import com.bluekhata.utils.LinearLayoutManagerWrapper;

import dagger.Module;
import dagger.Provides;

@Module
public class HistoryFilterModule {


    @Provides
    HistoryFilterAdapter provideHistoryAdapter(HistoryFilterBottomSheet homeFragment){
        return new HistoryFilterAdapter(homeFragment.getActivity());
    }

    @Provides
    LinearLayoutManagerWrapper provideLinearLayoutManager(HistoryFilterBottomSheet fragment){
        return new LinearLayoutManagerWrapper(fragment.getContext());
    }

    @Provides
    DividerItemDecoration provideDividerItemDecoration(HistoryFilterBottomSheet fragment){
        return new DividerItemDecoration(fragment.getContext(),DividerItemDecoration.VERTICAL);
    }
}
