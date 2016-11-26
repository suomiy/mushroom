package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.MushroomCountDao;
import cz.fi.muni.pa165.dao.VisitDao;
import cz.fi.muni.pa165.entity.*;
import cz.fi.muni.pa165.enums.MushroomType;
import cz.fi.muni.pa165.enums.Rank;
import cz.fi.muni.pa165.enums.Role;
import cz.fi.muni.pa165.service.config.ServiceConfig;
import cz.fi.muni.pa165.service.mappers.ObjectsHelper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.not;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.times;

/**
 * Created by michal on 11/25/16.
 *
 * @author Michal Kysilko 436339
 */

@ContextConfiguration(classes = ServiceConfig.class)
public class MushroomCountServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    @InjectMocks
    private MushroomCountService mushroomCountService = new MushroomCountServiceImpl();

    @Mock
    private MushroomCountDao mushroomCountDao;

    @Mock
    private VisitDao visitDao;

    @Mock
    private TimeService timeService;

    private Mushroom hrib;
    private Mushroom muchomurka;
    private Visit visit1;
    private Visit visit2;
    private Forest forest;
    private Hunter hunter;
    private Date toDate1;
    private Date fromDate1;
    private Date toDate2;
    private Date fromDate2;
    private MushroomCount mcount;
    private MushroomCount mcount2;
    private MushroomCount mcount3;

    @BeforeMethod
    public void init() {

        MockitoAnnotations.initMocks(this);

        fromDate1 = ObjectsHelper.buildDate(12, 10, 2016);
        toDate1 = ObjectsHelper.buildDate(14, 10, 2016);
        fromDate2 = ObjectsHelper.buildDate(15, 10, 2016);
        toDate2 = ObjectsHelper.buildDate(17, 10, 2016);

        hrib = new Mushroom();
        hrib.setName("Hrib smrkovy");
        hrib.setType(MushroomType.EDIBLE);

        muchomurka = new Mushroom();
        muchomurka.setName("Muchomurka cervena");
        muchomurka.setType(MushroomType.NONEDIBLE);

        forest = new Forest();
        forest.setName("Bukovy les");

        hunter = new Hunter();
        hunter.setFirstName("Jozef");
        hunter.setSurname("Straka");
        hunter.setNick("Straka");
        hunter.setEmail("straka@gmail.com");
        hunter.setPasswordHash("123456");
        hunter.setRank(Rank.GURU);
        hunter.setType(Role.USER);

        mcount = new MushroomCount();
        mcount.setMushroom(hrib);
        mcount.setCount(40);
        mcount.setVisit(visit1);

        mcount2 = new MushroomCount();
        mcount2.setMushroom(muchomurka);
        mcount2.setCount(12);
        mcount2.setVisit(visit1);

        mcount3 = new MushroomCount();
        mcount3.setMushroom(hrib);
        mcount3.setCount(2);
        mcount3.setVisit(visit2);

        visit1 = new Visit();
        visit1.setFromDate(fromDate1);
        visit1.setToDate(toDate1);
        visit1.setForest(forest);
        visit1.setHunter(hunter);
        visit1.addMushroomCount(mcount);
        visit1.addMushroomCount(mcount2);

        visit2 = new Visit();
        visit2.setFromDate(fromDate2);
        visit2.setToDate(toDate2);
        visit2.setForest(forest);
        visit2.setHunter(hunter);
        visit2.addMushroomCount(mcount3);

        Mockito.when(mushroomCountDao.findById(1L)).thenReturn(mcount);
        Mockito.when(mushroomCountDao.findById(argThat(not(1L)))).thenReturn(null);

        Mockito.when(mushroomCountDao.findAll()).thenReturn(Arrays.asList(mcount, mcount2));

        Mockito.when(timeService.getOneWeekBeforeTime()).thenReturn(fromDate1);
        Mockito.when(timeService.getCurrentTime()).thenReturn(toDate2);
        Mockito.when(visitDao.findByDate(fromDate1, toDate2))
                .thenReturn(Arrays.asList(visit1, visit2));
    }

    @Test
    public void findById() {
        MushroomCount mc = mushroomCountService.findById(1L);
        Assert.assertEquals(mc.getMushroom(), mcount.getMushroom());
    }

    @Test
    public void findNonExistingById() {
        MushroomCount mc = mushroomCountService.findById(3L);
        Assert.assertNull(mc);
    }

    @Test
    public void create() {
        MushroomCount mc = new MushroomCount();
        mc.setMushroom(hrib);
        mc.setVisit(visit1);
        mushroomCountService.create(mc);
        Mockito.verify(mushroomCountDao, times(1)).create(mc);
    }

    @Test
    public void update() {
        MushroomCount mc = new MushroomCount();
        mc.setMushroom(hrib);
        mc.setVisit(visit1);
        mushroomCountService.create(mc);
        mc.setCount(12);
        mushroomCountService.update(mc);
        Mockito.verify(mushroomCountDao, times(1)).update(mc);
    }

    @Test
    public void delete() {
        MushroomCount mc = new MushroomCount();
        mc.setMushroom(hrib);
        mc.setVisit(visit1);
        mushroomCountService.create(mc);
        mushroomCountService.delete(mc);
        Mockito.verify(mushroomCountDao, times(1)).delete(mc);
    }

    @Test
    public void findAll() {
        List<MushroomCount> mc = mushroomCountService.findAll();
        Assert.assertNotNull(mc);
        Assert.assertEquals(mc.size(), 2);
    }

    @Test
    public void findRecentlyFoundPickableMushrooms() {
        List<MushroomCount> loadedMushroomCount = mushroomCountService.findRecentlyFoundPickableMushrooms();
        assertThat(loadedMushroomCount).isNotNull().hasSize(3)
                .containsOnly(mcount, mcount2, mcount3);
    }
}
