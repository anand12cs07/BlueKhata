package com.everyrupee.ui.dashboard.history.filter;

public interface HistoryFilterListener {

    void sendFilterData(long[] categoryIds, long startDate, long endDate);

    public interface OnAllCheckedListener{
        void onAllCheckedItem(boolean isAllChecked);
    }
}
