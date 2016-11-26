package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.service.config.ServiceConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.context.ContextConfiguration;

import java.util.Calendar;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TimeServiceImpl.class)
@ContextConfiguration(classes = ServiceConfig.class)
public class TimeServiceTest {
    private static final Calendar now = Calendar.getInstance();

    private TimeServiceImpl timeService = new TimeServiceImpl();

    @Test
    public void testCurrentTime() throws InterruptedException {
        mockCalendar();
        Assert.assertTrue(timeService.getCurrentTime().compareTo(getCalendarInstance().getTime()) == 0);
    }

    @Test
    public void testOneWeekBeforeTime() throws InterruptedException {
        mockCalendar();
        Calendar oneWeekBack = getCalendarInstance();
        oneWeekBack.set(Calendar.DATE, -7);
        Assert.assertTrue(timeService.getOneWeekBeforeTime().compareTo(oneWeekBack.getTime()) == 0);
    }

    private void mockCalendar() throws InterruptedException {
        PowerMockito.mockStatic(Calendar.class);
        PowerMockito.when(Calendar.getInstance()).thenReturn(getCalendarInstance());
        Thread.sleep(100);
    }

    private Calendar getCalendarInstance() {
        return (Calendar) now.clone();
    }
}
