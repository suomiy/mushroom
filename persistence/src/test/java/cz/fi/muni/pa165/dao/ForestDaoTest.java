package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.PersistenceApplicationContext;
import cz.fi.muni.pa165.entity.Forest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;


/**
 * Created by Erik Macej 433744 , on 23.10.16.
 *
 * @author Erik Macej 433744
 */

@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class ForestDaoTest extends AbstractTestNGSpringContextTests {

    @Inject
    private ForestDao forestDao;

    @PersistenceContext
    private EntityManager em;

    private Forest forest1;
    private Forest forest2;

    @BeforeMethod
    public void createForest(){
        forest1 = new Forest();
        forest2 = new Forest();

        forest1.setName("Forest1");
        forest1.setLocalityDescription("locality1");
        forest2.setName("Forest2");
        forest2.setLocalityDescription("locality2");

        forestDao.create(forest1);
        forestDao.create(forest2);
    }

    @Test
    public void createForestTest(){

        Forest forestForCreate = new Forest();
        forestForCreate.setName("ForestForCreate");

        forestDao.create(forestForCreate);
        Forest found = forestDao.findById(forestForCreate.getId());

        Assert.assertEquals(found.getName(),forestForCreate.getName());
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createForestWithNullName(){
        Forest forest = new Forest();
        forest.setName(null);
        forestDao.create(forest);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void createTwoForestWithSameName(){
        Forest anotherForest = new Forest();
        anotherForest.setName("Forest1");
        forestDao.create(anotherForest);
    }

    @Test
    public void findAllForestsTest(){
        List<Forest> loadedForests = forestDao.findAll();
        Assert.assertEquals(loadedForests.size(),2);

        Forest assertForest1 = new Forest();
        Forest assertForest2 = new Forest();

        assertForest1.setName(forest1.getName());
        assertForest1.setLocalityDescription(forest1.getLocalityDescription());
        assertForest2.setName(forest2.getName());
        assertForest2.setLocalityDescription(forest2.getLocalityDescription());

        Assert.assertTrue(loadedForests.contains(assertForest1));
        Assert.assertTrue(loadedForests.contains(assertForest2));
    }

    @Test
    public void findForestByIdTest(){
        Forest found = forestDao.findById(forest1.getId());

        Assert.assertEquals(found.getName(),forest1.getName());
        Assert.assertEquals(found.getLocalityDescription(),
                forest1.getLocalityDescription());
    }

    @Test
    public void deleteForestTest(){
        forestDao.delete(forest1);
        Assert.assertNull(forestDao.findById(forest1.getId()));
    }

    @Test
    public void editForestTest() {
        Forest editedForest = new Forest();
        editedForest.setId(forest1.getId());
        editedForest.setName("Dark Forest");
        editedForest.setLocalityDescription("Dark locality");

        forestDao.update(editedForest);

        Forest loadedForest = forestDao.findById(forest1.getId());

        Assert.assertEquals(loadedForest.getName(),editedForest.getName());
        Assert.assertEquals(loadedForest.getLocalityDescription(),
                editedForest.getLocalityDescription());
    }

    @Test
    public void findForestByNameTest(){
        Forest found = forestDao.findByName("Forest1");
        Assert.assertEquals(found.getName(),forest1.getName());
        Assert.assertEquals(found.getLocalityDescription(),
                forest1.getLocalityDescription());
    }

}
