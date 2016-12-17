package cz.fi.muni.pa165.rest.security;

import cz.fi.muni.pa165.dto.HunterDTO;
import cz.fi.muni.pa165.dto.RegistrateHunterDTO;
import cz.fi.muni.pa165.dto.VisitDTO;
import cz.fi.muni.pa165.entity.Hunter;
import cz.fi.muni.pa165.entity.MushroomCount;
import cz.fi.muni.pa165.entity.Visit;
import cz.fi.muni.pa165.enums.Rank;
import cz.fi.muni.pa165.enums.Role;
import cz.fi.muni.pa165.exception.HunterAuthenticationException;

/**
 * @author Filip Krepinsky (410022) on 12/16/16
 */

public class ResourceAccess {
    private static final String ACCESS_DENIED = "Access is denied";
    private static final String ACCESS_DENIED_ATTRIBUTES = "Access is denied. No Permission to change these attributes";

    public static void verify(RegistrateHunterDTO registrateHunterDTO) {
        if (registrateHunterDTO.getRank() != Rank.BEGINNER || registrateHunterDTO.getType() != Role.USER) {
            throw new HunterAuthenticationException(ResourceAccess.ACCESS_DENIED_ATTRIBUTES);
        }
    }

    public static void verify(Hunter loggedHunter, HunterDTO hunterDTO) {
        if (loggedHunter.getType() != Role.ADMIN &&
                (loggedHunter.getRank() != hunterDTO.getRank() || loggedHunter.getType() != hunterDTO.getType())) {
            throw new HunterAuthenticationException(ACCESS_DENIED_ATTRIBUTES);
        }
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
