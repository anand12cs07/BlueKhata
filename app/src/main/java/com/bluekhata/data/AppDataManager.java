package com.bluekhata.data;

import android.content.Context;

import com.bluekhata.data.local.db.DbHelper;
import com.bluekhata.data.model.db.Category;
import com.bluekhata.data.model.db.Recurrence;
import com.bluekhata.data.model.db.Tag;
import com.bluekhata.data.model.db.custom.UpcomingTransaction;
import com.google.gson.Gson;
import com.bluekhata.data.local.prefs.PreferencesHelper;
import com.bluekhata.data.model.db.Reminder;
import com.bluekhata.data.model.db.Transaction;
import com.bluekhata.data.model.db.TransactionTags;
import com.bluekhata.data.model.db.custom.RecurrenceDetail;
import com.bluekhata.data.model.db.custom.RecurrenceTransactionCategory;
import com.bluekhata.data.model.db.custom.TransactionWithCategory;
import com.bluekhata.data.model.db.custom.TransactionWithTag;
import com.bluekhata.data.model.db.custom.TransactionWithTagAndCategory;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

public class AppDataManager implements DataManager {
    private final Context mContext;

    private final DbHelper mDbHelper;

    private final Gson mGson;

    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public AppDataManager(Context context, DbHelper dbHelper, PreferencesHelper preferencesHelper, Gson gson) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        mGson = gson;
    }

    @Override
    public void setAppTheme(String appTheme) {
        mPreferencesHelper.setAppTheme(appTheme);
    }

    @Override
    public String getAppTheme() {
        return mPreferencesHelper.getAppTheme();
    }

    @Override
    public void setAppThemeChange(boolean isAppThemeChange) {
        mPreferencesHelper.setAppThemeChange(isAppThemeChange);
    }

    @Override
    public boolean isAppThemeChange() {
        return mPreferencesHelper.isAppThemeChange();
    }

    // Add Edit Category Module
    @Override
    public Observable<Boolean> insertCategory(Category category) {
        return mDbHelper.insertCategory(category);
    }

    @Override
    public Observable<Boolean> updateCategory(Category category) {
        return mDbHelper.updateCategory(category);
    }

    @Override
    public Observable<Boolean> deleteCategory(long categoryId) {
        return mDbHelper.deleteCategory(categoryId);
    }

    @Override
    public Observable<Boolean> deleteTransactionByCategoryId(long categoryId) {
        return mDbHelper.deleteTransactionByCategoryId(categoryId);
    }

    // Category Module
    @Override
    public Observable<List<Category>> getCategoryExpenses() {
        return mDbHelper.getCategoryExpenses();
    }

    @Override
    public Observable<List<Category>> getCategoryIncomes() {
        return mDbHelper.getCategoryIncomes();
    }

    @Override
    public Observable<Boolean> isCategoryEmpty() {
        return mDbHelper.isCategoryEmpty();
    }

    @Override
    public Observable<Boolean> saveCategoryList(List<Category> categoryList) {
        return mDbHelper.saveCategoryList(categoryList);
    }

    @Override
    public Observable<Boolean> seedCategory() {
        return mDbHelper.isCategoryEmpty()
                .flatMap(new Function<Boolean, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(Boolean aBoolean) throws Exception {
                        if (aBoolean){
                            return saveCategoryList(Category.getCategoryList());
                        }
                        return Observable.just(false);
                    }
                });
    }

    @Override
    public Observable<List<Category>> getAllCategory() {
        return mDbHelper.getAllCategory();
    }

    @Override
    public Observable<Category> getCategory(long categoryId) {
        return mDbHelper.getCategory(categoryId);
    }

    @Override
    public Observable<Integer> getMaxExpenseShortIndex() {
        return mDbHelper.getMaxExpenseShortIndex();
    }

    @Override
    public Observable<Integer> getMaxIncomeShortIndex() {
        return mDbHelper.getMaxIncomeShortIndex();
    }

    @Override
    public Observable<Boolean> updateDragCategoryListShortIndex(int fromIndex, int toIndex, long id) {
        return mDbHelper.updateDragCategoryListShortIndex(fromIndex, toIndex, id);
    }

    @Override
    public Observable<Boolean> updateCategoryList(List<Category> categoryList) {
        return mDbHelper.updateCategoryList(categoryList);
    }

    // Reminder Module
    @Override
    public Observable<Boolean> isReminderEmpty() {
        return mDbHelper.isReminderEmpty();
    }

    @Override
    public Observable<List<Reminder>> loadAllReminders() {
        return mDbHelper.loadAllReminders();
    }

    @Override
    public Observable<Boolean> saveReminderList(List<Reminder> reminders) {
        return mDbHelper.saveReminderList(reminders);
    }

    @Override
    public Observable<Reminder> getReminderById(long id) {
        return mDbHelper.getReminderById(id);
    }

    @Override
    public Observable<Reminder> getReminderByTitle(String title) {
        return mDbHelper.getReminderByTitle(title);
    }

    @Override
    public Observable<Boolean> seedReminders() {
        return mDbHelper.isReminderEmpty()
                .flatMap(new Function<Boolean, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            return saveReminderList(Reminder.getInitialReminders());
                        }
                        return Observable.just(false);
                    }
                });
    }

    // Recurrence Module
    @Override
    public Observable<Boolean> isRecurrenceEmpty() {
        return mDbHelper.isRecurrenceEmpty();
    }

    @Override
    public Observable<List<Recurrence>> loadAllRecurrence() {
        return mDbHelper.loadAllRecurrence();
    }

    @Override
    public Observable<Recurrence> getRecurrenceById(long id) {
        return mDbHelper.getRecurrenceById(id);
    }

    @Override
    public Observable<Recurrence> getRecurrenceByTitle(String title) {
        return mDbHelper.getRecurrenceByTitle(title);
    }

    @Override
    public Observable<Boolean> saveRecurrenceList(List<Recurrence> recurrences) {
        return mDbHelper.saveRecurrenceList(recurrences);
    }

    @Override
    public Observable<Boolean> seedRecurrence() {
        return mDbHelper.isRecurrenceEmpty()
                .flatMap(new Function<Boolean, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            return saveRecurrenceList(Recurrence.getInitialRecurrences());
                        }
                        return Observable.just(false);
                    }
                });
    }

    // Tag Module
    @Override
    public Observable<Boolean> isTagEmpty() {
        return mDbHelper.isTagEmpty();
    }

    @Override
    public Observable<List<Tag>> loadAllTags() {
        return mDbHelper.loadAllTags();
    }

    @Override
    public Observable<Tag> getTagById(long id) {
        return mDbHelper.getTagById(id);
    }

    @Override
    public Observable<Tag> getTagByTitle(String title) {
        return mDbHelper.getTagByTitle(title);
    }

    @Override
    public Observable<Boolean> saveTagList(List<Tag> tags) {
        return mDbHelper.saveTagList(tags);
    }

    @Override
    public Observable<Long> addTag(Tag tag) {
        return mDbHelper.addTag(tag);
    }

    @Override
    public Observable<Boolean> updateTag(Tag tag) {
        return mDbHelper.updateTag(tag);
    }

    @Override
    public Observable<Boolean> deleteTag(Tag tag) {
        return mDbHelper.deleteTag(tag);
    }

    @Override
    public Observable<List<Tag>> insertTransactionTag(List<String> tagTitles, long transactionId) {
        return mDbHelper.insertTransactionTag(tagTitles, transactionId);
    }

    @Override
    public Observable<Boolean> seedTags() {
        return mDbHelper.isTagEmpty()
                .flatMap(new Function<Boolean, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            return mDbHelper.saveTagList(Tag.getDefaultTags());
                        }
                        return Observable.just(false);
                    }
                });
    }

    // Transaction Module
    @Override
    public Observable<Transaction> getTransactionById(long id) {
        return mDbHelper.getTransactionById(id);
    }

    @Override
    public Observable<Long> addTransaction(Transaction transaction) {
        return mDbHelper.addTransaction(transaction);
    }

    @Override
    public Observable<List<Tag>> getTagFromTitle(final List<String> tagsTitle) {
        return Observable.fromIterable(tagsTitle)
                .flatMap(new Function<String, ObservableSource<Tag>>() {
                    @Override
                    public ObservableSource<Tag> apply(String title) throws Exception {
                        Observable<Tag> tagObservable = mDbHelper.getTagByTitle(title);
                        if (tagObservable == null) {
                            mDbHelper.addTag(new Tag(title));
                        }
                        return tagObservable;
                    }
                })
                .toList()
                .toObservable();
    }

    @Override
    public Observable<Boolean> addTransactionTag(TransactionTags transactionTags) {
        return mDbHelper.addTransactionTag(transactionTags);
    }

    // Home Module

    @Override
    public Observable<Double> getExpenseHeader(long startDate, long endDate) {
        return mDbHelper.getExpenseHeader(startDate, endDate);
    }

    @Override
    public Observable<Double> getIncomeHeader(long startDate, long endDate) {
        return mDbHelper.getIncomeHeader(startDate, endDate);
    }

    @Override
    public Observable<List<TransactionWithCategory>> getAllExpenseTransactionWithCategory(long startDate, long endDate) {
        return mDbHelper.getAllExpenseTransactionWithCategory(startDate, endDate);
    }

    @Override
    public Observable<List<TransactionWithCategory>> getAllIncomeTransactionWithCategory(long startDate, long endDate) {
        return mDbHelper.getAllIncomeTransactionWithCategory(startDate, endDate);
    }

    // Home Detail


    @Override
    public Observable<List<Tag>> getTagListByTransactionId(long transactionId) {
        return mDbHelper.getTagListByTransactionId(transactionId);
    }

    @Override
    public Observable<List<Transaction>> getTransactionByCategoryIdAndDate(long categoryId, long startDate, long endDate) {
        return mDbHelper.getTransactionByCategoryIdAndDate(categoryId, startDate, endDate);
    }

    @Override
    public Observable<Boolean> updateTransaction(List<String> tagTitles, Transaction transaction) {
        return mDbHelper.updateTransaction(tagTitles, transaction);
    }

    @Override
    public Observable<Boolean> deleteTransactionTagById(long transactionId) {
        return mDbHelper.deleteTransactionTagById(transactionId);
    }

    @Override
    public Observable<Boolean> deleteTransactionById(long transactionId) {
        return mDbHelper.deleteTransactionById(transactionId);
    }

    @Override
    public Observable<List<TransactionWithTag>> getTransactionWithTag(long categoryId, long startDate, long endDate) {
        return mDbHelper.getTransactionByCategoryIdAndDate(categoryId, startDate, endDate)
                .flatMap(new Function<List<Transaction>, ObservableSource<Transaction>>() {
                    @Override
                    public ObservableSource<Transaction> apply(List<Transaction> transactions) throws Exception {
                        return Observable.fromIterable(transactions);
                    }
                }).flatMap(new Function<Transaction, ObservableSource<TransactionWithTag>>() {
                    @Override
                    public ObservableSource<TransactionWithTag> apply(final Transaction transaction) throws Exception {
                        return Observable.zip(
                                mDbHelper.getTagListByTransactionId(transaction.getTransactionId()),
                                Observable.just(transaction),
                                new BiFunction<List<Tag>, Transaction, TransactionWithTag>() {
                                    @Override
                                    public TransactionWithTag apply(List<Tag> tags, Transaction transaction1) throws Exception {
                                        return new TransactionWithTag(tags, transaction1);
                                    }
                                });

                    }
                })
                .toList()
                .toObservable();
    }

    // History Module


    @Override
    public Observable<List<Transaction>> getAllTransactionUpToCurrentDate(long startDate, long endDate) {
        return mDbHelper.getAllTransactionUpToCurrentDate(startDate, endDate);
    }

    @Override
    public Observable<List<Long>> getSearchTransactionIDs(String searchTag) {
        return mDbHelper.getSearchTransactionIDs(searchTag);
    }

    @Override
    public Observable<List<Transaction>> getSearchTransactions(List<Long> ids, long startDate, long endDate) {
        return mDbHelper.getSearchTransactions(ids, startDate, endDate);
    }

    @Override
    public Observable<List<TransactionWithTagAndCategory>> getAllTransactionWithTag(final List<Long> categoryIds, long startDate, long endDate) {
        return mDbHelper.getAllTransactionUpToCurrentDate(startDate, endDate)
                .flatMap(new Function<List<Transaction>, ObservableSource<Transaction>>() {
                    @Override
                    public ObservableSource<Transaction> apply(List<Transaction> transactions) throws Exception {
                        return Observable.fromIterable(transactions);
                    }
                }).filter(new Predicate<Transaction>() {
                    @Override
                    public boolean test(Transaction transaction) throws Exception {
                        if (categoryIds.size() == 0) {
                            return true;
                        }
                        return categoryIds.contains(transaction.getCategoryId());
                    }
                }).flatMap(new Function<Transaction, ObservableSource<TransactionWithTag>>() {
                    @Override
                    public ObservableSource<TransactionWithTag> apply(final Transaction transaction) throws Exception {
                        return Observable.zip(
                                mDbHelper.getTagListByTransactionId(transaction.getTransactionId()),
                                Observable.just(transaction),
                                new BiFunction<List<Tag>, Transaction, TransactionWithTag>() {
                                    @Override
                                    public TransactionWithTag apply(List<Tag> tags, Transaction transaction1) throws Exception {
                                        return new TransactionWithTag(tags, transaction1);
                                    }
                                });

                    }
                }).flatMap(new Function<TransactionWithTag, ObservableSource<TransactionWithTagAndCategory>>() {
                    @Override
                    public ObservableSource<TransactionWithTagAndCategory> apply(TransactionWithTag transactionWithTag) throws Exception {
                        return Observable.zip(
                                mDbHelper.getCategory(transactionWithTag.getTransaction().getCategoryId()),
                                Observable.just(transactionWithTag),
                                new BiFunction<Category, TransactionWithTag, TransactionWithTagAndCategory>() {
                                    @Override
                                    public TransactionWithTagAndCategory apply(Category category, TransactionWithTag transactionWithTag) throws Exception {
                                        return new TransactionWithTagAndCategory(transactionWithTag, category);
                                    }
                                }
                        );
                    }
                })
                .toList()
                .toObservable();
    }

    @Override
    public Observable<List<TransactionWithTagAndCategory>> getAllTransactionWithTagAndCategory(final List<Long> categoryIds, String searchTag, final long startDate, final long endDate) {
        return mDbHelper.getSearchTransactionIDs(searchTag)
                .flatMap(new Function<List<Long>, ObservableSource<List<TransactionWithTagAndCategory>>>() {
                    @Override
                    public ObservableSource<List<TransactionWithTagAndCategory>> apply(List<Long> longs) throws Exception {
                        return mDbHelper.getSearchTransactions(longs, startDate, endDate)
                                .flatMap(new Function<List<Transaction>, ObservableSource<Transaction>>() {
                                    @Override
                                    public ObservableSource<Transaction> apply(List<Transaction> transactions) throws Exception {
                                        return Observable.fromIterable(transactions);
                                    }
                                }).filter(new Predicate<Transaction>() {
                                    @Override
                                    public boolean test(Transaction transaction) throws Exception {
                                        if (categoryIds.size() == 0) {
                                            return true;
                                        }
                                        return categoryIds.contains(transaction.getCategoryId());
                                    }
                                }).flatMap(new Function<Transaction, ObservableSource<TransactionWithTag>>() {
                                    @Override
                                    public ObservableSource<TransactionWithTag> apply(final Transaction transaction) throws Exception {
                                        return Observable.zip(
                                                mDbHelper.getTagListByTransactionId(transaction.getTransactionId()),
                                                Observable.just(transaction),
                                                new BiFunction<List<Tag>, Transaction, TransactionWithTag>() {
                                                    @Override
                                                    public TransactionWithTag apply(List<Tag> tags, Transaction transaction1) throws Exception {
                                                        return new TransactionWithTag(tags, transaction1);
                                                    }
                                                });

                                    }
                                }).flatMap(new Function<TransactionWithTag, ObservableSource<TransactionWithTagAndCategory>>() {
                                    @Override
                                    public ObservableSource<TransactionWithTagAndCategory> apply(TransactionWithTag transactionWithTag) throws Exception {
                                        return Observable.zip(
                                                mDbHelper.getCategory(transactionWithTag.getTransaction().getCategoryId()),
                                                Observable.just(transactionWithTag),
                                                new BiFunction<Category, TransactionWithTag, TransactionWithTagAndCategory>() {
                                                    @Override
                                                    public TransactionWithTagAndCategory apply(Category category, TransactionWithTag transactionWithTag) throws Exception {
                                                        return new TransactionWithTagAndCategory(transactionWithTag, category);
                                                    }
                                                }
                                        );
                                    }
                                })
                                .toList()
                                .toObservable();
                    }
                });
    }

    // Recurrence Module

    @Override
    public Observable<List<RecurrenceTransactionCategory>> getAllRecurrenceTransactionCategory(long startDate) {
        return mDbHelper.getAllRecurrenceTransactionCategory(startDate);
    }

    @Override
    public Observable<List<RecurrenceDetail>> getAllRecurrence(long startDate) {
        return mDbHelper.getAllRecurrenceTransactionCategory(startDate)
                .flatMap(new Function<List<RecurrenceTransactionCategory>, ObservableSource<RecurrenceTransactionCategory>>() {
                    @Override
                    public ObservableSource<RecurrenceTransactionCategory> apply(List<RecurrenceTransactionCategory> recurrenceTransactionCategories) throws Exception {
                        return Observable.fromIterable(recurrenceTransactionCategories);
                    }
                })
                .flatMap(new Function<RecurrenceTransactionCategory, ObservableSource<RecurrenceDetail>>() {
                    @Override
                    public ObservableSource<RecurrenceDetail> apply(RecurrenceTransactionCategory item) throws Exception {
                        return Observable.zip(
                                mDbHelper.getRecurrenceById(item.getTransaction().getRecurrenceId()),
                                Observable.just(item),
                                new BiFunction<Recurrence, RecurrenceTransactionCategory, RecurrenceDetail>() {
                                    @Override
                                    public RecurrenceDetail apply(Recurrence recurrence, RecurrenceTransactionCategory recurrenceTransactionCategory) throws Exception {
                                        RecurrenceDetail recurrenceDetail = new RecurrenceDetail();
                                        recurrenceDetail.setTransaction(recurrenceTransactionCategory.getTransaction());
                                        recurrenceDetail.setCategory(recurrenceTransactionCategory.getCategory());
                                        recurrenceDetail.setRecurrence(recurrence);
                                        return recurrenceDetail;
                                    }
                                }
                        );
                    }
                })
                .flatMap(new Function<RecurrenceDetail, ObservableSource<RecurrenceDetail>>() {
                    @Override
                    public ObservableSource<RecurrenceDetail> apply(final RecurrenceDetail recurrenceDetail) throws Exception {
                        return Observable.zip(
                                mDbHelper.getTagListByTransactionId(recurrenceDetail.getTransaction().getTransactionId()),
                                Observable.just(recurrenceDetail),
                                new BiFunction<List<Tag>, RecurrenceDetail, RecurrenceDetail>() {
                                    @Override
                                    public RecurrenceDetail apply(List<Tag> tags, RecurrenceDetail recurrenceDetail) throws Exception {
                                        recurrenceDetail.setTagList(tags);
                                        return recurrenceDetail;
                                    }
                                }
                        );
                    }
                })
                .filter(new Predicate<RecurrenceDetail>() {
                    @Override
                    public boolean test(RecurrenceDetail recurrenceDetail) throws Exception {
                        return recurrenceDetail.getRecurrence().getRecurrenceId() != 1;
                    }
                })
                .toList()
                .toObservable();
    }

    // Upcoming Module

    @Override
    public Observable<List<UpcomingTransaction>> getAllUpcomingTransaction(long startDate, long endDate) {
        return mDbHelper.getAllTransactionUpToCurrentDate(startDate, endDate)
                .flatMap(new Function<List<Transaction>, ObservableSource<Transaction>>() {
                    @Override
                    public ObservableSource<Transaction> apply(List<Transaction> transactions) throws Exception {
                        return Observable.fromIterable(transactions);
                    }
                })
                .flatMap(new Function<Transaction, ObservableSource<UpcomingTransaction>>() {
                    @Override
                    public ObservableSource<UpcomingTransaction> apply(Transaction transaction) throws Exception {
                        return Observable.zip(
                                mDbHelper.getCategory(transaction.getCategoryId()),
                                Observable.just(transaction),
                                new BiFunction<Category, Transaction, UpcomingTransaction>() {
                                    @Override
                                    public UpcomingTransaction apply(Category category, Transaction transaction) throws Exception {
                                        UpcomingTransaction upcomingTransaction = new UpcomingTransaction();
                                        upcomingTransaction.setTransaction(transaction);
                                        upcomingTransaction.setCategory(category);
                                        return upcomingTransaction;
                                    }
                                }
                        );
                    }
                })
                .flatMap(new Function<UpcomingTransaction, ObservableSource<UpcomingTransaction>>() {
                    @Override
                    public ObservableSource<UpcomingTransaction> apply(UpcomingTransaction upcomingTransaction) throws Exception {
                        return Observable.zip(
                                mDbHelper.getTagListByTransactionId(upcomingTransaction.getTransaction().getTransactionId()),
                                Observable.just(upcomingTransaction),
                                new BiFunction<List<Tag>, UpcomingTransaction, UpcomingTransaction>() {
                                    @Override
                                    public UpcomingTransaction apply(List<Tag> tags, UpcomingTransaction upcomingTransaction) throws Exception {
                                        upcomingTransaction.setTagList(tags);
                                        return upcomingTransaction;
                                    }
                                }
                        );
                    }
                })
                .flatMap(new Function<UpcomingTransaction, ObservableSource<UpcomingTransaction>>() {
                    @Override
                    public ObservableSource<UpcomingTransaction> apply(UpcomingTransaction upcomingTransaction) throws Exception {
                        return Observable.zip(
                                mDbHelper.getRecurrenceById(upcomingTransaction.getTransaction().getRecurrenceId()),
                                Observable.just(upcomingTransaction),
                                new BiFunction<Recurrence, UpcomingTransaction, UpcomingTransaction>() {
                                    @Override
                                    public UpcomingTransaction apply(Recurrence recurrence, UpcomingTransaction upcomingTransaction) throws Exception {
                                        upcomingTransaction.setRecurrence(recurrence);
                                        return upcomingTransaction;
                                    }
                                }
                        );
                    }
                })
                .toList()
                .toObservable()
                .flatMap(new Function<List<UpcomingTransaction>, ObservableSource<List<UpcomingTransaction>>>() {
                    @Override
                    public ObservableSource<List<UpcomingTransaction>> apply(List<UpcomingTransaction> upcomingTransactions) throws Exception {
                        List<UpcomingTransaction> upcomingTransactionList = UpcomingTransaction.getRecursiveTransaction(upcomingTransactions);
                        Collections.sort(upcomingTransactionList,UpcomingTransaction.upcomingTransactionComparator);
                        return Observable.just(upcomingTransactionList);
                    }
                });
    }
}
