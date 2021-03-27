package com.bluekhata.ui.dashboard.home;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bluekhata.ui.category.CategoryAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeFragmentModule {


    @Provides
    HomeAdapter provideCategoryPagerAdapter(HomeFragment homeFragment){
        return new HomeAdapter(homeFragment.getActivity());
    }

    @Provides
    CategoryAdapter provideCategoryAdapter(HomeFragment homeFragment){
        return new CategoryAdapter(homeFragment.getActivity());
    }

    @Provides
    RecommendedProductAdapter provideRecommendedProductAdapter(HomeFragment homeFragment){
        return new RecommendedProductAdapter(homeFragment.getActivity());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(HomeFragment fragment){
        return new LinearLayoutManager(fragment.getContext());
    }

    @Provides
    DividerItemDecoration provideDividerItemDecoration(HomeFragment fragment){
        return new DividerItemDecoration(fragment.getContext(),DividerItemDecoration.VERTICAL);
    }
}
