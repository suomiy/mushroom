package cz.fi.muni.pa165.service.facade;

import cz.fi.muni.pa165.dto.ForestDTO;
import cz.fi.muni.pa165.entity.Forest;
import cz.fi.muni.pa165.facade.ForestFacade;
import cz.fi.muni.pa165.service.ForestService;
import cz.fi.muni.pa165.service.config.ServiceConfig;
import cz.fi.muni.pa165.service.mappers.ForestMapperService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

/**
 * Created by Erik Macej 433744 , on 26.11.16.
 *
 * @author Erik Macej 433744
 */
@ContextConfiguration(classes = ServiceConfig.class)
public class ForestFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private ForestMapperService mapperService;

    @Mock
    private ForestService forestService;

    @InjectMocks
    private ForestFacade forestFacade = new ForestFacadeImpl();

    private Forest forest1;
    private Forest forest2;
    private ForestDTO forestDto1;
    private ForestDTO forestDto2;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void prepareData() {
        prepareForests();
        prepareDtos();
        prepareMocks();
    }

    @Test
    private void crateForestTest() {
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Forest entity = (Forest) invocationOnMock.getArguments()[0];
                entity.setId(1L);
                return null;
            }
        }).when(forestService).create(forest2);

        forestFacade.create(forestDto2);
        assertThat(forest2.getId()).isNotNull().isEqualTo(1L);
    }

    @Test
    private void updateForestTest() {

        when(forestService.update(forest1)).thenReturn(forest1);
        ForestDTO loadedForest = forestFacade.update(forestDto1);
        assertThat(loadedForest).isNotNull().isEqualToComparingFieldByField(forestDto1);
    }

    @Test
    public void findForestByIdTest() {

        when(forestService.findById(1L)).thenReturn(forest1);

        ForestDTO loadedForest = forestFacade.findById(1L);
        assertThat(loadedForest).isNotNull().isEqualToComparingFieldByField(forestDto1);
    }

    @Test
    public void deleteForestTest() {
        when(forestService.findById(1L)).thenReturn(forest1);
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                forestDto1.setName("Entity deleted");
                return null;
            }
        }).when(forestService).delete(forest1);

        forestFacade.delete(forestDto1.getId());
        String result = forestDto1.getName();

        assertThat(result).isNotNull().isEqualTo("Entity deleted");
    }

    @Test
    public void findAllForestsTest() {

        List<Forest> forests = Arrays.asList(forest1,forest2);

        when(forestService.findAll()).thenReturn(forests);

        List<ForestDTO> loadedList = forestFacade.findAll();
        assertThat(loadedList).isNotNull().
                isNotEmpty().hasSize(2).containsOnly(forestDto1,forestDto2);
    }

    @Test
    public void findForestByNameTest() {
        when(forestService.findByName("Dark forest")).thenReturn(forest1);

        ForestDTO loadedForest = forestFacade.findByName("Dark forest");
        assertThat(loadedForest).isNotNull()
                .isEqualToComparingFieldByField(forestDto1);
    }

    private void prepareMocks() {
        when(mapperService.asDto(forest1)).thenReturn(forestDto1);
        when(mapperService.asDto(forest2)).thenReturn(forestDto2);
        when(mapperService.asEntity(forestDto1)).thenReturn(forest1);
        when(mapperService.asEntity(forestDto2)).thenReturn(forest2);
    }

    private void prepareForests() {
        forest1 = new Forest();
        forest2 = new Forest();

        forest1.setId(1L);
        forest1.setName("Dark forest");
        forest1.setLocalityDescription("Very dark forest");

        forest2.setName("White forest");
        forest2.setLocalityDescription("Very white forest");
    }

    private void prepareDtos() {
        forestDto1 = new ForestDTO();
        forestDto2 = new ForestDTO();

        forestDto1.setId(1L);
        forestDto1.setName("Dark forest");
        forestDto1.setLocalityDescription("Very dark forest");

        forestDto2.setName("White forest");
        forestDto2.setLocalityDescription("Very white forest");
    }
}
