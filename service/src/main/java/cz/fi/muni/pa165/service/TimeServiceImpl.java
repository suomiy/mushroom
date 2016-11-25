package cz.fi.muni.pa165.service;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Filip Krepinsky (410022) on 11/25/16
 */

public class TimeServiceImpl implements TimeService {

    @Override
    public Date getCurrentTime() {
        return new Date();
    }

    @Override
    public Date getOneWeekBeforeTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, -7);

        return calendar.getTime();
    }
}
