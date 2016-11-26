package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.service.config.ServiceConfig;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeMethod;

import java.util.Date;

/**
 * Created by michal on 11/25/16.
 *
 * @author Michal Kysilko 436339
 */

@ContextConfiguration(classes = ServiceConfig.class)
public class TimeServiceTest {

    @Autowired
    @InjectMocks
    private TimeService timeService  = new TimeServiceImpl();

    private Date now = new Date();

    @BeforeMethod
    public void init() {
        MockitoAnnotations.initMocks(this);



    }
}
