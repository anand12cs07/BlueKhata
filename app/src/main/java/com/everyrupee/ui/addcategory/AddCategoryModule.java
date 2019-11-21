package com.everyrupee.ui.addcategory;

import dagger.Module;
import dagger.Provides;

@Module
public class AddCategoryModule {
    @Provides
    AddCategoryPagerAdapter provideAddCategoryPagerAdapter(AddCategoryActivity categoryActivity){
        return new AddCategoryPagerAdapter(categoryActivity.getSupportFragmentManager());
    }
}
