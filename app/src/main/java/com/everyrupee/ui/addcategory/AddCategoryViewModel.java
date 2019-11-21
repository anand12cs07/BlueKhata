package com.everyrupee.ui.addcategory;

import androidx.lifecycle.MutableLiveData;
import androidx.databinding.ObservableField;

import com.everyrupee.data.DataManager;
import com.everyrupee.data.model.db.Category;
import com.everyrupee.ui.base.BaseViewModel;
import com.everyrupee.utils.rx.SchedulerProvider;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class AddCategoryViewModel extends BaseViewModel<AddCategoryNavigator> {
    public final ObservableField<String> categoryTitle = new ObservableField<>("");
    private final ObservableField<String> categoryType = new ObservableField<>();
    private final ObservableField<String> categoryIcon = new ObservableField<>("ic_bank");
    private final ObservableField<String> categoryColor = new ObservableField<>("#1A8FE3");

    private final ObservableField<Boolean> mIsNewCategory = new ObservableField<>();
    private final MutableLiveData<String> snackBarMessage;

    private int categoryTypeInt;

    private long mCategoryId;

    private int mCategoryShortIndex;

    public AddCategoryViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        snackBarMessage = new MutableLiveData<>();
    }

    public void loadData(long categoryId) {
        mCategoryId = categoryId;
        if (categoryId == -1) {
            mIsNewCategory.set(true);
            return;
        }
        mIsNewCategory.set(false);
        setCategory(categoryId);

    }

    public boolean saveCategory() {
        if (categoryTitle.get().trim().isEmpty()) {
            snackBarMessage.setValue("Category title can't be empty");
            return false;
        }
        if (mIsNewCategory.get() || mCategoryId == -1) {
            if (categoryTypeInt == 0) {
                createCategoryExpense();
            } else {
                createCategoryIncome();
            }
        } else {
            updateCategory();
        }
        return true;
    }

    public boolean deleteCategory() {
        if (mIsNewCategory.get()) {
            return false;
        }
        getCompositeDisposable().add(getDataManager()
                .deleteTransactionByCategoryId(mCategoryId)
                .flatMap(new Function<Boolean, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(Boolean aBoolean) throws Exception {
                        return getDataManager().deleteCategory(mCategoryId);
                    }
                })
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        snackBarMessage.setValue("Category deleted successfully");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        snackBarMessage.setValue("Error on deleting");
                    }
                }));
        return true;
    }

    public void setCategoryType(String type) {
        this.categoryType.set(type);
        categoryTypeInt = type.equals("Expense") ? 0 : 1;
    }

    public ObservableField<String> getCategoryType() {
        return categoryType;
    }

    public void setCategoryColor(String color) {
        this.categoryColor.set(color);
    }

    public ObservableField<String> getCategoryColor() {
        return categoryColor;
    }

    public void setCategoryIcon(String icon) {
        this.categoryIcon.set(icon);
    }

    public ObservableField<String> getCategoryIcon() {
        return categoryIcon;
    }

    public MutableLiveData<String> getSnackBarMessage() {
        return snackBarMessage;
    }

    public ObservableField<Boolean> isNewCategory() {
        return mIsNewCategory;
    }

    private void setCategory(long mCategoryId) {
        getCompositeDisposable().add(getDataManager()
                .getCategory(mCategoryId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<Category>() {
                    @Override
                    public void accept(Category category) throws Exception {
                        categoryTitle.set(category.getCatTitle());
                        categoryType.set(category.getCatType() == 0 ? "Expense" : "Income");
                        categoryColor.set(category.getCatColor());
                        categoryIcon.set(category.getCatIcon());
                        mCategoryShortIndex = category.getCatShortIndex();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        snackBarMessage.setValue("Error on fetching data");
                    }
                }));
    }

    private void createCategoryExpense() {
        getCompositeDisposable().add(getDataManager()
                .getMaxExpenseShortIndex()
                .flatMap(new Function<Integer, ObservableSource<? extends Boolean>>() {
                    @Override
                    public ObservableSource<? extends Boolean> apply(Integer shortIndex) throws Exception {
                        Category category = new Category(
                                categoryTitle.get(), categoryColor.get(),
                                categoryIcon.get(), categoryTypeInt, shortIndex + 1
                        );
                        return getDataManager().insertCategory(category);
                    }
                })
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        snackBarMessage.setValue("Category added successfully");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        snackBarMessage.setValue("Category not added");
                    }
                }));
    }

    private void createCategoryIncome() {
        getCompositeDisposable().add(getDataManager()
                .getMaxIncomeShortIndex()
                .flatMap(new Function<Integer, ObservableSource<? extends Boolean>>() {
                    @Override
                    public ObservableSource<? extends Boolean> apply(Integer shortIndex) throws Exception {
                        Category category = new Category(categoryTitle.get(), categoryColor.get(),
                                categoryIcon.get(), categoryTypeInt, shortIndex + 1
                        );
                        return getDataManager().insertCategory(category);
                    }
                })
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        snackBarMessage.setValue("Category added successfully");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        snackBarMessage.setValue("Category not added");
                    }
                }));
    }

    private void updateCategory() {
        Category category = new Category(
                mCategoryId, categoryTitle.get(), categoryColor.get(),
                categoryIcon.get(), categoryTypeInt, mCategoryShortIndex
        );
        getCompositeDisposable().add(getDataManager()
                .updateCategory(category)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        snackBarMessage.setValue("Category updated successfully");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        snackBarMessage.setValue("Category not updated");
                    }
                }));
    }
}
