package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.PersistenceApplicationContext;
import cz.fi.muni.pa165.entity.Forest;
import cz.fi.muni.pa165.entity.Hunter;
import cz.fi.muni.pa165.entity.Visit;
import cz.fi.muni.pa165.enums.Rank;
import cz.fi.muni.pa165.enums.Role;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;


/**
 * Created by "Michal Kysilko" on 26.10.16.
 *
 * @author Michal Kysilko 436339
 */

@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@org.springframework.transaction.annotation.Transactional
public class HunterDaoTest extends AbstractTestNGSpringContextTests {

    @Inject
    private HunterDao hunterDao;

    @Inject
    private ForestDao forestDao;

    @Inject
    private VisitDao visitDao;

    private Hunter fullInfo;
    private Hunter neededInfo;

    private Hunter missingRank;
    private Hunter missingEmail;
    private Hunter missingNick;
    private Hunter missingPassword;
    private Hunter missingRole;

    @BeforeMethod
    public void init() {
        createObjects();
        setData();
    }

    private void createObjects() {
        fullInfo = new Hunter();
        neededInfo = new Hunter();

        missingRank = new Hunter();
        missingEmail = new Hunter();
        missingNick = new Hunter();
        missingPassword = new Hunter();
        missingRole = new Hunter();
    }

    private void setData() {
        fullInfo.setSurname("Dobrota");
        fullInfo.setFirstName("Arnost");
        fullInfo.setNick("dobrak_arnie");
        fullInfo.setEmail("Arnost@Dobrota.com");
        fullInfo.setPasswordHash("abcde");
        fullInfo.setRank(Rank.BEGINNER);
        fullInfo.setType(Role.USER);

        neededInfo.setNick("fake");
        neededInfo.setEmail("fake@fake.fake");
        neededInfo.setPasswordHash("fAkE");
        neededInfo.setType(Role.USER);
        neededInfo.setRank(Rank.BEGINNER);

        missingRank.setNick("dj");
        missingRank.setType(Role.USER);
        missingRank.setEmail("dj@seznam.cz");
        missingRank.setPasswordHash("12345");

        missingEmail.setNick("noMailGuy");
        missingEmail.setType(Role.USER);
        missingEmail.setPasswordHash("badas");
        missingEmail.setRank(Rank.BEGINNER);

        missingNick.setEmail("noNick@glo.goal");
        missingNick.setType(Role.USER);
        missingNick.setPasswordHash("unknown");
        missingNick.setRank(Rank.BEGINNER);

        missingPassword.setNick("Mr.Security");
        missingPassword.setEmail("nothingtohide@everywhere.com");
        missingPassword.setType(Role.USER);
        missingPassword.setRank(Rank.BEGINNER);

        missingRole.setNick("noob");
        missingRole.setEmail("idc@ikr.lul");
        missingRole.setPasswordHash("boom");
        missingRole.setRank(Rank.BEGINNER);
    }

    @Test
    public void createFullInfo() {
        hunterDao.create(fullInfo);

        Hunter h = hunterDao.findById(fullInfo.getId());
        Assert.assertEquals(h.getSurname(), "Dobrota");
        Assert.assertEquals(h.getFirstName(), "Arnost");
        Assert.assertEquals(h.getNick(), "dobrak_arnie");
        Assert.assertEquals(h.getEmail(), "Arnost@Dobrota.com");
        Assert.assertEquals(h.getPasswordHash(), "abcde");
        Assert.assertEquals(h.getRank(), Rank.BEGINNER);
        Assert.assertEquals(h.getType(), Role.USER);
    }

    @Test
    public void testVisitsCRUD() {
        Forest forest = new Forest();
        forest.setName("Dark Forest");
        forestDao.create(forest);

        Visit visit = new Visit();
        visit.setForest(forest);
        visit.setDate(new Date());

        Visit visit2 = new Visit();
        visit2.setForest(forest);
        visit2.setDate(new Date());

        fullInfo.addVisit(visit);
        fullInfo.addVisit(visit2);
        hunterDao.create(fullInfo);

        List<Visit> visits = visitDao.findAll();

        Assert.assertEquals(fullInfo.getVisits().size(), 2);
        Assert.assertEquals(visits.size(), 2);
        Assert.assertTrue(visits.contains(visit));
        Assert.assertTrue(visits.contains(visit2));

        visit2.setNote("Nice Visit");

        visits = visitDao.findAll();

        Visit updated = visits.get(visits.indexOf(visit2));
        Assert.assertEquals(updated.getNote(), "Nice Visit");

        fullInfo.removeVisit(visit2);

        visits = visitDao.findAll();

        Assert.assertEquals(fullInfo.getVisits().size(), 1);
        Assert.assertEquals(visits.size(), 1);
        Assert.assertTrue(visits.contains(visit));
        Assert.assertTrue(!visits.contains(visit2));
    }

    @Test
    public void createNeededInfo() {
        hunterDao.create(neededInfo);

        Hunter h = hunterDao.findById(neededInfo.getId());
        Assert.assertEquals(h.getNick(), "fake");
        Assert.assertEquals(h.getEmail(), "fake@fake.fake");
        Assert.assertEquals(h.getPasswordHash(), "fAkE");
        Assert.assertEquals(h.getRank(), Rank.BEGINNER);
        Assert.assertEquals(h.getType(), Role.USER);
    }

    @Test(expectedExceptions = Exception.class)
    public void createMissingNick() {
        hunterDao.create(missingNick);
    }

    @Test(expectedExceptions = Exception.class)
    public void createMissingEmail() {
        hunterDao.create(missingEmail);
    }

    @Test(expectedExceptions = Exception.class)
    public void createMissingPassword() {
        hunterDao.create(missingPassword);
    }

    @Test(expectedExceptions = Exception.class)
    public void createMissingRole() {
        hunterDao.create(missingRole);
    }

    @Test(expectedExceptions = Exception.class)
    public void createMissingRank() {
        hunterDao.create(missingRank);
    }

    @Test
    public void delete() {
        hunterDao.create(fullInfo);
        hunterDao.create(neededInfo);
        hunterDao.delete(fullInfo);

        Assert.assertEquals(hunterDao.findAll().size(), 1);
    }

    @Test
    public void update() {
        hunterDao.create(neededInfo);
        fullInfo.setId(neededInfo.getId());

        fullInfo.setNick("black");
        fullInfo.setPasswordHash("blue");
        fullInfo.setEmail("blue@black.com");
        fullInfo.setRank(Rank.BEGINNER);
        fullInfo.setType(Role.USER);

        hunterDao.update(fullInfo);

        Hunter lh = hunterDao.findById(neededInfo.getId());

        Assert.assertEquals(fullInfo.getNick(), lh.getNick());
        Assert.assertEquals(fullInfo.getEmail(), lh.getEmail());
        Assert.assertEquals(fullInfo.getPasswordHash(), lh.getPasswordHash());
        Assert.assertEquals(fullInfo.getRank(), lh.getRank());
        Assert.assertEquals(fullInfo.getType(), lh.getType());
    }

    @Test
    public void findById() {

        hunterDao.create(fullInfo);
        hunterDao.create(neededInfo);

        Hunter h = hunterDao.findById(neededInfo.getId());
        Assert.assertEquals(h, neededInfo);
    }

    @Test
    public void findByEmail() {
        //Assert.assertNull(hunterDao.findById(1L));

        hunterDao.create(fullInfo);
        hunterDao.create(neededInfo);


        Hunter h = hunterDao.findByEmail(neededInfo.getEmail());
        Assert.assertEquals(h, neededInfo);

        Assert.assertNull(hunterDao.findByEmail("a@a.a"));
    }

    @Test
    public void findAll() {
        List<Hunter> lh = hunterDao.findAll();
        Assert.assertEquals(lh.size(), 0);

        hunterDao.create(fullInfo);
        lh = hunterDao.findAll();
        Assert.assertEquals(lh.size(), 1);

        hunterDao.create(neededInfo);
        lh = hunterDao.findAll();
        Assert.assertEquals(lh.size(), 2);

        hunterDao.delete(neededInfo);
        lh = hunterDao.findAll();
        Assert.assertEquals(lh.size(), 1);
    }
}
