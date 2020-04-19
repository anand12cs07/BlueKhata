package com.bluekhata.ui.addcategory;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bluekhata.ViewModelProviderFactory;
import com.bluekhata.ui.addcategory.icons.IconFragment;
import com.bluekhata.ui.base.BaseActivity;
import com.bluekhata.ui.base.BaseAlertButtonClickListener;
import com.bluekhata.BR;
import com.bluekhata.R;
import com.bluekhata.databinding.ActivityAddCategoryBinding;
import com.bluekhata.ui.addcategory.colors.ColorFragment;

import javax.inject.Inject;

public class AddCategoryActivity extends BaseActivity<ActivityAddCategoryBinding, AddCategoryViewModel>
        implements ViewPager.OnPageChangeListener, TabLayout.OnTabSelectedListener,
        View.OnClickListener, OnIconListener, BaseAlertButtonClickListener {

    public static final String CATEGORY_ID = "categoryId";
    public static final String CATEGORY_TYPE = "categoryType";

    @Inject
    ViewModelProviderFactory factory;
    @Inject
    AddCategoryPagerAdapter adapter;

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FloatingActionButton fab;
    private Toolbar toolbar;

    private AddCategoryViewModel viewModel;
    private ActivityAddCategoryBinding addCategoryBinding;

    public static Intent newIntent(Context context, String categoryType, long categoryID) {
        Intent intent = new Intent(context, AddCategoryActivity.class);
        intent.putExtra(CATEGORY_ID, categoryID);
        intent.putExtra(CATEGORY_TYPE, categoryType);
        return intent;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_category;
    }

    @Override
    public AddCategoryViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(AddCategoryViewModel.class);
        return viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addCategoryBinding = getViewDataBinding();

        attachViews();
        setUpSnackBarMessage();
        alertButtonClickListener = this;

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabLayout.addTab(tabLayout.newTab().setText("Icons"));
        tabLayout.addTab(tabLayout.newTab().setText("Colors"));

        viewModel.setCategoryType(getCategoryType());
        adapter.addFragment(new IconFragment());
        adapter.addFragment(new ColorFragment());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        //Adding onTabSelectedListener to swipe views
        tabLayout.addOnTabSelectedListener(this);
        viewPager.addOnPageChangeListener(this);
        addCategoryBinding.layoutDel.setOnClickListener(this);

        viewModel.loadData(getIntent().getLongExtra(CATEGORY_ID, -1));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_category, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_ok:
                if (viewModel.saveCategory()) {
                    finish();
                }
                break;
        }
        return false;
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
    public void onClick(View view) {
       showAlertDialog();
    }

    @Override
    public void getFabColor(int color) {
        viewModel.setCategoryColor(getColorFromResource(color));
        Log.e("category>", getColorFromResource(color));

    }

    @Override
    public void getFabDrawable(int resDrawable) {
        viewModel.setCategoryIcon(resourceIdToResourceString(resDrawable));
        Log.e("category>",resourceIdToResourceString(resDrawable));
    }

    @Override
    public void onPositionClickListener() {
        if (viewModel.deleteCategory()) {
            finish();
        }
    }

    @Override
    public void onNegativeClickListener() {
        hideAlertDialog();
    }

    public OnIconListener getListener() {
        return this;
    }

    private void attachViews() {
        tabLayout = (TabLayout) addCategoryBinding.tabLayout;
        viewPager = (ViewPager) addCategoryBinding.viewPager;
        toolbar = (Toolbar) addCategoryBinding.toolbar;
        fab = (FloatingActionButton) addCategoryBinding.fab;
    }

    private void setUpSnackBarMessage() {
        viewModel.getSnackBarMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Snackbar.make(addCategoryBinding.parentLayout, s, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private String getCategoryType() {
        return getIntent().getExtras().getString(CATEGORY_TYPE);
    }

    private String resourceIdToResourceString(int resId) {
        try {
            return getResources().getResourceEntryName(resId);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private String getColorFromResource(int colorRes) {
        return "#" + Integer.toHexString(colorRes).toUpperCase();
    }

}
