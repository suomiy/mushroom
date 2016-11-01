package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.PersistenceApplicationContext;
import cz.fi.muni.pa165.entity.Mushroom;
import cz.fi.muni.pa165.enums.MushroomType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Filip Krepinsky (410022) on 10/31/16
 */

@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@org.springframework.transaction.annotation.Transactional
public class MushroomDaoTest extends AbstractTestNGSpringContextTests {

    @Inject
    private MushroomDao mushroomDao;

    @PersistenceContext
    private EntityManager em;


    private Mushroom edibleShroom;
    private Mushroom dangerousFullInfoShroom;

    private Mushroom duplicateEdibleShroom;

    private Mushroom unknownNameShroom;
    private Mushroom unknownTypeShroom;
    private Mushroom unknownShroom;


    @BeforeMethod
    public void init() {
        createObjects();
        setData();
    }

    private void createObjects() {
        edibleShroom = new Mushroom();
        dangerousFullInfoShroom = new Mushroom();
        duplicateEdibleShroom = new Mushroom();
        unknownNameShroom = new Mushroom();
        unknownTypeShroom = new Mushroom();
        unknownShroom = new Mushroom();
    }

    private void setData() {
        edibleShroom.setName("Hribek");
        edibleShroom.setType(MushroomType.EDIBLE);

        duplicateEdibleShroom.setName("Hribek");
        duplicateEdibleShroom.setType(MushroomType.EDIBLE);

        dangerousFullInfoShroom.setName("Mochomurka");
        dangerousFullInfoShroom.setType(MushroomType.POISONOUS);
        dangerousFullInfoShroom.setDescription("This shroom is red and pretty.");
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.set(2016, Calendar.FEBRUARY, 1);
        to.set(2016, Calendar.NOVEMBER, 30);
        dangerousFullInfoShroom.setFromDate(from.getTime());
        dangerousFullInfoShroom.setToDate(to.getTime());

        unknownNameShroom.setType(MushroomType.NONEDIBLE);

        unknownTypeShroom.setName("Horcak");
    }

    @Test
    public void createMinInfo() {
        mushroomDao.create(edibleShroom);

        Mushroom persisted = mushroomDao.findById(edibleShroom.getId());

        Assert.assertEquals(persisted.getName(), "Hribek");
        Assert.assertEquals(persisted.getType(), MushroomType.EDIBLE);
    }

    @Test
    public void createFullInfo() {
        mushroomDao.create(dangerousFullInfoShroom);

        Mushroom persisted = mushroomDao.findById(dangerousFullInfoShroom.getId());

        Assert.assertEquals(persisted.getName(), "Mochomurka");
        Assert.assertEquals(persisted.getType(), MushroomType.POISONOUS);
        Assert.assertEquals(persisted.getDescription(), "This shroom is red and pretty.");
        Assert.assertNotNull(persisted.getFromDate());
        Assert.assertNotNull(persisted.getToDate());
    }

    @Test(expectedExceptions = Exception.class)
    public void createDuplicate() {
        mushroomDao.create(edibleShroom);
        mushroomDao.create(duplicateEdibleShroom);
    }

    @Test(expectedExceptions = Exception.class)
    public void createUnknownName() {
        mushroomDao.create(unknownNameShroom);
    }

    @Test(expectedExceptions = Exception.class)
    public void createUnknownType() {
        mushroomDao.create(unknownTypeShroom);
    }

    @Test(expectedExceptions = Exception.class)
    public void createUnknown() {
        mushroomDao.create(unknownShroom);
    }

    @Test
    public void update() {
        mushroomDao.create(edibleShroom);

        dangerousFullInfoShroom.setId(edibleShroom.getId());
        Mushroom persisted = mushroomDao.update(dangerousFullInfoShroom);

        em.flush();

        Assert.assertEquals(persisted.getName(), "Mochomurka");
        Assert.assertEquals(persisted.getType(), MushroomType.POISONOUS);
        Assert.assertEquals(persisted.getDescription(), "This shroom is red and pretty.");
        Assert.assertNotNull(persisted.getFromDate());
        Assert.assertNotNull(persisted.getToDate());

        mushroomDao.create(duplicateEdibleShroom);
        mushroomDao.update(dangerousFullInfoShroom);

        Assert.assertEquals(duplicateEdibleShroom.getName(), "Hribek");
        Assert.assertEquals(duplicateEdibleShroom.getType(), MushroomType.EDIBLE);
        Assert.assertEquals(mushroomDao.findAll().size(), 2);
    }

    @Test
    public void delete() {
        mushroomDao.create(edibleShroom);
        mushroomDao.create(dangerousFullInfoShroom);
        mushroomDao.delete(edibleShroom);

        Assert.assertEquals(mushroomDao.findAll().size(), 1);

    }

    @Test
    public void findById() {
        Assert.assertNull(mushroomDao.findById(1L));

        mushroomDao.create(dangerousFullInfoShroom);
        mushroomDao.create(edibleShroom);

        Mushroom persisted = mushroomDao.findById(edibleShroom.getId());

        Assert.assertEquals(persisted.getName(), "Hribek");
        Assert.assertEquals(persisted.getType(), MushroomType.EDIBLE);
    }

    @Test
    public void findAll() {
        List<Mushroom> shrooms = mushroomDao.findAll();
        Assert.assertEquals(shrooms.size(), 0);

        mushroomDao.create(dangerousFullInfoShroom);
        mushroomDao.create(edibleShroom);

        shrooms = mushroomDao.findAll();
        Assert.assertEquals(shrooms.size(), 2);

        mushroomDao.delete(dangerousFullInfoShroom);

        shrooms = mushroomDao.findAll();
        Assert.assertEquals(shrooms.size(), 1);
    }

    @Test
    public void findByName() {
        mushroomDao.create(dangerousFullInfoShroom);
        mushroomDao.create(edibleShroom);

        Mushroom found = mushroomDao.findByName(edibleShroom.getName());
        Assert.assertEquals(found, edibleShroom);

        Assert.assertNull(mushroomDao.findByName("zyzxzy"));
    }

    @Test
    public void findByType() {
        mushroomDao.create(dangerousFullInfoShroom);
        mushroomDao.create(edibleShroom);

        List<Mushroom> shrooms = mushroomDao.findByType(edibleShroom.getType());
        Assert.assertEquals(shrooms.size(), 1);

        edibleShroom.setType(MushroomType.POISONOUS);

        shrooms = mushroomDao.findByType(edibleShroom.getType());
        Assert.assertEquals(shrooms.size(), 2);

        Assert.assertEquals(mushroomDao.findByType(MushroomType.NONEDIBLE).size(), 0);
    }

    @Test
    public void findByDate() {
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.set(2016, Calendar.JUNE, 25);
        to.set(2016, Calendar.AUGUST, 1);

        mushroomDao.create(dangerousFullInfoShroom);

        edibleShroom.setFromDate(from.getTime());
        edibleShroom.setToDate(to.getTime());
        mushroomDao.create(edibleShroom);

        Calendar fromSearch = Calendar.getInstance();
        Calendar toSearch = Calendar.getInstance();

        fromSearch.set(2016, Calendar.JANUARY, 31);
        toSearch.set(2016, Calendar.NOVEMBER, 30);
        List<Mushroom> shrooms = mushroomDao.findByDate(fromSearch.getTime(), toSearch.getTime());
        Assert.assertEquals(shrooms.size(), 0);

        fromSearch.set(2016, Calendar.FEBRUARY, 1);
        toSearch.set(2016, Calendar.DECEMBER, 1);
        shrooms = mushroomDao.findByDate(fromSearch.getTime(), toSearch.getTime());
        Assert.assertEquals(shrooms.size(), 0);

        fromSearch.set(2016, Calendar.FEBRUARY, 1);
        toSearch.set(2016, Calendar.NOVEMBER, 30);
        shrooms = mushroomDao.findByDate(fromSearch.getTime(), toSearch.getTime());
        Assert.assertEquals(shrooms.size(), 1);

        fromSearch.set(2016, Calendar.JUNE, 25);
        toSearch.set(2016, Calendar.AUGUST, 1);
        shrooms = mushroomDao.findByDate(fromSearch.getTime(), toSearch.getTime());
        Assert.assertEquals(shrooms.size(), 2);

        fromSearch.set(2016, Calendar.JULY, 15);
        toSearch.set(2016, Calendar.JULY, 15);
        shrooms = mushroomDao.findByDate(fromSearch.getTime(), toSearch.getTime());
        Assert.assertEquals(shrooms.size(), 2);

        fromSearch.set(2016, Calendar.AUGUST, 2);
        toSearch.set(2016, Calendar.NOVEMBER, 30);
        shrooms = mushroomDao.findByDate(fromSearch.getTime(), toSearch.getTime());
        Assert.assertEquals(shrooms.size(), 1);

        fromSearch.set(2016, Calendar.AUGUST, 2);
        toSearch.set(2016, Calendar.NOVEMBER, 31);
        shrooms = mushroomDao.findByDate(fromSearch.getTime(), toSearch.getTime());
        Assert.assertEquals(shrooms.size(), 0);

        edibleShroom.setToDate(null);
        fromSearch.set(2016, Calendar.JUNE, 25);
        toSearch.set(2016, Calendar.AUGUST, 1);
        shrooms = mushroomDao.findByDate(fromSearch.getTime(), toSearch.getTime());
        Assert.assertEquals(shrooms.size(), 1);

        edibleShroom.setFromDate(null);
        shrooms = mushroomDao.findByDate(fromSearch.getTime(), toSearch.getTime());
        Assert.assertEquals(shrooms.size(), 1);

        edibleShroom.setToDate(to.getTime());
        shrooms = mushroomDao.findByDate(fromSearch.getTime(), toSearch.getTime());
        Assert.assertEquals(shrooms.size(), 1);

        to.set(2016, Calendar.JULY, 20);
        edibleShroom.setToDate(to.getTime());
        from.set(2016, Calendar.NOVEMBER, 1);
        edibleShroom.setFromDate(from.getTime());
        fromSearch.set(2016, Calendar.NOVEMBER, 1);
        toSearch.set(2016, Calendar.JULY, 20);
        shrooms = mushroomDao.findByDate(fromSearch.getTime(), toSearch.getTime());
        Assert.assertEquals(shrooms.size(), 1);

        fromSearch.set(2016, Calendar.JANUARY, 1);
        toSearch.set(2016, Calendar.JULY, 20);
        shrooms = mushroomDao.findByDate(fromSearch.getTime(), toSearch.getTime());
        Assert.assertEquals(shrooms.size(), 1);

        shrooms = mushroomDao.findByDate(toSearch.getTime());
        Assert.assertEquals(shrooms.size(), 2);

        from.set(2016, Calendar.FEBRUARY, 29);
        edibleShroom.setFromDate(from.getTime());
        to.set(2016, Calendar.MARCH, 2);
        edibleShroom.setToDate(to.getTime());
        fromSearch.set(2016, Calendar.FEBRUARY, 29);
        toSearch.set(2016, Calendar.MARCH, 2);
        shrooms = mushroomDao.findByDate(fromSearch.getTime(), toSearch.getTime());
        Assert.assertEquals(shrooms.size(), 2);

        fromSearch.set(2016, Calendar.FEBRUARY, 28);
        toSearch.set(2016, Calendar.MARCH, 2);
        shrooms = mushroomDao.findByDate(fromSearch.getTime(), toSearch.getTime());
        Assert.assertEquals(shrooms.size(), 1);

        fromSearch.set(2016, Calendar.FEBRUARY, 29);
        toSearch.set(2016, Calendar.MARCH, 3);
        shrooms = mushroomDao.findByDate(fromSearch.getTime(), toSearch.getTime());
        Assert.assertEquals(shrooms.size(), 1);

        toSearch.set(2016, Calendar.MARCH, 2);
        shrooms = mushroomDao.findByDate(toSearch.getTime());
        Assert.assertEquals(shrooms.size(), 2);
    }

    @Test(expectedExceptions = Exception.class)
    public void findByDateThrowsOne() {
        mushroomDao.findByDate(null, new Date());
    }

    @Test(expectedExceptions = Exception.class)
    public void findByDateThrowsTwo() {
        mushroomDao.findByDate(new Date(), null);
    }

    @Test(expectedExceptions = Exception.class)
    public void findByDateThrowsThree() {
        mushroomDao.findByDate(null, null);
    }

    @Test(expectedExceptions = Exception.class)
    public void findByDateThrowsFour() {
        mushroomDao.findByDate(null);
    }
}
