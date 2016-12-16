package cz.fi.muni.pa165.rest.security;

import cz.fi.muni.pa165.dto.HunterDTO;
import cz.fi.muni.pa165.dto.VisitDTO;
import cz.fi.muni.pa165.entity.Hunter;
import cz.fi.muni.pa165.entity.MushroomCount;
import cz.fi.muni.pa165.entity.Visit;
import cz.fi.muni.pa165.enums.Role;
import cz.fi.muni.pa165.exception.HunterAuthenticationException;

/**
 * @author Filip Krepinsky (410022) on 12/16/16
 */

public class ResourceAccess {
    private static final String ACCESS_DENIED = "Access is denied";

    public static void verify(Hunter loggedHunter, HunterDTO hunterDTO) {
        verifyHunterId(loggedHunter, hunterDTO == null ? null : hunterDTO.getId());
    }

    public static void verify(Hunter loggedHunter, VisitDTO visitDTO) {
        verifyHunterId(loggedHunter, visitDTO == null ? null : visitDTO.getHunterId());
    }

    public static void verify(Hunter loggedHunter, MushroomCount count) {
        verify(loggedHunter, count == null ? null : count.getVisit());
    }

    public static void verify(Hunter loggedHunter, Visit visit) {
        verify(loggedHunter, visit == null ? null : visit.getHunter());
    }

    public static void verify(Hunter loggedHunter, Hunter hunter) {
        verifyHunterId(loggedHunter, hunter == null ? null : hunter.getId());
    }

    public static void verifyHunterId(Hunter loggedHunter, Long id) {
        if (loggedHunter == null ||
                (loggedHunter.getType() != Role.ADMIN && !areEqual(loggedHunter.getId(), id))) {
            throw new HunterAuthenticationException(ACCESS_DENIED);
        }
    }

    private static boolean areEqual(Long first, Long second) {
        return first != null && first.equals(second);
    }
}
