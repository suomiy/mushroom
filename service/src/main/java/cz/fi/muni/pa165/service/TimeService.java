package cz.fi.muni.pa165.service;

import java.util.Date;

/**
 * @author Filip Krepinsky (410022) on 11/25/16
 */

public interface TimeService {
    Date getCurrentTime();

    Date getOneWeekBeforeTime();
}
