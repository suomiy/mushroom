package cz.fi.muni.pa165.service.mappers;

import cz.fi.muni.pa165.dto.ForestDTO;
import cz.fi.muni.pa165.entity.Forest;
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
public class ForestMapperServiceTest extends AbstractTestNGSpringContextTests {

    @Inject
    ForestMapperService mushroomMapperService;

    private Forest entity;
    private ForestDTO dto;

    @BeforeClass
    public void prepareData() {
        entity = ObjectsHelper.getForestEntity();
        dto = ObjectsHelper.getForestDTO();
    }

    @Test
    public void mapDto() {
        ForestDTO result = mushroomMapperService.asDto(entity);
        assertThat(result).isEqualToComparingFieldByField(dto);
    }

    @Test
    public void mapNullDto() {
        Assert.isNull(mushroomMapperService.asDto(null));
    }

    @Test
    public void mapEntity() {
        Forest result = mushroomMapperService.asEntity(dto);
        assertThat(result).isEqualToComparingFieldByField(entity);
    }

    @Test
    public void mapNullEntity() {
        Assert.isNull(mushroomMapperService.asEntity(null));
    }
}
