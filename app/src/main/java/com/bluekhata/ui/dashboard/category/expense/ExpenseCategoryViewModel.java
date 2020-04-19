package com.bluekhata.ui.dashboard.category.expense;

import androidx.lifecycle.MutableLiveData;

import com.bluekhata.data.DataManager;
import com.bluekhata.data.model.db.Category;
import com.bluekhata.ui.base.BaseViewModel;
import com.bluekhata.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class ExpenseCategoryViewModel extends BaseViewModel<ExpenseCategoryFragmentNavigator> {
    private final MutableLiveData<List<Category>> expenseListLiveData;
    private final MutableLiveData<String> snackBarMessage;

    public ExpenseCategoryViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        expenseListLiveData = new MutableLiveData<>();
        expenseListLiveData.setValue(new ArrayList<Category>());
        snackBarMessage = new MutableLiveData<>();
    }

    public void fetchExpenseList() {
        getCompositeDisposable().add(getDataManager()
                .getCategoryExpenses()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<Category>>() {
                    @Override
                    public void accept(List<Category> categories) throws Exception {
                        expenseListLiveData.setValue(categories);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        snackBarMessage.setValue("Error on fetching Expense list");
                    }
                }));
    }

    public void updateDragCategoryList(int fromIndex, int toIndex, long id) {
        getCompositeDisposable().add(getDataManager()
                .updateDragCategoryListShortIndex(fromIndex, toIndex, id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe());
    }

    public void updateCategoryList(List<Category> categoryList) {
        getCompositeDisposable().add(getDataManager()
                .updateCategoryList(categoryList)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }

    public MutableLiveData<List<Category>> getExpenseListLiveData() {
        return expenseListLiveData;
    }

    public MutableLiveData<String> getSnackBarMessage() {
        return snackBarMessage;
    }

    public int getExpenseListSize() {
        return expenseListLiveData.getValue().size();
    }
}
