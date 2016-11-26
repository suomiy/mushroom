package cz.fi.muni.pa165.service.facade;

import cz.fi.muni.pa165.dto.DateDTO;
import cz.fi.muni.pa165.dto.ForestDTO;
import cz.fi.muni.pa165.dto.HunterDTO;
import cz.fi.muni.pa165.dto.VisitDTO;
import cz.fi.muni.pa165.entity.Forest;
import cz.fi.muni.pa165.entity.Hunter;
import cz.fi.muni.pa165.entity.Visit;
import cz.fi.muni.pa165.enums.Rank;
import cz.fi.muni.pa165.enums.Role;
import cz.fi.muni.pa165.facade.VisitFacade;
import cz.fi.muni.pa165.service.VisitService;
import cz.fi.muni.pa165.service.config.ServiceConfig;
import cz.fi.muni.pa165.service.mappers.ForestMapperService;
import cz.fi.muni.pa165.service.mappers.HunterMapperService;
import cz.fi.muni.pa165.service.mappers.MushroomMapperService;
import cz.fi.muni.pa165.service.mappers.VisitMapperService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeMethod;

import java.util.Arrays;

import static cz.fi.muni.pa165.service.mappers.ObjectsHelper.buildDate;

/**
 * Created by michal on 11/26/16.
 *
 * @author Michal Kysilko 436339
 */

@ContextConfiguration(classes = ServiceConfig.class)
public class VisitFacadeTest {

    @Autowired
    @InjectMocks
    private VisitFacade visitFacade = new VisitFaceadeImpl();

    @Mock
    private VisitService visitService;

    @Mock
    private VisitMapperService mapperService;

    @Mock
    private HunterMapperService hunterMapperService;

    @Mock
    private ForestMapperService forestMapperService;

    @Mock
    private MushroomMapperService mushroomMapperService;

    private HunterDTO hunterDTO;
    private Hunter hunter;
    private VisitDTO visitDTO;
    private Visit visit;
    private ForestDTO forestDTO;
    private Forest forest;

    private DateDTO fromDate;
    private DateDTO toDate;
    private DateDTO okTestDate;
    private DateDTO failTestDate;

    @BeforeMethod
    public void init() {
        MockitoAnnotations.initMocks(this);

        fromDate = new DateDTO();
        toDate = new DateDTO();
        okTestDate = new DateDTO();
        failTestDate = new DateDTO();

        fromDate.setDate(buildDate(20, 11, 2016));
        toDate.setDate(buildDate(22, 11, 2016));
        okTestDate.setDate(buildDate(21, 11, 2016));
        failTestDate.setDate(buildDate(25, 11, 2016));

        hunterDTO = new HunterDTO();
        hunterDTO.setNick("fake");
        hunterDTO.setEmail("fake@fake.fake");
        hunterDTO.setType(Role.USER);
        hunterDTO.setRank(Rank.BEGINNER);

        hunter = new Hunter();
        hunter.setNick("fake");
        hunter.setEmail("fake@fake.fake");
        hunter.setType(Role.USER);
        hunter.setRank(Rank.BEGINNER);

        forestDTO = new ForestDTO();
        forestDTO.setName("Bukovy les");

        forest = new Forest();
        forest.setName("Bukovy les");

        /*
        visitDTO = new VisitDTO();
        visitDTO.setFromDate(fromDate);
        visitDTO.setToDate(toDate);
        visitDTO.setHunter(hunterDTO);
        visitDTO.setForest(forestDTO);
*/
        visit = new Visit();
        visit.setFromDate(buildDate(20, 11, 2016));
        visit.setToDate(buildDate(22, 11, 2016));
        visit.setHunter(hunter);
        visit.setForest(forest);

        Mockito.when(visitService.findByDate(buildDate(25, 11, 2016))).thenReturn(null);

        Mockito.when(visitService.findByForest(forest)).thenReturn(Arrays.asList(visit));

        Mockito.when(visitService.findByHunter(hunter)).thenReturn(Arrays.asList(visit));

        Mockito.when(visitService.findById(1L)).thenReturn(visit);

        Mockito.when(visitService.findAll()).thenReturn(Arrays.asList(visit));
    }

    /**
     @Test public void create() {
     visitFacade.create(visitDTO);
     Mockito.verify(visitService, times(1)).create(visit);
     }


     @Test public void delete() {
     visitFacade.create(visitDTO);
     visitFacade.delete(visitDTO.getId());
     Mockito.verify(visitService, times(1)).delete(visit);
     }

     @Test public void update() {
     visitDTO.setToDate(buildDate(22,11,2017));
     visitFacade.update(visitDTO);
     Mockito.verify(visitService, times(1)).update(visit);
     }

     @Test public void findById() {
     VisitDTO v = visitFacade.findById(1L);
     Assert.assertEquals(beanMapperService.mapTo(visit, VisitDTO.class), v);

     }

     @Test public void findAll() {
     List<VisitDTO> v = visitFacade.findAll();
     Assert.assertEquals(v.size(), 1);
     Assert.assertEquals(v.get(0).getHunter().getRank(), hunter.getRank());
     }

     @Test public void findByDate() {
     List<VisitDTO> v = visitFacade.findByDate(failTestDate);
     Assert.assertNull(v);
     }

     @Test public void findByForest() {
     List<VisitDTO> v = visitFacade.findByForest(forestDTO);
     Assert.assertEquals(v.size(), 1);
     Assert.assertEquals(v.get(0).getForest().getName(), forestDTO.getName());
     }

     @Test public void findByHunter() {
     List<VisitDTO> v = visitFacade.findByHunter(hunterDTO);
     Assert.assertEquals(v.size(), 1);
     Assert.assertEquals(v.get(0).getHunter().getNick(), hunterDTO.getNick());
     }
     */
}
