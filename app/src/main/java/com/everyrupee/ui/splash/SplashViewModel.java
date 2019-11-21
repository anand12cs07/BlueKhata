package com.everyrupee.ui.splash;

import com.everyrupee.data.DataManager;
import com.everyrupee.ui.base.BaseViewModel;
import com.everyrupee.utils.rx.SchedulerProvider;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class SplashViewModel extends BaseViewModel<SplashNavigator> {
    public SplashViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void startSeeding() {
        getCompositeDisposable().add(getDataManager()
                .seedReminders()
                .flatMap(new Function<Boolean, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(Boolean aBoolean) throws Exception {
                        return getDataManager().seedRecurrence();
                    }
                })
                .flatMap(new Function<Boolean, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(Boolean aBoolean) throws Exception {
                        return getDataManager().seedTags();
                    }
                })
                .flatMap(new Function<Boolean, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(Boolean aBoolean) throws Exception {
                        return getDataManager().seedCategory();
                    }
                })
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe());
    }
}
