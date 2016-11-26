package cz.fi.muni.pa165.service.mappers;

import cz.fi.muni.pa165.dto.VisitDTO;
import cz.fi.muni.pa165.entity.Visit;
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
public class VisitMapperServiceTest extends AbstractTestNGSpringContextTests {

    @Inject
    VisitMapperService visitMapperService;

    private Visit entity;
    private VisitDTO dto;

    @BeforeClass
    public void prepareData() {
        entity = ObjectsHelper.getVisitEntity();
        dto = ObjectsHelper.getVisitEntityDTO();
    }

    @Test
    public void mapDto() {
        VisitDTO result = visitMapperService.asDto(entity);
        assertThat(result).isEqualToComparingFieldByField(dto);
    }

    @Test
    public void mapNullDto() {
        Assert.isNull(visitMapperService.asDto(null));
    }

    @Test
    public void mapEntity() {
        Visit result = visitMapperService.asEntity(dto);
        assertThat(result).isEqualToComparingFieldByField(entity);
    }

    @Test
    public void mapNullEntity() {
        Assert.isNull(visitMapperService.asEntity(null));
    }
}
