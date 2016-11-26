package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.ForestDao;
import cz.fi.muni.pa165.entity.Forest;
import cz.fi.muni.pa165.service.config.ServiceConfig;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Created by Erik Macej 433744 , on 25.11.16.
 *
 * @author Erik Macej 433744
 */
@ContextConfiguration(classes = ServiceConfig.class)
public class ForestServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private ForestDao forestDao;

    @Autowired
    @InjectMocks
    private ForestService forestService;

    private Forest forest1;
    private Forest forest2;
    private Forest forest3;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void prepareData() {
        prepareForests();
    }

    @Test
    public void createForestTest() {
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Forest entity = (Forest) invocationOnMock.getArguments()[0];
                entity.setId(1L);
                return null;
            }
        }).when(forestDao).create(forest2);

        forestService.create(forest2);
        assertThat(forest2.getId()).isNotNull().isEqualTo(1L);
    }

    @Test
    public void updateForestTest() {
        forest2.setId(forest1.getId());

        when(forestDao.update(forest2)).thenReturn(forest2);
        Forest loadedForest = forestService.update(forest2);
        assertThat(loadedForest).isNotNull().isEqualToComparingFieldByField(forest2);
    }

    @Test
    public void findForestByIdTest() {
        when(forestDao.findById(1L)).thenReturn(forest1);

        Forest loadedForest = forestService.findById(1L);
        assertThat(loadedForest).isNotNull().isEqualToComparingFieldByField(forest1);
    }

    @Test
    public void deleteForestTest() {
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Forest entity = (Forest) invocationOnMock.getArguments()[0];
                entity.setName("Entity deleted");
                return null;
            }
        }).when(forestDao).delete(forest1);

        forestService.delete(forest1);
        String result = forest1.getName();

        assertThat(result).isNotNull().isEqualTo("Entity deleted");
    }

    @Test
    public void findAllForestsTest() {
        forest2.setId(2L);
        forest3.setId(3L);

        when(forestDao.findAll()).thenReturn(Arrays.asList(forest1, forest2, forest3));

        List<Forest> loadedList = forestService.findAll();
        assertThat(loadedList).isNotNull().
                isNotEmpty().hasSize(3).containsOnly(forest1, forest2, forest3);
    }

    @Test
    public void findForestByNameTest() {
        when(forestDao.findByName("Dark forest")).thenReturn(forest1);

        Forest loadedForest = forestService.findByName("Dark forest");
        assertThat(loadedForest).isNotNull().isEqualToComparingFieldByField(forest1);
    }

    private void prepareForests() {
        forest1 = new Forest();
        forest2 = new Forest();
        forest3 = new Forest();

        forest1.setId(1L);
        forest1.setName("Dark forest");
        forest1.setLocalityDescription("Very dark forest");

        forest2.setName("White forest");
        forest2.setLocalityDescription("Very white forest");

        forest3.setName("Green forest");
        forest3.setLocalityDescription("Very green forest");
    }
}
