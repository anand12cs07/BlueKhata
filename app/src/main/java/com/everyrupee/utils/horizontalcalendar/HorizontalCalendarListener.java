package com.everyrupee.utils.horizontalcalendar;

import java.util.Date;

/**
 * Created by aman on 11-08-2018.
 */

public abstract class HorizontalCalendarListener {

    public abstract void onDateSelected(Date date, int position);

    public void onCalendarScroll(HorizontalCalendarView calendarView, int dx, int dy) {
    }

    public boolean onDateLongClicked(Date date, int position) {
        return false;
    }

}
