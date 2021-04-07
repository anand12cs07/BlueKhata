package com.bluekhata.ui.dashboard.home;

import androidx.lifecycle.MutableLiveData;

import com.bluekhata.data.DataManager;
import com.bluekhata.data.local.prefs.AppPreferencesHelper;
import com.bluekhata.data.model.db.Category;
import com.bluekhata.data.model.db.custom.TransactionWithCategory;
import com.bluekhata.ui.base.BaseViewModel;
import com.bluekhata.utils.rx.SchedulerProvider;

import java.util.List;

import io.reactivex.functions.Consumer;

public class HomeViewModel extends BaseViewModel<HomeNavigator> {
    private final MutableLiveData<List<TransactionWithCategory>> expenseWithCategory = new MutableLiveData<>();
    private final MutableLiveData<List<TransactionWithCategory>> incomeWithCategory = new MutableLiveData<>();
    private final MutableLiveData<List<Category>> categoryMutableLiveData = new MutableLiveData<>();

    private final MutableLiveData<String> snackBarMessage = new MutableLiveData<>();
    private final MutableLiveData<Double> headerExpense = new MutableLiveData<>();
    private final MutableLiveData<Double> headerIncome = new MutableLiveData<>();

    public HomeViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void fetchExpenseList(long startDate, long endDate){
        getCompositeDisposable().add(getDataManager()
        .getAllExpenseTransactionWithCategory(startDate,endDate)
        .subscribeOn(getSchedulerProvider().io())
        .observeOn(getSchedulerProvider().ui())
        .subscribe(new Consumer<List<TransactionWithCategory>>() {
            @Override
            public void accept(List<TransactionWithCategory> transactionWithCategories) throws Exception {
                expenseWithCategory.setValue(transactionWithCategories);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                snackBarMessage.setValue("Error while loading Expenses");
            }
        }));
    }

    public void fetchIncomeList(long startDate, long endDate){
        getCompositeDisposable().add(getDataManager()
                .getAllIncomeTransactionWithCategory(startDate,endDate)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<TransactionWithCategory>>() {
                    @Override
                    public void accept(List<TransactionWithCategory> transactionWithCategories) throws Exception {
                        incomeWithCategory.setValue(transactionWithCategories);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        snackBarMessage.setValue("Error while loading Income");
                    }
                }));
    }

    public void fetchRecommendedCategories(){
        getCompositeDisposable().add(getDataManager()
                .getCategoryExpenses()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<Category>>() {
                    @Override
                    public void accept(List<Category> categories) throws Exception {
                        categoryMutableLiveData.setValue(categories);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        snackBarMessage.setValue("Error while loading recommended categories");
                    }
                }));
    }

    public void fetchHeaderExpenses(long startDate, long endDate){
        getCompositeDisposable().add(getDataManager()
        .getExpenseHeader(startDate,endDate)
        .subscribeOn(getSchedulerProvider().io())
        .observeOn(getSchedulerProvider().ui())
        .subscribe(new Consumer<Double>() {
            @Override
            public void accept(Double aDouble) throws Exception {
                headerExpense.setValue(aDouble);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                snackBarMessage.setValue("Error while parsing Expense");
            }
        }));
    }

    public void fetchHeaderIncome(long startDate, long endDate){
        getCompositeDisposable().add(getDataManager()
                .getIncomeHeader(startDate,endDate)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<Double>() {
                    @Override
                    public void accept(Double aDouble) throws Exception {
                        headerIncome.setValue(aDouble);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        snackBarMessage.setValue("Error while parsing Income");
                    }
                }));
    }

    public MutableLiveData<List<TransactionWithCategory>> getExpenseWithCategory() {
        return expenseWithCategory;
    }

    public MutableLiveData<List<TransactionWithCategory>> getIncomeWithCategory() {
        return incomeWithCategory;
    }

    public MutableLiveData<List<Category>> getCategoryMutableLiveData() {
        return categoryMutableLiveData;
    }

    public MutableLiveData<String> getSnackBarMessage() {
        return snackBarMessage;
    }

    public MutableLiveData<Double> getHeaderExpense() {
        return headerExpense;
    }

    public MutableLiveData<Double> getHeaderIncome() {
        return headerIncome;
    }

    public void clearCalendarMode(){
        getDataManager().setSelectedCalenderType(AppPreferencesHelper.PREF_KEY_DAILY_TYPE);
    }
}
