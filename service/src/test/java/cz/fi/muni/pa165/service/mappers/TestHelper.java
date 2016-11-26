package cz.fi.muni.pa165.service.mappers;

import cz.fi.muni.pa165.dto.MushroomDTO;
import cz.fi.muni.pa165.entity.Hunter;
import cz.fi.muni.pa165.utils.DateIntervalUtils;
import org.testng.Assert;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Krepinsky (410022) on 11/25/16
 */

class TestHelper {
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

    public static void NewEntityEquals(Hunter expected, Hunter actual) {
        assertThat(actual).isEqualToComparingOnlyGivenFields(expected, "id", "firstName", "surname",
                "nick", "email", "type", "rank", "visits");
    }
}
