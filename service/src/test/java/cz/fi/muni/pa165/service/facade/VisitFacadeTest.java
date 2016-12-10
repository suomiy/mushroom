package cz.fi.muni.pa165.service.facade;

import cz.fi.muni.pa165.dto.MushroomCountDTO;
import cz.fi.muni.pa165.dto.MushroomDTO;
import cz.fi.muni.pa165.dto.VisitDTO;
import cz.fi.muni.pa165.entity.Mushroom;
import cz.fi.muni.pa165.entity.MushroomCount;
import cz.fi.muni.pa165.entity.Visit;
import cz.fi.muni.pa165.facade.VisitFacade;
import cz.fi.muni.pa165.service.ForestService;
import cz.fi.muni.pa165.service.HunterService;
import cz.fi.muni.pa165.service.MushroomService;
import cz.fi.muni.pa165.service.VisitService;
import cz.fi.muni.pa165.service.config.ServiceConfig;
import cz.fi.muni.pa165.service.mappers.ForestMapperService;
import cz.fi.muni.pa165.service.mappers.MushroomMapperService;
import cz.fi.muni.pa165.service.mappers.ObjectsHelper;
import cz.fi.muni.pa165.service.mappers.VisitMapperService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
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
public class VisitFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private VisitService visitService;

    @Mock
    private HunterService hunterService;

    @Mock
    private ForestService forestService;

    @Mock
    private MushroomService mushroomService;

    @Mock
    private VisitMapperService mapperService;

    @Mock
    private ForestMapperService forestMapperService;

    @Mock
    private MushroomMapperService mushroomMapperService;

    @InjectMocks
    private VisitFacade visitFacade = new VisitFacadeImpl();

    private Visit visit;
    private Visit emptyVisit;
    private VisitDTO visitDTO;
    private VisitDTO emptyVisitDTO;

    private List<VisitDTO> visitDTOs;
    private List<Visit> visits;
    private MushroomCountDTO mushroomCount;

    private Mushroom mushroom;
    private MushroomDTO mushroomDto;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void prepareData() {
        visit = ObjectsHelper.getVisitEntity();
        visitDTO = ObjectsHelper.getVisitDTO();

        emptyVisit = ObjectsHelper.getEmptyVisitEntity();
        emptyVisitDTO = ObjectsHelper.getEmptyVisitDTO();
        mushroomCount = ObjectsHelper.getMushroomCountDTO();

        visits = new ArrayList<>();
        visits.add(visit);
        visits.add(emptyVisit);

        visitDTOs = new ArrayList<>();
        visitDTOs.add(visitDTO);
        visitDTOs.add(emptyVisitDTO);

        mushroom = visit.getMushroomsCount().first().getMushroom();
        mushroomDto = visitDTO.getMushroomsCount().get(0).getMushroom();

        when(mapperService.asDto(visit)).thenReturn(visitDTO);
        when(mapperService.asEntity(visitDTO)).thenReturn(visit);

        when(mapperService.asDto(emptyVisit)).thenReturn(emptyVisitDTO);
        when(mapperService.asEntity(emptyVisitDTO)).thenReturn(emptyVisit);

        when(forestMapperService.asDto(visit.getForest())).thenReturn(visitDTO.getForest());
        when(forestMapperService.asDto(emptyVisit.getForest())).thenReturn(emptyVisitDTO.getForest());
        when(forestMapperService.asEntity(visitDTO.getForest())).thenReturn(visit.getForest());
        when(forestMapperService.asEntity(emptyVisitDTO.getForest())).thenReturn(emptyVisit.getForest());

        when(mushroomMapperService.asDto(mushroom)).thenReturn(mushroomDto);
        when(mushroomMapperService.asEntity(mushroomDto)).thenReturn(mushroom);
    }

    @Test
    public void create() {
        visitFacade.create(visitDTO);
        verify(visitService).create(visit);
    }

    @Test
    public void update() {
        when(visitService.update(visit)).thenReturn(visit);
        assertThat(visitFacade.update(visitDTO)).isEqualToComparingFieldByField(visitDTO);
    }

    @Test
    public void delete() {
        when(visitService.findById(visit.getId())).thenReturn(visit);
        visitFacade.delete(visit.getId());
        verify(visitService).delete(visit);
    }

    @Test
    public void findByID() {
        when(visitService.findById(visit.getId())).thenReturn(visit);
        VisitDTO result = visitFacade.findById(visit.getId());
        assertThat(result).isEqualToComparingFieldByField(visitDTO);
    }

    @Test
    public void findByForest() {
        removeLast();

        when(forestService.findById(visitDTO.getForest().getId())).thenReturn(visit.getForest());
        when(visitService.findByForest(visit.getForest())).thenReturn(visits);
        List<VisitDTO> result = visitFacade.findByForest(visitDTO.getForest().getId());

        assertThat(result).isEqualTo(visitDTOs);
    }

    @Test
    public void findByHunter() {
        removeLast();

        when(visitService.findByHunter(visit.getHunter())).thenReturn(visits);
        when(hunterService.findById(visitDTO.getHunterId())).thenReturn(visit.getHunter());
        List<VisitDTO> result = visitFacade.findByHunter(visitDTO.getHunterId());

        assertThat(result).isEqualTo(visitDTOs);
    }

    @Test
    public void findByMushroom() {
        removeLast();

        when(mushroomService.findById(mushroomCount.getMushroom().getId())).thenReturn(mushroom);
        when(visitService.findByMushroom(mushroom)).thenReturn(visits);
        List<VisitDTO> result = visitFacade.findByMushroom(mushroomDto.getId());

        assertThat(result).isEqualTo(visitDTOs);
    }

    private void removeLast() {
        if (!visitDTOs.isEmpty()) {
            visitDTOs.remove(visitDTOs.size() - 1);
        }

        if (!visits.isEmpty()) {
            visits.remove(visits.size() - 1);
        }
    }

    @Test
    public void findByDate() {
        when(visitService.findByDate(ObjectsHelper.getFrom())).thenReturn(visits);
        List<VisitDTO> result = visitFacade.findByDate(ObjectsHelper.getFromDTO());

        assertThat(result).isEqualTo(visitDTOs);
    }

    @Test
    public void findByDateInterval() {
        when(visitService.findByDate(ObjectsHelper.getFrom(), ObjectsHelper.getTo())).thenReturn(visits);
        List<VisitDTO> result = visitFacade.findByDate(ObjectsHelper.getIntervalDTO());

        assertThat(result).isEqualTo(visitDTOs);
    }

    @Test
    public void findAll() {
        when(visitService.findAll()).thenReturn(visits);
        Collection<VisitDTO> result = visitFacade.findAll();
        assertThat(result).isEqualTo(visitDTOs);
    }
}
