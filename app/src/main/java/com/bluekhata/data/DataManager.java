package com.bluekhata.data;

import com.bluekhata.data.local.db.DbHelper;
import com.bluekhata.data.model.db.Tag;
import com.bluekhata.data.model.db.custom.UpcomingTransaction;
import com.bluekhata.data.local.prefs.PreferencesHelper;
import com.bluekhata.data.model.db.custom.RecurrenceDetail;
import com.bluekhata.data.model.db.custom.TransactionWithTag;
import com.bluekhata.data.model.db.custom.TransactionWithTagAndCategory;

import java.util.List;

import io.reactivex.Observable;

public interface DataManager extends DbHelper, PreferencesHelper {
    Observable<Boolean> seedReminders();

    Observable<Boolean> seedRecurrence();

    Observable<Boolean> seedTags();

    Observable<Boolean> seedCategory();

    Observable<List<Tag>> getTagFromTitle(List<String> tagsTitle);

    Observable<List<TransactionWithTag>> getTransactionWithTag(long categoryId, long startDate, long endDate);

    Observable<List<TransactionWithTagAndCategory>> getAllTransactionWithTag(List<Long> categoryIds, long startDate, long endDate);

    Observable<List<TransactionWithTagAndCategory>> getAllTransactionWithTagAndCategory(final List<Long> categoryIds,String searchTag, long startDate, long endDate);

    Observable<List<RecurrenceDetail>> getAllRecurrence(long startDate);

    Observable<List<UpcomingTransaction>> getAllUpcomingTransaction(long startDate, long endDate);
}
