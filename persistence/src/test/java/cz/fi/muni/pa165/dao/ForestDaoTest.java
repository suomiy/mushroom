package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.PersistenceApplicationContext;
import cz.fi.muni.pa165.entity.Forest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


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

    @Test
    public void createForestTest(){

        Forest forest = new Forest();
        forest.setName("Dark forest");

        forestDao.create(forest);

        Forest loadedForest = forestDao.findById(forest.getId());

        Assert.assertEquals(loadedForest.getId(),forest.getId());
        Assert.assertEquals(loadedForest.getName(),forest.getName());
    }
}
