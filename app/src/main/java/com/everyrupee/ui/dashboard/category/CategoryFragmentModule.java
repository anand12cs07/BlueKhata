package com.everyrupee.ui.dashboard.category;

import dagger.Module;
import dagger.Provides;

@Module
public class CategoryFragmentModule {

    @Provides
    CategoryPagerAdapter provideCategoryPagerAdapter(CategoryFragment categoryFragment){
        return new CategoryPagerAdapter(categoryFragment.getChildFragmentManager());
    }
}
