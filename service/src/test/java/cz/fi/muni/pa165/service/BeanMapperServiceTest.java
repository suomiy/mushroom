package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dto.*;
import cz.fi.muni.pa165.entity.*;
import cz.fi.muni.pa165.enums.Rank;
import cz.fi.muni.pa165.enums.Role;
import cz.fi.muni.pa165.service.config.ServiceConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.*;

import javax.inject.Inject;
import java.util.Date;

/**
 * Created by Erik Macej 433744 , on 21.11.16.
 *
 * @author Erik Macej 433744
 */
@ContextConfiguration(classes = ServiceConfig.class)
public class BeanMapperServiceTest extends AbstractTestNGSpringContextTests {

    @Inject
    BeanMapperService beanMapperService;

    private Visit visit1;
    private Visit visit2;
    private Hunter hunter1;
    private Hunter hunter2;
    private Forest forest;
    private Mushroom mushroom;
    private MushroomCount mushroomCount;

    private VisitDTO visitDto1;
    private VisitDTO visitDto2;
    private HunterDTO hunterDto1;
    private HunterDTO hunterDto2;
    private ForestDTO forestDto;
    private MushroomDTO mushroomDto;
    private MushroomCountDTO mushroomCountDto;

    @BeforeClass
    public void prepareData(){
        createMushroom();
        createForest();
        createVisits();
        createMushroomCount();
        createHunters();

        createMushroomDtos();
        createForestDtos();
        createVisitDtos();
        createMushroomCountDtos();
        createHunterDtos();

    }

    @Test
    public void mapHunterToDtoTest() {
        HunterDTO mappedDto = beanMapperService.mapTo(hunter1,HunterDTO.class);
        assertThat(mappedDto).isEqualToComparingFieldByField(hunterDto1);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void mapNullHunterToDtoTest() {
        HunterDTO mappedDto = beanMapperService.mapTo(hunter1,HunterDTO.class);
    }

    @Test
    public void mapDtoToHunterTest(){
        Hunter mappedHunter = beanMapperService.mapTo(hunterDto1,Hunter.class);
        hunter1.setPasswordHash(null);
        assertThat(mappedHunter).isEqualToComparingFieldByField(hunter1);
    }

    @Test
    public void mapDtoToVisit() {
        Visit mappedVisit = beanMapperService.mapTo(visitDto1,Visit.class);
        assertThat(mappedVisit).isEqualToComparingFieldByField(visit1);
    }

    @Test
    public void mapForestToDtoTest() {
        ForestDTO mappedDto = beanMapperService.mapTo(forest,ForestDTO.class);
        assertThat(mappedDto).isEqualToComparingFieldByField(forestDto);
    }

    @Test
    public void mapDtoToForestTest() {
        ForestDTO mappedDto = beanMapperService.mapTo(forest,ForestDTO.class);
        assertThat(mappedDto).isEqualToComparingFieldByField(forestDto);
    }

    private void createHunters() {
        hunter1 = new Hunter();
        hunter2 = new Hunter();

        hunter1.setId(1L);
        hunter1.setFirstName("Samuel");
        hunter1.setSurname("Hunter");
        hunter1.setNick("Samo");
        hunter1.setEmail("samo@hunter.cz");
        hunter1.setPasswordHash("123");
        hunter1.setType(Role.USER);
        hunter1.setRank(Rank.GURU);
        hunter1.addVisit(visit1);
        hunter1.addVisit(visit2);

        hunter1.setId(2L);
        hunter1.setFirstName("Miro");
        hunter1.setSurname("Noga");
        hunter1.setNick("noga");
        hunter1.setEmail("noga@hunter.cz");
        hunter1.setPasswordHash("321");
        hunter1.setType(Role.ADMIN);
        hunter1.setRank(Rank.BEGINNER);

    }

    private void createVisits() {
        visit1 = new Visit();
        visit2 = new Visit();

        visit1.setForest(forest);
        visit1.setFromDate(new Date(11,5,2016));
        visit1.setId(1L);
        visit1.setHunter(hunter1);

        visit2.setForest(forest);
        visit2.setFromDate(new Date(13,5,2016));
        visit2.setId(2L);
        visit2.setHunter(hunter1);
    }

    private void createVisitDtos() {
        visitDto1 = new VisitDTO();
        visitDto2 = new VisitDTO();

        visitDto1.setForest(forestDto);
        visitDto1.setDate(new Date(11,5,2016));
        visitDto1.setId(1L);
        visitDto1.setHunter(hunterDto1);

        visitDto2.setForest(forestDto);
        visitDto2.setDate(new Date(13,5,2016));
        visitDto2.setId(2L);
        visitDto2.setHunter(hunterDto2);
    }


    private void createHunterDtos(){
        hunterDto1 = new HunterDTO();
        hunterDto2 = new HunterDTO();

        hunterDto1.setId(hunter1.getId());
        hunterDto1.setFirstName(hunter1.getFirstName());
        hunterDto1.setSurname(hunter1.getSurname());
        hunterDto1.setNick(hunter1.getNick());
        hunterDto1.setEmail(hunter1.getEmail());
        hunterDto1.setType(hunter1.getType());
        hunterDto1.setRank(hunter1.getRank());
        hunterDto1.addVisit(visitDto1);
        hunterDto1.addVisit(visitDto2);

        hunterDto2.setId(hunter2.getId());
        hunterDto2.setFirstName(hunter2.getFirstName());
        hunterDto2.setSurname(hunter2.getSurname());
        hunterDto2.setNick(hunter2.getNick());
        hunterDto2.setEmail(hunter2.getEmail());
        hunterDto2.setType(hunter2.getType());
        hunterDto2.setRank(hunter2.getRank());
    }

    private void createForestDtos() {
        forestDto = new ForestDTO();
        forestDto.setId(forest.getId());
        forestDto.setLocalityDescription(forest.getLocalityDescription());
        forestDto.setName(forest.getName());
    }

    private void createForest(){
        forest = new Forest();
        forest.setId(1L);
        forest.setName("Dark forest");
        forest.setLocalityDescription("very dark forest");
    }

    private void createMushroomCount() {
           mushroomCount = new MushroomCount();
           mushroomCount.setId(1L);
           mushroomCount.setVisit(visit1);
           mushroomCount.setMushroom(mushroom);
           mushroomCount.setCount(5);
    }

    private void createMushroom() {
        mushroom = new Mushroom();
    }

    private void createMushroomDtos(){
        mushroomDto = new MushroomDTO();
    }

    private void createMushroomCountDtos(){
        mushroomCountDto = new MushroomCountDTO();
        mushroomCountDto.setId(mushroomCount.getId());
        mushroomCountDto.setVisit(visitDto1);
        mushroomCountDto.setMushroom(mushroomDto);
        mushroomCountDto.setCount(mushroomCount.getCount());

    }
}
