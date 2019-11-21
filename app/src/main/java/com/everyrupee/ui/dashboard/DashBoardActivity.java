package com.everyrupee.ui.dashboard;

import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.IdRes;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.Fragment;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.everyrupee.ui.dashboard.home.HomeFragment;
import com.everyrupee.utils.AppUtils;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.everyrupee.BR;
import com.everyrupee.R;
import com.everyrupee.ViewModelProviderFactory;
import com.everyrupee.databinding.ActivityDashBoardBinding;
import com.everyrupee.di.dependency.ActivityDependency;
import com.everyrupee.di.dependency.AppDependency;
import com.everyrupee.ui.base.BaseActivity;
import com.everyrupee.ui.dashboard.category.CategoryFragment;
import com.everyrupee.ui.dashboard.history.HistoryFragment;
import com.everyrupee.ui.dashboard.history.filter.HistoryFilterListener;
import com.everyrupee.ui.setting.SettingsActivity;
import com.everyrupee.ui.tags.TagActivity;
import com.everyrupee.ui.upcoming.UpcomingTransactionActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;


public class DashBoardActivity extends BaseActivity<ActivityDashBoardBinding, DashBoardViewModel> implements
        NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener,
        HasSupportFragmentInjector, HistoryFilterListener, RefreshListOnDismiss,View.OnClickListener {
    private static final String TAG = "DashBoard";
    @Inject
    AppDependency appDependency; // same object from App

    @Inject
    ActivityDependency activityDependency;

    @Inject
    ViewModelProviderFactory factory;
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    private ActivityDashBoardBinding mActivityDashBoardBinding;
    private DashBoardViewModel mDashBoardViewModel;

    private Toolbar mToolbar;
    private DrawerLayout drawerLayout;
    private AppCompatSpinner mSpinner;
    private NavigationView navigationView;
    private BottomNavigationView mBottomNavigationView;

    public static Intent newIntent(Context context) {
        return new Intent(context, DashBoardActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_dash_board;
    }

    @Override
    public DashBoardViewModel getViewModel() {
        mDashBoardViewModel = ViewModelProviders.of(this, factory).get(DashBoardViewModel.class);
        return mDashBoardViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityDashBoardBinding = getViewDataBinding();

        attachView();

        setTitle("");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);

        mActivityDashBoardBinding.tvVersion.setText("Ver. " + getCurrentVersionName());

        setUpUpdateText();

        AppUtils.setFragment(new HomeFragment(), true, false, this, R.id.container, HomeFragment.TAG);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStackImmediate();
            } else {
                super.onBackPressed();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_tags:
                startActivity(TagActivity.newIntent(this));
                break;
            case R.id.nav_upcoming:
                startActivity(UpcomingTransactionActivity.newIntent(this));
                break;
//            case R.id.nav_recursive:
//                startActivity(RecursiveTransactionActivity.newIntent(this));
//                break;
            case R.id.nav_setting:
                startActivity(SettingsActivity.newIntent(this));
                break;
            case R.id.nav_rateus:
//                new DialogRateUs(this).show();
                rateUsIntent();
                break;
            case R.id.nav_share:
                shareIntent();
                break;
            case R.id.nav_follow:
                followUsIntent();
                break;
            case R.id.bnv_home:
                showHome();
                break;
            case R.id.bnv_category:
                showCategories();
                break;
            case R.id.bnv_history:
                showHistory();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void sendFilterData(long[] categoryIds, long startDate, long endDate) {
        HistoryFragment historyFragment = (HistoryFragment) getSupportFragmentManager().findFragmentByTag(HistoryFragment.TAG);
        if (historyFragment != null) {
            historyFragment.sendFilterData(categoryIds, startDate, endDate);
        }
    }

    @Override
    public void onDismiss() {
        HomeFragment homeFragment = (HomeFragment)getSupportFragmentManager().findFragmentByTag(HomeFragment.TAG);
        if (homeFragment != null){
            homeFragment.onDismiss();
        }
    }

    @Override
    public void onClick(View view) {
        rateUsIntent();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    public void setTitle(String title) {
        mDashBoardViewModel.setAppTitle(title);
    }

    public void selectBottomNavigationMenu(@IdRes int resId) {
        mBottomNavigationView.setOnNavigationItemSelectedListener(null);
        mBottomNavigationView.setSelectedItemId(resId);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    public void fabShow(FloatingActionButton fab) {
        fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }

    public void fabHide(FloatingActionButton fab) {

        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
        int fabMargin = lp.bottomMargin;
        fab.animate().translationY(fab.getHeight() + fabMargin).setInterpolator(new AccelerateInterpolator(2.0f)).start();

    }

    public AppCompatSpinner getSpinner() {
        return mSpinner;
    }

    private void attachView() {
        mToolbar = (Toolbar) mActivityDashBoardBinding.dashboardToolbar;
        mSpinner = (AppCompatSpinner) mActivityDashBoardBinding.dashBoardSpinner;
        drawerLayout = (DrawerLayout) mActivityDashBoardBinding.drawerLayout;
        navigationView = (NavigationView) mActivityDashBoardBinding.navView;
        mBottomNavigationView = (BottomNavigationView) mActivityDashBoardBinding.bottomNavMenu;

        setSupportActionBar(mToolbar);
    }

    private void showHome() {
        HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(HomeFragment.TAG);
        if (homeFragment == null || !homeFragment.isVisible()) {
            AppUtils.setFragment(new HomeFragment(), true, false,
                    this, R.id.container, HomeFragment.TAG);
        }
    }

    private void showCategories() {
        CategoryFragment categoryFragment = (CategoryFragment) getSupportFragmentManager().findFragmentByTag(CategoryFragment.TAG);
        if (categoryFragment == null || !categoryFragment.isVisible()) {
            AppUtils.setFragment(new CategoryFragment(), true, true,
                    this, R.id.container, CategoryFragment.TAG);
        }
    }

    private void showHistory() {
        HistoryFragment historyFragment = (HistoryFragment) getSupportFragmentManager().findFragmentByTag(HistoryFragment.TAG);
        if (historyFragment == null || !historyFragment.isVisible()) {
            AppUtils.setFragment(new HistoryFragment(), true, true, this, R.id.container, HistoryFragment.TAG);
        }
    }

    private void rateUsIntent() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }

    private void shareIntent() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "Hey, \nEveryRupee is fast, simple and very unique app that I use " +
                        "to track my expense and income on daily basis. \n \n " +
                        "Get it for free at \n https://play.google.com/store/apps/details?id=" + getPackageName());
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    private void followUsIntent() {
        String url = "https://twitter.com/everyrupee";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private String getCurrentVersionName() {
        try {
            return getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setUpUpdateText() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("update_status");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String updatedVersion = dataSnapshot.getValue(String.class);
                Boolean value = getCurrentVersionName().compareTo(updatedVersion) < 0;
                mActivityDashBoardBinding.tvVersion.setText(value ? "Update Available" : "Ver. " + getCurrentVersionName());
                mActivityDashBoardBinding.tvVersion.setOnClickListener(value ? DashBoardActivity.this : null);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

}
