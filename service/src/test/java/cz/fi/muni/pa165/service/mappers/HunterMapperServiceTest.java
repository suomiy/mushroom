package cz.fi.muni.pa165.service.mappers;

import cz.fi.muni.pa165.dto.HunterDTO;
import cz.fi.muni.pa165.entity.Hunter;
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
public class HunterMapperServiceTest extends AbstractTestNGSpringContextTests {

    @Inject
    HunterMapperService hunterMapperService;

    private Hunter entity;
    private HunterDTO dto;

    @BeforeClass
    public void prepareData() {
        entity = ObjectsHelper.getHunterEntity();
        dto = ObjectsHelper.getHunterDTO();
    }

    @Test
    public void mapDto() {
        HunterDTO result = hunterMapperService.asDto(entity);
        assertThat(result).isEqualToComparingFieldByField(dto);
    }

    @Test
    public void mapNullDto() {
        Assert.isNull(hunterMapperService.asDto(null));
    }

    @Test
    public void mapEntity() {
        Hunter result = hunterMapperService.asEntity(dto);
        TestHelper.NewEntityEquals(result, entity);
    }

    @Test
    public void mapNullEntity() {
        Assert.isNull(hunterMapperService.asEntity(null));
    }
}
