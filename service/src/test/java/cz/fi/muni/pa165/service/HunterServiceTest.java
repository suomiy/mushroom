package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.HunterDao;
import cz.fi.muni.pa165.entity.Hunter;
import cz.fi.muni.pa165.entity.Mushroom;
import cz.fi.muni.pa165.enums.Rank;
import cz.fi.muni.pa165.enums.Role;
import cz.fi.muni.pa165.exception.HunterAuthenticationException;
import cz.fi.muni.pa165.service.config.ServiceConfig;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

/**
 * @author Filip Krepinsky (410022) on 11/25/16
 */
@ContextConfiguration(classes = ServiceConfig.class)
public class HunterServiceTest extends AbstractTestNGSpringContextTests {
    private static final String PASSWORD = "password123";
    private static final String BAD_PASSWORD = "blablabla";

    @Mock
    private HunterDao hunterDao;

    @Inject
    @InjectMocks
    private HunterService hunterService;

    private Hunter hunter;

    private Mushroom mushroom2;
    private Mushroom mushroom3;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void prepareData() {
        hunter = getHunterEntity();
    }

    @Test
    public void registeHunterNormal() {
        answerWithId1();

        try {
            hunterService.registerHunter(hunter, PASSWORD);
        } catch (HunterAuthenticationException e) {
            Assert.fail(e.getMessage());
        }

        assertThat(hunter.getId()).isNotNull().isEqualTo(1L);
    }

    @Test
    public void registeHunterWeakPassword() {
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Hunter entity = (Hunter) invocationOnMock.getArguments()[0];
                entity.setId(1L);
                if (entity.getPasswordHash().length() < 20 || entity.getPasswordHash().contains(PASSWORD)) {
                    Assert.fail("Weak Password!");
                }
                return null;
            }
        }).when(hunterDao).create(hunter);

        try {
            hunterService.registerHunter(hunter, PASSWORD);
        } catch (HunterAuthenticationException e) {
            Assert.fail(e.getMessage());
        }

        assertThat(hunter.getId()).isNotNull().isEqualTo(1L);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void registeHunterNullEmptyPassword() throws HunterAuthenticationException {
        hunterService.registerHunter(hunter, "");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void registeHunterNullPassword() throws HunterAuthenticationException {
        hunterService.registerHunter(hunter, null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void registeHunterNullHunter() throws HunterAuthenticationException {
        hunterService.registerHunter(null, "nice password");
    }

    @Test
    public void testAuthenticateEmptyArgs() throws Exception {
        Assert.assertFalse(hunterService.authenticate(hunter, ""));
        Assert.assertFalse(hunterService.authenticate(hunter, null));
        Assert.assertFalse(hunterService.authenticate(null, null));
        Assert.assertFalse(hunterService.authenticate(null, "hey"));
    }

    @Test
    public void testAuthenticate() throws Exception {
        answerWithId1();
        hunterService.registerHunter(hunter, PASSWORD);
        Assert.assertTrue(hunterService.authenticate(hunter, PASSWORD));
        Assert.assertFalse(hunterService.authenticate(hunter, BAD_PASSWORD));
    }

    @Test
    public void testCahngePassword() throws Exception {
        answerWithId1();
        hunterService.registerHunter(hunter, PASSWORD);
        Assert.assertTrue(hunterService.authenticate(hunter, PASSWORD));
        Assert.assertFalse(hunterService.authenticate(hunter, BAD_PASSWORD));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void changePasswordNullPassword() throws HunterAuthenticationException {
        answerWithId1();
        hunterService.registerHunter(hunter, PASSWORD);
        hunterService.changePassword(null, null, null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void changePasswordEmptyPassword() throws HunterAuthenticationException {
        answerWithId1();
        hunterService.registerHunter(hunter, PASSWORD);
        hunterService.changePassword(hunter.getId(), "", "");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void changePasswordNullHunterPassword() throws HunterAuthenticationException {
        answerWithId1();
        hunterService.registerHunter(hunter, PASSWORD);
        hunterService.changePassword(null, BAD_PASSWORD, BAD_PASSWORD);
    }

    @Test
    public void changePassword() throws HunterAuthenticationException {
        answerWithId1();
        hunterService.registerHunter(hunter, PASSWORD);
        answerWithFindById();

        hunterService.changePassword(hunter.getId(), PASSWORD, BAD_PASSWORD);
        Assert.assertFalse(hunterService.authenticate(hunter, PASSWORD));
        Assert.assertTrue(hunterService.authenticate(hunter, BAD_PASSWORD));

        hunterService.changePassword(hunter.getId(), PASSWORD, PASSWORD);
        Assert.assertFalse(hunterService.authenticate(hunter, PASSWORD));

        hunterService.changePassword(hunter.getId(), BAD_PASSWORD, PASSWORD);
        Assert.assertTrue(hunterService.authenticate(hunter, PASSWORD));
    }

    @Test
    public void testUpdateMethod() {
        answerWithUpdate();
        assertThat(hunter).isEqualToComparingFieldByField(hunterService.update(hunter));
    }

    @Test
    public void testFindByIdMethod() {
        answerWithFindById();
        assertThat(hunter).isEqualToComparingFieldByField(hunterService.findById(hunter.getId()));
    }

    @Test
    public void testDeletedMethod() {
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Hunter entity = (Hunter) invocationOnMock.getArguments()[0];
                entity.setNick("Entity deleted");
                return null;
            }
        }).when(hunterDao).delete(hunter);

        hunterService.delete(hunter);
        String result = hunter.getNick();

        assertThat(result).isNotNull().isEqualTo("Entity deleted");
    }

    @Test
    public void testFindByEmail() {
        answerWithFindByEmail();
        assertThat(hunter).isEqualToComparingFieldByField(hunterService.findByEmail(hunter.getEmail()));
    }

    @Test
    public void testFindAll() {
        answerWithFindAll();
        assertThat(new ArrayList<>()).isEqualTo(hunterService.findAll());
    }

    private void answerWithId1() {
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Hunter entity = (Hunter) invocationOnMock.getArguments()[0];
                entity.setId(1L);
                return null;
            }
        }).when(hunterDao).create(hunter);
    }

    private void answerWithFindById() {
        when(hunterDao.findById(hunter.getId())).thenReturn(hunter);
    }

    private void answerWithFindByEmail() {
        when(hunterDao.findByEmail(hunter.getEmail())).thenReturn(hunter);
    }

    private void answerWithFindAll() {
        when(hunterDao.findAll()).thenReturn(new ArrayList<>());
    }

    private void answerWithUpdate() {
        when(hunterDao.update(hunter)).thenReturn(hunter);
    }

    static Hunter getHunterEntity() {
        Hunter hunter = new Hunter();
        hunter.setFirstName("Samuel");
        hunter.setSurname("Hunter");
        hunter.setNick("Samo");
        hunter.setEmail("samo@hunter.cz");
        hunter.setPasswordHash("123");
        hunter.setType(Role.USER);
        hunter.setRank(Rank.GURU);
        return hunter;
    }
}
