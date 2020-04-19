package com.bluekhata.ui.dashboard.history.filter;

import androidx.lifecycle.MutableLiveData;

import com.bluekhata.data.model.db.Category;
import com.bluekhata.ui.dashboard.DashBoardNavigator;
import com.bluekhata.data.DataManager;
import com.bluekhata.ui.base.BaseViewModel;
import com.bluekhata.utils.rx.SchedulerProvider;

import java.util.List;

import io.reactivex.functions.Consumer;

public class HistoryFilterViewModel extends BaseViewModel<DashBoardNavigator> {

    private final MutableLiveData<List<Category>> categoryList = new MutableLiveData<>();
    private final MutableLiveData<String> snackBarMessage = new MutableLiveData<>();

    public HistoryFilterViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void fetchAllCategory() {
        getCompositeDisposable().add(getDataManager()
                .getAllCategory()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<Category>>() {
                    @Override
                    public void accept(List<Category> categories) throws Exception {
                        categoryList.setValue(categories);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        snackBarMessage.setValue("All category not fetched");
                    }
                }));
    }

    public void fetchExpenseList() {
        getCompositeDisposable().add(getDataManager()
                .getCategoryExpenses()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<Category>>() {
                    @Override
                    public void accept(List<Category> categories) throws Exception {
                        categoryList.setValue(categories);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        snackBarMessage.setValue("Expense category not fetched");
                    }
                }));
    }

    public void fetchIncomeList(){
        getCompositeDisposable().add(getDataManager()
                .getCategoryIncomes()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<Category>>() {
                    @Override
                    public void accept(List<Category> categories) throws Exception {
                        categoryList.setValue(categories);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        snackBarMessage.setValue("Income category not fetched");
                    }
                }));
    }

    public MutableLiveData<List<Category>> getCategoryList() {
        return categoryList;
    }
}
