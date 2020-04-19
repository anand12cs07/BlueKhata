package com.bluekhata.ui.tags;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import dagger.Module;
import dagger.Provides;

@Module
public class TagModule {
    @Provides
    DividerItemDecoration provideAddCategoryPagerAdapter(TagActivity tagActivity){
        return new DividerItemDecoration(tagActivity, DividerItemDecoration.VERTICAL);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(TagActivity tagActivity){
        return new LinearLayoutManager(tagActivity);
    }

    @Provides
    TagAdapter provideTagAdapter(TagActivity tagActivity){
        return new TagAdapter(tagActivity);
    }
}
