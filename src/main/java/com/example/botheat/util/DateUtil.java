package com.example.botheat.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static Date getMonthStart(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime( date );
        cal.set( cal.get( Calendar.YEAR ), cal.get( Calendar.MONTH ), 1, 0, 0, 0 );
        return cal.getTime();
    }

    public static Date getMonthEnd(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime( date );
        cal.set( cal.get( Calendar.YEAR ), cal.get( Calendar.MONTH ) + 1, 1, 0, 0, 0 );
        cal.set( Calendar.SECOND, cal.get( Calendar.SECOND ) - 1 );
        return cal.getTime();
    }

    public static Date getDayStart(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime( date );
        cal.set( cal.get( Calendar.YEAR ), cal.get( Calendar.MONTH ), cal.get( Calendar.DAY_OF_MONTH ), 0, 0, 0 );
        return cal.getTime();
    }

    public static Date getDayEnd(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime( date );
        cal.set( cal.get( Calendar.YEAR ), cal.get( Calendar.MONTH ), cal.get( Calendar.DAY_OF_MONTH ), 23, 59, 59 );
        return cal.getTime();
    }

    public static Date getDayHour(Date date, int hour) {
        Calendar cal = Calendar.getInstance();
        cal.setTime( date );
        cal.set( cal.get( Calendar.YEAR ), cal.get( Calendar.MONTH ), cal.get( Calendar.DAY_OF_MONTH ), hour, 0, 0 );
        return cal.getTime();
    }

    public static Date getPriorDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime( date );
        cal.add( Calendar.DAY_OF_YEAR, -1 );
        return cal.getTime();
    }

    public static Date getPriorMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime( date );
        cal.add( Calendar.MONTH, -1 );
        return cal.getTime();
    }
}
