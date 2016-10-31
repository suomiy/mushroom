package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.PersistenceApplicationContext;
import cz.fi.muni.pa165.entity.Hunter;
import cz.fi.muni.pa165.enums.Rank;
import cz.fi.muni.pa165.enums.Role;
import org.hibernate.service.spi.InjectService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@org.springframework.transaction.annotation.Transactional
public class HunterDaoTest extends AbstractTestNGSpringContextTests {


    @Inject
    private HunterDao hunterDao;

    @PersistenceContext
    private EntityManager em;


    private Hunter fullInfo;
    private Hunter neededInfo;

    private Hunter missingRank;
    private Hunter missingEmail;
    private Hunter missingNick;
    private Hunter missingPassword;
    private Hunter missingRole;

    @BeforeClass
    public void init() {
        createObjects();
        setData();
    }

    private void createObjects(){
        fullInfo = new Hunter();
        neededInfo = new Hunter();

        missingRank = new Hunter();
        missingEmail = new Hunter();
        missingNick = new Hunter();
        missingPassword = new Hunter();
        missingRole = new Hunter();
    }

    private void setData(){
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
    public void createNeededInfo(){
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
    public void delete(){
        hunterDao.create(fullInfo);
        hunterDao.create(neededInfo);
        hunterDao.delete(fullInfo);

        Assert.assertEquals(hunterDao.findAll(), 1);
    }

    @Test
    public void update(){
        hunterDao.create(fullInfo);


    }

    @Test
    public void findById(){
        Assert.assertNull(hunterDao.findById(1L));

        hunterDao.create(fullInfo);
        hunterDao.create(neededInfo);

        Hunter h = hunterDao.findById(neededInfo.getId());
        Assert.assertEquals(h, neededInfo);
    }

    @Test
    public void findByEmail(){
        Assert.assertNull(hunterDao.findById(1L));

        hunterDao.create(fullInfo);
        hunterDao.create(neededInfo);

        Hunter h = hunterDao.findByEmail(neededInfo.getEmail());
        Assert.assertEquals(h, neededInfo);

        Assert.assertNull(hunterDao.findByEmail("a@a.a"));
    }

    @Test
    public void findAll(){
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
