package com.bluekhata.ui.recursive;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import dagger.Module;
import dagger.Provides;

@Module
public class RecursiveModule {
    @Provides
    DividerItemDecoration provideAddCategoryPagerAdapter(RecursiveTransactionActivity transactionActivity){
        return new DividerItemDecoration(transactionActivity, DividerItemDecoration.VERTICAL);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(RecursiveTransactionActivity transactionActivity){
        return new LinearLayoutManager(transactionActivity);
    }

    @Provides
    RecursiveTransactionAdapter provideTagAdapter(RecursiveTransactionActivity transactionActivity){
        return new RecursiveTransactionAdapter(transactionActivity);
    }
}
