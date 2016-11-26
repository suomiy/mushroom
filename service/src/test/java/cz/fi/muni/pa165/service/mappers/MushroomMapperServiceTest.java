package cz.fi.muni.pa165.service.mappers;

import cz.fi.muni.pa165.dto.MushroomDTO;
import cz.fi.muni.pa165.entity.Mushroom;
import cz.fi.muni.pa165.service.config.ServiceConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.util.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Krepinsky (410022) on 11/25/16
 */
@ContextConfiguration(classes = ServiceConfig.class)
public class MushroomMapperServiceTest extends AbstractTestNGSpringContextTests {

    @Inject
    MushroomMapperService mushroomMapperService;

    private Mushroom entity;
    private MushroomDTO dto;

    @BeforeClass
    public void prepareData() {
        entity = ObjectsHelper.getMushroomEntity();
        dto = ObjectsHelper.getMushroomDTO();
    }

    @Test
    public void mapDto() {
        MushroomDTO result = mushroomMapperService.asDto(entity);
        TestHelper.NewDTOEquals(dto, result);
    }

    @Test
    public void mapNullDto() {
        Assert.isNull(mushroomMapperService.asDto(null));
    }

    @Test
    public void mapEntity() {
        Mushroom result = mushroomMapperService.asEntity(dto);
        assertThat(result).isEqualToComparingFieldByField(entity);
    }

    @Test
    public void mapNullEntity() {
        Assert.isNull(mushroomMapperService.asEntity(null));
    }
}
