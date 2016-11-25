package cz.fi.muni.pa165.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import cz.fi.muni.pa165.dao.MushroomDao;
import cz.fi.muni.pa165.entity.Mushroom;
import cz.fi.muni.pa165.enums.MushroomType;
import cz.fi.muni.pa165.service.config.ServiceConfig;
import java.util.Arrays;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.*;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;


/**
 * Created by Erik Macej 433744 , on 25.11.16.
 *
 * @author Erik Macej 433744
 */
@ContextConfiguration(classes = ServiceConfig.class)
public class MushroomServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    MushroomDao mushroomDao;

    @Autowired
    @InjectMocks
    MushroomService mushroomService;

    private Mushroom mushroom1;
    private Mushroom mushroom2;
    private Mushroom mushroom3;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void prepareData() {
        prepareMushrooms();
    }

    @Test
    public void createMushroomTest() {
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Mushroom entity = (Mushroom) invocationOnMock.getArguments()[0];
                entity.setId(1L);
                return null;
            }
        }).when(mushroomDao).create(mushroom1);

        mushroomService.create(mushroom1);
        assertThat(mushroom1.getId()).isNotNull().isEqualTo(1L);
    }

    @Test
    public void deleteMushroomTest() {
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Mushroom entity = (Mushroom) invocationOnMock.getArguments()[0];
                entity.setName("Entity deleted");
                return null;
            }
        }).when(mushroomDao).delete(mushroom1);

        mushroomService.delete(mushroom1);
        String result = mushroom1.getName();

        assertThat(result).isNotNull().isEqualTo("Entity deleted");
    }

    @Test
    public void findMushroomByIdTest() {
        when(mushroomDao.findById(2L)).thenReturn(mushroom2);

        Mushroom loadedMushroom = mushroomService.findById(2L);
        assertThat(loadedMushroom).isNotNull().isEqualToComparingFieldByField(mushroom2);
    }

    @Test
    public void updateMushroomTest() {
        mushroom3.setId(mushroom2.getId());

        when(mushroomDao.update(mushroom3)).thenReturn(mushroom3);
        Mushroom loadedMushroom = mushroomService.update(mushroom3);
        assertThat(loadedMushroom).isNotNull().isEqualToComparingFieldByField(mushroom3);
    }

    @Test
    public void findAllMushroomsTest() {
        mushroom1.setId(1L);
        mushroom3.setId(3L);

        when(mushroomDao.findAll()).thenReturn(Arrays.asList(mushroom1,mushroom2,mushroom3));

        List<Mushroom> loadedList = mushroomService.findAll();
        assertThat(loadedList).isNotNull().
                isNotEmpty().hasSize(3).containsOnly(mushroom1,mushroom2,mushroom3);
    }

    @Test
    public void findMushroomByNameTest() {
        when(mushroomDao.findByName("Hubicky")).thenReturn(mushroom2);

        Mushroom loadedMushroom = mushroomService.findByName("Hubicky");
        assertThat(loadedMushroom).isNotNull().isEqualToComparingFieldByField(mushroom2);
    }

    @Test
    public void findMushroomsByTypeTest() {
        when(mushroomDao.findByType(MushroomType.PSYCHEDELIC)).thenReturn(Arrays.asList(mushroom2));

        List<Mushroom> loadedMushrooms = mushroomService.findByType(MushroomType.PSYCHEDELIC);
        assertThat(loadedMushrooms).isNotNull().isNotEmpty()
                .hasSize(1).containsOnly(mushroom2);
    }

    @Test
    public void findMushroomsByDateTest() {
        when(mushroomDao.findByDate(new Date(16,5,2016))).thenReturn(Arrays.asList(mushroom2));

        List<Mushroom> loadedMushrooms = mushroomService.findByDate(new Date(16,5,2016));
        assertThat(loadedMushrooms).isNotNull().isNotEmpty()
                .hasSize(1).containsOnly(mushroom2);
    }

    @Test
    public void findMushroomsByDatesTest() {
        mushroom1.setId(1L);

        when(mushroomDao.findByDate(new Date(16,5,2016),new Date(18,5,2016)))
                .thenReturn(Arrays.asList(mushroom1,mushroom2));

        List<Mushroom> loadedMushrooms = mushroomService.
                findByDate(new Date(16,5,2016),new Date(18,5,2016));
        assertThat(loadedMushrooms).isNotNull().isNotEmpty()
                .hasSize(2).containsOnly(mushroom1,mushroom2);
    }

    private void prepareMushrooms() {
        mushroom1 = new Mushroom();
        mushroom2 = new Mushroom();
        mushroom3 = new Mushroom();

        mushroom1.setType(MushroomType.EDIBLE);
        mushroom1.setName("Kozak");
        mushroom1.setDescription("Velky Kozak");
        mushroom1.setToDate(new Date(10,5,2116));
        mushroom1.setToDate(new Date(12,5,2016));

        mushroom2.setType(MushroomType.PSYCHEDELIC);
        mushroom2.setId(2L);
        mushroom2.setName("Hubicky");
        mushroom2.setDescription("Dobre hubicky");
        mushroom2.setToDate(new Date(16,5,2116));
        mushroom2.setToDate(new Date(17,5,2016));

        mushroom3.setType(MushroomType.NONEDIBLE);
        mushroom3.setName("Muchtravka");
        mushroom3.setDescription("zla muchtravka");
        mushroom3.setToDate(new Date(23,6,2116));
        mushroom3.setToDate(new Date(24,6,2016));
    }


}
