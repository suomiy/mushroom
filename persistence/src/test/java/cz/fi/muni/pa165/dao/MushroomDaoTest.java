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
import java.time.Month;
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
        dangerousFullInfoShroom.setFromDate(Month.FEBRUARY);
        dangerousFullInfoShroom.setToDate(Month.NOVEMBER);

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
        Assert.assertEquals(persisted.getFromDate(), Month.FEBRUARY);
        Assert.assertEquals(persisted.getToDate(), Month.NOVEMBER);
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
        mushroomDao.update(dangerousFullInfoShroom);
        Mushroom persisted = mushroomDao.findById(dangerousFullInfoShroom.getId());


        Assert.assertEquals(persisted.getName(), "Mochomurka");
        Assert.assertEquals(persisted.getType(), MushroomType.POISONOUS);
        Assert.assertEquals(persisted.getDescription(), "This shroom is red and pretty.");
        Assert.assertEquals(persisted.getFromDate(), Month.FEBRUARY);
        Assert.assertEquals(persisted.getToDate(), Month.NOVEMBER);

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
        mushroomDao.create(dangerousFullInfoShroom);

        edibleShroom.setFromDate(Month.JUNE);
        edibleShroom.setToDate(Month.AUGUST);
        mushroomDao.create(edibleShroom);

        List<Mushroom> shrooms = mushroomDao.findByDate(Month.FEBRUARY);
        Assert.assertEquals(shrooms.size(), 1);

        shrooms = mushroomDao.findByDate(Month.JUNE);
        Assert.assertEquals(shrooms.size(), 2);

        shrooms = mushroomDao.findByDate(Month.AUGUST);
        Assert.assertEquals(shrooms.size(), 2);

        shrooms = mushroomDao.findByDate(Month.NOVEMBER);
        Assert.assertEquals(shrooms.size(), 1);

        shrooms = mushroomDao.findByDate(Month.DECEMBER);
        Assert.assertEquals(shrooms.size(), 0);

        edibleShroom.setToDate(null);

        shrooms = mushroomDao.findByDate(Month.JUNE);
        Assert.assertEquals(shrooms.size(), 1);

        edibleShroom.setFromDate(null);

        shrooms = mushroomDao.findByDate(Month.JUNE);
        Assert.assertEquals(shrooms.size(), 1);

    }
}
