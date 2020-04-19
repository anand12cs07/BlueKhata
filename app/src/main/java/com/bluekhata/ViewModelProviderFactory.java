package com.bluekhata;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.bluekhata.data.DataManager;
import com.bluekhata.ui.dashboard.DashBoardViewModel;
import com.bluekhata.ui.dashboard.category.CategoryViewModel;
import com.bluekhata.ui.dashboard.category.expense.ExpenseCategoryViewModel;
import com.bluekhata.ui.dashboard.category.income.IncomeCategoryViewModel;
import com.bluekhata.ui.dashboard.history.HistoryViewModel;
import com.bluekhata.ui.dashboard.history.filter.HistoryFilterViewModel;
import com.bluekhata.ui.dashboard.home.HomeViewModel;
import com.bluekhata.ui.dashboard.home.homedetails.HomeDetailViewModel;
import com.bluekhata.ui.dashboard.transaction.TransactionViewModel;
import com.bluekhata.ui.dashboard.transaction.datedialog.DateOptionViewModel;
import com.bluekhata.ui.recursive.RecursiveActivityViewModel;
import com.bluekhata.ui.setting.SettingActivityViewModel;
import com.bluekhata.ui.addcategory.AddCategoryViewModel;
import com.bluekhata.ui.splash.SplashViewModel;
import com.bluekhata.ui.tags.TagActivityViewModel;
import com.bluekhata.ui.upcoming.UpcomingActivityViewModel;
import com.bluekhata.utils.rx.SchedulerProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ViewModelProviderFactory extends ViewModelProvider.NewInstanceFactory {

  private final DataManager dataManager;
  private final SchedulerProvider schedulerProvider;

  @Inject
  public ViewModelProviderFactory(DataManager dataManager,
                                  SchedulerProvider schedulerProvider) {
    this.dataManager = dataManager;
    this.schedulerProvider = schedulerProvider;
  }


  @Override
  public <T extends ViewModel> T create(Class<T> modelClass) {
    if (modelClass.isAssignableFrom(SplashViewModel.class)) {
      //noinspection unchecked
      return (T) new SplashViewModel(dataManager,schedulerProvider);
    }
    else if (modelClass.isAssignableFrom(DashBoardViewModel.class)) {
      //noinspection unchecked
      return (T) new DashBoardViewModel(dataManager,schedulerProvider);
    }
    else if (modelClass.isAssignableFrom(HomeViewModel.class)) {
      //noinspection unchecked
      return (T) new HomeViewModel(dataManager,schedulerProvider);
    }
    else if (modelClass.isAssignableFrom(HomeDetailViewModel.class)) {
      //noinspection unchecked
      return (T) new HomeDetailViewModel(dataManager,schedulerProvider);
    }
    else if (modelClass.isAssignableFrom(TransactionViewModel.class)) {
      //noinspection unchecked
      return (T) new TransactionViewModel(dataManager,schedulerProvider);
    }
    else if (modelClass.isAssignableFrom(DateOptionViewModel.class)) {
      //noinspection unchecked
      return (T) new DateOptionViewModel(dataManager,schedulerProvider);
    }
    else if (modelClass.isAssignableFrom(CategoryViewModel.class)) {
      //noinspection unchecked
      return (T) new CategoryViewModel(dataManager,schedulerProvider);
    }
    else if (modelClass.isAssignableFrom(ExpenseCategoryViewModel.class)) {
      //noinspection unchecked
      return (T) new ExpenseCategoryViewModel(dataManager,schedulerProvider);
    } else if (modelClass.isAssignableFrom(IncomeCategoryViewModel.class)) {
      //noinspection unchecked
      return (T) new IncomeCategoryViewModel(dataManager,schedulerProvider);
    }
    else if (modelClass.isAssignableFrom(AddCategoryViewModel.class)) {
      //noinspection unchecked
      return (T) new AddCategoryViewModel(dataManager,schedulerProvider);
    }
    else if (modelClass.isAssignableFrom(HistoryViewModel.class)) {
      //noinspection unchecked
      return (T) new HistoryViewModel(dataManager,schedulerProvider);
    }
    else if (modelClass.isAssignableFrom(HistoryFilterViewModel.class)) {
      //noinspection unchecked
      return (T) new HistoryFilterViewModel(dataManager,schedulerProvider);
    }
    else if (modelClass.isAssignableFrom(SettingActivityViewModel.class)) {
      //noinspection unchecked
      return (T) new SettingActivityViewModel(dataManager,schedulerProvider);
    }
    else if (modelClass.isAssignableFrom(TagActivityViewModel.class)) {
      //noinspection unchecked
      return (T) new TagActivityViewModel(dataManager,schedulerProvider);
    }
    else if (modelClass.isAssignableFrom(UpcomingActivityViewModel.class)) {
      //noinspection unchecked
      return (T) new UpcomingActivityViewModel(dataManager,schedulerProvider);
    }
    else if (modelClass.isAssignableFrom(RecursiveActivityViewModel.class)) {
      //noinspection unchecked
      return (T) new RecursiveActivityViewModel(dataManager,schedulerProvider);
    }

    throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
  }
}