package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.VisitDao;
import cz.fi.muni.pa165.entity.*;
import cz.fi.muni.pa165.enums.MushroomType;
import cz.fi.muni.pa165.enums.Rank;
import cz.fi.muni.pa165.enums.Role;
import cz.fi.muni.pa165.service.config.ServiceConfig;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.times;

/**
 * Created by michal on 11/25/16.
 *
 * @author Michal Kysilko 436339
 */
@ContextConfiguration(classes = ServiceConfig.class)
public class VisitServiceTest {

    @Autowired
    @InjectMocks
    private VisitService visitService  = new VisitServiceImpl();

    @Mock
    private VisitDao visitDao;

    private Hunter hunter;
    private Visit visit;
    private Visit visit2;
    private Forest forest;
    private Forest forest2;
    private Mushroom hrib;
    private Mushroom muchomurka;
    private MushroomCount mcount;
    private MushroomCount mcount2;

    private Date fromDate = buildDate(20,11,2016);
    private Date toDate = buildDate(22,11,2016);
    private Date okTestDate = buildDate(21, 11, 2016);
    private Date failTestDate = buildDate(25, 11, 2016);

    @BeforeMethod
    public void init() {
        MockitoAnnotations.initMocks(this);

        hrib = new Mushroom();
        hrib.setName("Hrib smrkovy");
        hrib.setType(MushroomType.EDIBLE);

        muchomurka = new Mushroom();
        muchomurka.setName("Muchomurka cervena");
        muchomurka.setType(MushroomType.NONEDIBLE);

        hunter = new Hunter();
        hunter.setNick("fake");
        hunter.setEmail("fake@fake.fake");
        hunter.setPasswordHash("fAkE");
        hunter.setType(Role.USER);
        hunter.setRank(Rank.BEGINNER);

        forest = new Forest();
        forest.setName("Bukovy les");

        forest2 = new Forest();
        forest2.setName("Smrkovy les");

        mcount = new MushroomCount();
        mcount.setMushroom(hrib);
        mcount.setCount(40);
        mcount.setVisit(visit);

        mcount2 = new MushroomCount();
        mcount2.setMushroom(muchomurka);
        mcount2.setCount(12);
        mcount2.setVisit(visit2);

        visit = new Visit();
        visit.setFromDate(fromDate);
        visit.setToDate(okTestDate);
        visit.setHunter(hunter);
        visit.setForest(forest);
        visit.addMushroomCount(mcount);

        visit2 = new Visit();
        visit2.setFromDate(okTestDate);
        visit2.setToDate(toDate);
        visit2.setHunter(hunter);
        visit2.setForest(forest2);
        visit2.addMushroomCount(mcount2);

        Mockito.when(visitDao.findByDate(okTestDate)).thenReturn(Arrays.asList(visit, visit2));
        Mockito.when(visitDao.findByDate(fromDate)).thenReturn(Arrays.asList(visit));
        Mockito.when(visitDao.findByDate(failTestDate)).thenReturn(null);

        Mockito.when(visitDao.findByDate(fromDate, okTestDate)).thenReturn(Arrays.asList(visit));
        Mockito.when(visitDao.findByDate(fromDate, failTestDate)).thenReturn(null);

        Mockito.when(visitDao.findByForest(forest)).thenReturn(Arrays.asList(visit));
        Mockito.when(visitDao.findByForest(forest2)).thenReturn(Arrays.asList(visit2));

        Mockito.when(visitDao.findByHunter(hunter)).thenReturn(Arrays.asList(visit, visit2));

        Mockito.when(visitDao.findById(1L)).thenReturn(visit);
        Mockito.when(visitDao.findById(2L)).thenReturn(visit2);
        Mockito.when(visitDao.findById(3L)).thenReturn(null);

        Mockito.when(visitDao.findAll()).thenReturn(Arrays.asList(visit, visit2));
        Mockito.when(visitDao.findByMushroom(hrib)).thenReturn(Arrays.asList(visit));
    }

    private Date buildDate(int day, int month, int year) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.YEAR, year);
        return c.getTime();
    }

    @Test
    public void create() {
        visitService.create(visit);
        Mockito.verify(visitDao, times(1)).create(visit);
    }


    @Test
    public void delete() {
        visitService.create(visit);
        visitService.delete(visit);
        Mockito.verify(visitDao, times(1)).delete(visit);
    }

    @Test
    public void update() {
        visit2.setToDate(failTestDate);
        visitService.update(visit2);
        Mockito.verify(visitDao, times(1)).update(visit2);
    }

    @Test
    public void findById() {
        Visit v = visitService.findById(1L);
        Assert.assertEquals(v.getForest().getName(), forest.getName());
    }

    @Test
    public void findNonExistingById() {
        Visit v = visitService.findById(3L);
        Assert.assertNull(v);
    }

    @Test
    public void findAll() {
        List<Visit> v = visitService.findAll();
        Assert.assertEquals(v.size(), 2);
        Assert.assertEquals(v.get(0).getHunter().getRank(), hunter.getRank());
        Assert.assertEquals(v.get(1).getHunter().getRank(), hunter.getRank());
    }

    @Test
    public void findBySingleDate() {
        List<Visit> v = visitService.findByDate(okTestDate);
        Assert.assertEquals(v.size(), 2);
    }

    @Test
    public void findBySingleWrongDate() {
        List<Visit> v = visitService.findByDate(failTestDate);
        Assert.assertNull(v);
    }

    @Test
    public void findByDoubleDate() {
        List<Visit> v = visitService.findByDate(fromDate, okTestDate);
        Assert.assertEquals(v.size(), 1);
    }

    @Test
    public void findByDoubleWrongDate() {
        List<Visit> v = visitService.findByDate(fromDate, failTestDate);
        Assert.assertNull(v);
    }

    @Test
    public void findByForest() {
        List<Visit> v = visitService.findByForest(forest);
        Assert.assertEquals(v.size(), 1);
        Assert.assertEquals(v.get(0).getForest().getName(), forest.getName());
    }

    @Test
    public void findByHunter() {
        List<Visit> v = visitService.findByHunter(hunter);
        Assert.assertEquals(v.size(), 2);
        Assert.assertEquals(v.get(0).getHunter().getNick(), hunter.getNick());
    }

    @Test
    public void findByMushroom() {
        List<Visit> loadedVisits = visitService.findByMushroom(hrib);

        assertThat(loadedVisits).isNotNull().hasSize(1).containsOnly(visit);
    }

}
