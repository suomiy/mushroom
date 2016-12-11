package cz.fi.muni.pa165.service.facade;

import cz.fi.muni.pa165.dto.MushroomCountDTO;
import cz.fi.muni.pa165.entity.MushroomCount;
import cz.fi.muni.pa165.facade.MushroomCountFacade;
import cz.fi.muni.pa165.service.MushroomCountService;
import cz.fi.muni.pa165.service.MushroomService;
import cz.fi.muni.pa165.service.VisitService;
import cz.fi.muni.pa165.service.config.ServiceConfig;
import cz.fi.muni.pa165.service.mappers.MushroomCountMapperService;
import cz.fi.muni.pa165.service.mappers.ObjectsHelper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
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
public class MushroomCountFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private MushroomCountService mushroomCountService;

    @Mock
    private MushroomCountMapperService mapperService;

    @Mock
    private MushroomService mushroomService;

    @Mock
    private VisitService visitService;

    @InjectMocks
    private MushroomCountFacade mushroomCountFacade = new MushroomCountFacadeImpl();

    private MushroomCount mushroomCount;
    private MushroomCount emptyMushroomCount;
    private MushroomCountDTO mushroomCountDTO;
    private MushroomCountDTO emptyMushroomCountDTO;

    private List<MushroomCountDTO> mushroomCountDTOs;
    private List<MushroomCount> mushroomCounts;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void prepareData() {
        mushroomCount = ObjectsHelper.getMushroomCountEntity();
        mushroomCountDTO = ObjectsHelper.getMushroomCountDTO();

        emptyMushroomCount = ObjectsHelper.getEmptyMushroomCountEntity();
        emptyMushroomCountDTO = ObjectsHelper.getEmptyMushroomCountDTO();

        mushroomCounts = new ArrayList<>();
        mushroomCounts.add(mushroomCount);
        mushroomCounts.add(emptyMushroomCount);

        mushroomCountDTOs = new ArrayList<>();
        mushroomCountDTOs.add(mushroomCountDTO);
        mushroomCountDTOs.add(emptyMushroomCountDTO);

        when(mapperService.asDto(mushroomCount)).thenReturn(mushroomCountDTO);
        when(mapperService.asDto(emptyMushroomCount)).thenReturn(emptyMushroomCountDTO);
        when(mapperService.asEntity(mushroomCountDTO)).thenReturn(mushroomCount);
        when(mapperService.asEntity(emptyMushroomCountDTO)).thenReturn(emptyMushroomCount);
    }

    @Test
    public void create() {
        mushroomCountFacade.create(mushroomCountDTO);
        verify(mushroomCountService).create(mushroomCount);
    }

    @Test
    public void update() {
        when(mushroomCountService.update(mushroomCount)).thenReturn(mushroomCount);
        assertThat(mushroomCountFacade.update(mushroomCountDTO)).isEqualToComparingFieldByField(mushroomCountDTO);
    }

    @Test
    public void delete() {
        when(mushroomCountService.findById(mushroomCount.getId())).thenReturn(mushroomCount);
        mushroomCountFacade.delete(mushroomCount.getId());
        verify(mushroomCountService).delete(mushroomCount);
    }

    @Test
    public void findByID() {
        when(mushroomCountService.findById(mushroomCount.getId())).thenReturn(mushroomCount);
        MushroomCountDTO result = mushroomCountFacade.findById(mushroomCount.getId());
        assertThat(result).isEqualToComparingFieldByField(mushroomCountDTO);
    }

    @Test
    public void findRecentlyFoundPickableMushrooms() {
        when(mushroomCountService.findRecentlyFoundPickableMushrooms()).thenReturn(mushroomCounts);
        List<MushroomCountDTO> result = mushroomCountFacade.findRecentlyFoundPickableMushrooms();

        assertThat(result).isEqualTo(mushroomCountDTOs);
    }

    @Test
    public void findAll() {
        when(mushroomCountService.findAll()).thenReturn(mushroomCounts);
        Collection<MushroomCountDTO> result = mushroomCountFacade.findAll();
        assertThat(result).isEqualTo(mushroomCountDTOs);
    }
}
