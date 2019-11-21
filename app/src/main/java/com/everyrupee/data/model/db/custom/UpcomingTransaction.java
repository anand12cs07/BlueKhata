package com.everyrupee.data.model.db.custom;

import android.text.TextUtils;

import com.everyrupee.data.model.db.Recurrence;
import com.everyrupee.data.model.db.Category;
import com.everyrupee.data.model.db.Tag;
import com.everyrupee.data.model.db.Transaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class UpcomingTransaction {
    private Transaction transaction;
    private Category category;
    private List<Tag> tagList;
    private Recurrence recurrence;

    public UpcomingTransaction() {
        tagList = new ArrayList<>();
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public String getTagList() {
        return TextUtils.join(", ", tagList);
    }

    public void setRecurrence(Recurrence recurrence) {
        this.recurrence = recurrence;
    }

    public Recurrence getRecurrence() {
        return recurrence;
    }

    public static Comparator<UpcomingTransaction> upcomingTransactionComparator = new Comparator<UpcomingTransaction>() {
        @Override
        public int compare(UpcomingTransaction transaction, UpcomingTransaction transaction1) {
            return transaction.getTransaction().getTransactionDate().compareTo(
                    transaction1.getTransaction().getTransactionDate()
            );
        }
    };

    public static List<UpcomingTransaction> getRecursiveTransaction(List<UpcomingTransaction> list) {
        List<UpcomingTransaction> upcomingTransactions = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            UpcomingTransaction item = list.get(i);
            String title = item.getRecurrence().getRecurrenceTitle();
            switch (title) {
                case "None":
                    upcomingTransactions.add(item);
                    break;
                case "Daily":
                    upcomingTransactions.addAll(getDailyList(item));
                    break;
                case "Monday to Friday":
                    upcomingTransactions.addAll(getMonToFriList(item));
                    break;
                case "Evey Saturday":
                    upcomingTransactions.addAll(getSaturday(item));
                    break;
                case "Every Sunday":
                    upcomingTransactions.addAll(getSunday(item));
                    break;
                case "Saturday and Sunday":
                    upcomingTransactions.addAll(getSatAndSunList(item));
                    break;
                case "Every Week":
                    upcomingTransactions.addAll(getEveryWeek(item));
                    break;
                case "Every 2 Week":
                    upcomingTransactions.add(item);
                    break;
                case "Every Month":
                    upcomingTransactions.add(item);
                    break;
                default:
                    break;
            }
        }
        return upcomingTransactions;
    }

    private static List<UpcomingTransaction> getDailyList(UpcomingTransaction item) {
        List<UpcomingTransaction> upcomingTransactions = new ArrayList<>();
        Date date = item.getTransaction().getTransactionDate();
        upcomingTransactions.add(item);

        Calendar calendar = Calendar.getInstance();
        for (int i = 1; i < 7; i++) {
            calendar.setTime(date);
            calendar.add(Calendar.DATE, 1);

            UpcomingTransaction upcomingTransaction = getUpcomingTransaction(calendar.getTime(), item);

            upcomingTransactions.add(upcomingTransaction);

            date = calendar.getTime();
        }

        return upcomingTransactions;
    }


    private static List<UpcomingTransaction> getMonToFriList(UpcomingTransaction item) {
        List<UpcomingTransaction> upcomingTransactions = new ArrayList<>();
        Date date = item.getTransaction().getTransactionDate();
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 7; i++) {
            calendar.setTime(date);
            int j = calendar.get(Calendar.DAY_OF_WEEK);
            if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                UpcomingTransaction upcomingTransaction = getUpcomingTransaction(calendar.getTime(), item);

                upcomingTransactions.add(upcomingTransaction);
            }
            calendar.add(Calendar.DATE, 1);
            date = calendar.getTime();
        }
        return upcomingTransactions;
    }

    private static List<UpcomingTransaction> getSaturday(UpcomingTransaction item) {
        return getSatSunList(item, Calendar.SATURDAY);
    }

    private static List<UpcomingTransaction> getSunday(UpcomingTransaction item) {
        return getSatSunList(item, Calendar.SUNDAY);
    }

    private static List<UpcomingTransaction> getSatAndSunList(UpcomingTransaction item) {
        List<UpcomingTransaction> upcomingTransactions = new ArrayList<>();
        Date date = item.getTransaction().getTransactionDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            upcomingTransactions.add(item);

            calendar.add(Calendar.DATE, 1);
            upcomingTransactions.add(getUpcomingTransaction(calendar.getTime(), item));

            calendar.add(Calendar.DATE, 6);
            upcomingTransactions.add(getUpcomingTransaction(calendar.getTime(), item));
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {

            calendar.setTime(date);
            calendar.add(Calendar.DATE, 6);

            upcomingTransactions.add(getUpcomingTransaction(calendar.getTime(), item));
            calendar.add(Calendar.DATE, 1);
            upcomingTransactions.add(getUpcomingTransaction(calendar.getTime(), item));
        } else {
            calendar.setTime(date);
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            upcomingTransactions.add(getUpcomingTransaction(calendar.getTime(), item));

            calendar.add(Calendar.DATE, 1);
            upcomingTransactions.add(getUpcomingTransaction(calendar.getTime(), item));
        }
        return upcomingTransactions;
    }

    private static List<UpcomingTransaction> getEveryWeek(UpcomingTransaction item) {
        List<UpcomingTransaction> upcomingTransactions = new ArrayList<>();
        Date date = item.getTransaction().getTransactionDate();
        upcomingTransactions.add(item);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 7);

        upcomingTransactions.add(getUpcomingTransaction(calendar.getTime(), item));
        return upcomingTransactions;
    }

    private static List<UpcomingTransaction> getSatSunList(UpcomingTransaction item, int day) {
        List<UpcomingTransaction> upcomingTransactions = new ArrayList<>();
        Date date = item.getTransaction().getTransactionDate();
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.DAY_OF_WEEK) == day) {
            upcomingTransactions.add(item);
            calendar.add(Calendar.DATE, 7);
            upcomingTransactions.add(getUpcomingTransaction(calendar.getTime(), item));

        } else {
            calendar.setTime(date);
            calendar.set(Calendar.DAY_OF_WEEK, day);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            if (Calendar.SUNDAY == day) {
                calendar.add(Calendar.DATE, 7);
            }

            upcomingTransactions.add(getUpcomingTransaction(calendar.getTime(), item));
        }
        return upcomingTransactions;
    }

    private static UpcomingTransaction getUpcomingTransaction(Date date, UpcomingTransaction item) {
        UpcomingTransaction upcomingTransaction = new UpcomingTransaction();

        upcomingTransaction.setTransaction(new Transaction(
                item.getTransaction().getTransactionAmount(),
                date,
                item.getTransaction().getCategoryId(),
                item.getTransaction().getRecurrenceId(),
                item.getTransaction().getReminderId()));

        upcomingTransaction.setRecurrence(item.getRecurrence());
        upcomingTransaction.setTagList(item.tagList);
        upcomingTransaction.setCategory(item.category);

        return upcomingTransaction;
    }
}

