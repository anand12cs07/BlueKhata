package com.bluekhata.ui.dashboard.transaction.datedialog;

import com.bluekhata.data.model.db.Recurrence;
import com.bluekhata.data.model.db.Reminder;

import java.util.Date;

public interface DateOptionListener {
    void onDateOptionDismiss(boolean isDateChange, Date date, Reminder reminder, Recurrence recurrence);
}
