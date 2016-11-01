package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.PersistenceApplicationContext;
import cz.fi.muni.pa165.entity.*;
import cz.fi.muni.pa165.enums.MushroomType;
import cz.fi.muni.pa165.enums.Rank;
import cz.fi.muni.pa165.enums.Role;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

/**
 * Created by Erik Macej 433744 , on 31.10.16.
 *
 * @author Erik Macej 433744
 */


@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class MushroomCountDaoTest extends AbstractTestNGSpringContextTests {

    @Inject
    private ForestDao forestDao;

    @Inject
    private HunterDao hunterDao;

    @Inject
    private MushroomDao mushroomDao;

    @Inject
    private VisitDao visitDao;

    @Inject
    private MushroomCountDao mushroomCountDao;

    @PersistenceContext
    private EntityManager em;

    private Forest forest;
    private Hunter hunter;
    private Mushroom mushroom1;
    private Mushroom mushroom2;
    private Visit visit;
    private MushroomCount mushroomCount1;
    private MushroomCount mushroomCount2;

    @BeforeMethod
    public void setUp() {
        prepareForest();
        prepareHunter();
        prepareMushroom();
        prepareMushroomCount();
        prepareVisit();
    }

    private void prepareForest() {
        forest = new Forest();
        forest.setName("Forest");
        forestDao.create(forest);
    }

    private void prepareHunter() {
        hunter = new Hunter();
        hunter.setFirstName("Jozef");
        hunter.setSurname("Straka");
        hunter.setNick("Straka");
        hunter.setEmail("straka@gmail.com");
        hunter.setPasswordHash("123456");
        hunter.setRank(Rank.GURU);
        hunter.setType(Role.USER);

        hunterDao.create(hunter);
    }

    private void prepareMushroom() {
        mushroom1 = new Mushroom();
        mushroom1.setName("Dubak");
        mushroom1.setType(MushroomType.EDIBLE);
        mushroom1.setFromDate(new Date());
        mushroom1.setToDate(new Date());

        mushroom2 = new Mushroom();
        mushroom2.setName("Kozak");
        mushroom2.setType(MushroomType.EDIBLE);
        mushroom2.setFromDate(new Date());
        mushroom2.setToDate(new Date());

        mushroomDao.create(mushroom1);
        mushroomDao.create(mushroom2);
    }

    private void prepareMushroomCount() {
        mushroomCount1 = new MushroomCount();
        mushroomCount1.setMushroom(mushroom1);
        mushroomCount1.setCount(6);

        mushroomCount2 = new MushroomCount();
        mushroomCount2.setMushroom(mushroom2);
        mushroomCount2.setCount(5);
    }

    private void prepareVisit() {
        visit = new Visit();
        visit.setDate(new Date(1, 1, 1994));
        visit.setForest(forest);
        visit.setHunter(hunter);
        visit.addMushroomCount(mushroomCount1);
        visit.addMushroomCount(mushroomCount2);

        visitDao.create(visit);
    }

    @Test
    public void findAllMushroomCountsTest() {
        List<MushroomCount> found = mushroomCountDao.findAll();

        Assert.assertEquals(found.size(), 2);
        Assert.assertTrue(found.contains(mushroomCount1));
        Assert.assertTrue(found.contains(mushroomCount2));
    }

    @Test
    public void findMushroomCountTest() {
        MushroomCount found = mushroomCountDao.findById(mushroomCount1.getId());
        Assert.assertEquals(mushroomCount1, found);
    }


}
