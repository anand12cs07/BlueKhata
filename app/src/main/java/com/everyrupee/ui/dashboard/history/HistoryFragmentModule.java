package com.everyrupee.ui.dashboard.history;

import androidx.recyclerview.widget.DividerItemDecoration;

import com.everyrupee.utils.LinearLayoutManagerWrapper;

import dagger.Module;
import dagger.Provides;

@Module
public class HistoryFragmentModule {


    @Provides
    HistoryAdapter provideHistoryAdapter(HistoryFragment homeFragment){
        return new HistoryAdapter(homeFragment.getActivity());
    }

    @Provides
    LinearLayoutManagerWrapper provideLinearLayoutManager(HistoryFragment fragment){
        return new LinearLayoutManagerWrapper(fragment.getContext());
    }

    @Provides
    DividerItemDecoration provideDividerItemDecoration(HistoryFragment fragment){
        return new DividerItemDecoration(fragment.getContext(),DividerItemDecoration.VERTICAL);
    }
}
