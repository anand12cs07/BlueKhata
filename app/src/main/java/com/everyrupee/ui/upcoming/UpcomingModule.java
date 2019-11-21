package com.everyrupee.ui.upcoming;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import dagger.Module;
import dagger.Provides;

@Module
public class UpcomingModule {
    @Provides
    DividerItemDecoration provideAddCategoryPagerAdapter(UpcomingTransactionActivity transactionActivity){
        return new DividerItemDecoration(transactionActivity, DividerItemDecoration.VERTICAL);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(UpcomingTransactionActivity transactionActivity){
        return new LinearLayoutManager(transactionActivity);
    }

    @Provides
    UpcomingTransactionAdapter provideTagAdapter(UpcomingTransactionActivity transactionActivity){
        return new UpcomingTransactionAdapter(transactionActivity);
    }
}
