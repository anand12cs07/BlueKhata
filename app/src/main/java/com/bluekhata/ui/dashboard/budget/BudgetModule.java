package com.bluekhata.ui.dashboard.budget;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bluekhata.ui.dashboard.home.wallets.settings.WalletSettingActivity;
import com.bluekhata.ui.dashboard.home.wallets.settings.WalletSettingAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class BudgetModule {
    @Provides
    DividerItemDecoration provideItemDecoration(BudgetActivity activity){
        return new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(BudgetActivity activity){
        return new LinearLayoutManager(activity);
    }

    @Provides
    BudgetAdapter provideBudgetAdapter(BudgetActivity activity){
        return new BudgetAdapter(activity);
    }
}
