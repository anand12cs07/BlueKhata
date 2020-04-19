package com.bluekhata.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtils {

    public static long[] getStartAndEndTimeOfDay(Date date) {
        long[] time = new long[2];
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        time[0] = cal.getTimeInMillis();

        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        time[1] = cal.getTimeInMillis();

        return time;
    }

    public static long[] getStartAndEndTimeOfMonth(Date date) {
        long[] time = new long[2];
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.DATE, 1);
        time[0] = cal.getTimeInMillis();

        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
        time[1] = cal.getTimeInMillis();

        return time;
    }

    public static String getDateToStringDate(Date date) {
        return getMilliToStringDate(date.getTime());
    }

    public static String getMilliToStringDate(long milliSecond) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy,  hh:mm:ss a");
        Date date = new Date(milliSecond);
        return format.format(date);
    }

    public static String getDateInDdMmmYyyy(Date date){
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");
        Date date1 = new Date(date.getTime());
        return format.format(date1);
    }
}
