package cz.fi.muni.pa165.service.mappers;

import cz.fi.muni.pa165.dto.MushroomCountDTO;
import cz.fi.muni.pa165.entity.MushroomCount;
import fr.xebia.extras.selma.Mapper;

/**
 * @author Filip Krepinsky (410022) on 11/25/16
 */
@Mapper(withIgnoreFields = {"visit", "visitId"})
interface MushroomCountMapper extends EntityDTOMapper<MushroomCount, MushroomCountDTO> {
}

