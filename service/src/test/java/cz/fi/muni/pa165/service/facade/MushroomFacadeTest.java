package cz.fi.muni.pa165.service.facade;

import cz.fi.muni.pa165.dto.MushroomDTO;
import cz.fi.muni.pa165.entity.Mushroom;
import cz.fi.muni.pa165.facade.MushroomFacade;
import cz.fi.muni.pa165.service.MushroomService;
import cz.fi.muni.pa165.service.config.ServiceConfig;
import cz.fi.muni.pa165.service.mappers.MushroomMapperService;
import cz.fi.muni.pa165.service.mappers.ObjectsHelper;
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
public class MushroomFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private MushroomService mushroomService;

    @Mock
    private MushroomMapperService mapperService;

    @InjectMocks
    private MushroomFacade mushroomFacade = new MushroomFacadeImpl();

    private Mushroom mushroom;
    private Mushroom emptyMushroom;
    private MushroomDTO mushroomDTO;
    private MushroomDTO emptyMushroomDTO;

    private List<MushroomDTO> mushroomDTOs;
    private List<Mushroom> mushrooms;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void prepareData() {
        mushroom = ObjectsHelper.getMushroomEntity();
        mushroomDTO = ObjectsHelper.getMushroomDTO();

        emptyMushroom = ObjectsHelper.getEmptyMushroomEntity();
        emptyMushroomDTO = ObjectsHelper.getEmptyMushroomDTO();

        mushrooms = new ArrayList<>();
        mushrooms.add(mushroom);
        mushrooms.add(emptyMushroom);

        mushroomDTOs = new ArrayList<>();
        mushroomDTOs.add(mushroomDTO);
        mushroomDTOs.add(emptyMushroomDTO);

        when(mapperService.asDto(mushroom)).thenReturn(mushroomDTO);
        when(mapperService.asDto(emptyMushroom)).thenReturn(emptyMushroomDTO);
        when(mapperService.asEntity(mushroomDTO)).thenReturn(mushroom);
        when(mapperService.asEntity(emptyMushroomDTO)).thenReturn(emptyMushroom);
    }

    @Test
    public void create() {
        mushroomFacade.create(mushroomDTO);
        verify(mushroomService).create(mushroom);
    }

    @Test
    public void update() {
        when(mushroomService.update(mushroom)).thenReturn(mushroom);
        assertThat(mushroomFacade.update(mushroomDTO)).isEqualToComparingFieldByField(mushroomDTO);
    }

    @Test
    public void delete() {
        when(mushroomService.findById(mushroom.getId())).thenReturn(mushroom);
        mushroomFacade.delete(mushroom.getId());
        verify(mushroomService).delete(mushroom);
    }

    @Test
    public void findByID() {
        when(mushroomService.findById(mushroom.getId())).thenReturn(mushroom);
        MushroomDTO result = mushroomFacade.findById(mushroom.getId());
        assertThat(result).isEqualToComparingFieldByField(mushroomDTO);
    }

    @Test
    public void findByName() {
        when(mushroomService.findByName(mushroom.getName())).thenReturn(mushroom);
        MushroomDTO result = mushroomFacade.findByName(mushroom.getName());
        assertThat(result).isEqualToComparingFieldByField(mushroomDTO);
    }

    @Test
    public void findByType() {
        if (!mushroomDTOs.isEmpty()) {
            mushroomDTOs.remove(mushroomDTOs.size() - 1);
        }

        if (!mushrooms.isEmpty()) {
            mushrooms.remove(mushrooms.size() - 1);
        }

        when(mushroomService.findByType(mushroom.getType())).thenReturn(mushrooms);
        List<MushroomDTO> result = mushroomFacade.findByType(mushroom.getType());

        assertThat(result).isEqualTo(mushroomDTOs);
    }

    @Test
    public void findByDate() {
        when(mushroomService.findByDate(ObjectsHelper.getFrom())).thenReturn(mushrooms);
        List<MushroomDTO> result = mushroomFacade.findByDate(ObjectsHelper.getFromDTO());

        assertThat(result).isEqualTo(mushroomDTOs);
    }

    @Test
    public void findByDateInterval() {
        when(mushroomService.findByDate(ObjectsHelper.getFrom(), ObjectsHelper.getTo())).thenReturn(mushrooms);
        List<MushroomDTO> result = mushroomFacade.findByDate(ObjectsHelper.getIntervalDTO());

        assertThat(result).isEqualTo(mushroomDTOs);
    }

    @Test
    public void findAll() {
        when(mushroomService.findAll()).thenReturn(mushrooms);
        Collection<MushroomDTO> result = mushroomFacade.findAll();
        assertThat(result).isEqualTo(mushroomDTOs);
    }
}
