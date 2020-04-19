package com.bluekhata.ui.dashboard.transaction;

import androidx.lifecycle.MutableLiveData;
import androidx.databinding.ObservableField;

import com.bluekhata.data.DataManager;
import com.bluekhata.data.model.db.Category;
import com.bluekhata.data.model.db.Recurrence;
import com.bluekhata.data.model.db.Reminder;
import com.bluekhata.data.model.db.Tag;
import com.bluekhata.data.model.db.Transaction;
import com.bluekhata.utils.CalculatorUtil;
import com.bluekhata.ui.base.BaseViewModel;
import com.bluekhata.utils.CommonUtils;
import com.bluekhata.utils.rx.SchedulerProvider;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import io.reactivex.functions.Consumer;

public class TransactionViewModel extends BaseViewModel {
    private final ObservableField<String> currency = new ObservableField<>("₹ 0.0");
    private final ObservableField<Boolean> equal = new ObservableField<>(false);

    private final MutableLiveData<Reminder> reminderMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Recurrence> recurrenceMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Category> categoryMutableLiveData = new MutableLiveData<>();

    private final MutableLiveData<List<Category>> categoryList = new MutableLiveData<>();
    private final MutableLiveData<List<Tag>> tagList = new MutableLiveData<>();
    private final MutableLiveData<Long> transactionId = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isTransactionSaved = new MutableLiveData<>();
    private final MutableLiveData<String> snackBarMessage;

    private CalculatorUtil calculatorUtil;
    private final String currencySymbol = "₹ ";

    public TransactionViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        snackBarMessage = new MutableLiveData<>();
        isTransactionSaved.setValue(false);
        calculatorUtil = new CalculatorUtil();
    }

    public void fetchReminders(final String title) {
        getCompositeDisposable().add(getDataManager()
                .getReminderByTitle(title)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<Reminder>() {
                               @Override
                               public void accept(Reminder reminder) throws Exception {
                                   reminderMutableLiveData.setValue(reminder);
                               }
                           }
                ));
    }

    public void fetchRecurrence(final String title) {
        getCompositeDisposable().add(getDataManager()
                .getRecurrenceByTitle(title)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<Recurrence>() {
                               @Override
                               public void accept(Recurrence recurrence) throws Exception {
                                   recurrenceMutableLiveData.setValue(recurrence);
                               }
                           }
                ));
    }

    public void fetchTagList() {
        getCompositeDisposable().add(getDataManager()
                .loadAllTags()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<Tag>>() {
                    @Override
                    public void accept(List<Tag> tags) throws Exception {
                        tagList.setValue(tags);
                    }
                }));
    }

    public void fetchRecurrence(Transaction transaction) {
        getCompositeDisposable().add(getDataManager()
                .getRecurrenceById(transaction.getRecurrenceId())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<Recurrence>() {
                    @Override
                    public void accept(Recurrence recurrence) throws Exception {
                        recurrenceMutableLiveData.setValue(recurrence);
                    }
                }));
    }

    public void fetchReminder(Transaction transaction) {
        getCompositeDisposable().add(getDataManager()
                .getReminderById(transaction.getReminderId())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<Reminder>() {
                    @Override
                    public void accept(Reminder reminder) throws Exception {
                        reminderMutableLiveData.setValue(reminder);
                    }
                }));
    }

    public void fetchCategory(Transaction transaction) {
        getCompositeDisposable().add(getDataManager()
                .getCategory(transaction.getCategoryId())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<Category>() {
                    @Override
                    public void accept(Category category) throws Exception {
                        categoryMutableLiveData.setValue(category);
                    }
                }));
    }

    public void onSave(Category category, Date date, Reminder reminder, Recurrence recurrence,
                       Transaction transaction, boolean isToEdit, List<String> tagList) {
        if (equal.get()) {
            setCalcData();
        } else {
            if (isToEdit) {
                updateTransaction(transaction, category, date, reminder, recurrence, tagList);
            } else {
                saveTransaction(category, date, reminder, recurrence);
            }
        }
    }

    public void saveTransactionTags(List<String> tags, final long transactionId) {
        getCompositeDisposable().add(getDataManager()
                .insertTransactionTag(tags, transactionId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object aBoolean) throws Exception {
                        snackBarMessage.setValue("Transaction Added Successfully");
                        isTransactionSaved.setValue(true);
                    }
                }));
    }

    public void setDigits(String digit) {
        if (calculatorUtil.getOperator() == 'a') {
            calculatorUtil.setPreValue(calculatorUtil.getPreValue().equals("0.0")
                    ? digit : calculatorUtil.getPreValue() + digit);

            currency.set(currencySymbol + getFormattedValue(calculatorUtil.getPreValue()));

        } else {
            calculatorUtil.setPostValue(calculatorUtil.getPostValue() + digit);
            currency.set(currencySymbol + getFormattedValue(calculatorUtil.getPreValue()) + " " +
                    calculatorUtil.getOperator() + " " + getFormattedValue(calculatorUtil.getPostValue()));
        }
    }

    public void setDot() {
        if (calculatorUtil.getOperator() == 'a' && !calculatorUtil.getPreValue().contains(".")) {
            calculatorUtil.setPreValue(calculatorUtil.getPreValue() + ".");
            currency.set(currencySymbol + getFormattedValue(calculatorUtil.getPreValue()));
        } else if (calculatorUtil.getOperator() != 'a' && !calculatorUtil.getPostValue().contains(".")) {
            calculatorUtil.setPostValue(calculatorUtil.getPostValue() + ".");
            currency.set(currencySymbol + getFormattedValue(calculatorUtil.getPreValue()) + " " +
                    calculatorUtil.getOperator() + " " + getFormattedValue(calculatorUtil.getPostValue()));
        }
    }

    public void setOperator(String operator) {
        String amount = currency.get();
        if (amount.length() > 10) {
            snackBarMessage.setValue("Amount Exceeded");
            return;
        }
        if (calculatorUtil.getPreValue().equals("") || calculatorUtil.getPreValue().equals(".")) {
            snackBarMessage.setValue("Add Some Amount");
            return;
        }
        String s = "";
        String operatorWithSpace = " " + operator + " ";
        if (calculatorUtil.getOperator() != 'a') {
            setOutPut(operatorWithSpace);
            s = amount.replace(calculatorUtil.getOperator(), operator.charAt(0));
        } else {
            s = amount + operatorWithSpace;
        }
        currency.set(s);
        equal.set(true); // Enable equal sign
        calculatorUtil.setOperator(operator.charAt(0));
    }

    private void setCalcData() {
        String postValue = calculatorUtil.getPostValue();
        if (postValue.equals("") && calculatorUtil.getOperator() == '/') {
            if (postValue.equals("0.") || postValue.equals("0.0") || postValue.equals("0.00")) {
                snackBarMessage.setValue("Can't be divided by 0");
                return;
            }
        }

        if (isValidValue(calculatorUtil.getPreValue()) || isValidValue(calculatorUtil.getPostValue())) {
            snackBarMessage.setValue("Invalid Amount");
            return;
        }
        if (calculatorUtil.getPostValue().equals("")) {
            currency.set(currencySymbol + getFormattedValue(calculatorUtil.getPreValue()));
        } else {
            equal.set(false); // Disable Equal Sign
            calculatorUtil.calValue();
            currency.set(currencySymbol + getFormattedValue(calculatorUtil.getCalcValue()));
        }
        calculatorUtil.setPreValue(calculatorUtil.getCalcValue());
        calculatorUtil.setPostValue("");
        calculatorUtil.setOperator('a');
    }

    public void clearCurrency() {
        calculatorUtil.setPreValue("");
        calculatorUtil.setPostValue("");
        calculatorUtil.setOperator('a');
        currency.set(currencySymbol);
    }

    public void removeOneDigit() {
        String preValue = calculatorUtil.getPreValue();
        String postValue = calculatorUtil.getPostValue();
        if (calculatorUtil.getOperator() != 'a' && postValue.length() > 1) {
            calculatorUtil.setPostValue(postValue.substring(0, postValue.length() - 1));
            currency.set(currencySymbol + getFormattedValue(preValue) + calculatorUtil.getOperator() + " " + getFormattedValue(calculatorUtil.getPostValue()));
        } else if (!postValue.equals("")) {
            calculatorUtil.setPostValue("");
            currency.set(currencySymbol + getFormattedValue(preValue) + calculatorUtil.getOperator() + " " + calculatorUtil.getPostValue());
        } else if (calculatorUtil.getOperator() != 'a') {
            calculatorUtil.setOperator('a');
            currency.set(currencySymbol + getFormattedValue(preValue));
        } else if (preValue.length() > 1) {
            if (!preValue.contains("E")) {
                String s = preValue.substring(0, preValue.length() - 1);
                calculatorUtil.setPreValue(s);
            } else {
                String tempPreValue = getFormattedValue(preValue).replace(",", "");
                calculatorUtil.setPreValue(tempPreValue.substring(0, tempPreValue.length() - 1));
            }
            currency.set(currencySymbol + getFormattedValue(calculatorUtil.getPreValue()));
        } else {
            calculatorUtil.setPreValue("");
            currency.set(currencySymbol);
        }
    }

    public ObservableField<String> getCurrency() {
        return currency;
    }

    public ObservableField<Boolean> getEqual() {
        return equal;
    }

    public void setCategoryList(boolean isExpense) {
        if (isExpense) {
            fetchExpenseList();
        } else {
            fetchIncomeList();
        }
    }

    public MutableLiveData<List<Category>> getCategoryList() {
        return categoryList;
    }

    public MutableLiveData<Category> getCategoryMutableLiveData() {
        return categoryMutableLiveData;
    }

    public MutableLiveData<String> getSnackBarMessage() {
        return snackBarMessage;
    }

    public MutableLiveData<Long> getTransactionId() {
        return transactionId;
    }

    public MutableLiveData<Recurrence> getRecurrenceMutableLiveData() {
        return recurrenceMutableLiveData;
    }

    public MutableLiveData<Reminder> getReminderMutableLiveData() {
        return reminderMutableLiveData;
    }

    public MutableLiveData<List<Tag>> getTagList() {
        return tagList;
    }

    public MutableLiveData<Boolean> getIsTransactionSaved() {
        return isTransactionSaved;
    }

    public void setAmount(double amount) {
        currency.set(CommonUtils.getFormattedAmount(amount));
        calculatorUtil.setPreValue(String.valueOf(amount));
    }

    private void fetchExpenseList() {
        getCompositeDisposable().add(getDataManager()
                .getCategoryExpenses()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<Category>>() {
                    @Override
                    public void accept(List<Category> categories) throws Exception {
                        categoryList.setValue(categories);
                        categoryMutableLiveData.setValue(categories.get(0));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        categoryMutableLiveData.setValue(
                                new Category(
                                        "NA",
                                        "#ba2f39",
                                        "ic_language",
                                        0,
                                        -1
                                )
                        );
                        snackBarMessage.setValue("Error on fetching Expense list");
                    }
                }));
    }

    private void fetchIncomeList() {
        getCompositeDisposable().add(getDataManager()
                .getCategoryIncomes()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<Category>>() {
                    @Override
                    public void accept(List<Category> categories) throws Exception {
                        categoryList.setValue(categories);
                        categoryMutableLiveData.setValue(categories.get(0));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        categoryMutableLiveData.setValue(
                                new Category(
                                        "NA",
                                        "#ba2f39",
                                        "ic_language",
                                        1,
                                        -1
                                )
                        );
                        snackBarMessage.setValue("Error on fetching Income list");
                    }
                }));
    }

    private void saveTransaction(Category category, final Date date, Reminder reminder, final Recurrence recurrence) {
        if (isTransactionValid(category, date, reminder, recurrence)) {
            double amount = Double.parseDouble(calculatorUtil.getPreValue());
            if (amount == 0) {
                snackBarMessage.setValue("Invalid Amount");
                return;
            }
            final Transaction transaction = new Transaction(amount, date, category.getCatId(),
                    recurrence.getRecurrenceId(), reminder.getReminderId());

            getCompositeDisposable().add(getDataManager()
                    .addTransaction(transaction)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            transactionId.setValue(aLong);
                        }
                    }));
        }
    }

    private void updateTransaction(final Transaction transaction, Category category, final Date date,
                                   Reminder reminder, final Recurrence recurrence, List<String> tagList) {

        if (isTransactionValid(category, date, reminder, recurrence)) {
            double amount = Double.parseDouble(calculatorUtil.getPreValue());
            if (amount == 0) {
                snackBarMessage.setValue("Invalid Amount");
                return;
            }
            Transaction transaction1 = new Transaction(transaction.getTransactionId(), amount, date, category.getCatId(),
                    recurrence.getRecurrenceId(), reminder.getReminderId());

            getCompositeDisposable().add(getDataManager()
                    .updateTransaction(tagList, transaction1)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            snackBarMessage.setValue("Transaction Updated Successfully");
                            isTransactionSaved.setValue(true);

                        }
                    }));
        }
    }

    private void checkAndAddNextTransaction(Transaction transaction, Date date, Recurrence recurrence) {
        if (date.before(new Date()) && !recurrence.getRecurrenceTitle().equals("None")) {
            Transaction newTransaction = new Transaction(
                    transaction.getTransactionAmount(),
                    Recurrence.getNextDate(date, recurrence.getRecurrenceTitle()),
                    transaction.getCategoryId(),
                    transaction.getRecurrenceId(),
                    transaction.getReminderId()
            );

            getCompositeDisposable().add(getDataManager()
                    .addTransaction(newTransaction)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {

                        }
                    }));
        }
    }

    private void setOutPut(String operator) {
        if (!calculatorUtil.getPostValue().equals("")) {
            calculatorUtil.calValue();
            calculatorUtil.setPreValue(calculatorUtil.getCalcValue());
            currency.set(currencySymbol + getFormattedValue(calculatorUtil.getPreValue()) + operator);
            calculatorUtil.setPostValue("");
        }
    }

    private String getFormattedValue(String value) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        DecimalFormat decimalFormat1 = new DecimalFormat("#,##0");
        if (!value.equals(".") && !value.equals("")) {
            if (value.contains(".")) {
                return decimalFormat.format(Double.parseDouble(value));
            } else {
                return decimalFormat1.format(Double.parseDouble(value));
            }
        }
        return "0.";
    }

    private boolean isValidValue(String value) {
        return value.lastIndexOf(".") == value.length() - 1;
    }

    private boolean isTransactionValid(Category category, Date date, Reminder reminder, Recurrence recurrence) {
        if (category == null || category.getCatTitle().equals("NA")) {
            snackBarMessage.setValue("Category not set");
            return false;
        }
        if (date == null) {
            snackBarMessage.setValue("Date not set");
            return false;
        }
        if (recurrence == null) {
            snackBarMessage.setValue("Recursion not set");
            return false;
        }
        if (reminder == null) {
            snackBarMessage.setValue("Reminder not set");
            return false;
        }
        if (calculatorUtil.getPreValue().equals("") || calculatorUtil.getPreValue().equals(".")) {
            snackBarMessage.setValue("Add some Amount");
            return false;
        }
        return true;
    }
}
