package cz.fi.muni.pa165.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Filip Krepinsky (410022) on 11/24/16
 */

public class DateIntervalUtils {
    private static final int BASE_YEAR = 1970;// 1970 or 1971 are not leap years

    public static Date makeIntervalFromDate(Date fromDate) {
        if (fromDate == null) {
            return null;
        }

        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(fromDate);
        fromCalendar.set(Calendar.YEAR, BASE_YEAR);

        return fromCalendar.getTime();
    }

    public static Date makeIntervalToDate(Date fromDate, Date toDate) {

        Date result = null;

        if (toDate != null) {
            Calendar toCalendar = Calendar.getInstance();
            toCalendar.setTime(toDate);
            toCalendar.set(Calendar.YEAR, DateIntervalUtils.BASE_YEAR);

            if (fromDate != null) {
                Calendar fromCalendar = Calendar.getInstance();
                fromCalendar.setTime(fromDate);
                if (toCalendar.compareTo(fromCalendar) < 0) {
                    toCalendar.add(Calendar.YEAR, 1);
                }
            }
            result = toCalendar.getTime();
        }

        return result;
    }

    public static FromToQueryDates getDateQueryParameters(Date from, Date to) {
        Calendar fromCalendar = Calendar.getInstance();

        fromCalendar.setTime(from);
        fromCalendar.set(Calendar.YEAR, BASE_YEAR);
        Calendar newYearFromCalendar = (Calendar) fromCalendar.clone();

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(to);
        toCalendar.set(Calendar.YEAR, BASE_YEAR);
        Calendar newYearToCalendar = (Calendar) toCalendar.clone();

        if (toCalendar.compareTo(fromCalendar) < 0) {
            toCalendar.add(Calendar.YEAR, 1);
        } else {
            newYearFromCalendar.add(Calendar.YEAR, 1);
        }
        newYearToCalendar.add(Calendar.YEAR, 1);

        return new FromToQueryDates(fromCalendar.getTime(),
                toCalendar.getTime(),
                newYearFromCalendar.getTime(),
                newYearToCalendar.getTime());
    }

    public static void checkIsNotAfter(Date from, Date to) {
        if (from != null && to != null && from.compareTo(to) > 0) {
            throw new IllegalArgumentException("fromDate cannot be after toDate");
        }
    }

    public static class FromToQueryDates {
        private Date searchFrom1970;
        private Date searchTo1970;
        private Date searchFrom1971;
        private Date searchTo1971;

        public FromToQueryDates(Date searchFrom1970, Date searchTo1970, Date searchFrom1971, Date searchTo1971) {
            this.searchFrom1970 = searchFrom1970;
            this.searchTo1970 = searchTo1970;
            this.searchFrom1971 = searchFrom1971;
            this.searchTo1971 = searchTo1971;
        }

        public Date getSearchFrom1970() {
            return searchFrom1970;
        }

        public Date getSearchTo1970() {
            return searchTo1970;
        }

        public Date getSearchFrom1971() {
            return searchFrom1971;
        }

        public Date getSearchTo1971() {
            return searchTo1971;
        }
    }
}
