package cz.fi.muni.pa165.service.mappers;

import cz.fi.muni.pa165.dto.RegistrateHunterDTO;
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
 * Created by Erik Macej 433744 , on 10.12.16.
 *
 * @author Erik Macej 433744
 */

@ContextConfiguration(classes = ServiceConfig.class)
public class RegistrateHunterMapperTest extends AbstractTestNGSpringContextTests {

    @Inject
    RegistrateHunterMapperService registrateHunterMapperService;

    private Hunter entity;
    private Hunter entityWithVisit;
    private RegistrateHunterDTO dto;

    @BeforeClass
    public void prepareData() {
        entity = ObjectsHelper.getRegistrateHunterEntity();
        dto = ObjectsHelper.getRegistrateHunterDTO();
        entityWithVisit = ObjectsHelper.getRegistrateHunterEntity();
        entityWithVisit.addVisit(ObjectsHelper.getVisitEntity());
    }

    @Test
    public void mapDto() {
        RegistrateHunterDTO result = registrateHunterMapperService.asDto(entity);
        result.setPassword(dto.getPassword());
        assertThat(result).isEqualToComparingFieldByField(dto);
    }

    @Test
    public void mapNullDto() {
        Assert.isNull(registrateHunterMapperService.asDto(null));
    }

    @Test
    public void mapEntity() {
        Hunter result = registrateHunterMapperService.asEntity(dto);
        TestHelper.NewEntityEquals(result, entity);
    }

    @Test
    public void mapNullEntity() {
        Assert.isNull(registrateHunterMapperService.asEntity(null));
    }


}
