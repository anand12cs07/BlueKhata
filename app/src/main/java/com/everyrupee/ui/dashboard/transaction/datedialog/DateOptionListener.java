package com.everyrupee.ui.dashboard.transaction.datedialog;

import com.everyrupee.data.model.db.Recurrence;
import com.everyrupee.data.model.db.Reminder;

import java.util.Date;

public interface DateOptionListener {
    void onDateOptionDismiss(boolean isDateChange, Date date, Reminder reminder, Recurrence recurrence);
}
