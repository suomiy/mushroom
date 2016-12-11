package cz.fi.muni.pa165.service.facade;

import cz.fi.muni.pa165.dto.HunterDTO;
import cz.fi.muni.pa165.dto.RegistrateHunterDTO;
import cz.fi.muni.pa165.dto.UserAuthenticateDTO;
import cz.fi.muni.pa165.entity.Hunter;
import cz.fi.muni.pa165.exception.HunterAuthenticationException;
import cz.fi.muni.pa165.facade.HunterFacade;
import cz.fi.muni.pa165.service.HunterService;
import cz.fi.muni.pa165.service.config.ServiceConfig;
import cz.fi.muni.pa165.service.mappers.HunterMapperService;
import cz.fi.muni.pa165.service.mappers.ObjectsHelper;
import cz.fi.muni.pa165.service.mappers.RegistrateHunterMapperService;
import cz.fi.muni.pa165.service.mappers.TestHelper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Filip Krepinsky (410022) on 11/25/16
 */
@org.springframework.test.context.ContextConfiguration(classes = ServiceConfig.class)
public class HunterFacadeTest extends AbstractTestNGSpringContextTests {
    private static final String PASSWORD = "password123";

    @Mock
    private HunterService hunterService;

    @Mock
    private HunterMapperService hunterMapperService;

    @Mock
    private RegistrateHunterMapperService registrateHunterMapperService;

    @InjectMocks
    private HunterFacade hunterFacade = new HunterFacadeImpl();

    private Hunter hunter;
    private Hunter emptyHunter;
    private HunterDTO hunterDTO;
    private HunterDTO emptyHunterDTO;
    private RegistrateHunterDTO registrateHunterDTO;

    private List<HunterDTO> hunterDTOs;
    private List<Hunter> hunters;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void prepareData() {
        hunter = ObjectsHelper.getHunterEntity();
        hunter.setId(null);
        hunterDTO = ObjectsHelper.getHunterDTO();
        registrateHunterDTO = ObjectsHelper.getRegistrateHunterDTO();

        emptyHunter = ObjectsHelper.getEmptyHunterEntity();
        emptyHunterDTO = ObjectsHelper.getEmptyHunterDTO();

        hunters = new ArrayList<>();
        hunters.add(hunter);
        hunters.add(emptyHunter);

        hunterDTOs = new ArrayList<>();
        hunterDTOs.add(hunterDTO);
        hunterDTOs.add(emptyHunterDTO);

        when(hunterMapperService.asDto(hunter)).thenReturn(hunterDTO);
        when(hunterMapperService.asDto(emptyHunter)).thenReturn(emptyHunterDTO);
        when(registrateHunterMapperService.asEntity(registrateHunterDTO)).thenReturn(hunter);
        when(hunterMapperService.asEntity(hunterDTO)).thenReturn(hunter);
        when(hunterMapperService.asEntity(emptyHunterDTO)).thenReturn(emptyHunter);
    }

    @Test
    public void registerHunter() throws HunterAuthenticationException {
        hunterFacade.registerHunter(registrateHunterDTO);
        verify(hunterService).registerHunter(hunter, registrateHunterDTO.getUnencryptedPassword());
    }

    @Test
    public void authenticate() throws HunterAuthenticationException {
        String email = hunter.getEmail();
        when(hunterService.findByEmail(email)).thenReturn(hunter);
        when(hunterService.authenticate(hunter, PASSWORD)).thenReturn(true);

        UserAuthenticateDTO authenticate = new UserAuthenticateDTO();
        authenticate.setEmail(email);
        authenticate.setPassword(PASSWORD);

        Assert.assertTrue(hunterFacade.authenticate(authenticate));
    }

    @Test
    public void update() {
        when(hunterService.findById(hunter.getId())).thenReturn(hunter);
        when(hunterService.update(hunter)).thenReturn(hunter);
        TestHelper.updatedEntityEquals(hunterFacade.update(hunterDTO), hunterDTO);
    }

    @Test
    public void delete() {
        when(hunterService.findById(hunter.getId())).thenReturn(hunter);
        hunterFacade.delete(hunter.getId());
        verify(hunterService).delete(hunter);
    }

    @Test
    public void findByID() {
        when(hunterService.findById(hunter.getId())).thenReturn(hunter);
        HunterDTO result = hunterFacade.findById(hunter.getId());
        assertThat(result).isEqualToComparingFieldByField(hunterDTO);
    }

    @Test
    public void findByEmail() {
        when(hunterService.findByEmail(hunter.getEmail())).thenReturn(hunter);
        HunterDTO result = hunterFacade.findByEmail(hunter.getEmail());
        assertThat(result).isEqualToComparingFieldByField(hunterDTO);
    }

    @Test
    public void findAll() {
        when(hunterService.findAll()).thenReturn(hunters);
        Collection<HunterDTO> result = hunterFacade.findAll();
        assertThat(result).isEqualTo(hunterDTOs);
    }
}
