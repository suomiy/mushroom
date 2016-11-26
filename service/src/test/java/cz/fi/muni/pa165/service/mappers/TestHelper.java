package cz.fi.muni.pa165.service.mappers;

import cz.fi.muni.pa165.dto.HunterDTO;
import cz.fi.muni.pa165.dto.MushroomCountDTO;
import cz.fi.muni.pa165.dto.MushroomDTO;
import cz.fi.muni.pa165.dto.VisitDTO;
import cz.fi.muni.pa165.entity.Hunter;
import cz.fi.muni.pa165.utils.DateIntervalUtils;
import org.testng.Assert;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * @author Filip Krepinsky (410022) on 11/25/16
 */

public class TestHelper {
    static void TestNewDateDTOEquals(Date expectedFrom, Date expectedTo, Date actualFrom, Date actualTo) {
        Date from = DateIntervalUtils.makeIntervalFromDate(expectedFrom);
        Date to = DateIntervalUtils.makeIntervalToDate(from, expectedTo);
        Assert.assertEquals(from, actualFrom);
        Assert.assertEquals(to, actualTo);
    }

    public static void NewDTOEquals(MushroomDTO expected, MushroomDTO actual) {
        assertThat(actual).isEqualToComparingOnlyGivenFields(expected, "id", "name", "type", "description");

        TestNewDateDTOEquals(expected.getFromDate(), expected.getToDate(),
                actual.getFromDate(), actual.getToDate());
    }

    public static void NewDTOEquals(MushroomCountDTO expected, MushroomCountDTO actual) {
        assertThat(actual).isEqualToComparingOnlyGivenFields(expected, "id", "visitId", "count");
        NewDTOEquals(expected.getMushroom(), actual.getMushroom());
    }

    public static void NewDTOEquals(VisitDTO expected, VisitDTO actual) {
        assertThat(actual).isEqualToComparingOnlyGivenFields(expected, "id", "hunterId", "forest",
                "note", "fromDate", "toDate");
        for (MushroomCountDTO count : actual.getMushroomsCount()) {
            int index = expected.getMushroomsCount().indexOf(count);
            if (index < 0) {
                fail("getMushroomsCount not valid");
            }
            NewDTOEquals(count, expected.getMushroomsCount().get(index));
        }
    }

    public static void NewEntityEquals(Hunter expected, Hunter actual) {
        assertThat(actual).isEqualToComparingOnlyGivenFields(expected, "id", "firstName", "surname",
                "nick", "email", "type", "rank", "visits");
    }

    public static void updatedEntityEquals(HunterDTO expected, HunterDTO actual) {
        assertThat(actual).isEqualToComparingOnlyGivenFields(expected, "id", "firstName", "surname",
                "nick", "email", "type", "rank", "visits");
    }
}
