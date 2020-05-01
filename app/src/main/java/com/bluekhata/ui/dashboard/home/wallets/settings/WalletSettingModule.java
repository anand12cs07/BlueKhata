package com.bluekhata.ui.dashboard.home.wallets.settings;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bluekhata.ui.dashboard.home.wallets.WalletAdapter;
import com.bluekhata.ui.tags.TagActivity;
import com.bluekhata.ui.tags.TagAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class WalletSettingModule {
    @Provides
    DividerItemDecoration provideAddCategoryPagerAdapter(WalletSettingActivity activity){
        return new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(WalletSettingActivity activity){
        return new LinearLayoutManager(activity);
    }

    @Provides
    WalletSettingAdapter provideTagAdapter(WalletSettingActivity activity){
        return new WalletSettingAdapter(activity);
    }
}
