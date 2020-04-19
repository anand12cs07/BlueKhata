package com.bluekhata.ui.dashboard.home.homedetails;

import androidx.lifecycle.MutableLiveData;

import com.bluekhata.data.DataManager;
import com.bluekhata.data.model.db.Category;
import com.bluekhata.data.model.db.custom.TransactionWithTag;
import com.bluekhata.ui.base.BaseViewModel;
import com.bluekhata.utils.rx.SchedulerProvider;

import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class HomeDetailViewModel extends BaseViewModel {
    private final MutableLiveData<List<TransactionWithTag>> categoryDetail = new MutableLiveData<>();
    private final MutableLiveData<String> categoryTitle = new MutableLiveData<>();
    private final MutableLiveData<String> snackBarMessage = new MutableLiveData<>();

    public HomeDetailViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void fetchCategoryDetail(long categoryId, long startDate, long endDate) {
        getCompositeDisposable().add(getDataManager()
                .getTransactionWithTag(categoryId, startDate, endDate)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<TransactionWithTag>>() {
                    @Override
                    public void accept(List<TransactionWithTag> list) throws Exception {
                        categoryDetail.setValue(list);
                    }
                }));
    }

    public void fetchCategoryTitle(long categoryId) {
        getCompositeDisposable().add(getDataManager()
                .getCategory(categoryId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<Category>() {
                    @Override
                    public void accept(Category category) throws Exception {
                        categoryTitle.setValue(category.getCatTitle());
                    }
                }));
    }

    public void deleteTransaction(final long transactionId) {
        getCompositeDisposable().add(getDataManager()
                .deleteTransactionTagById(transactionId)
                .flatMap(new Function<Boolean, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(Boolean aBoolean) throws Exception {
                        return getDataManager().deleteTransactionById(transactionId);
                    }
                })
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        snackBarMessage.setValue("Transaction deleted successfully");
                    }
                }));
    }

    public MutableLiveData<List<TransactionWithTag>> getCategoryDetail() {
        return categoryDetail;
    }

    public MutableLiveData<String> getCategoryTitle() {
        return categoryTitle;
    }

    public MutableLiveData<String> getSnackBarMessage() {
        return snackBarMessage;
    }
}
