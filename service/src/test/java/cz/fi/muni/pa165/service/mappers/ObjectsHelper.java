package cz.fi.muni.pa165.service.mappers;

import cz.fi.muni.pa165.dto.*;
import cz.fi.muni.pa165.entity.*;
import cz.fi.muni.pa165.enums.MushroomType;
import cz.fi.muni.pa165.enums.Rank;
import cz.fi.muni.pa165.enums.Role;

import java.util.*;

/**
 * @author Filip Krepinsky (410022) on 11/25/16
 */

public class ObjectsHelper {
    private static Calendar someTime = Calendar.getInstance();

    public static Hunter getEmptyHunterEntity() {
        Hunter hunter = new Hunter();
        hunter.setId(10L);

        return hunter;
    }

    public static HunterDTO getEmptyHunterDTO() {
        HunterDTO hunter = new HunterDTO();
        hunter.setId(10L);

        return hunter;
    }

    public static Visit getEmptyVisitEntity() {
        Visit visit = new Visit();
        visit.setId(5L);

        return visit;
    }

    public static VisitDTO getEmptyVisitDTO() {
        VisitDTO visit = new VisitDTO();
        visit.setId(5L);

        return visit;
    }

    public static Hunter getHunterEntity() {
        Hunter hunter = getEmptyHunterEntity();
        hunter.setId(1L);
        hunter.setFirstName("Samuel");
        hunter.setSurname("Hunter");
        hunter.setNick("Samo");
        hunter.setEmail("samo@hunter.cz");
        hunter.setPasswordHash("123");
        hunter.setType(Role.USER);
        hunter.setRank(Rank.GURU);
        hunter.addVisit(getVisitEntity());
        hunter.addVisit(getEmptyVisitEntity());
        return hunter;
    }

    public static HunterDTO getHunterDTO() {
        HunterDTO hunter = getEmptyHunterDTO();
        hunter.setId(1L);
        hunter.setFirstName("Samuel");
        hunter.setSurname("Hunter");
        hunter.setNick("Samo");
        hunter.setEmail("samo@hunter.cz");
        hunter.setType(Role.USER);
        hunter.setRank(Rank.GURU);
        ArrayList<VisitDTO> visitDTOs = new ArrayList<>(1);
        visitDTOs.add(getVisitDTO());
        visitDTOs.add(getEmptyVisitDTO());
        hunter.setVisits(visitDTOs);

        return hunter;
    }

    public static Visit getVisitEntity() {
        Visit visit = getEmptyVisitEntity();
        visit.setId(1L);
        visit.setHunter(getEmptyHunterEntity());
        visit.setForest(getForestEntity());
        visit.setNote("Nice visit");
        visit.setFromDate(getFrom());
        visit.setToDate(getTo());
        SortedSet<MushroomCount> mushroomCounts = new TreeSet<>();
        mushroomCounts.add(getMushroomCountEntity());
        visit.setMushroomsCount(mushroomCounts);

        return visit;
    }

    public static VisitDTO getVisitDTO() {
        VisitDTO visit = getEmptyVisitDTO();
        visit.setId(1L);
        visit.setHunterId(getEmptyHunterDTO().getId());
        visit.setForest(getForestDTO());
        visit.setNote("Nice visit");
        visit.setFromDate(getFrom());
        visit.setToDate(getTo());
        ArrayList<MushroomCountDTO> mushroomCounts = new ArrayList<>(1);
        mushroomCounts.add(getMushroomCountDTO());
        visit.setMushroomsCount(mushroomCounts);

        return visit;
    }

    public static ForestDTO getForestDTO() {
        ForestDTO forest = new ForestDTO();
        forest.setId(1L);
        forest.setName("Dark forest");
        forest.setLocalityDescription("very dark forest");

        return forest;
    }

    public static Forest getForestEntity() {
        Forest forest = new Forest();
        forest.setId(1L);
        forest.setName("Dark forest");
        forest.setLocalityDescription("very dark forest");

        return forest;
    }

    public static MushroomCount getEmptyMushroomCountEntity() {
        MushroomCount mushroomCount = new MushroomCount();
        mushroomCount.setId(10L);

        return mushroomCount;
    }

    public static MushroomCountDTO getEmptyMushroomCountDTO() {
        MushroomCountDTO mushroomCountDTO = new MushroomCountDTO();
        mushroomCountDTO.setId(10L);

        return mushroomCountDTO;
    }

    public static MushroomCount getMushroomCountEntity() {
        MushroomCount entity = new MushroomCount();
        entity.setId(1L);
        entity.setCount(5);

        Mushroom mushroom = getMushroomEntity();
        Visit visit = getEmptyVisitEntity();

        entity.setMushroom(mushroom);
        entity.setVisit(visit);

        return entity;
    }

    public static MushroomCountDTO getMushroomCountDTO() {
        MushroomCountDTO dto = new MushroomCountDTO();
        dto.setId(1L);
        dto.setCount(5);

        MushroomDTO mushroomDTO = getMushroomDTO();
        VisitDTO visitDTO = getEmptyVisitDTO();

        dto.setMushroom(mushroomDTO);
        dto.setVisitId(visitDTO.getId());

        return dto;
    }

    public static Mushroom getEmptyMushroomEntity() {
        Mushroom mushroom = new Mushroom();
        mushroom.setId(10L);

        return mushroom;
    }

    public static MushroomDTO getEmptyMushroomDTO() {
        MushroomDTO mushroomDTO = new MushroomDTO();
        mushroomDTO.setId(10L);

        return mushroomDTO;
    }

    public static Mushroom getMushroomEntity() {
        Mushroom entity = new Mushroom();
        entity.setId(4L);
        entity.setName("Mochomurka");
        entity.setType(MushroomType.POISONOUS);
        entity.setDescription("This shroom is red and pretty.");
        entity.setFromDate(getFrom());
        entity.setToDate(getTo());

        return entity;
    }

    public static MushroomDTO getMushroomDTO() {
        MushroomDTO dto = new MushroomDTO();
        dto.setName("Mochomurka");
        dto.setId(4L);
        dto.setType(MushroomType.POISONOUS);
        dto.setDescription("This shroom is red and pretty.");
        dto.setFromDate(getFrom());
        dto.setToDate(getTo());

        return dto;
    }

    public static Date getFrom() {
        Calendar from = (Calendar) someTime.clone();
        from.set(2016, Calendar.FEBRUARY, 1);
        return from.getTime();
    }

    public static DateDTO getFromDTO() {
        DateDTO dateDTO = new DateDTO();
        dateDTO.setDate(getFrom());
        return dateDTO;
    }

    public static Date getTo() {
        Calendar to = (Calendar) someTime.clone();
        to.set(2016, Calendar.NOVEMBER, 30);
        return to.getTime();
    }

    public static DateDTO getToDTO() {
        DateDTO dateDTO = new DateDTO();
        dateDTO.setDate(getTo());
        return dateDTO;
    }

    public static DateIntervalDTO getIntervalDTO() {
        DateIntervalDTO dateDTO = new DateIntervalDTO();
        dateDTO.setFrom(getFrom());
        dateDTO.setTo(getTo());
        return dateDTO;
    }

    public static Date buildDate(int day, int month, int year) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.YEAR, year);
        return c.getTime();
    }
}
