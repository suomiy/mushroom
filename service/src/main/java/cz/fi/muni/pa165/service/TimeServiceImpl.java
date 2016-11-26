package cz.fi.muni.pa165.service;

import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Filip Krepinsky (410022) on 11/25/16
 */

@Service
public class TimeServiceImpl implements TimeService {

    @Override
    public Date getCurrentTime() {
        return getNow().getTime();
    }

    @Override
    public Date getOneWeekBeforeTime() {
        Calendar calendar = getNow();
        calendar.set(Calendar.DATE, -7);

        return calendar.getTime();
    }

    private static Calendar getNow() {
        return Calendar.getInstance();
    }
}
