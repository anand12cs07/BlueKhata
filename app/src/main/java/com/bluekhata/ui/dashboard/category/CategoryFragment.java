package com.bluekhata.ui.dashboard.category;


import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluekhata.ui.dashboard.DashBoardActivity;
import com.bluekhata.BR;
import com.bluekhata.R;
import com.bluekhata.ViewModelProviderFactory;
import com.bluekhata.databinding.FragmentCategoriesBinding;
import com.bluekhata.di.dependency.ActivityDependency;
import com.bluekhata.di.dependency.AppDependency;
import com.bluekhata.di.dependency.FragmentDependency;
import com.bluekhata.ui.base.BaseFragment;
import com.bluekhata.ui.dashboard.category.expense.ExpenseCategoryFragment;
import com.bluekhata.ui.dashboard.category.income.IncomeCategoryFragment;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class CategoryFragment extends BaseFragment<FragmentCategoriesBinding, CategoryViewModel> implements
        TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener,HasSupportFragmentInjector {
    public static final String TAG = "CategoryFragment";

    @Inject
    AppDependency appDependency; // same object from App

    @Inject
    ActivityDependency activityDependency; // same object from MainActivity

    @Inject
    FragmentDependency fragmentDependency;

    @Inject
    CategoryPagerAdapter adapter;
    @Inject
    DispatchingAndroidInjector<Fragment> childFragmentInjector;

    @Inject
    ViewModelProviderFactory factory;

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private DashBoardActivity dashBoardActivity;
    private FragmentCategoriesBinding fragmentCategoriesBinding;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_categories;
    }

    @Override
    public CategoryViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(CategoryViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater,container,savedInstanceState);

        fragmentCategoriesBinding = getViewDataBinding();

        dashBoardActivity = ((DashBoardActivity) getActivity());
        dashBoardActivity.setTitle("Categories");

        viewPager = fragmentCategoriesBinding.viewPager;
        tabLayout = fragmentCategoriesBinding.tabLayout;

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("Expense"));
        tabLayout.addTab(tabLayout.newTab().setText("Income"));

        setHasOptionsMenu(true);

        adapter.addFragment(new ExpenseCategoryFragment());
        adapter.addFragment(new IncomeCategoryFragment());

        viewPager.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(this);
        viewPager.addOnPageChangeListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((DashBoardActivity) getActivity()).selectBottomNavigationMenu(R.id.bnv_category);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition(), true);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tabLayout.getTabAt(position).select();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return childFragmentInjector;
    }
}
