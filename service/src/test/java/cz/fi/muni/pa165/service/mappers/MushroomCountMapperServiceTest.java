package cz.fi.muni.pa165.service.mappers;

import cz.fi.muni.pa165.dto.MushroomCountDTO;
import cz.fi.muni.pa165.entity.MushroomCount;
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
public class MushroomCountMapperServiceTest extends AbstractTestNGSpringContextTests {

    @Inject
    MushroomCountMapperService mushroomCountMapperService;

    private MushroomCountDTO dto;
    private MushroomCount entity;

    @BeforeClass
    public void prepareData() {
        entity = ObjectsHelper.getMushroomCountEntity();
        dto = ObjectsHelper.getMushroomCountDTO();
    }

    @Test
    public void mapDto() {
        MushroomCountDTO result = mushroomCountMapperService.asDto(entity);
        assertThat(result).isEqualToComparingFieldByField(dto);
    }

    @Test
    public void mapNullDto() {
        Assert.isNull(mushroomCountMapperService.asDto(null));
    }

    @Test
    public void mapEntity() {
        MushroomCount result = mushroomCountMapperService.asEntity(dto);
        assertThat(result).isEqualToComparingFieldByField(entity);
    }

    @Test
    public void mapNullEntity() {
        Assert.isNull(mushroomCountMapperService.asEntity(null));
    }
}
