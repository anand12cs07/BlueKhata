package com.everyrupee.ui.dashboard.category.income;

import androidx.lifecycle.MutableLiveData;

import com.everyrupee.data.DataManager;
import com.everyrupee.data.model.db.Category;
import com.everyrupee.ui.base.BaseViewModel;
import com.everyrupee.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class IncomeCategoryViewModel extends BaseViewModel<IncomeCategoryFragmentNavigator> {
    private final MutableLiveData<List<Category>> incomeListLiveData;
    private final MutableLiveData<String> snackBarMessage;

    public IncomeCategoryViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        incomeListLiveData = new MutableLiveData<>();
        incomeListLiveData.setValue(new ArrayList<Category>());
        snackBarMessage = new MutableLiveData<>();
    }

    public void fetchIncomeList() {
        getCompositeDisposable().add(getDataManager()
                .getCategoryIncomes()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<Category>>() {
                    @Override
                    public void accept(List<Category> categories) throws Exception {
                        incomeListLiveData.setValue(categories);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        snackBarMessage.setValue("Error on fetching Income list");
                    }
                }));
    }

    public void updateDragCategoryList(int fromIndex, int toIndex, long id){
        getCompositeDisposable().add(getDataManager()
                .updateDragCategoryListShortIndex(fromIndex,toIndex,id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe());
    }

    public MutableLiveData<List<Category>> getIncomeListLiveData() {
        return incomeListLiveData;
    }

    public MutableLiveData<String> getSnackBarMessage() {
        return snackBarMessage;
    }

    public int getIncomeListSize(){
        return incomeListLiveData.getValue().size();
    }
}
