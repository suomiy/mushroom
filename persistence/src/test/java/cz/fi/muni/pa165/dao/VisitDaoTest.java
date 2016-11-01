package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.PersistenceApplicationContext;
import cz.fi.muni.pa165.entity.*;
import cz.fi.muni.pa165.enums.MushroomType;
import cz.fi.muni.pa165.enums.Rank;
import cz.fi.muni.pa165.enums.Role;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.util.Date;
import java.util.List;
/**
 * @author Jiri Sacha 409861
 */

@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@org.springframework.transaction.annotation.Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class VisitDaoTest extends AbstractTestNGSpringContextTests {

    @Inject
    private VisitDao visitDao;

    @Inject
    private MushroomCountDao mushroomCountDao;

    @PersistenceContext
    private EntityManager em;

    @PersistenceUnit
    private EntityManagerFactory emf;

    private Visit visit;
    private Visit basicVisit;
    private Hunter hunter;
    private Forest forest;
    private Forest forest2;
    private MushroomCount mc;
    private Mushroom mushroom;
    private Mushroom mushroom2;

    @BeforeMethod
    public void setup() {

        forest = new Forest();
        forest2 = new Forest();
        hunter = new Hunter();
        mushroom = new Mushroom();
        mushroom2 = new Mushroom();
        visit = new Visit();
        basicVisit = new Visit();
        mc = new MushroomCount();

        forest.setLocalityDescription("unknown");
        forest.setName("Birch forest");

        forest2.setLocalityDescription("muddy place");
        forest2.setName("Swamp of sorrows");

        mushroom.setName("champignon");
        mushroom.setType(MushroomType.EDIBLE);

        mushroom2.setName("psilocybin");
        mushroom2.setType(MushroomType.PSYCHEDELIC);

        mc.setMushroom(mushroom);
        mc.setVisit(visit);
        mc.setCount(10);

        hunter.setNick("Fury");
        hunter.setFirstName("Jan");
        hunter.setSurname("Novak");
        hunter.setEmail("jan@novak.cz");
        hunter.setPasswordHash("deadbeef");
        hunter.setType(Role.USER);
        hunter.setRank(Rank.BEGINNER);

        visit.setHunter(hunter);
        visit.setForest(forest);
        visit.addMushroomCount(mc);
        visit.setDate(new Date());

        basicVisit.setHunter(hunter);
        basicVisit.setForest(forest);
        basicVisit.setDate(new Date());
    }


    @Test
    public void createVisitTest(){

        EntityManager m = emf.createEntityManager();
        m.getTransaction().begin();
        m.persist(mushroom);
        m.persist(hunter);
        m.persist(forest);
        m.getTransaction().commit();
        m.close();

        visitDao.create(visit);

        List<Visit> listVisit = em.createQuery("select v from Visit v", Visit.class)
                .getResultList();
        if (listVisit.size() != 1) { Assert.fail();}
        Visit dbVisit = listVisit.get(0);

        Assert.assertEquals(dbVisit.getId(),visit.getId());
        Assert.assertEquals(dbVisit.getForest().getId(),forest.getId());
        Assert.assertEquals(dbVisit.getHunter().getId(),hunter.getId());
        Assert.assertEquals(dbVisit.getMushroomsCount(),visit.getMushroomsCount());
        Assert.assertEquals(new java.sql.Date(dbVisit.getDate().getTime()).toString(),
                new java.sql.Date(visit.getDate().getTime()).toString());
    }

    @Test
    public void findByIdVisitTest(){

        EntityManager m = emf.createEntityManager();
        m.getTransaction().begin();
        m.persist(mushroom);
        m.persist(hunter);
        m.persist(forest);
        m.getTransaction().commit();
        m.close();

        visitDao.create(visit);
        Visit dbVisit = visitDao.findById(visit.getId());

        Assert.assertEquals(dbVisit.getId(),visit.getId());
        Assert.assertEquals(dbVisit.getForest().getId(),forest.getId());
        Assert.assertEquals(dbVisit.getHunter().getId(),hunter.getId());
        Assert.assertEquals(dbVisit.getMushroomsCount(),visit.getMushroomsCount());
        Assert.assertEquals(new java.sql.Date(dbVisit.getDate().getTime()).toString(),
                new java.sql.Date(visit.getDate().getTime()).toString());
    }


    @Test
    public void deleteVisitTest(){

        EntityManager m = emf.createEntityManager();
        m.getTransaction().begin();
        m.persist(mushroom);
        m.persist(hunter);
        m.persist(forest);
        m.getTransaction().commit();
        m.close();

        visitDao.create(visit);
        Visit dbVisit = visitDao.findById(visit.getId());
        visitDao.delete(dbVisit);
        List<Visit> list = em.createQuery("select v from Visit v", Visit.class)
                .getResultList();

        Assert.assertTrue(em.createQuery("select v from Visit v", Visit.class)
                .getResultList().isEmpty());
    }

    @Test
    public void updateVisitTest(){

        EntityManager m = emf.createEntityManager();
        m.getTransaction().begin();
        m.persist(mushroom);
        m.persist(hunter);
        m.persist(forest);
        m.persist(forest2);
        m.getTransaction().commit();
        m.close();

        visitDao.create(visit);
        visit.setForest(forest2);
        visitDao.update(visit);

        List<Visit> listVisit = em.createQuery("select v from Visit v", Visit.class)
                .getResultList();
        if (listVisit.size() != 1) { Assert.fail();}
        Visit dbVisit = listVisit.get(0);

        Assert.assertEquals(dbVisit.getId(),visit.getId());
        Assert.assertEquals(dbVisit.getForest().getId(),forest2.getId());
        Assert.assertEquals(dbVisit.getHunter().getId(),hunter.getId());
        Assert.assertEquals(dbVisit.getMushroomsCount(),visit.getMushroomsCount());
        Assert.assertEquals(new java.sql.Date(dbVisit.getDate().getTime()).toString(),
                new java.sql.Date(visit.getDate().getTime()).toString());
    }

    @Test
    public void findByHunter(){

        EntityManager m = emf.createEntityManager();
        m.getTransaction().begin();
        m.persist(mushroom);
        m.persist(hunter);
        m.persist(forest);
        m.getTransaction().commit();
        m.close();

        visitDao.create(visit);
        List <Visit> dbListVisit = visitDao.findByHunter(hunter);
        if (dbListVisit.size() != 1) { Assert.fail();}
        Visit dbVisit = dbListVisit.get(0);

        Assert.assertEquals(dbVisit.getId(),visit.getId());
        Assert.assertEquals(dbVisit.getForest().getId(),forest.getId());
        Assert.assertEquals(dbVisit.getHunter().getId(),hunter.getId());
        Assert.assertEquals(dbVisit.getMushroomsCount(),visit.getMushroomsCount());
        Assert.assertEquals(new java.sql.Date(dbVisit.getDate().getTime()).toString(),
                new java.sql.Date(visit.getDate().getTime()).toString());
    }

    @Test
    public void findByForest(){

        EntityManager m = emf.createEntityManager();
        m.getTransaction().begin();
        m.persist(mushroom);
        m.persist(hunter);
        m.persist(forest);
        m.persist(forest2);
        m.getTransaction().commit();
        m.close();

        visitDao.create(visit);
        List <Visit> dbListVisit = visitDao.findByForest(forest2);
        if (dbListVisit.size() != 0) {Assert.fail();}
    }

    @Test
    public void findByMushroom(){

        EntityManager m = emf.createEntityManager();
        m.getTransaction().begin();
        m.persist(mushroom);
        m.persist(hunter);
        m.persist(forest);
        m.getTransaction().commit();
        m.close();

        visitDao.create(visit);
        List <Visit> dbListVisit = visitDao.findByMushroom(mushroom);
        if (dbListVisit.size() != 1) {Assert.fail();}
        Visit dbVisit = dbListVisit.get(0);

        Assert.assertEquals(dbVisit.getId(),visit.getId());
        Assert.assertEquals(dbVisit.getForest().getId(),forest.getId());
        Assert.assertEquals(dbVisit.getHunter().getId(),hunter.getId());
        Assert.assertEquals(dbVisit.getMushroomsCount(),visit.getMushroomsCount());
        Assert.assertEquals(new java.sql.Date(dbVisit.getDate().getTime()).toString(),
                new java.sql.Date(visit.getDate().getTime()).toString());
    }

    @Test
    public void findByDate(){

        EntityManager m = emf.createEntityManager();
        m.getTransaction().begin();
        m.persist(mushroom);
        m.persist(hunter);
        m.persist(forest);
        m.getTransaction().commit();
        m.close();

        visitDao.create(visit);
        List <Visit> dbListVisit = visitDao.findByDate(visit.getDate());
        if (dbListVisit.size() != 1) {Assert.fail();}
        Visit dbVisit = dbListVisit.get(0);

        Assert.assertEquals(dbVisit.getId(),visit.getId());
        Assert.assertEquals(dbVisit.getForest().getId(),forest.getId());
        Assert.assertEquals(dbVisit.getHunter().getId(),hunter.getId());
        Assert.assertEquals(dbVisit.getMushroomsCount(),visit.getMushroomsCount());
        Assert.assertEquals(new java.sql.Date(dbVisit.getDate().getTime()).toString(),
                new java.sql.Date(visit.getDate().getTime()).toString());
    }

    @Test
    public void testMushroomCountCRUD() {
        EntityManager m = emf.createEntityManager();
        m.getTransaction().begin();
        m.persist(mushroom);
        m.persist(mushroom2);
        m.persist(hunter);
        m.persist(forest);
        m.getTransaction().commit();
        m.close();

        MushroomCount mushroomCount = new MushroomCount();
        mushroomCount.setMushroom(mushroom);
        mushroomCount.setCount(1);

        MushroomCount mushroomCount2 = new MushroomCount();
        mushroomCount2.setMushroom(mushroom2);
        mushroomCount2.setCount(2);

        basicVisit.addMushroomCount(mushroomCount);
        basicVisit.addMushroomCount(mushroomCount2);
        visitDao.create(basicVisit);

        List<MushroomCount> mushroomCountList = mushroomCountDao.findAll();

        Assert.assertEquals(basicVisit.getMushroomsCount().size(), 2);
        Assert.assertEquals(mushroomCountList.size(), 2);
        Assert.assertTrue(mushroomCountList.contains(mushroomCount));
        Assert.assertTrue(mushroomCountList.contains(mushroomCount2));

        mushroomCount2.setCount(5);

        mushroomCountList = mushroomCountDao.findAll();

        MushroomCount updated = mushroomCountList.get(mushroomCountList.indexOf(mushroomCount2));
        Assert.assertEquals(updated.getCount(), 5);

        basicVisit.removeMushroomCount(mushroomCount2);

        mushroomCountList = mushroomCountDao.findAll();

        Assert.assertEquals(basicVisit.getMushroomsCount().size(), 1);
        Assert.assertEquals(mushroomCountList.size(), 1);
        Assert.assertTrue(mushroomCountList.contains(mushroomCount));
        Assert.assertTrue(!mushroomCountList.contains(mushroomCount2));
    }
}
